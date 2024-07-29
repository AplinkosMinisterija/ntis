package lt.project.ntis.logic.forms;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.app.job.request.impl.NtisDisposalOrdersImportLoaderJobRequest;
import lt.project.ntis.app.job.request.impl.NtisResearchOrdersImportLoaderJobRequest;
import lt.project.ntis.app.job.request.impl.NtisTechOrdersImportLoaderJobRequest;
import lt.project.ntis.service.NtisOrderFileDataDBService;
import lt.project.ntis.service.NtisOrderFileDataErrsDBService;
import lt.project.ntis.service.NtisOrderFilesDBService;
import lt.project.ntis.service.NtisServicesDBService;

/**
 * Klasė skirta Importuotų užsakymų sąrašo formos biznio logikai apibrėžti, kai naudotojas turi paslaugų teikėjo administratoriaus teises
 */
@Component
@Qualifier("ntisOrdImportForOrgList")
public class NtisOrdImportForOrgList extends NtisOrdImportList {

    public NtisOrdImportForOrgList(BaseControllerJDBC baseControllerJDBC, SprFilesDBService sprFilesDBService, FileStorageService fileStorageService,
            NtisOrderFilesDBService ntisOrderFilesDBService, NtisOrderFileDataDBService ntisOrderFileDataDBService,
            NtisOrderFileDataErrsDBService ntisOrderFileDataErrsDBService, DBStatementManager dbStatementManager,
            SprJobRequestsDBService sprJobRequestsDBService, ExecutorJob executorJob, NtisTechOrdersImportLoaderJobRequest ntisTechOrdersImportLoaderJobRequest,
            NtisDisposalOrdersImportLoaderJobRequest ntisDisposalOrdersImportLoaderJobRequest,
            NtisResearchOrdersImportLoaderJobRequest ntisResearchOrdersImportLoaderJobRequest, NtisServicesDBService ntisServicesDBService, NtisCommonMethods ntisCommonMethods) {
        super(baseControllerJDBC, sprFilesDBService, fileStorageService, ntisOrderFilesDBService, ntisOrderFileDataDBService, ntisOrderFileDataErrsDBService,
                dbStatementManager, sprJobRequestsDBService, executorJob, ntisTechOrdersImportLoaderJobRequest, ntisDisposalOrdersImportLoaderJobRequest,
                ntisResearchOrdersImportLoaderJobRequest, ntisServicesDBService, ntisCommonMethods);
    }

    @Override
    public String getFormName() {
        return "NTIS_ORD_IMPORT_FOR_ORG_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Importuoti užsakymai", "Importuotų užsakymų sąrašo forma paslaugų teikėjui");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
    }

}
