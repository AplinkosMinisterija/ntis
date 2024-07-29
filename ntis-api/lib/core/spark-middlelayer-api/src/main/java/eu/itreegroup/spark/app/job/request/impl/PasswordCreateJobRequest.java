package eu.itreegroup.spark.app.job.request.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("PASSWORD_CREATE_REQUEST")
public class PasswordCreateJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(PasswordCreateJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String PASSWORD_CREATE_URL = "passwordCreateUrl";

    public static final String USER_NAME = "userName";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(HOME_URL, "");
        params.put(PASSWORD_CREATE_URL, "");
        params.put(USER_NAME, "");
        params.put(ExecuteEmailSendTask.RECEIVER, "");
        return params;
    }

    @Override
    public String getCode() {
        return "PASSWORD_CREATE";
    }

    @Override
    public String getDescription() {
        return "Template for new user password";
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
