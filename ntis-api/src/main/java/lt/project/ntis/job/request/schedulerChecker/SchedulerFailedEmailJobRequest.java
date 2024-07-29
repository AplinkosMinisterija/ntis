package lt.project.ntis.job.request.schedulerChecker;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("SCHEDULER_FAILED_EMAIL")
public class SchedulerFailedEmailJobRequest extends JobRequestImpl {

    public static final String HOME_URL = "homeLink";

    public static final String JOB_NAME = "jobName";

    public static final String JOB_CODE = "jobCode";

    public static final String JDE_ID = "jdeId";

    public static final String CODE = "SCHEDULER_FAILED_EMAIL";

    public static final String NAME = "Template for failed scheduler background job";

    public static final String DESCRIPTION = "Template for failed scheduler background job";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(JOB_NAME, "");
        params.put(JOB_CODE, "");
        params.put(HOME_URL, "");
        params.put(JDE_ID, "");
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
