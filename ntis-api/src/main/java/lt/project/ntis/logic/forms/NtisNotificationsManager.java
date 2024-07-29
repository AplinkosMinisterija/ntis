package lt.project.ntis.logic.forms;

import java.io.StringWriter;
import java.sql.Connection;
import java.util.Date;
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
import eu.itreegroup.spark.app.tools.NotificationManager;
import eu.itreegroup.spark.modules.admin.dao.SprNotificationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.service.SprNotificationsDBServiceImpl;

@Component
public class NtisNotificationsManager extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(NtisNotificationsManager.class);

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

    @Override
    public String getFormName() {
        return "NTIS_NOTIFICATIONS_MANAGER";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "NTIS notifications manager", "NTIS notifications manager");
        addFormActions(FormBase.ACTION_CREATE);
    }

    public void saveNotification(Connection conn, String type, Double reference, String title, String message, String refType, String msgSubject, Date dateFrom,
            Double userId, Double orgId, Double roleId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
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
        saveNotification(conn, type, reference, templateSubject, templateText, refType, msgSubject, dateFrom, userId, orgId, roleId);
    }

    public void saveNotification(Connection conn, String type, Double reference, String templateCode, String templateTitleCode, String templateMessageCode,
            Map<String, Object> params, String refType, String msgSubject, Date dateFrom, Double userId, Double orgId, Double roleId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        VelocityContext context = new VelocityContext();
        if (params != null) {
            params.forEach((key, value) -> {
                context.put(key, value);
            });
        }
        this.setParams(conn, type, reference, templateCode, templateTitleCode, templateMessageCode, context, refType, msgSubject, dateFrom, userId, orgId,
                roleId);
    }
}
