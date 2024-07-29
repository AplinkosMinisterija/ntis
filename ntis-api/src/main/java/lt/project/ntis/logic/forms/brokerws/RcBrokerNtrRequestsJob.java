package lt.project.ntis.logic.forms.brokerws;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprProcessRequestsDBService;
import lt.project.ntis.enums.NtisBuildingOwnerType;
import lt.project.ntis.enums.NtisProcessRequestTypes;

/**
 * Suplanuoto darbo klasė, atnaujinanti nurodytų asmenų duomenis iš NTR.
 */
@Service("BROKERWS_NTR_REQUESTS_EXECUTOR")
public class RcBrokerNtrRequestsJob extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerNtrRequestsJob.class);

    @Autowired
    SprProcessRequestsDBService sprProcessRequestsDBService;

    @Autowired
    DBStatementManager dBStatementManager;

    @Autowired
    RcBroker ntr;

    /**
     * Pagrindinis suplanuoto darbo vykdymo metodas.
     * @param conn - prisijungimas prie DB.
     * @param scriptName - įrašo DB lentoje spr_job_definitions stulpelyje jde_execution_parameter reikšmė.
     * @param request - įrašo DB lentoje spr_job_request reikšmė.
     */
    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("START processing BrokerWS NTR requests.");
        for (SprProcessRequestsDAO dao : loadProcessRequests(conn)) {
            boolean isLegalPerson = NtisBuildingOwnerType.J.getCode().equals(dao.getC01());
            Double perId = isLegalPerson ? null : dao.getPrq_reference_id();
            Double orgId = isLegalPerson ? dao.getPrq_reference_id() : null;

            ntr.updateNtrOwnerData(conn, perId, orgId);

            sprProcessRequestsDBService.deleteRecord(conn, dao.getPrq_id());
        }
        log.info("FINISH processing BrokerWS NTR requests.");
    }

    private List<SprProcessRequestsDAO> loadProcessRequests(Connection conn) throws Exception {
        return sprProcessRequestsDBService.loadRecordsByParams(conn,
                " WHERE PRQ_TYPE = ? and " + dBStatementManager.getPeriodValidationForCurrentDateStr("PRQ_DATE_FROM", "PRQ_DATE_TO", false),
                new SelectParamValue(NtisProcessRequestTypes.BROKERWS_NTR_REQUEST.getCode()));
    }

}
