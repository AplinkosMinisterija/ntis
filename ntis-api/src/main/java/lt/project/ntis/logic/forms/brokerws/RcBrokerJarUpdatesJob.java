package lt.project.ntis.logic.forms.brokerws;

import java.sql.Connection;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

/**
 * Suplanuoto darbo klasė, pritaikanti JAR duomenų pasikeitimus RC.
 */
@Service("BROKERWS_JAR_UPDATES_GETTER")
public class RcBrokerJarUpdatesJob extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerJarUpdatesJob.class);

    @Autowired
    RcBroker jar;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("START BrokerWS JAR data update.");

        ProblemsConsumer problemsConsumer = new ProblemsConsumer();
        SprJobDefinitionsDAO definition = null;
        try {
            definition = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
            jar.updateJarData(conn, findDayOfInterest(conn, request), problemsConsumer);

        } catch (SparkBusinessException e) {
            problemsConsumer.accept(new RcBroker.Problem(null, e));
            log.error("JAR data update from RC broker failed with business error.", e);
        } catch (Exception e) {
            problemsConsumer.accept(new RcBroker.Problem(null, e));
            log.error("JAR data update from RC broker failed.", e);
        } finally {
            if (problemsConsumer.hasProblems()) {
                logError(conn, definition, request, problemsConsumer.toString());
            }
        }

        log.info("FINISH BrokerWS JAR data update.");
    }

    private LocalDate findDayOfInterest(Connection conn, SprJobRequestsDAO request) throws Exception {
        String parameter = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id()).getJde_execution_parameter();
        if (parameter == null) {
            throw new Exception("Execution parameter not set. Expecting to find negative integer, meaning amount of days going back from today.");
        }

        LocalDate result = null;
        try {
            int daysParameterValue = Integer.valueOf(parameter);
            result = LocalDate.now().plusDays(daysParameterValue);
        } catch (Exception e) {
            throw new Exception(
                    String.format("Failed to parse execution parameter '%s'. Expecting to find negative integer, meaning amount of days going back from today.",
                            parameter),
                    e);
        }

        log.info("Using day of interest: {}.", result);
        return result;
    }

}
