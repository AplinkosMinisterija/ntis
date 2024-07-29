package lt.project.ntis.logic.forms.isense;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;

/**
 * Suplanuoto darbo klasė, išvalanti į iSense sistemą užkrautus duomenis, reikalingus pasirašytų sutarties dokumentų peržiūrai.
 */
@Service("ISENSE_PREVIEW_SESSIONS_CLEANUP_EXECUTOR")
public class IsensePreviewSessionsCleanupJob extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(IsensePreviewSessionsCleanupJob.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    IsenseHttpRestTemplate isenseHttp;

    /**
     * Pagrindinis suplanuoto darbo vykdymo metodas.
     * @param conn - prisijungimas prie DB.
     * @param scriptName - įrašo DB lentoje spr_job_definitions stulpelyje jde_execution_parameter reikšmė.
     * @param request - įrašo DB lentoje spr_job_request reikšmė.
     */
    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        SprJobDefinitionsDAO jobDefinition = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
        Long validPreviewPeriodInMinutes = Utils.getLong(jobDefinition.getJde_execution_parameter());
        if (validPreviewPeriodInMinutes == null) {
            throw new Exception("Job execution parameter for validPreviewPeriodInMinutes is missing.");
        }

        Date cutoffDate = getCutoffDate(validPreviewPeriodInMinutes.intValue());
        log.info("START iSense cleaning of expired preview sessions. Cutoff datetime: {}.", cutoffDate);

        Collection<String> expiredTokens = collectExpiredTokens(conn, cutoffDate);
        if (!expiredTokens.isEmpty()) {
            deleteOldPreviewSessions(expiredTokens);
            int markedRequestsCount = markProcessRequestsAsProcessed(conn, cutoffDate);

            log.info("FINISH iSense cleaning of expired preview sessions. Processed tokens count: {}.", markedRequestsCount);
        } else {
            log.info("FINISH iSense cleaning of expired preview sessions. No expired tokens were found.");
        }
    }

    private Date getCutoffDate(int validPreviewPeriodInMinutes) {
        return DateUtils.addMinutes(new Date(), -validPreviewPeriodInMinutes);
    }

    private Collection<String> collectExpiredTokens(Connection conn, Date cutoffDate) throws Exception {
        StatementAndParams requestsStmt = new StatementAndParams(String.format("SELECT prq_token " //
                + "   FROM spr_process_requests " //
                + "  WHERE prq_type = '%s' " //
                + "    AND prq_date_from < ? " //
                + "    AND n02 is null", //
                NtisProcessRequestTypes.ISENSE_PREVIEW_REQUEST.getCode()));
        requestsStmt.addSelectParam(cutoffDate);

        Collection<String> expiredTokens = new HashSet<>();
        for (HashMap<String, String> requestResult : baseControllerJDBC.selectQueryAsDataArrayList(conn, requestsStmt)) {
            expiredTokens.add(requestResult.get("prq_token"));
        }
        return expiredTokens;
    }

    private void deleteOldPreviewSessions(Collection<String> expiredTokens) throws Exception {
        String ntisToken = isenseHttp.createNtisToken();
        for (String expiredToken : expiredTokens) {
            try {
                isenseHttp.deleteSession(ntisToken, expiredToken);

            } catch (Exception e) {
                log.error(String.format("Could not delete expired iSense session '%s'.", expiredToken), e);
            }
        }
    }

    private int markProcessRequestsAsProcessed(Connection conn, Date cutoffDate) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(String.format("UPDATE spr_process_requests " //
                + "   SET n02 = 1 " //
                + " WHERE prq_type = '%s' " //
                + "   AND prq_date_from < ? " //
                + "   AND n02 is null", //
                NtisProcessRequestTypes.ISENSE_PREVIEW_REQUEST.getCode()));
        stmt.addSelectParam(cutoffDate);
        return baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}
