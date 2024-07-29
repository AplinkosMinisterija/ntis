package lt.project.ntis.job.request.order;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("ORDER_SUBMITTED")
public class OrderSubmittedEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(OrderSubmittedEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String ORDER_URL = "orderUrl";

    public static final String SERVICE_TYPE = "serviceType";

    public static final String ORDER_ID = "orderId";

    public static final String ORDER_DATE = "orderDate";

    public static final String CLIENT_NAME = "clientName";

    public static final String CODE = "ORDER_SUBMITTED";

    public static final String NAME = "Template for submitted order email";

    public static final String DESCRIPTION = "Template for submitted order email";

    @Override
    public Map<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ORDER_ID, "");
        params.put(ORDER_URL, "");
        params.put(HOME_URL, "");
        params.put(SERVICE_TYPE, "");
        params.put(ORDER_DATE, "");
        params.put(CLIENT_NAME, "");
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
