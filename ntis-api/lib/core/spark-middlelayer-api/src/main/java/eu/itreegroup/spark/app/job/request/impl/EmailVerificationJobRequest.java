package eu.itreegroup.spark.app.job.request.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("EMAIL_VERIFICATION")
public class EmailVerificationJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(EmailVerificationJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String EMAIL_VERIFICATION_URL = "emailVerificationUrl";

    public static final String CODE = "EMAIL_VERIFICATION";

    public static final String NAME = "Template for email verification";

    public static final String DESCRIPTION = "Template for email verification";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(HOME_URL, "");
        params.put(EMAIL_VERIFICATION_URL, "");
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
