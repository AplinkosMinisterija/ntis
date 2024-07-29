package eu.itreegroup.spark.app.job.request.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("PASSWORD_CHANGE_REQUEST")
public class PasswordChangeJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(PasswordChangeJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String PASSWORD_RESET_URL = "passwordResetUrl";

    public static final String USER_NAME = "userName";

    public static final String CODE = "PASSWORD_RESET";

    public static final String NAME = "Template for new user";

    public static final String DESCRIPTION = "Template for new user";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(HOME_URL, "");
        params.put(PASSWORD_RESET_URL, "");
        params.put(USER_NAME, "");
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
