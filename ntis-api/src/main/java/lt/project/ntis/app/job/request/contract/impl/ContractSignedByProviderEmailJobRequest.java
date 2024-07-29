package lt.project.ntis.app.job.request.contract.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("CONTRACT_SIGNED_BY_PROVIDER")
public class ContractSignedByProviderEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ContractSignedByProviderEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String CONTRACT_ID = "contractId";

    public static final String SRV_PROVIDER = "srvProvider";

    public static final String CONTRACT_URL = "contractUrl";

    public static final String CODE = "CONTRACT_SIGNED_BY_PROVIDER";

    public static final String NAME = "Template for contract signed by provider email";

    public static final String DESCRIPTION = "Template for contract signed by provider email";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(HOME_URL, "");
        params.put(CONTRACT_ID, "");
        params.put(SRV_PROVIDER, "");
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
