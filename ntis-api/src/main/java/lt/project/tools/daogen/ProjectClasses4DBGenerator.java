package lt.project.tools.daogen;

import eu.itreegroup.spark.dao.daogen.GenerateClassess4DB;
import eu.itreegroup.spark.dao.daogen.MiddleLayerClassess4DBGenerator;

public class ProjectClasses4DBGenerator extends MiddleLayerClassess4DBGenerator {

    public static final String PROJECT_GEN_DB_TYPE = DB_TYPE_POSTGRE;

    // DEV
//    public static final String PROJECT_GEN_DB_ADDRESS = "";
    // TEST
     public static final String PROJECT_GEN_DB_ADDRESS = "";

    public static final String PROJECT_GEN_DB_USERNAME = "";

    public static final String PROJECT_GEN_DB_PASSWORD = "";

    public ProjectClasses4DBGenerator(String homePath, String dbType, String dbConnection, String dbUser, String dbPassword) {
        super(homePath, dbType, dbConnection, dbUser, dbPassword);
    }

    public void generateProjectPart() {
        String service = "lt.project.ntis.service";
        String dao = "lt.project.ntis.dao";
        // generateStuff("NTIS_CARS", "CR_ID", "NTIS_CARS_CR_ID_SEQ", "N", "lt.project.ntis.dao", "NtisCarsDAO", "lt.project.ntis.service", "NtisCarsDBService",
        // "Gen");
        // generateStuff("NTIS_SERVICES", "SRV_ID", "NTIS_SERVICES_SRV_ID_SEQ", "N", "lt.project.ntis.dao", "NtisServicesDAO", "lt.project.ntis.service",
        // "NtisServicesDBService", "Gen");
        // generateStuff("NTIS_SERVICE_MUNICIPALITIES", "SMN_ID", "NTIS_SERVICE_MUNICIPALITIES_SMN_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisServiceMunicipalitiesDAO", "lt.project.ntis.service", "NtisServiceMunicipalitiesDBService", "Gen");
        // generateStuff("NTIS_FAVORITE_SRV_PROVIDERS", "FSP_ID", "NTIS_FAVORITE_SRV_PROVIDERS_FSP_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisFavoriteSrvProvidersDAO", "lt.project.ntis.service", "NtisFavoriteSrvProvidersDBService", "Gen");
        // generateStuff("NTIS_SERVICE_REQUESTS", "SR_ID", "NTIS_SERVICE_REQUESTS_SR_ID_SEQ", "N", "lt.project.ntis.dao", "NtisServiceRequestsDAO",
        // "lt.project.ntis.service", "NtisServiceRequestsDBService", "Gen");
        // generateStuff("NTIS_SERVICE_REQ_FILES", "SRF_ID", "NTIS_SERVICE_REQ_FILES_SRF_ID_SEQ", "N", "lt.project.ntis.dao", "NtisServiceReqFilesDAO",
        // "lt.project.ntis.service", "NtisServiceReqFilesDBService", "Gen");
        // generateStuff("NTIS_SERVICE_REQ_ITEMS", "SRI_ID", "NTIS_SERVICE_REQ_ITEMS_SRI_ID_SEQ", "N", "lt.project.ntis.dao", "NtisServiceReqItemsDAO",
        // "lt.project.ntis.service", "NtisServiceReqItemsDBService", "Gen");
        // generateStuff("NTIS_SERVICE_REQ_STATUS_LOGS", "SRS_ID", "NTIS_SERVICE_REQ_STATUS_LOGS_SRS_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisServiceReqStatusLogsDAO", "lt.project.ntis.service", "NtisServiceReqStatusLogsDBService", "Gen");
        // generateStuff("NTIS_CONTRACTS", "COT_ID", "NTIS_CONTRACTS_COT_ID_SEQ", "N", "lt.project.ntis.dao", "NtisContractsDAO", "lt.project.ntis.service",
        // "NtisContractsDBService", "Gen");
        // generateStuff("NTIS_CONTRACT_SERVICES", "CS_ID", "NTIS_CONTRACT_SERVICES_CS_ID_SEQ", "N", "lt.project.ntis.dao", "NtisContractServicesDAO",
        // "lt.project.ntis.service", "NtisContractServicesDBService", "Gen");
        // generateStuff("NTIS_CONTRACT_COMMENTS", "CC_ID", "NTIS_CONTRACT_COMMENTS_CC_ID_SEQ", "N", "lt.project.ntis.dao", "NtisContractCommentsDAO",
        // "lt.project.ntis.service", "NtisContractCommentsDBService", "Gen");
        // generateStuff("NTIS_MESSAGES", "MES_ID", "NTIS_MESSAGES_MES_ID_SEQ", "N", "lt.project.ntis.dao", "NtisMessagesDAO", "lt.project.ntis.service",
        // "NtisMessagesDBService", "Gen");
        // generateStuff("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_ID", "NTIS_WASTEWATER_TREATMENT_FACI_WTF_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisWastewaterTreatmentFaciDAO", "lt.project.ntis.service", "NtisWastewaterTreatmentFaciDBService", "Gen");
        // generateStuff("NTIS_FACILITY_LOCATIONS", "FL_ID", "NTIS_FACILITY_LOCATIONS_FL_ID_SEQ", "N", "lt.project.ntis.dao", "NtisFacilityLocationsDAO",
        // "lt.project.ntis.service", "NtisFacilityLocationsDBService", "Gen");
        // generateStuff("NTIS_ORDERS", "ORD_ID", "NTIS_ORDERS_ORD_ID_SEQ", "N", "lt.project.ntis.dao", "NtisOrdersDAO", "lt.project.ntis.service",
        // "NtisOrdersDBService", "Gen");
        // generateStuff("NTIS_ORDER_COMPLETED_WORKS", "OCW_ID", "NTIS_ORDER_COMPLETED_WORKS_OCW_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisOrderCompletedWorksDAO",
        // "lt.project.ntis.service", "NtisOrderCompletedWorksDBService", "Gen");
        // generateStuff("NTIS_SERVED_OBJECTS", "SO_ID", "NTIS_SERVED_OBJECTS_SO_ID_SEQ", "N", "lt.project.ntis.dao", "NtisServedObjectsDAO",
        // "lt.project.ntis.service", "NtisServedObjectsDBService", "Gen");
        // generateStuff("NTIS_DISCHARGE_WASTEWATER", "DW_ID", "NTIS_DISCHARGE_WASTEWATER_DW_ID_SEQ", "N", "lt.project.ntis.dao", "NtisDischargeWastewaterDAO",
        // "lt.project.ntis.service", "NtisDischargeWastewaterDBService", "Gen");
        // generateStuff("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_ID", "NTIS_WASTEWATER_TREATMENT_ORGS_WTO_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisWastewaterTreatmentOrgDAO", "lt.project.ntis.service", "NtisWastewaterTreatmentOrgDBService", "Gen");
        // generateStuff("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_ID", "NTIS_FACILITY_UPDATE_AGREEMENT_FUA_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisFacilityUpdateAgreementDAO", "lt.project.ntis.service", "NtisFacilityUpdateAgreementDBService", "Gen");
        // generateStuff("NTIS_DELIVERY_FACILITIES", "DF_ID", "NTIS_DELIVERY_FACILITIES_DF_ID_SEQ", "N", "lt.project.ntis.dao", "NtisDeliveryFacilitiesDAO",
        // "lt.project.ntis.service", "NtisDeliveryFacilitiesDBService", "Gen");
        // generateStuff("NTIS_USED_SLUDGES", "US_ID", "NTIS_USED_SLUDGES_US_ID_SEQ", "N", "lt.project.ntis.dao", "NtisUsedSludgesDAO",
        // "lt.project.ntis.service",
        // "NtisUsedSludgesDBService", "Gen");
        // generateStuff("NTIS_WASTEWATER_DELIVERIES", "WD_ID", "NTIS_WASTEWATER_DELIVERIES_DW_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisWastewaterDeliveriesDAO",
        // "lt.project.ntis.service", "NtisWastewaterDeliveriesDBService", "Gen");
        // generateStuff("NTIS_BUILDING_NTRS", "BN_ID", "NTIS_BUILDING_NTRS_BN_ID_SEQ", "N", "lt.project.ntis.dao", "NtisBuildingNtrsDAO",
        // "lt.project.ntis.service", "NtisBuildingNtrsDBService", "Gen");
        // generateStuff("NTIS_CW_FILES", "CWF_ID", "NTIS_CW_FILES_CWF_ID_SEQ", "N", "lt.project.ntis.dao", "NtisCwFilesDAO", "lt.project.ntis.service",
        // "NtisCwFilesDBService", "Gen");
        // generateStuff("NTIS_CW_FILE_DATA", "CWFD_ID", "NTIS_CW_FILE_DATA_CWFD_ID_SEQ", "N", "lt.project.ntis.dao", "NtisCwFileDataDAO",
        // "lt.project.ntis.service", "NtisCwFileDataDBService", "Gen");
        // generateStuff("NTIS_CW_FILE_DATA_ERRS", "CWFDE_ID", "NTIS_CW_FILE_DATA_ERRS_CWFDE_ID_SEQ", "N", "lt.project.ntis.dao", "NtisCwFileDataErrsDAO",
        // "lt.project.ntis.service", "NtisCwFileDataErrsDBService", "Gen");
        // generateStuff("NTIS_ADR_ADDRESSES", "AD_ID", "NTIS_ADR_ADDRESSES_AD_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAdrAddressesDAO",
        // "lt.project.ntis.service", "NtisAdrAddressesDBService", "Gen");
        // generateStuff("NTIS_ADR_PAT_LRS", "APL_ID", "NTIS_ADR_PAT_LRS_APL_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAdrPatLrsDAO", "lt.project.ntis.service",
        // "NtisAdrPatLrsDBService", "Gen");
        // generateStuff("NTIS_ADR_STATS", "ADS_ID", "NTIS_ADR_STATS_ADS_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAdrStatsDAO", "lt.project.ntis.service",
        // "NtisAdrStatsDBService", "Gen");
        // generateStuff("NTIS_AGGLOMERATIONS", "AGG_ID", "NTIS_AGGLOMERATIONS_AGG_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAgglomerationsDAO",
        // "lt.project.ntis.service", "NtisAgglomerationsDBService", "Gen");
        // generateStuff("NTIS_AGGLOMERATION_GEOMS", "AG_ID", "NTIS_AGGLOMERATION_GEOMS_AG_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAgglomerationGeomsDAO",
        // "lt.project.ntis.service", "NtisAgglomerationGeomsDBService", "Gen");
        // generateStuff("NTIS_AGGLOMERATION_VERSIONS", "AV_ID", "NTIS_AGGLOMERATION_VERSIONS_AV_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisAgglomerationVersionsDAO", "lt.project.ntis.service", "NtisAgglomerationVersionsDBService", "Gen");
        // generateStuff("NTIS_AGGLOMERATION_NOTES", "AN_ID", "NTIS_AGGLOMERATION_NOTES_AN_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAgglomerationNotesDAO",
        // "lt.project.ntis.service", "NtisAgglomerationNotesDBService", "Gen");
        // generateStuff("NTIS_ADR_RESIDENCES", "RE_ID", "NTIS_ADR_RESIDENCES_RE_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAdrResidencesDAO",
        // "lt.project.ntis.service", "NtisAdrResidencesDBService", "Gen");
        // generateStuff("NTIS_ADR_STREETS", "STR_ID", "NTIS_ADR_STREETS_STR_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAdrStreetsDAO",
        // "lt.project.ntis.service",
        // "NtisAdrStreetsDBService", "Gen");
        // generateStuff("NTIS_FACILITY_UPDATE_LOGS", "FUL_ID", "NTIS_FACILITY_UPDATE_LOGS_FUL_ID_SEQ", "N", "lt.project.ntis.dao", "NtisFacilityUpdateLogDAO",
        // "lt.project.ntis.service", "NtisFacilityUpdateLogDBService", "Gen");
        // generateStuff("NTIS_FAVORITE_WAST_TREAT_ORGS", "FWTO_ID", "NTIS_FAVORITE_WAST_TREAT_ORGS_FWTO_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisFavoriteWastTreatOrgDAO", "lt.project.ntis.service", "NtisFavoriteWastTreatOrgDBService", "Gen");
        // generateStuff("NTIS_RESEARCHES", "RES_ID", "NTIS_RESEARCHES_RES_ID_SEQ", "N", "lt.project.ntis.dao", "NtisResearchDAO", "lt.project.ntis.service",
        // "NtisResearchDBService", "Gen");
        // generateStuff("NTIS_RESEARCH_NORMS", "RN_ID", "NTIS_RESEARCH_NORMS_RN_ID_SEQ", "N", "lt.project.ntis.dao", "NtisResearchNormDAO",
        // "lt.project.ntis.service", "NtisResearchNormDBService", "Gen");
        // generateStuff("NTIS_FACILITY_FILES", "FF_ID", "NTIS_FACILITY_FILES_FF_ID_SEQ", "N", "lt.project.ntis.dao", "NtisFacilityFilesDAO",
        // "lt.project.ntis.service", "NtisFacilityFilesDBService", "Gen");
        // generateStuff("NTIS_BUILDING_NTR_OWNERS", "BNO_ID", "NTIS_BUILDING_NTR_OWNERS_BNO_ID_SEQ", "N", "lt.project.ntis.dao", "NtisBuildingNtrOwnersDAO",
        // "lt.project.ntis.service", "NtisBuildingNtrOwnersDBService", "Gen");
        // generateStuff("NTIS_SELECTED_FACILITIES", "FS_ID", "NTIS_SELECTED_FACILITIES_FS_ID_SEQ", "N", "lt.project.ntis.dao", "NtisSelectedFacilitiesDAO",
        // "lt.project.ntis.service", "NtisSelectedFacilitiesDBService", "Gen");
        // generateStuff("NTIS_MUNICIPALITIES", "MP_ID", "NTIS_MUNICIPALITIES_MP_ID_SEQ", "N", "lt.project.ntis.dao", "NtisMunicipalitiesDAO",
        // "lt.project.ntis.service", "NtisMunicipalitiesDBService", "Gen");
        // generateStuff("NTIS_BUILDING_AGREEMENTS", "BA_ID", "NTIS_BUILDING_AGREEMENTS_BA_ID_SEQ", "N", "lt.project.ntis.dao", "NtisBuildingAgreementsDAO",
        // "lt.project.ntis.service", "NtisBuildingAgreementsDBService", "Gen");
        // generateStuff("NTIS_RESEARCHES", "RES_ID", "NTIS_RESEARCHES_RES_ID_SEQ", "N", "lt.project.ntis.dao", "NtisResearchesDAO", "lt.project.ntis.service",
        // "NtisResearchesDBService", "Gen");
        // generateStuff("NTIS_ADR_MAPPINGS", "AM_ID", "NTIS_ADR_MAPPINGS_AM_ID_SEQ", "N", "lt.project.ntis.dao", "NtisAdrMappingsDAO",
        // "lt.project.ntis.service",
        // "NtisAdrMappingsDBService", "Gen");
        //
        // generateStuff("NTIS_ORDER_FILES", "ORF_ID", "NTIS_ORDER_FILES_ORF_ID_SEQ", "N", "lt.project.ntis.dao", "NtisOrderFilesDAO",
        // "lt.project.ntis.service",
        // "NtisOrderFilesDBService", "Gen");
        // generateStuff("NTIS_ORDER_FILE_DATA", "ORFD_ID", "NTIS_ORDER_FILE_DATA_ORFD_ID_SEQ", "N", "lt.project.ntis.dao", "NtisOrderFileDataDAO",
        // "lt.project.ntis.service", "NtisOrderFileDataDBService", "Gen");
        // generateStuff("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_ID", "NTIS_ORDER_FILE_DATA_ERRS_ORFDE_ID_SEQ", "N", "lt.project.ntis.dao",
        // "NtisOrderFileDataErrsDAO",
        // "lt.project.ntis.service", "NtisOrderFileDataErrsDBService", "Gen");
        generateStuff("NTIS_FACILITY_MODEL", "FAM_ID", "NTIS_FACILITY_MODEL_FAM_ID_SEQ", "N", "lt.project.ntis.dao", "NtisFacilityModelDAO",
                "lt.project.ntis.service", "NtisFacilityModelDBService", "Gen");
         generateStuffByTableName("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_SERVED_OBJECTS_VERSION", "SOV_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_BUILDING_AGREEMENTS", "BA_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_FACILITY_OWNERS", "FO_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_ADR_SENIUNIJOS", "SEN_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_ADR_ADDRESSES", "AD_ID", "", dao, service, false);
        // generateStuffByTableName("NTIS_BUILDING_NTRS", "BN_ID", "", dao, service, false);

    }

    public static void main(String[] args) {
        try {

            ProjectClasses4DBGenerator generateProjectClasses4DB = new ProjectClasses4DBGenerator(GEN_PATH, PROJECT_GEN_DB_TYPE, PROJECT_GEN_DB_ADDRESS,
                    PROJECT_GEN_DB_USERNAME, PROJECT_GEN_DB_PASSWORD);
            // generateProjectClasses4DB.setGenerationMode(GenerateClassess4DB.GENERATE_SERVICE);
            // generateProjectClasses4DB.generateCoreModule();
            generateProjectClasses4DB.setGenerationMode(GenerateClassess4DB.GENERATE_ALL);
            generateProjectClasses4DB.generateProjectPart();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}