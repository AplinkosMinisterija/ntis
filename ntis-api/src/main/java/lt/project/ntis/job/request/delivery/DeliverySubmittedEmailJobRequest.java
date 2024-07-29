package lt.project.ntis.job.request.delivery;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("DELIVERY_SUBMITTED")
public class DeliverySubmittedEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(DeliverySubmittedEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String DELIVERY_URL = "deliveryUrl";

    public static final String DELIVERY_ID = "deliveryId";

    public static final String SERVICE_PROVIDER = "serviceProvider";

    public static final String CODE = "DELIVERY_SUBMITTED";

    public static final String NAME = "Template for submitted delivery email";

    public static final String DESCRIPTION = "Template for submitted delivery email";

    @Override
    public Map<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(DELIVERY_ID, "");
        params.put(DELIVERY_URL, "");
        params.put(HOME_URL, "");
        params.put(SERVICE_PROVIDER, "");
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
