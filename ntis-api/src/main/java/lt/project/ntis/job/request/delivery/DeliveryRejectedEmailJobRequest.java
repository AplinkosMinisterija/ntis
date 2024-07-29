package lt.project.ntis.job.request.delivery;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("DELIVERY_REJECTED")
public class DeliveryRejectedEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(DeliveryRejectedEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String DELIVERY_URL = "deliveryUrl";

    public static final String DELIVERY_ID = "deliveryId";

    public static final String CODE = "DELIVERY_REJECTED";

    public static final String NAME = "Template for rejected delivery email";

    public static final String DESCRIPTION = "Template for rejected delivery email";

    @Override
    public Map<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(DELIVERY_ID, "");
        params.put(DELIVERY_URL, "");
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
