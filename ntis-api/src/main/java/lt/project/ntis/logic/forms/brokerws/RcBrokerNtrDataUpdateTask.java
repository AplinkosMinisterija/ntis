package lt.project.ntis.logic.forms.brokerws;

import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;

/**
 * Atnaujina nurodyto asmens arba organizacijos NTR duomenis iš Registrų centro.
 */
@Service("RC_BROKER_NTR_DATA_UPDATE_EXECUTOR")
public class RcBrokerNtrDataUpdateTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerNtrDataUpdateTask.class);

    @Autowired
    RcBrokerNtrDataUpdateTaskRequest rcBrokerNtrDataUpdateRequest;

    @Autowired
    RcBroker ntr;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        Map<String, String> params = rcBrokerNtrDataUpdateRequest.loadJobRequestParams(conn, request.getJrq_id());
        Double perId = Utils.getDouble(params.get(RcBrokerNtrDataUpdateTaskRequest.JOB_REQUEST_PARAM_PERSON_ID));
        Double orgId = Utils.getDouble(params.get(RcBrokerNtrDataUpdateTaskRequest.JOB_REQUEST_PARAM_ORGANISATION_ID));
        try {
            ntr.updateNtrOwnerData(conn, perId, orgId);
        } catch (Exception e) {
            log.error("NTR update from RC failed for per_id {}, org_id {}.", perId, orgId, e);
        }
    }

}
