package lt.project.ntis.job.request.serviceRequest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("SRV_REQ_CONFIRMED")
public class SrvReqConfirmedEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SrvReqConfirmedEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String REQUEST_URL = "requestUrl";

    public static final String REQUEST_ID = "requestId";

    public static final String SERVICE_TYPE = "serviceType";

    public static final String CODE = "SRV_REQ_CONFIRMED";

    public static final String NAME = "Template for confirmed service request email";

    public static final String DESCRIPTION = "Template for confirmed service request email";

    @Override
    public Map<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(REQUEST_ID, "");
        params.put(REQUEST_URL, "");
        params.put(HOME_URL, "");
        params.put(SERVICE_TYPE, "");
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
