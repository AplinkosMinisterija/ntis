package lt.project.ntis.app.job.executor.impl.data.loader.csv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.project.ntis.app.job.request.impl.NtisCentralDataLoaderJobrequest;

@Service("CSV_POSTGRE_DATA_LOADER")
public class ExecutePostgreDataLoaderTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExecutePostgreDataLoaderTask.class);

    @Autowired
    @Qualifier("postgreDataSource")
    DataSource postgreDataSource;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprFilesDBService sprFileDBService;

    @Autowired
    NtisCentralDataLoaderJobrequest dataLoaderJobRequest;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.debug("postgreDataSource: " + postgreDataSource);
        SprJobDefinitionsDAO jobDef = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
        Map<String, String> params = dataLoaderJobRequest.loadJobRequestParams(conn, request.getJrq_id());
        log.debug("SOURCE_FILE_ID: " + params.get(NtisCentralDataLoaderJobrequest.SOURCE_FILE_ID));
        SprFilesDAO fileDAO = sprFileDBService.loadRecord(conn, Utils.getDouble(params.get(NtisCentralDataLoaderJobrequest.SOURCE_FILE_ID)));
        log.debug("fileDAO: " + fileDAO);
        Gson gson = new Gson();
        CsvDataLoaderSetup setupData = gson.fromJson(jobDef.getJde_execution_parameter(), CsvDataLoaderSetup.class);
        try (Connection postgreConn = postgreDataSource.getConnection()) {
            log.debug("postgreConn: " + postgreConn);
            String stmtStr1 = "CREATE TEMP TABLE temp_table (seq serial not null PRIMARY KEY, " + setupData.constructCsvInsertFields() + ")";
            try (Statement stmt1 = postgreConn.createStatement()) {
                log.debug("stmtStr1: " + stmtStr1);
                stmt1.executeUpdate(stmtStr1);
            } catch (Exception ex) {
                log.error("Error during creation temporarry table: " + stmtStr1, ex);
                throw ex;
            }
            BaseConnection pgcon = (BaseConnection) postgreConn;
            CopyManager mgr = new CopyManager(pgcon);
            String sql = "copy temp_table" + setupData.constructCsvCopyFields() + " FROM stdin " + setupData.getDataCopyInstructionPart();
            String encodingStr = setupData.getFileEncoding();
            log.debug("file encoding from setup: " + encodingStr);
            if (params.containsKey(NtisCentralDataLoaderJobrequest.SOURCE_FILE_ENCODING)) {
                encodingStr = params.get(NtisCentralDataLoaderJobrequest.SOURCE_FILE_ENCODING);
                log.debug("file encoding from request params: " + encodingStr);
            }
            BufferedReader in = null;
            if (encodingStr != null && !encodingStr.isEmpty()) {
                Charset fileCharSet = Charset.forName(encodingStr);
                in = new BufferedReader(new InputStreamReader(fileStorageService.getFileBySprFilesDAO(fileDAO), fileCharSet));
            } else {
                in = new BufferedReader(new InputStreamReader(fileStorageService.getFileBySprFilesDAO(fileDAO)));
            }
            log.debug("sql: " + sql);
            long rowsaffected = mgr.copyIn(sql, in);
            log.debug("Rows copied: " + rowsaffected);
            // "insert into () select ()"
            String stmtStr2 = setupData.getInsertStatement();
            log.debug("stmtStr2: " + stmtStr2);
            log.debug("params:" + params);
            log.debug("Statement params: " + setupData.getStatementParamNames());
            try (PreparedStatement stmt2 = postgreConn.prepareStatement(stmtStr2)) {
                int idx = 0;
                for (String paramName : setupData.getStatementParamNames()) {
                    log.debug((idx + 1) + "->" + paramName + ": " + params.get(paramName));
                    stmt2.setObject(++idx, params.get(paramName));
                }
                stmt2.execute();
            } catch (Exception ex) {
                log.error(stmtStr2);
            }
        } catch (Exception ex) {
            log.error("Exception during data import", ex);
            throw (ex);
        }

    }

}
