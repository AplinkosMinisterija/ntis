package eu.itreegroup.spark.app.job.executor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestExecutionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestExecutionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

public abstract class BaseRequestLogicExecutor implements RequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(BaseRequestLogicExecutor.class);

    private static final int MAX_JRQ_ERROR_COLUMN_BYTES_LENGTH = 4000;

    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected SprFilesDBService sprFilesDBService;

    @Autowired
    protected SprJobDefinitionsDBService sprJobDefinitionDBService;

    @Autowired
    SprJobRequestsDBService sprJobRequestsDBService;

    @Autowired
    protected SprJobRequestExecutionsDBService sprJobRequestExecutionsDBService;

    @Override
    public abstract void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception;

    @Override
    public String execute(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception, InterruptedException, SparkBusinessException {
        String answer = "";
        try {
            executeSpecificLogic(conn, scriptName, request);
            answer = "Success!";
        } catch (Exception ex) {
            log.error("Exception on job request " + request.getJrq_id() + " execution: ", ex);
            answer = "Error!";
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String errorString = errors.toString();
            errorString = errorString.length() > 2000 ? errorString.substring(0, 1980) + "...." : errorString;
            request.setJrq_error(errorString);
            SprJobRequestExecutionsDAO executionDAO = sprJobRequestExecutionsDBService.newRecord();
            executionDAO.setJre_jrq_id(request.getJrq_id());
            executionDAO.setJre_err(errorString);
            executionDAO.setJre_time(new Date());
            try {
                SprJobDefinitionsDAO jobDefinitionsDAO = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
                executionDAO.setJre_name(jobDefinitionsDAO.getJde_name());
            } catch (Exception ex1) {
                log.error("Error while trying to get information of job definition", ex1);
            }
            sprJobRequestExecutionsDBService.saveRecord(conn, executionDAO);
            throw ex;
        }
        return answer;
    }

    /**
     * Function will load all request arguments to the hash table structure.
     * @param conn - used for data access from DB
     * @param requestId - reference to the request which arguments should be loaded
     * @return request arguments.
     */
    public HashMap<String, Object> loadJobRequestParams(Connection conn, Double requestId) {
        HashMap<String, Object> answer = new HashMap<String, Object>();
        if (requestId != null) {
            String stmtStr = "select jra_name, jra_value from spr_job_request_args where jra_jrq_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
                pstmt.setDouble(1, requestId);
                try (ResultSet rez = pstmt.executeQuery()) {
                    while (rez.next()) {
                        answer.put(rez.getString(1), rez.getString(2));
                    }
                }
            } catch (SQLException e1) {
                log.error("Error on loading request parameters statment: " + stmtStr + " with parameter: " + requestId, e1);
            }
        }
        return answer;
    }

    protected Double attachFile(Connection conn, SprJobRequestsDAO request, String fileName, InputStream fileContent, String fileMimeType) throws Exception {
        Double previousFileId = request.getJrq_fil_id();
        if (previousFileId != null) {
            sprFilesDBService.markAsDeleted(conn, previousFileId);
        }
        Double fileId = fileStorageService.saveFile(fileContent, fileName, fileMimeType, conn).getFil_id();
        sprFilesDBService.markAsConfirmed(conn, fileId);
        return fileId;
    }

    protected void logError(Connection conn, SprJobDefinitionsDAO definition, SprJobRequestsDAO request, String errorLog) throws Exception {
        String logSafeLength = errorLog;

        byte[] logBytes = errorLog.getBytes(StandardCharsets.UTF_8);
        if (logBytes.length > MAX_JRQ_ERROR_COLUMN_BYTES_LENGTH) {
            log.warn("Full errorLog too long for DB column - saving into separate spr_files record.");
            Double fileId = attachFile(conn, request, buildLogFileName(request), new ByteArrayInputStream(logBytes), "text/plain");
            request.setJrq_fil_id(fileId);
            log.warn("Created spr_files record with full execution log - fileId: {}.", fileId);

            logSafeLength = "Full log is in attached file.\n\n" + trimToBytesLength(errorLog, logBytes, MAX_JRQ_ERROR_COLUMN_BYTES_LENGTH - 100);
        }

        request.setJrq_error(logSafeLength);
        sprJobRequestsDBService.updateRecord(conn, request);

        Double jreId = createExecution(conn, definition, request, logSafeLength);
        log.warn("Created spr_job_request_executions record with all/part of execution log - jreId: {}.", jreId);

        conn.commit();
    }

    private Double createExecution(Connection conn, SprJobDefinitionsDAO definition, SprJobRequestsDAO request, String executionLog) throws Exception {
        SprJobRequestExecutionsDAO dao = sprJobRequestExecutionsDBService.newRecord();
        dao.setJre_jrq_id(request.getJrq_id());
        dao.setJre_err(executionLog);
        dao.setJre_time(new Date());
        if (definition != null) {
            dao.setJre_name(definition.getJde_name());
        }
        return sprJobRequestExecutionsDBService.saveRecord(conn, dao).getJre_id();
    }

    private static String trimToBytesLength(String data, byte[] allBytes, int bytesLength) {
        String result = data;
        if (allBytes.length > bytesLength) {
            byte[] trimmedBytes = new byte[bytesLength];
            System.arraycopy(allBytes, 0, trimmedBytes, 0, bytesLength);
            result = new String(trimmedBytes, StandardCharsets.UTF_8);

            int lastCharPos = result.length() - 1;
            if (lastCharPos >= 0 && result.charAt(lastCharPos) != data.charAt(lastCharPos)) {
                result = result.substring(0, lastCharPos);
            }
        }
        return result;
    }

    private static String buildLogFileName(SprJobRequestsDAO dao) {
        return String.format("jobRequest-%d-log.txt", dao.getJrq_id().longValue());
    }
}
