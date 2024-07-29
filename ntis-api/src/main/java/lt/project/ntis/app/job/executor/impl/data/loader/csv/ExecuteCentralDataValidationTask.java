package lt.project.ntis.app.job.executor.impl.data.loader.csv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.app.job.request.impl.NtisCentralDataValidationJobRequest;
import lt.project.ntis.dao.NtisCwFilesDAO;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.NtisNotificationsManager;
import lt.project.ntis.service.NtisCwFilesDBService;

@Service("CENTRAL_DATA_VALIDATOR")
public class ExecuteCentralDataValidationTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExecuteCentralDataValidationTask.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    NtisCentralDataValidationJobRequest centralDataValidationJobRequest;

    @Autowired
    NtisNotificationsManager ntisNotificationsManager;

    @Autowired
    NtisCwFilesDBService cwFilesDBService;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        Map<String, String> params = centralDataValidationJobRequest.loadJobRequestParams(conn, request.getJrq_id());
        NtisCwFilesDAO fileDAO = cwFilesDBService.loadRecord(conn, Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)));
        try {
            StatementAndParams stmt = new StatementAndParams("CALL ntis.validate_data_import(?::integer, ?::integer)");
            log.debug("SOURCE_FILE_ID: " + params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID));
            stmt.addSelectParam(Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)));
            stmt.addSelectParam(Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)));
            PreparedStatement ps = conn.prepareStatement(stmt.getStatemenWithParams());
            stmt.setValues(ps);
            ps.execute();
        } catch (Exception ex) {
            conn.rollback();
            StatementAndParams stmt2 = new StatementAndParams(
                    "update ntis.ntis_cw_files set cwf_status = 'CW_FIL_PENDING_ERR', cwf_usr_id = ?::int where cwf_id = ?::int");
            stmt2.addSelectParam(Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)));
            stmt2.addSelectParam(Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)));
            baseControllerJDBC.adjustRecordsInDB(conn, stmt2);

            SprFilesDAO sprFileDAO = sprFilesDBService.loadRecord(conn, fileDAO.getCwf_fil_id());

            if (params.get(NtisCentralDataValidationJobRequest.ROLE).equalsIgnoreCase("VAND_ADMIN")) {
                Map<String, Object> context = new HashMap<String, Object>();
                context.put("cwfId", Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)).intValue());
                context.put("fileName", sprFileDAO.getFil_name());
                ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE,
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)), "NTIS_CENTRALIZED_NOTIF",
                        "CENTRALIZED_UPLOADED_VAND_SUBJECT", "CENTRALIZED_UPLOADED_VAND_BODY", context, NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode(),
                        NtisMessageSubject.MSG_SBJ_CENTRALIZED_UPLOADED.getCode(), new Date(),
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)),
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.VAND_ORG_ID)), null);
            } else {
                Map<String, Object> context = new HashMap<String, Object>();
                context.put("cwfId", Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)).intValue());
                context.put("orgId", Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.VAND_ORG_ID)).intValue());
                context.put("fileName", sprFileDAO.getFil_name());
                Double sesOrgId = null;
                try {
                    sesOrgId = Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SES_ORG_ID));
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE,
                            Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)), "NTIS_CENTRALIZED_NOTIF",
                            "CENTRALIZED_UPLOADED_ADMIN_SUBJECT", "CENTRALIZED_UPLOADED_ADMIN_BODY", context, NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode(),
                            NtisMessageSubject.MSG_SBJ_CENTRALIZED_UPLOADED.getCode(), new Date(),
                            Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)), sesOrgId, null);
                } catch (Exception exc) {
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE,
                            Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)), "NTIS_CENTRALIZED_NOTIF",
                            "CENTRALIZED_UPLOADED_ADMIN_SUBJECT", "CENTRALIZED_UPLOADED_ADMIN_BODY", context, NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode(),
                            NtisMessageSubject.MSG_SBJ_CENTRALIZED_UPLOADED.getCode(), new Date(),
                            Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)), null, null);
                }
            }
            conn.commit();
            throw new SparkBusinessException(new S2Message("common.error.fileProcessingError", SparkMessageType.ERROR));
        }
        SprFilesDAO sprFileDAO = sprFilesDBService.loadRecord(conn, fileDAO.getCwf_fil_id());
        if (params.get(NtisCentralDataValidationJobRequest.ROLE).equalsIgnoreCase("VAND_ADMIN")) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("cwfId", Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)).intValue());
            context.put("fileName", sprFileDAO.getFil_name());
            ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE,
                    Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)), "NTIS_CENTRALIZED_NOTIF",
                    "CENTRALIZED_UPLOADED_VAND_SUBJECT", "CENTRALIZED_UPLOADED_VAND_BODY", context, NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode(),
                    NtisMessageSubject.MSG_SBJ_CENTRALIZED_UPLOADED.getCode(), new Date(),
                    Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)),
                    Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.VAND_ORG_ID)), null);
        } else {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("cwfId", Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)).intValue());
            context.put("orgId", Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.VAND_ORG_ID)).intValue());
            context.put("fileName", sprFileDAO.getFil_name());
            Double sesOrgId = null;
            try {
                sesOrgId = Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SES_ORG_ID));
                ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE,
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)), "NTIS_CENTRALIZED_NOTIF",
                        "CENTRALIZED_UPLOADED_ADMIN_SUBJECT", "CENTRALIZED_UPLOADED_ADMIN_BODY", context, NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode(),
                        NtisMessageSubject.MSG_SBJ_CENTRALIZED_UPLOADED.getCode(), new Date(),
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)), sesOrgId, null);
            } catch (Exception ex) {
                ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE,
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID)), "NTIS_CENTRALIZED_NOTIF",
                        "CENTRALIZED_UPLOADED_ADMIN_SUBJECT", "CENTRALIZED_UPLOADED_ADMIN_BODY", context, NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode(),
                        NtisMessageSubject.MSG_SBJ_CENTRALIZED_UPLOADED.getCode(), new Date(),
                        Utils.getDouble(params.get(NtisCentralDataValidationJobRequest.USER_ID)), null, null);
            }

        }

    }

}
