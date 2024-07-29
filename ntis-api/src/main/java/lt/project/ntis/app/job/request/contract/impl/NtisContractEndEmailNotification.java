package lt.project.ntis.app.job.request.contract.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("NTIS_CONTRACT_END_NOTIFICATION")
public class NtisContractEndEmailNotification extends JobRequestImpl {

    public static final String CODE = "NTIS_CONTRACT_END_NOTIFICATION";

    public static final String NAME = "Informavimas apie pasibaigusią sutartį";

    public static final String DESCRIPTION = "INTS savininko informavimas apie baigusią galioti paslaugų teikimo sutartį";

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
        return SprJobDefinitionsDBService.EXECUTOR_TYPE_MANUAL;
    }

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        return params;
    }

}
