package lt.project.ntis.app.job.request.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("NTIS_CENTRAL_DATA_LOADER")
public class NtisCentralDataLoaderJobrequest extends JobRequestImpl {

    public static final String CODE = "NTIS_CENTRAL_DATA_LOADER";

    public static final String NAME = "Vandenvalos duomenų užkrovimas";

    public static final String DESCRIPTION = "Vandenvalos įmonių pateiktų failų su aptarnaujamų objektų duomenimis užkrovimas";

    public static final String SOURCE_FILE_ID = "SRC_FILE_ID";

    public static final String SOURCE_FILE_ENCODING = "SRC_FILE_ENCODING";

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
