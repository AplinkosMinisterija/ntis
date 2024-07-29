package lt.project.ntis.app.job.request.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("NTIS_CENTRAL_DATA_LOADER_VALIDATION")
public class NtisCentralDataValidationJobRequest extends JobRequestImpl{
    
    public static final String CODE = "NTIS_CENTRAL_DATA_LOADER_VALIDATION";

    public static final String NAME = "Vandentvarkos įkeltų duomenų validavimas";

    public static final String DESCRIPTION = "Vandenvalos įmonių pateiktų failų su aptarnaujamų objektų duomenimis validavimas";

    public static final String SOURCE_FILE_ID = "SRC_FILE_ID";

    public static final String USER_ID = "USER_ID";
    
    public static final String ROLE = "ROLE";
    
    public static final String VAND_ORG_ID = "VAND_ORG_ID";
    
    public static final String SES_ORG_ID = "SES_ORG_ID";

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
