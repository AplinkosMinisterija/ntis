package lt.project.ntis.logic.forms.brokerws;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("NTIS_BROKERWS_NTR_UPDATE_REQUEST")
public class RcBrokerNtrDataUpdateTaskRequest extends JobRequestImpl {

    public static final String CODE = "NTIS_BROKERWS_NTR_UPDATE_REQUEST";

    public static final String DESCRIPTION = "Process registered requests to update NTR data for specified person/organisation.";

    public static final String JOB_REQUEST_PARAM_PERSON_ID = "person_id";

    public static final String JOB_REQUEST_PARAM_ORGANISATION_ID = "organisation_id";

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getType() {
        return SprJobDefinitionsDBService.PROGRAM_TYPE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getExecutorType() {
        return SprJobDefinitionsDBService.EXECUTOR_TYPE_MANUAL; // will be triggering request execution manually
    }

    @Override
    public Map<String, String> getJobRequestParams() {
        return Collections.emptyMap();
    }

}
