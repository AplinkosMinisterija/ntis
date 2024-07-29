package lt.project.ntis.logic.forms.brokerws.ntr;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.logic.forms.brokerws.ntr.RcBrokerNtr.Problem;

/**
 * Suplanuoto darbo klasė, masiškai atnaujinanti NTR duomenis iš RC.
 */
@Service("BROKERWS_NTR_UPDATES_GETTER")
public class RcBrokerNtrJob extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerNtrJob.class);

    private static final String UPDATE_NTR_BATCH_DATA = "RcNtrBatchCallsFor-UpdateData-ENABLED";

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    RcBrokerNtr rcBrokerNtr;

    @Value("${brokerws.active}")
    private boolean active;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO jobRequest) throws Exception {
        log.info("START BrokerWS NTR batch data update (jobRequest:{}).", jobRequest.getJrq_id());
        if (isRcNtrBatchOn()) {
            Map<String, String> mdc = MDC.getCopyOfContextMap();
            ProblemsConsumer problemsConsumer = new ProblemsConsumer();
            SprJobDefinitionsDAO definition = null;
            try {
                definition = sprJobDefinitionDBService.loadRecord(conn, jobRequest.getJrq_jde_id());
                rcBrokerNtr.doJob(conn, jobRequest, findDateFrom(definition), findLastSunday(), problemsConsumer);

            } catch (SparkBusinessException e) {
                problemsConsumer.accept(new Problem(null, null, e));
                log.error("Ntr batch data update from RC broker failed with business error.", e);
            } catch (Exception e) {
                problemsConsumer.accept(new Problem(null, null, e));
                log.error("Ntr batch data update from RC broker failed.", e);
            } finally {
                MDC.setContextMap(mdc);
                if (problemsConsumer.hasProblems()) {
                    logError(conn, definition, jobRequest, problemsConsumer.toString());
                }
            }
        } else {
            log.warn(
                    "Integration with RC broker NTR batch service is switched off either in ntis-api.properties settings or in spr_properties table with property '{}'.",
                    UPDATE_NTR_BATCH_DATA);
        }
        log.info("FINISH BrokerWS NTR batch data update (jobRequest:{}).", jobRequest.getJrq_id());
    }

    private boolean isRcNtrBatchOn() throws Exception {
        return active && YesNo.getEnumByCode(dbPropertyManager.getPropertyByName(UPDATE_NTR_BATCH_DATA, YesNo.Y.getCode())).getBoolean();
    }

    private LocalDate findDateFrom(SprJobDefinitionsDAO dao) throws Exception {
        try {
            LocalDate dateFromInclusive = LocalDate.parse(dao.getJde_execution_parameter());
            log.info("Identified job starting date {}.", dateFromInclusive);
            return dateFromInclusive;

        } catch (Exception e) {
            throw new Exception(String.format("Job execution parameter '%s' must be a date from which to continue requests.", dao.getJde_execution_parameter()),
                    e);
        }
    }

    private LocalDate findLastSunday() {
        return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
    }
}
