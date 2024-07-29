package lt.project.ntis.job.request.agglomerations;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import lt.project.ntis.job.request.delivery.DeliveryConfirmedEmailJobRequest;

@Component("AGGLO_REJECTED_EMAIL")
public class AggloRejectedEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(DeliveryConfirmedEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String AGGLO_URL = "aggloUrl";

    public static final String MUNICIPALITY = "municipality";

    public static final String AGG_ID = "aggId";

    public static final String CODE = "AGGLO_REJECTED_EMAIL";

    public static final String NAME = "Template for rejected agglomeration";

    public static final String DESCRIPTION = "Template for rejected agglomeration";

    @Override
    public Map<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AGGLO_URL, "");
        params.put(MUNICIPALITY, "");
        params.put(AGG_ID, "");
        params.put(HOME_URL, "");
        params.put(ExecuteEmailSendTask.RECEIVER, "");
        return params;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getType() {
        return SprJobDefinitionsDBService.EMAIL_TYPE;
    }

    @Override
    public String getExecutorType() {
        return SprJobDefinitionsDBService.EXECUTOR_TYPE_JOB;
    }

}
