package eu.itreegroup.spark.dao.daogen;

public class MiddleLayerClassess4DBGenerator extends GenerateClassess4DB {

    public MiddleLayerClassess4DBGenerator(String homePath, String dbType, String dbConnection, String dbUser, String dbPassword) {
        super(homePath, dbType, dbConnection, dbUser, dbPassword, GENERATE_ALL);
    }

    public MiddleLayerClassess4DBGenerator(String homePath, String dbType, String dbConnection, String dbUser, String dbPassword, int generationMode) {
        super(homePath, dbType, dbConnection, dbUser, dbPassword, generationMode);
    }

    public void generateCoreModule() {
        String adminDaoPath = "eu.itreegroup.spark.modules.admin.dao";
        String adminServicePath = "eu.itreegroup.spark.modules.admin.service";
        generateStuff("SPR_USERS", "USR_ID", "SPARK.SPR_USR_SEQ", "N", adminDaoPath, "SprUsersDAO", adminServicePath, "SprUsersDBService", "Gen",
                "SprUsersDBService");
        generateStuff("SPR_USER_SESSIONS_OPEN", "SES_ID", "SPARK.SPR_SES_SEQ", "N", adminDaoPath, "SprSessionOpenDAO", adminServicePath,
                "SprSessionOpenDBService", "Gen", "SprSessionOpenDBService");
        generateStuff("SPR_USER_SESSIONS_CLOSED", "SEC_ID", "SPARK.SPR_SEC_SEQ", "N", adminDaoPath, "SprSessionClosedDAO", adminServicePath,
                "SprSessionClosedDBService", "Gen", "SprSessionClosedDBService");
        generateStuff("SPR_USER_EXT_IDENTIFICATIONS", "EID_ID", "SPARK.SPR_EID_SEQ", "N", adminDaoPath, "SprUserExtIdentificationsDAO", adminServicePath,
                "SprUserExtIdentificationsDBService", "Gen", "SprUserExtIdentificationsDBService");
        generateStuff("SPR_PROPERTIES", "PRP_ID", "SPARK.SPR_PRP_SEQ", "N", adminDaoPath, "SprPropertiesDAO", adminServicePath, "SprPropertiesDBService", "Gen",
                "SprPropertiesDBService");
        generateStuff("SPR_ROLES", "ROL_ID", "SPARK.SPR_ROL_SEQ", "N", adminDaoPath, "SprRolesDAO", adminServicePath, "SprRolesDBService", "Gen",
                "SprRolesDBService");
        generateStuff("SPR_ROLE_ACTIONS", "ROA_ID", "SPARK.SPR_ROA_SEQ", "N", adminDaoPath, "SprRoleActionsDAO", adminServicePath, "SprRoleActionsDBService",
                "Gen", "SprRoleActionsDBService");
        generateStuff("SPR_ORGANIZATIONS", "ORG_ID", "SPARK.SPR_ORG_SEQ", "N", adminDaoPath, "SprOrganizationsDAO", adminServicePath,
                "SprOrganizationsDBService", "Gen", "SprOrganizationsDBService");
        generateStuff("SPR_PERSONS", "PER_ID", "SPARK.SPR_PER_SEQ", "N", adminDaoPath, "SprPersonsDAO", adminServicePath, "SprPersonsDBService", "Gen",
                "SprPersonsDBService");
        generateStuff("SPR_FORMS", "FRM_ID", "SPARK.SPR_FRM_SEQ", "N", adminDaoPath, "SprFormsDAO", adminServicePath, "SprFormsDBService", "Gen",
                "SprFormsDBService");
        generateStuff("SPR_FORM_ACTIONS", "FRA_ID", "SPARK.SPR_FRA_SEQ", "N", adminDaoPath, "SprFormActionsDAO", adminServicePath, "SprFormActionsDBService",
                "Gen", "SprFormActionsDBService");
        generateStuff("SPR_MENU_STRUCTURES", "MST_ID", "SPARK.SPR_MST_SEQ", "N", adminDaoPath, "SprMenuStructuresDAO", adminServicePath,
                "SprMenuStructuresDBService", "Gen", "SprMenuStructuresDBService");
        generateStuff("SPR_USER_ROLES", "URO_ID", "SPARK.SPR_URO_SEQ", "N", adminDaoPath, "SprUserRolesDAO", adminServicePath, "SprUserRolesDBService", "Gen",
                "SprUserRolesDBService");
        generateStuff("SPR_REF_CODES", "RFC_ID", "SPARK.SPR_RFC_SEQ", "N", adminDaoPath, "SprRefCodesDAO", adminServicePath, "SprRefCodesDBService", "Gen",
                "SprRefCodesDBService");
        generateStuff("SPR_REF_DICTIONARIES", "RFD_ID", "SPARK.SPR_RFD_SEQ", "N", adminDaoPath, "SprRefDictionariesDAO", adminServicePath,
                "SprRefDictionariesDBService", "Gen", "SprRefDictionariesDBService");
        generateStuff("SPR_REF_TRANSLATIONS", "RFT_ID", "SPARK.SPR_RFT_SEQ", "N", adminDaoPath, "SprRefTranslationsDAO", adminServicePath,
                "SprRefTranslationDBService", "Gen", "SprRefTranslationDBService");
        generateStuff("SPR_ROLE_DISABLED_ACTIONS", "RDA_ID", "SPR_RDA_SEQ", "N", adminDaoPath, "SprRoleDisabledActionsDAO", adminServicePath,
                "SprRoleDisabledActionsDBService", "Gen", "SprRoleDisabledActionsDBService");
        generateStuff("SPR_TEMPLATES", "TML_ID", "SPR_TML_SEQ", "N", adminDaoPath, "SprTemplatesDAO", adminServicePath, "SprTemplatesDBService", "Gen",
                "SprTemplatesDBService");
        generateStuff("SPR_TEMPLATE_TEXTS", "TMT_ID", "SPR_TMT_SEQ", "N", adminDaoPath, "SprTemplateTextsDAO", adminServicePath, "SprTemplateTextsDBService",
                "Gen", "SprTemplateTextsDBService");
        generateStuff("SPR_JOB_REQUESTS", "JRQ_ID", "SPR_JRQ_SEQ", "N", adminDaoPath, "SprJobRequestsDAO", adminServicePath, "SprJobRequestsDBService", "Gen",
                "SprJobRequestsDBService");
        generateStuff("SPR_JOB_DEFINITIONS", "JDE_ID", "SPR_JDE_SEQ", "N", adminDaoPath, "SprJobDefinitionsDAO", adminServicePath, "SprJobDefinitionsDBService",
                "Gen", "SprJobDefinitionsDBService");
        generateStuff("SPR_JOB_REQUEST_ARGS", "JRA_ID", "SPR_JRA_SEQ", "N", adminDaoPath, "SprJobRequestArgsDAO", adminServicePath,
                "SprJobRequestArgsDBService", "Gen", "SprJobRequestArgsDBService");
        generateStuff("SPR_JOB_REQUEST_AUTHORS", "JRT_ID", "SPR_JRT_SEQ", "N", adminDaoPath, "SprJobRequestAuthorsDAO", adminServicePath,
                "SprJobRequestAuthorsDBService", "Gen", "SprJobRequestAuthorsDBService");
        generateStuff("SPR_JOB_REQUEST_EXECUTIONS", "JRE_ID", "SPR_JRE_SEQ", "N", adminDaoPath, "SprJobRequestExecutionsDAO", adminServicePath,
                "SprJobRequestExecutionsDBService", "Gen", "SprJobRequestExecutionsDBService");
        generateStuff("SPR_FORM_DATA_FILTERS", "FDF_ID", "SPR_FDF_SEQ", "N", adminDaoPath, "SprFormDataFiltersDAO", adminServicePath,
                "SprFormDataFiltersDBService", "Gen", "SprFormDataFiltersDBService");
        generateStuff("SPR_PROCESS_REQUESTS", "PRQ_ID", "SPR_PRQ_SEQ", "N", adminDaoPath, "SprProcessRequestsDAO", adminServicePath,
                "SprProcessRequestsDBService", "Gen", "SprProcessRequestsDBService");
        generateStuff("SPR_AUDITABLE_TABLES", "AUT_ID", "SPR_AUT_SEQ", "N", adminDaoPath, "SprAuditableTablesDAO", adminServicePath,
                "SprAuditableTablesDBService", "Gen", "SprAuditableTablesDBService");
        generateStuff("SPR_ORG_AVAILABLE_ROLES", "OAR_ID", "SPR_OAR_SEQ", "N", adminDaoPath, "SprOrgAvailableRolesDAO", adminServicePath,
                "SprOrgAvailableRolesDBService", "Gen", "SprOrgAvailableRolesDBService");

        generateStuff("SPR_FILES", "FIL_ID", "SPR_FIL_SEQ", "N", "eu.itreegroup.spark.modules.common.dao", "SprFilesDAO",
                "eu.itreegroup.spark.modules.common.service", "SprFilesDBService", "Gen", "SprFilesDBService");

        generateStuff("SPR_NOTIFICATIONS", "NTF_ID", "SPR_NTF_SEQ", "N", "eu.itreegroup.spark.modules.common.dao", "SprNotificationsDAO",
                "eu.itreegroup.spark.modules.common.service", "SprNotificationsDBService", "Gen", "SprNotificationsDBService");

        generateStuffByTableName("SPR_QUESTION_ANSWERS", "FAC_ID", "SPR_FAC_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_QUESTION_FILES", "FCF_ID", "SPR_FCF_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_NEWS", "NW_ID", "SPR_NW_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_NEWS_FILES", "NWF_ID", "SPR_NWF_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_NEWS_COMMENTS", "NWC_ID", "SPR_NWC_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_ORG_USERS", "OU_ID", "SPR_OU_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_ORG_USER_ROLES", "OUR_ID", "SPR_OUR_SEQ", adminDaoPath, adminServicePath, true);
        generateStuffByTableName("SPR_API_KEYS", "API_ID", "SPR_API_SEQ", adminDaoPath, adminServicePath, true);

    }

    public void generateTaskModule() {
        generateStuff("SPR_TASKS", "TAS_ID", "SPR_TAS_SEQ", "N", "eu.itreegroup.spark.modules.tasks.dao", "SprTasksDAO",
                "eu.itreegroup.spark.modules.tasks.service", "SprTasksDBService", "Gen", "SprTasksDBService");
        generateStuff("SPR_TASK_ASSIGNMENTS", "TAT_ID", "SPR_TAT_SEQ", "N", "eu.itreegroup.spark.modules.tasks.dao", "SprTaskAssignmentsDAO",
                "eu.itreegroup.spark.modules.tasks.service", "SprTaskAssignmentsDBService", "Gen", "SprTaskAssignmentsDBService");
        generateStuff("SPR_TASK_FILES", "TFI_ID", "SPR_TFI_SEQ", "N", "eu.itreegroup.spark.modules.tasks.dao", "SprTaskFilesDAO",
                "eu.itreegroup.spark.modules.tasks.service", "SprTaskFilesDBService", "Gen", "SprTaskFilesDBService");
    }

    public static void main(String[] args) {
        try {

            MiddleLayerClassess4DBGenerator generator = new MiddleLayerClassess4DBGenerator(GEN_PATH, GEN_DB_TYPE, GEN_DB_ADDRESS, GEN_DB_USERNAME,
                    GEN_DB_PASSWORD, GenerateClassess4DB.GENERATE_DAO);
            generator.generateCoreModule();
            generator.generateTaskModule();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
