package lt.project.ntis.logic.forms.rcadrws;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.toLocalDate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestExecutionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.logic.forms.rcadrws.RcAdr.Problem;

/**
 * Suplanuoto darbo klasė, atnaujinanti adresų duomenis iš RC.
 */
@Service("RCADRWS_UPDATES_GETTER")
public class RcAdrWsJob extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(RcAdrWsJob.class);

    private static final String UPDATE_ADR_DATA = "RcAdrWsCallsFor-UpdateData-ENABLED";

    private static final int MAX_ERROR_TEXT_LENGTH = 3000;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    SprJobRequestsDBService sprJobRequestsDBService;

    @Autowired
    RcAdr rcAdr;

    @Value("${rcadrws.active}")
    private boolean active;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("START RC adrws data update.");
        if (isRcAdrWsOn()) {
            Map<String, String> mdc = MDC.getCopyOfContextMap();
            ProblemsConsumer problemsConsumer = new ProblemsConsumer();
            SprJobDefinitionsDAO definition = null;
            try {
                definition = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
                rcAdr.collectAndApplyUpdates(conn, definition, collectJobIntervals(findDateFrom(definition)), problemsConsumer);

            } catch (SparkBusinessException e) {
                problemsConsumer.accept(new Problem(null, e));
                log.error("Addresses data update from RC adrWS failed with business error.", e);
            } catch (Exception e) {
                problemsConsumer.accept(new Problem(null, e));
                log.error("Addresses data update from RC adrWS failed.", e);
            } finally {
                MDC.setContextMap(mdc);
                logErrors(conn, request, definition, problemsConsumer);
            }
        } else {
            log.warn("Integration with RC adrWS is switched off either in ntis-api.properties settings or in spr_properties table with property '{}'.",
                    UPDATE_ADR_DATA);
        }
        log.info("FINISH RC adrws data update.");
    }

    private void logErrors(Connection conn, SprJobRequestsDAO request, SprJobDefinitionsDAO definition, ProblemsConsumer problemsConsumer) throws Exception {
        if (problemsConsumer.hasProblems()) {
            String executionLog = problemsConsumer.toString();
            log.warn("Job identified problems - will attempt to log them into DB. Full log: {}.", executionLog);

            boolean logTooLong = executionLog.getBytes().length > MAX_ERROR_TEXT_LENGTH;
            String executionLogSafeLength = logTooLong ? executionLog.substring(0, MAX_ERROR_TEXT_LENGTH - 200) + "\n\nFull log is in attached file."
                    : executionLog;

            Double jreId = createExecution(conn, request, definition, executionLogSafeLength);
            log.warn("Created spr_job_request_executions record with all/part of execution log - jreId: {}.", jreId);

            if (logTooLong) {
                log.warn("Full log too long for DB columns - saving into separate spr_files record.");
                String fileName = String.format("'RCadrws-jobRequest-%d-log.txt", request.getJrq_id().longValue());
                Double fileId = attachFile(conn, fileName, new ByteArrayInputStream(executionLog.getBytes()), "text/plain");
                log.warn("Created spr_files record with full execution log - fileId: {}.", fileId);
                request.setJrq_fil_id(fileId);
            }
            request.setJrq_error(executionLogSafeLength);
            sprJobRequestsDBService.updateRecord(conn, request);
            conn.commit();
        }
    }

    protected Double attachFile(Connection conn, String fileName, InputStream fileContent, String fileMimeType) throws Exception {
        Double fileId = fileStorageService.saveFile(fileContent, fileName, fileMimeType, conn).getFil_id();
        sprFilesDBService.markAsConfirmed(conn, fileId);
        return fileId;
    }

    private Double createExecution(Connection conn, SprJobRequestsDAO request, SprJobDefinitionsDAO definition, String executionLog) throws Exception {
        SprJobRequestExecutionsDAO dao = sprJobRequestExecutionsDBService.newRecord();
        dao.setJre_jrq_id(request.getJrq_id());
        dao.setJre_err(executionLog);
        dao.setJre_time(new Date());
        if (definition != null) {
            dao.setJre_name(definition.getJde_name());
        }
        return sprJobRequestExecutionsDBService.saveRecord(conn, dao).getJre_id();
    }

    private boolean isRcAdrWsOn() throws Exception {
        return active && YesNo.getEnumByCode(dbPropertyManager.getPropertyByName(UPDATE_ADR_DATA, YesNo.Y.getCode())).getBoolean();
    }

    private Map<LocalDate, LocalDate> collectJobIntervals(LocalDate dateFromInclusive) {
        Map<LocalDate, LocalDate> result = Collections.emptyMap();
        LocalDate dateToExclusive = LocalDate.now();
        if (dateFromInclusive.isBefore(dateToExclusive)) {
            long limit = ChronoUnit.DAYS.between(dateFromInclusive, dateToExclusive);
            result = Stream.iterate(dateFromInclusive, date -> date.plusDays(1)).limit(limit)
                    .collect(toMap(identity(), identity(), (d1, d2) -> d1, TreeMap::new));
        }
        log.info("Collected job intervals {}.", result);
        return result;
    }

    private LocalDate findDateFrom(SprJobDefinitionsDAO dao) throws Exception {
        LocalDate dateFromInclusive = toLocalDate(dao.getJde_execution_parameter());
        if (dateFromInclusive == null) {
            throw new Exception(
                    String.format("Job execution parameter '%s' must be a date from which to continue requests.", dao.getJde_execution_parameter()));
        }
        log.info("Identified job starting date {}.", dateFromInclusive);
        return dateFromInclusive;
    }

}
