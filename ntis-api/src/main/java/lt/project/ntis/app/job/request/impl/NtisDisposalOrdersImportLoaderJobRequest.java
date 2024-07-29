package lt.project.ntis.app.job.request.impl;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.request.impl.JobRequestImpl;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("NTIS_DISPOSAL_ORDERS_LOADER")
public class NtisDisposalOrdersImportLoaderJobRequest extends JobRequestImpl {

    public static final String CODE = "NTIS_DISPOSAL_ORDERS_LOADER";

    public static final String NAME = "Nuotekų išvežimo užsakymų užkrovimas";

    public static final String DESCRIPTION = "Paslaugų teikimo įmonių nuotekų išvežimo užsakymų duomenų užkrovimas";

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
