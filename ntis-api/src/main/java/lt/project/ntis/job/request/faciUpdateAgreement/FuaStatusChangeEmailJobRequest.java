package lt.project.ntis.job.request.faciUpdateAgreement;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("FACILITY_UPDATE_STATUS_CHANGE")
public class FuaStatusChangeEmailJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(FuaStatusChangeEmailJobRequest.class);

    public static final String HOME_URL = "homeLink";

    public static final String FUA_URL = "fuaUrl";

    public static final String FUA_STATE = "fuaState"; 

    public static final String FUA_DATE = "fuaDate";

    public static final String FUA_ID = "fuaId";

    public static final String REVIEWER = "reviewer";

    public static final String CODE = "FACILITY_UPDATE_STATUS_CHANGE";

    public static final String NAME = "Template for facility update agreement status change email";

    public static final String DESCRIPTION = "Template for facility update agreement status change email";

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(FUA_STATE, "");
        params.put(FUA_URL, "");
        params.put(HOME_URL, "");
        params.put(FUA_DATE, "");
        params.put(FUA_ID, "");
        params.put(REVIEWER, "");
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
