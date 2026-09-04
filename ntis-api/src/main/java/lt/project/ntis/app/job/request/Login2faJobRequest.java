package lt.project.ntis.app.job.request;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

// LOGIN_2FA_EMAIL job'as (spr_job_definitions). Velocity kintamieji šablone: $code, $homeLink.
@Component("LOGIN_2FA_EMAIL")
public class Login2faJobRequest extends JobRequestImpl {

    public static final String CODE = "code";

    public static final String HOME_URL = "homeLink";

    public static final String JOB_CODE = "LOGIN_2FA_EMAIL";

    public static final String DESCRIPTION = "Prisijungimo patvirtinimo kodo el. laiškas";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(CODE, "");
        params.put(HOME_URL, "");
        params.put(ExecuteEmailSendTask.RECEIVER, "");
        return params;
    }

    @Override
    public String getCode() {
        return JOB_CODE;
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
