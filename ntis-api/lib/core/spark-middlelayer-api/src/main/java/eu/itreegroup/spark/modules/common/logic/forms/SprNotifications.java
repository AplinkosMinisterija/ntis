package eu.itreegroup.spark.modules.common.logic.forms;

import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.tools.NotificationManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.dao.SprNotificationsDAO;
import eu.itreegroup.spark.modules.common.logic.forms.security.SprNotificationsSecurityManager;
import eu.itreegroup.spark.modules.common.rest.model.NotificationRecipient;
import eu.itreegroup.spark.modules.common.rest.model.NotificationRequest;
import eu.itreegroup.spark.modules.common.service.SprNotificationsDBService;

@Component
public class SprNotifications extends FormBase {

    public static final String NOTIFICATION_TYPE_TASK = "TASK";

    public static final String NOTIFICATION_TYPE_MESSAGE = "MESSAGE";

    public static final String NOTIFICATION_TYPE_ALERT = "ALERT";

    private static final Logger log = LoggerFactory.getLogger(SprNotifications.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprNotificationsDBService notificationsDBService;

    @Autowired
    SprUsersDBService usersDBService;

    @Autowired
    SprTemplatesDBService sprTemplatesDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    SprAuthorization<?> sprAuthorization;

    @Value("${app.default.language}")
    private String defaultLanguage;

    @Override
    public String getFormName() {
        return "SPR_NOTIFICATIONS";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark notification manager", "Spark notification manager");
        addFormActionCRUD();

    }

    public String getAllNotifications(Connection conn, SelectRequestParams params, Double userId, Double orgId, Double rolId, String language)
            throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprNotifications.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT n.ntf_id, "//
                + " n.ntf_title, "//
                + " n.ntf_message,  "//
                + " c.rfc_meaning AS ntf_type, "//
                + " case when n.ntf_mark_as_read_date is null then 'Y' else 'N' end AS not_read, "//
                + " TO_CHAR(n.ntf_creation_date, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS ntf_creation_date, "//
                + " n.ntf_usr_id as receiver_usr_id, " //
                + " rp.per_name||' '||rp.per_surname as receiver_full_name, "//
                + " su.usr_id as sender_usr_id, "//
                + " cod.rfc_meaning, "//
                + " CASE WHEN n.rec_userid = 'system' THEN cod.rfc_description ELSE sp.per_name||' '||sp.per_surname END AS sender_full_name " //
                + " FROM spr_notifications n" //
                + " JOIN SPR_REF_CODES_VW C ON c.rfc_code = n.ntf_type AND c.rfc_domain = 'SPR_NOTIF_TYPE' AND c.rft_lang = ? " //
                + " JOIN SPR_REF_CODES cod ON cod.rfc_code = 'SYS' AND c.rft_lang = ? " //
                + " AND n.ntf_creation_date between c.rfc_date_from and COALESCE(c.rfc_date_to," + dbStatementManager.getSysdateCommand() + ") " //
                + " JOIN SPR_USERS ru on n.ntf_usr_id = ru.usr_id  "//
                + " JOIN SPR_PERSONS rp on ru.usr_per_id = rp.per_id  "//
                + " LEFT JOIN SPR_USERS su on n.rec_userid = su.usr_username  "//
                + " LEFT JOIN SPR_PERSONS sp on su.usr_per_id = sp.per_id  "//
                + " WHERE " + dbStatementManager.getPeriodValidationForCurrentDateStr("n.ntf_date_from", "n.ntf_date_to", false) //
                + " AND ntf_usr_id = ? " //
        );
        stmt.setWhereExists(true);
        stmt.addSelectParam(language);
        stmt.addSelectParam(language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        if (orgId != null) {
            stmt.addParam4WherePart(" ntf_org_id = ? ", orgId);
        }
        stmt.addSelectParam(userId);
        if (sprAuthorization.isSingleRoleMode()) {
            stmt.addParam4WherePart(" ntf_rol_id = ? ", rolId);
        }
        stmt.addParam4WherePart("ntf_title", StatementAndParams.PARAM_STRING, advancedParamList.get("ntf_title"));
        stmt.addParam4WherePart("ntf_message", StatementAndParams.PARAM_STRING, advancedParamList.get("ntf_message"));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("ntf_creation_date"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("ntf_creation_date"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ntf_type", StatementAndParams.PARAM_STRING, advancedParamList.get("ntf_type"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ntf_title", "ntf_message", "ntf_type",
                        "TO_CHAR(ntf_creation_date, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprNotificationsSecurityManager sqm = new SprNotificationsSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprNotifications.ACTION_READ, SprNotifications.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public String getUserNotificationList(Connection conn, Double userId, Double orgId, Double rolId, String language) throws Exception {
        this.checkIsFormActionAssigned(conn, SprNotifications.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ntf_id, " + //
                "       ntf_type, " + //
                "       ntf_reference, " + //
                "       ntf_title," + //
                "       ntf_message, " + //
                "       ntf_creation_date, " + //
                "       ntf_mark_as_read_date, " + //
                "       CASE when ntf_mark_as_read_date is null then 'Y' else 'N' end not_read " + //
                "       FROM spr_notifications ntf " + //
                "       WHERE " + dbStatementManager.getPeriodValidationForCurrentDateStr("ntf_date_from", "ntf_date_to", false) + //
                "       AND ntf_usr_id = ? " //
        );
        stmt.setWhereExists(true);
        if (orgId != null) {
            stmt.addParam4WherePart(" ntf_org_id = ? ", orgId);
        }
        stmt.addSelectParam(userId);
        if (sprAuthorization.isSingleRoleMode()) {
            stmt.addParam4WherePart(" ntf_rol_id = ? ", rolId);
        }
        stmt.setStatementOrderPart(" ORDER BY ntf_id DESC ");
        SprNotificationsSecurityManager sqm = new SprNotificationsSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprNotifications.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    public void markAsRead(Connection conn, Double userId, Double orgId, Double rolId, Double ntfId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        StatementAndParams stmt = new StatementAndParams();
        String stmtStr = "UPDATE spr_notifications SET ntf_mark_as_read_date = ? WHERE ntf_usr_id = ? ";
        stmt.addSelectParam(new Date());
        stmt.addSelectParam(userId);
        if (orgId != null) {
            stmtStr = stmtStr + " and ntf_org_id = ? ";
            stmt.addSelectParam(orgId);
        }
        if (sprAuthorization.isSingleRoleMode()) {
            stmtStr = stmtStr + " and ntf_rol_id = ? ";
            stmt.addSelectParam(rolId);
        }
        stmtStr = stmtStr + " and ntf_id = ? ";
        stmt.setStatement(stmtStr);
        stmt.addSelectParam(ntfId);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    public void deleteNotification(Connection conn, Double ntfId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        StatementAndParams stmt = new StatementAndParams("""
                DELETE FROM spr_notifications
                WHERE ntf_id = ?
                """);
        stmt.addSelectParam(ntfId);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    public void saveNotification(Connection conn, String type, Double reference, String title, String message, Date dateFrom, Double userId, Double orgId,
            Double roleId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        saveNotificationData(conn, type, reference, title, message, dateFrom, userId, orgId, roleId);
    }

    private void saveNotificationData(Connection conn, String type, Double reference, String title, String message, Date dateFrom, Double userId, Double orgId,
            Double roleId) throws Exception {
        List<NotificationRecipient> recipients = getRecipientsIds(conn, userId, orgId, roleId);
        for (NotificationRecipient recipient : recipients) {
            SprNotificationsDAO notificationDAO = notificationsDBService.newRecord();
            notificationDAO.setNtf_type(type);
            notificationDAO.setNtf_reference(reference);
            notificationDAO.setNtf_title(title);
            notificationDAO.setNtf_message(message);
            notificationDAO.setNtf_usr_id(recipient.getUsrId());
            notificationDAO.setNtf_org_id(recipient.getOrgId());
            if (sprAuthorization.isSingleRoleMode()) {
                notificationDAO.setNtf_rol_id(recipient.getRolId());
            }
            dateFrom = Optional.ofNullable(dateFrom).orElse(new Date());
            notificationDAO.setNtf_date_from(dateFrom);
            notificationDAO.setNtf_creation_date(new Date());
            notificationsDBService.saveRecord(conn, notificationDAO);
            notificationManager.sendNotification(conn, notificationDAO);
        }
    }

    /**
     * Method will return a list of NotificationRecipient objects (with user ids) according to the given roleId and/or orgId.
     * If the given rolId is null - then the method will return all users of the given organization (by orgId)
     * If the given orgId is null - then the method will return all users with the given role (by rolId)
     * If both rolId and orgId is not null - then the method will return all users of the given organization (by orgId) with the given role (by rolId)
     * @param conn - connection to the DB
     * @param userId - user ID
     * @param orgId - organization ID
     * @param roleId - role ID
     * @return list of NotificationRecipient objects
     * @throws Exception
     */
    private List<NotificationRecipient> getRecipientsIds(Connection conn, Double userId, Double orgId, Double roleId) throws Exception {
        if (userId == null) {
            StatementAndParams stmt = new StatementAndParams();
            if (roleId != null && orgId != null) {
                stmt = new StatementAndParams("""
                        select usr_id as usrId,
                               uro_rol_id as rolId,
                               ou_org_id as orgId
                        from spr_users usr
                        join spr_org_users ou on usr_id = ou_usr_id
                        left join spr_user_roles uro on usr_id = uro_usr_id
                        where ou_org_id = ?
                        and uro_rol_id = ?
                        and (usr_lock_date is null OR %s < usr_lock_date)
                        and %s
                        and %s
                        and %s
                        group by usr_id, uro_rol_id, ou_org_id
                        union
                        select usr_id as usrId,
                               our_rol_id as rolId,
                               ou_org_id as orgId
                        from spr_users usr
                        join spr_org_users ou on usr_id = ou_usr_id
                        left join spr_org_user_roles our on ou_id = our_ou_id
                        where ou_org_id = ?
                        and our_rol_id = ?
                        and (usr_lock_date is null OR %s < usr_lock_date)
                        and %s
                        and %s
                        and %s
                        group by usr_id, our_rol_id, ou_org_id
                            """.formatted(dbStatementManager.getSysdateCommand(),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("usr_date_from", "usr_date_to", true),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("ou_date_from", "ou_date_to", true),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("uro_date_from", "uro_date_to", true), dbStatementManager.getSysdateCommand(),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("usr_date_from", "usr_date_to", true),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("ou_date_from", "ou_date_to", true),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("our_date_from", "our_date_to", true)));
                stmt.addSelectParam(orgId);
                stmt.addSelectParam(roleId);
                stmt.addSelectParam(orgId);
                stmt.addSelectParam(roleId);
            } else if (roleId != null) {
                stmt = new StatementAndParams("""
                        select usr_id as usrId,
                               uro_rol_id as rolId
                        from spr_users usr
                        join spr_user_roles uro on usr_id = uro_usr_id
                        where uro_rol_id = ?
                        and (usr_lock_date is null OR %s < usr_lock_date)
                        and %s
                        and %s
                        group by usr_id, uro_rol_id
                                """.formatted(dbStatementManager.getSysdateCommand(),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("usr_date_from", "usr_date_to", true),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("uro_date_from", "uro_date_to", true)));
                stmt.addSelectParam(roleId);
            } else if (orgId != null) {
                stmt = new StatementAndParams("""
                        select usr_id as usrId,
                               ou_org_id as orgId
                        from spr_users usr
                        join spr_org_users ou on usr_id = ou_usr_id
                        where ou_org_id = ?
                        and (usr_lock_date is null OR %s < usr_lock_date)
                        and %s
                        and %s
                        group by usr_id, ou_org_id
                                """.formatted(dbStatementManager.getSysdateCommand(),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("usr_date_from", "usr_date_to", true),
                        dbStatementManager.getPeriodValidationForCurrentDateStr("ou_date_from", "ou_date_to", true)));
                stmt.addSelectParam(orgId);
            }
            return queryController.selectQueryAsObjectArrayList(conn, stmt, NotificationRecipient.class);
        } else {
            List<NotificationRecipient> recipients = new ArrayList<NotificationRecipient>();
            NotificationRecipient rec = new NotificationRecipient();
            rec.setUsrId(userId);
            rec.setOrgId(orgId);
            rec.setRolId(roleId);
            recipients.add(rec);
            return recipients;
        }
    }

    private void saveNotificationInternal(Connection conn, String type, Double reference, String templateCode, String templateTitleCode,
            String templateMessageCode, VelocityContext context, Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        SprUsersDAO userDAO = usersDBService.loadRecord(conn, userId);
        String language = defaultLanguage;
        if (userDAO.getUsr_language() != null) {
            language = userDAO.getUsr_language();
        }
        String templateSubject = sprTemplatesDBService.getTemplateText(conn, templateCode, templateTitleCode, language);
        String templateText = sprTemplatesDBService.getTemplateText(conn, templateCode, templateMessageCode, language);
        if (context != null) {
            StringWriter message = new StringWriter();
            Velocity.evaluate(context, message, "notificationTemplateProcessing", templateText);
            templateText = message.toString();
        }
        saveNotificationData(conn, type, reference, templateSubject, templateText, dateFrom, userId, orgId, roleId);
    }

    public void sendNotifications(Connection conn, NotificationRequest record) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        List<NotificationRecipient> users = record.getRecipients();
        for (NotificationRecipient recipient : users) {
            saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, record.getNtfReference(), record.getNtfTitle(), record.getNtfMessage(), null,
                    recipient.getUsrId(), recipient.getOrgId(), recipient.getRolId());
        }
    }

    /**
     * @deprecated
     * This function is deprecated, please provide a Map instead of VelocityContext
     */
    @Deprecated(since = "1.8.1", forRemoval = true)
    public void saveNotification(Connection conn, String type, Double reference, String templateCode, String templateTitleCode, String templateMessageCode,
            VelocityContext context, Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        this.saveNotificationInternal(conn, type, reference, templateCode, templateTitleCode, templateMessageCode, context, dateFrom, userId, orgId, roleId);
    }

    public void saveNotification(Connection conn, String type, Double reference, String templateCode, String templateTitleCode, String templateMessageCode,
            Map<String, String> params, Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        VelocityContext context = new VelocityContext();
        if (params != null) {
            params.forEach((key, value) -> {
                context.put(key, value);
            });
        }
        this.saveNotificationInternal(conn, type, reference, templateCode, templateTitleCode, templateMessageCode, context, dateFrom, userId, orgId, roleId);
    }
}
