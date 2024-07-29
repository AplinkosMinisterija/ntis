package lt.project.ntis.app.job.request.contract.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("CONTRACT_EXPIRATION_EMAIL_JOB")
public class ContractExpirationEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ContractExpirationEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String CONTRACT_ID = "contractId";

    public static final String REMAINING_DAYS = "remainingDays";

    public static final String CONTRACT_URL = "contractUrl";

    public static final String CODE = "CONTRACT_EXPIRATION_EMAIL_JOB";

    public static final String NAME = "Template for service contract expiration email";

    public static final String DESCRIPTION = "Email template to notify INTS owner of service contract expiration";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(HOME_URL, "");
        params.put(CONTRACT_ID, "");
        params.put(REMAINING_DAYS, "");
        params.put(CODE, "");
        params.put(CONTRACT_URL, "");
        params.put(NAME, "");
        params.put(DESCRIPTION, "");
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
