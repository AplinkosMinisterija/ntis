package lt.project.ntis.logic.forms.brokerws;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

/**
 * Suplanuoto darbo klasė, atnaujinanti JAR duomenis iš RC.
 */
@Service("BROKERWS_JAR_RENEWALS_MAKER")
public class RcJarRenewJob extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(RcJarRenewJob.class);

    @Autowired
    RcBroker jar;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("START BrokerWS JAR data renewals.");

        ProblemsConsumer problemsConsumer = new ProblemsConsumer();
        SprJobDefinitionsDAO definition = null;
        try {
            definition = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
            jar.renewJarData(conn, problemsConsumer);

        } catch (SparkBusinessException e) {
            problemsConsumer.accept(new RcBroker.Problem(null, e));
            log.error("JAR data renewals from RC broker failed with business error.", e);
        } catch (Exception e) {
            problemsConsumer.accept(new RcBroker.Problem(null, e));
            log.error("JAR data renewals from RC broker failed.", e);
        } finally {
            if (problemsConsumer.hasProblems()) {
                logError(conn, definition, request, problemsConsumer.toString());
            }
        }

        log.info("FINISH BrokerWS JAR data renewals.");
    }

}
