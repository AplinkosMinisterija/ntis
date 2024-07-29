package lt.project.ntis.app.job.executor.impl;

import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;

import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.tools.NotificationManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNotificationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprNotificationsDBServiceImpl;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.NtisNotificationsManager;
import lt.project.ntis.logic.forms.processRequests.ContractProcessRequest;

@Service("CONTRACT_EXPIRATION_EMAIL_EXECUTOR")
public class ContractExpirationEmailExecutor extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ContractExpirationEmailExecutor.class);

    @Autowired
    NtisNotificationsManager ntisNotificationsManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    SprJobDefinitionsDBService sprJobDefinitionDBService;

    @Autowired
    ContractProcessRequest contractProcessRequest;

    @Autowired
    SprNotificationsDBServiceImpl notificationsDBService;

    @Autowired
    SprUsersDBService usersDBService;

    @Autowired
    SprTemplatesDBService sprTemplatesDBService;

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    SprAuthorization<?> sprAuthorization;

    @Value("${app.default.language}")
    private String defaultLanguage;

    @Value("${app.host}")
    private String appHostUrl;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("Execute contract expiration email notification start");
        SprJobDefinitionsDAO jobDefinition = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
        Double days = Utils.getDouble(jobDefinition.getJde_execution_parameter());
        StatementAndParams stmt = new StatementAndParams("""
                select
                    cot_client_email,
                    cot_id,
                    o.org_id,
                    coalesce (ou.ou_usr_id, pu.usr_id) as usr_id,
                    cot_to_date::date-current_date::date as remaining_days
                from ntis.ntis_contracts
                left join spark.spr_organizations o on cot_org_id = org_id
                left join spark.spr_org_users ou on o.org_id = ou.ou_org_id and current_date between ou_date_from and coalesce(ou_date_to, current_date)
                left join spark.spr_users u on ou.ou_usr_id = u.usr_id
                left join spark.spr_persons p on cot_per_id = p.per_id
                left join spark.spr_users pu on pu.usr_per_id = p.per_id
                where cot_to_date::date-current_date::date between 1 and ?
                               """);
        stmt.addSelectParam(days);
        List<HashMap<String, String>> users = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        Double cotId = null;
        boolean emailSent = false;
        List<String> cotEmails = new ArrayList<>();
        for (HashMap<String, String> user : users) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("homeUrl", this.appHostUrl);
            params.put("contractId", user.get("cot_id"));
            params.put("contractUrl", user.get("cot_id"));
            params.put("remainingDays", user.get("remaining_days"));
            cotId = Utils.getDouble(user.get("cot_id"));
            if (!emailSent) {
                cotEmails = Arrays.asList(user.get("cot_client_email").split("\\s*,\\s*"));
                for (String email : cotEmails) {
                    this.contractProcessRequest.createContractExpirationJobRequest(conn, null, cotId, email, Languages.LT, params);
                }
                emailSent = true;
            }
            this.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF", "COT_EXPIRATION_SUBJECT",
                    "COT_EXPIRATION_BODY", params, NtisNtfRefType.CONTRACT.getCode(), NtisMessageSubject.MSG_SBJ_AGREEMENT_EXPIRATION.getCode(), null,
                    Utils.getDouble(user.get("usr_id")), user.get("org_id") != null ? Utils.getDouble(user.get("org_id")) : null, null);
        }
        log.info("Execute contract expiration email notification end");
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
