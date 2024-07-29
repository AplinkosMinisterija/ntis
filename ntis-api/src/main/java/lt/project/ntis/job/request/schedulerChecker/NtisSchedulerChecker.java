package lt.project.ntis.job.request.schedulerChecker;

import java.io.StringWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.tools.NotificationManager;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNotificationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprNotificationsDBServiceImpl;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.processRequests.SchedulerFailedProcessRequest;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;

@Service("NTIS_SCHEDULER_CHECKER")
public class NtisSchedulerChecker extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(NtisSchedulerChecker.class);

    @Value("${app.default.language}")
    private String defaultLanguage;

    @Autowired
    SprJobDefinitionsDBService sprJobDefinitionsDBService;

    @Autowired
    SprJobRequestsDBService sprJobRequestsDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    SprUsersDBService usersDBService;

    @Autowired
    SprTemplatesDBService sprTemplatesDBService;

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    SprAuthorization<?> sprAuthorization;

    @Autowired
    SprNotificationsDBServiceImpl notificationsDBService;

    @Autowired
    SchedulerFailedProcessRequest schedulerFailedProcessRequest;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.debug("Collecting scheduler jobs that have to be checked");
        List<SprJobDefinitionsDAO> definitionsDAO = sprJobDefinitionsDBService.loadRecordsByParams(conn,
                "jde_type = ? AND JDE_CODE <> 'NTIS_SCHEDULER_CHECKER'", new SelectParamValue("SCHEDULER"));
        if (definitionsDAO != null && !definitionsDAO.isEmpty()) {
            log.debug("Iterating through job definitions");
            for (SprJobDefinitionsDAO definitionDAO : definitionsDAO) {
                Double limitation = 1.0;
                limitation = definitionDAO.getN01() == null ? limitation : definitionDAO.getN01();
                log.debug("Collecting last job requests for job definition...");
                List<SprJobRequestsDAO> jobRequests = sprJobRequestsDBService.loadRecordsByParams(conn,
                        " jrq_jde_id = ?::int order by jrq_end_date desc nulls first limit ?::int ", new SelectParamValue(definitionDAO.getJde_id()),
                        new SelectParamValue(limitation));
                if (jobRequests != null && !jobRequests.isEmpty()) {
                    boolean hasCompleted = false;
                    if (jobRequests.get(0).getJrq_status().equalsIgnoreCase("ERROR")) {
                        log.debug("Checking if most recent job request completed with error and if so, iterating through job requests");
                        for (SprJobRequestsDAO jobRequestDAO : jobRequests) {
                            log.debug("Checking if the collected array has requests that were completed successfully");
                            if (jobRequestDAO.getJrq_status().equalsIgnoreCase("COMPLETED")) {
                                hasCompleted = true;
                            }
                        }
                        if (hasCompleted) {
                            log.debug(
                                    "If collected array had successfully completed job requests, setting next action time to be n minutes later as defined in job definition");
                            if (definitionDAO.getN02() != null) {
                                Date targetTime = DateUtils.addMinutes(definitionDAO.getJde_last_action_time(), definitionDAO.getN02().intValue());
                                definitionDAO.setJde_next_action_time(targetTime);
                                sprJobDefinitionsDBService.saveRecord(conn, definitionDAO);
                            }
                        } else {
                            StatementAndParams stmt = new StatementAndParams();
                            stmt.setStatement("""
                                    select usr_id,
                                           usr_email,
                                           usr_username,
                                           usr_language,
                                           per.c01,
                                           null as n01
                                    from spr_user_roles
                                    inner join spr_users on uro_usr_id = usr_id
                                    inner join spr_persons per on per_id = usr_per_id
                                    where uro_rol_id in
                                        (select rol_id from spr_roles where rol_code = 'NTIS_ADMIN')
                                      and current_date between uro_date_from and coalesce(uro_date_to, now())
                                      and usr_id not in (
                                          select ntf_usr_id from spr_notifications ntf
                                       left join spr_job_definitions on ntf_reference = jde_id
                                           where ntf.c01 = 'SCHEDULER'
                                             and  ntf.c02 = 'MSG_SBJ_SCHEDULER_FAILED'
                                             and jde_type = 'SCHEDULER'
                                             and jde_id = ?::int
                                             and (extract(epoch from (now() - ntf_creation_date))::integer)/3600 < 24
                                             and ntf_org_id is null
                                        group by ntf_usr_id
                                      )
                                    UNION
                                    select usr_id,
                                           usr_email,
                                           usr_username,
                                           usr_language,
                                           per.c01,
                                           ou_org_id as n01
                                    from spr_org_user_roles
                                    inner join spr_org_users ou on our_ou_id = ou_id
                                    inner join spr_users on usr_id = ou_usr_id
                                    inner join spr_persons per on usr_per_id = per_id
                                    where our_rol_id in
                                         (select rol_id from spr_roles where rol_code = 'NTIS_ADMIN')
                                     and current_date between our_date_from and coalesce(our_date_to, now())
                                     and usr_id not in (
                                          select ntf_usr_id from spr_notifications ntf
                                       left join spr_job_definitions on ntf_reference = jde_id
                                           where ntf.c01 = 'SCHEDULER'
                                             and  ntf.c02 = 'MSG_SBJ_SCHEDULER_FAILED'
                                             and jde_type = 'SCHEDULER'
                                             and jde_id = ?::int
                                             and (extract(epoch from (now() - ntf_creation_date))::integer)/3600 < 24
                                             and ntf_org_id is not null
                                        group by ntf_usr_id
                                      )
                                                                                                                                   """);
                            stmt.addSelectParam(definitionDAO.getJde_id());
                            stmt.addSelectParam(definitionDAO.getJde_id());
                            List<SprUsersDAO> usersToSend = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
                            if (usersToSend != null && !usersToSend.isEmpty()) {
                                if (definitionDAO.getN02() != null) {
                                    Date targetTime = DateUtils.addMinutes(definitionDAO.getJde_last_action_time(), definitionDAO.getN02().intValue());
                                    definitionDAO.setJde_next_action_time(targetTime);
                                    sprJobDefinitionsDBService.saveRecord(conn, definitionDAO);
                                }
                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("jobName", definitionDAO.getJde_name());
                                params.put("jdeId", definitionDAO.getJde_id().intValue());
                                params.put("jobCode", definitionDAO.getJde_code());
                                for (SprUsersDAO userDAO : usersToSend) {
                                    Double orgId = userDAO.getN01();
                                    this.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, definitionDAO.getJde_id(),
                                            "NTIS_SCHEDULER_FAIL_NOTIF", "SCHEDULER_FAILED_SUBJECT", "SCHEDULER_FAILED_SUBJECT_BODY", params,
                                            NtisNtfRefType.SCHEDULER.getCode(), NtisMessageSubject.MSG_SBJ_SCHEDULER_FAILED.getCode(), new Date(),
                                            userDAO.getUsr_id(), orgId, null);
                                    if (userDAO.getC01() != null && !userDAO.getC01().isBlank() && userDAO.getC01().equalsIgnoreCase(YesNo.Y.getCode())) {
                                        schedulerFailedProcessRequest.createSchedulerFailedRequest(conn, userDAO.getUsr_id(), definitionDAO.getJde_id(),
                                                userDAO.getUsr_email(), Languages.getLanguageByCode(userDAO.getUsr_language()), params);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void saveNotification(Connection conn, String type, Double reference, String templateCode, String templateTitleCode, String templateMessageCode,
            Map<String, Object> params, String refType, String msgSubject, Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        VelocityContext context = new VelocityContext();
        if (params != null) {
            params.forEach((key, value) -> {
                context.put(key, value);
            });
        }
        this.setParams(conn, type, reference, templateCode, templateTitleCode, templateMessageCode, context, refType, msgSubject, dateFrom, userId, orgId,
                roleId);
    }

    private void setParams(Connection conn, String type, Double reference, String templateCode, String templateTitleCode, String templateMessageCode,
            VelocityContext context, String refType, String msgSubject, Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        SprUsersDAO userDAO = usersDBService.loadRecord(conn, userId);
        String language = defaultLanguage;
        if (userDAO.getUsr_language() != null) {
            language = userDAO.getUsr_language();
        }
        String templateSubject = sprTemplatesDBService.getTemplateText(conn, templateCode, templateTitleCode, language);
        String templateText = sprTemplatesDBService.getTemplateText(conn, templateCode, templateMessageCode, language);
        if (context != null) {
            StringWriter title = new StringWriter();
            Velocity.evaluate(context, title, "notificationTemplateProcessing", templateSubject);
            templateSubject = title.toString();
            StringWriter message = new StringWriter();
            Velocity.evaluate(context, message, "notificationTemplateProcessing", templateText);
            templateText = message.toString();
        }
        this.saveNotification(conn, type, reference, templateSubject, templateText, refType, msgSubject, dateFrom, userId, orgId, roleId);
    }

    private void saveNotification(Connection conn, String type, Double reference, String title, String message, String refType, String msgSubject,
            Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        SprNotificationsNtisDAO notificationDAO = (SprNotificationsNtisDAO) notificationsDBService.newRecord();
        notificationDAO.setNtf_type(type);
        notificationDAO.setNtf_reference(reference);
        notificationDAO.setNtf_title(title);
        notificationDAO.setNtf_message(message);
        notificationDAO.setRefType(refType);
        notificationDAO.setMsgSubject(msgSubject);
        notificationDAO.setNtf_usr_id(userId);
        notificationDAO.setNtf_org_id(orgId);
        if (sprAuthorization.isSingleRoleMode()) {
            notificationDAO.setNtf_rol_id(roleId);
        }
        dateFrom = Optional.ofNullable(dateFrom).orElse(new Date());
        notificationDAO.setNtf_date_from(dateFrom);
        notificationDAO.setNtf_creation_date(new Date());
        notificationsDBService.saveRecord(conn, notificationDAO);
        notificationManager.sendNotification(conn, notificationDAO);
    }
}
