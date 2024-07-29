package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.job.executor.model.ErrorInfo;
import eu.itreegroup.spark.app.job.request.impl.JDBCStatementJobRequest;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.JavaObjectGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.app.job.request.impl.NtisCentralDataLoaderJobrequest;
import lt.project.ntis.app.job.request.impl.NtisCentralDataValidationJobRequest;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
import lt.project.ntis.dao.NtisCwFileDataErrsDAO;
import lt.project.ntis.dao.NtisCwFilesDAO;
import lt.project.ntis.enums.NtisCwFilStatus;
import lt.project.ntis.enums.NtisCwFileErrLevel;
import lt.project.ntis.enums.NtisCwFileErrType;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.security.NtisCwDataSecurityManager;
import lt.project.ntis.models.NtisWastewaterDataImportFile;
import lt.project.ntis.service.NtisAdrMappingsDBService;
import lt.project.ntis.service.NtisCwFileDataDBService;
import lt.project.ntis.service.NtisCwFileDataErrsDBService;
import lt.project.ntis.service.NtisCwFilesDBService;

/**
 * Klasė skirta formos "Centralizuoto nuotekų tvarkymo duomenys" (formos kodas N4200) biznio logikai apibrėžti, kai naudotojas turi ntis administratoriaus teises
 */

@Component("ntisCwDataList")
public class NtisCwDataList extends FormBase {

    public static final String VAND_ADMIN_ACTIONS = "VAND_ADMIN_ACTIONS";

    public static final String NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE = "NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    NtisCwFilesDBService cwFilesDBService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    NtisCwFileDataDBService cwFileDataDBService;

    @Autowired
    NtisCwFileDataErrsDBService cwFileDataErrsDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    NtisAdrMappingsDBService ntisAdrMappingsDBService;

    @Autowired
    NtisCentralDataLoaderJobrequest dataLoaderJobRequest;

    @Autowired
    NtisCentralDataValidationJobRequest ntisCentralDataValidationJobRequest;

    @Autowired
    ExecutorJob executorJob;

    @Autowired
    SprJobRequestsDBService sprJobRequestsDBService;

    @Autowired
    NtisCwFileDataErrsDBService NtisCwFileDataErrsDBService;

    @Autowired
    JDBCStatementJobRequest jdbcStatementJobRequest;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Autowired
    NtisNotificationsManager ntisNotificationsManager;

    @Override
    public String getFormName() {
        return "NTIS_CW_DATA_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Centralizuoto nuotekų tvarkymo duomenys", "Centralizuoto nuotekų tvarkymo duomenų forma administratoriui");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo duomenų formoje naudojamus pavyzdinius failus
     * @param conn - prisijungimas prie DB
     * @return Vector<SprFile> (failų sąrašas)
     * @throws Exception
     */
    public List<SprFile> getExemplaryFiles(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setFilKey4Encript", new StatementDataGetter[] {
                new JavaObjectGetter("fileStorageService", fileStorageService, FileStorageService.class), new StatementStringGetter("fil_key") }));
        stmt.setStatement("SELECT " + //
                "FIL.FIL_CONTENT_TYPE, " + //
                "FIL.FIL_KEY, " + //
                "FIL.FIL_NAME, " + //
                "FIL.FIL_SIZE, " + //
                "FIL.FIL_STATUS, " + //
                "FIL.FIL_STATUS_DATE " + //
                "FROM SPR_FILES FIL " + //
                "INNER JOIN SPR_PROPERTIES PRP ON FIL.FIL_ID = PRP.PRP_FIL_ID AND  PRP_NAME IN ('N4200_REGLAMENTAS', 'N4200_PAVYZDINE_VI_BYLA')");
        List<SprFile> data = queryController.selectQueryAsObjectArrayList(conn, stmt, new Data2ObjectProcessor<SprFile>(SprFile.class, methods));
        return data;
    }

    /**
     * Metodas grąžins n4200_reglamentas parametruose nurodytą nuorodą
     * @param conn - prisijungimas prie DB
     * @return IdKeyValuePair objektas
     * @throws Exception
     */
    public IdKeyValuePair getDocumentLink(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                    prp_name as id,
                    prp_value as value
                    from spr_properties
                    where prp_name = 'N4200_REGLAMENTAS'
                    and prp_type = 'S'
                """);
        List<IdKeyValuePair> data = queryController.selectQueryAsObjectArrayList(conn, stmt, IdKeyValuePair.class);

        return data != null && !data.isEmpty() ? data.get(0) : null;
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo duomenų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - organizacijos id
     * @return JSON objektas
     * @throws Exception
     */
    public String getCwFilesList(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setFilKeyEncrypted", new StatementDataGetter[] {
                new JavaObjectGetter("fileStorageService", fileStorageService, FileStorageService.class), new StatementStringGetter("fil_key") }));
        stmt.setStatement("SELECT CW.CWF_ID, " + //
                "TO_CHAR(CW.CWF_IMPORT_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS CWF_IMPORT_DATE, " + //
                "CW.CWF_STATUS, " + //
                "CW.CWF_FIL_ID, " + //
                "USR.USR_PERSON_NAME, " + //
                "USR.USR_PERSON_SURNAME, " + //
                "USR.USR_PHONE_NUMBER, " + //
                "USR.USR_EMAIL, " + //
                "concat_ws(', ', org.org_name , org.org_type , org.org_code) as org_name,  " + //
                "FIL.FIL_CONTENT_TYPE, " + //
                "FIL.FIL_KEY, " + //
                "FIL.FIL_NAME, " + //
                "FIL.FIL_SIZE, " + //
                "FIL.FIL_STATUS, " + //
                "(SELECT COUNT(*) " + //
                "FROM NTIS.NTIS_CW_FILE_DATA_ERRS CDE " + //
                "WHERE CDE.CWFDE_CWF_ID = CW.CWF_ID " + //
                "GROUP BY CW.CWF_ID) AS TOTAL_ERRORS, " + //
                "TO_CHAR(FIL.FIL_STATUS_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS FIL_STATUS_DATE " + //
                "FROM NTIS.NTIS_CW_FILES CW " + //
                "LEFT JOIN SPARK.SPR_USERS USR ON CW.CWF_USR_ID = USR.USR_ID " + //
                "INNER JOIN SPARK.SPR_FILES FIL ON FIL.FIL_ID = CW.CWF_FIL_ID " + //
                "INNER JOIN SPARK.SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = CW.CWF_ORG_ID and CW.CWF_ORG_ID = ?::int");
        stmt.addSelectParam(orgId);
        stmt.setStatementOrderPart("CW.CWF_ID DESC, CW.CWF_STATUS DESC, CW.CWF_IMPORT_DATE DESC");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart("cwf_status", StatementAndParams.PARAM_STRING, advancedParamList.get("cwf_status"));
        stmt.addParam4WherePart("fil.fil_name", StatementAndParams.PARAM_STRING, advancedParamList.get("fil_name"));
        stmt.addParam4WherePart("cwf_import_date", StatementAndParams.PARAM_DATE, advancedParamList.get("cwf_import_date"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("fil.fil_name",
                        "TO_CHAR(cwf_import_date, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisCwDataSecurityManager sqm = new NtisCwDataSecurityManager(fileStorageService);
        sqm.setFormActions(this.getFormActions(conn));
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);

        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins organizacijos pavadinimą
     * @param conn - prisijungimas prie DB
     * @param id - organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public String getOrgName(Connection conn, Double id) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                org.org_id as id,
                concat_ws(', ', org.org_name , org.org_type , org.org_code) as value
                from spr_organizations org
                where org_id = ?::int
                """);
        stmt.addSelectParam(id);
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Pagal per parametrus perduodama failo rakt, metodas sukurs naują centralizuoto nuotekų tvarkymo įrašą NTIS_CW_FILES lentelėje.
     * @param conn - prisijungimas prie DB
     * @param fileKey - failo raktas
     * @param usrId - naudotojo id, kuris užkrauna failą.
     * @param orgId - organizacijos id
     * @param language - kalbos kodas, kuruo naudotojas atliekas veiksmus.
     * @return JSON objektas
     * @throws Exception
     *
     */
    public NtisCwFilesDAO saveNewFile(Connection conn, String fileKey, Double usrId, Double orgId, Languages language) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_CREATE);
        SprFilesDAO sprFile = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileKey));
        sprFilesDBService.markAsConfirmed(conn, sprFile);
        NtisCwFilesDAO cwFile = cwFilesDBService.newRecord();
        cwFile.setCwf_fil_id(sprFile.getFil_id());
        cwFile.setCwf_import_date(Utils.getDate(new Date()));
        cwFile.setCwf_org_id(orgId);
        cwFile.setCwf_status(NtisCwFilStatus.CW_FIL_ERR_PROCESSING.getCode());
        cwFile.setCwf_status_date(Utils.getDate(new Date()));
        cwFile.setCwf_usr_id(usrId);
        cwFilesDBService.saveRecord(conn, cwFile);

        HashMap<String, String> params = dataLoaderJobRequest.getJobRequestParams();
        params.put(NtisCentralDataLoaderJobrequest.SOURCE_FILE_ID, "" + sprFile.getFil_id().intValue());
        params.put("CWF_ID", "" + cwFile.getCwf_id().intValue());
        params.put("ORG_ID", "" + cwFile.getCwf_org_id().intValue());
        Double jobRequestId = dataLoaderJobRequest.createJobRequestWithAuthors(conn, usrId, orgId, language, params);
        conn.commit();
        executorJob.execute(jobRequestId);
        conn.commit();
        SprJobRequestsDAO jobRequestDAO = sprJobRequestsDBService.loadRecord(conn, jobRequestId);
        if (SprJobRequestsDBService.REQUEST_STATUS_ERROR.equals(jobRequestDAO.getJrq_status())) {
            NtisCwFileDataErrsDAO errorDAO = NtisCwFileDataErrsDBService.newRecord();
            errorDAO.setCwfde_cwf_id(cwFile.getCwf_id());
            errorDAO.setCwfde_level(NtisCwFileErrLevel.CW_FILE_LEVEL.getCode());
            errorDAO.setCwfde_msg_code("FILE_FORMAT_ERROR");
            Gson gson = new Gson();
            try {
                ErrorInfo errorInfo = gson.fromJson(jobRequestDAO.getJrq_error(), ErrorInfo.class);
                errorDAO.setCwfde_msg_text("Pateikto failo užkrauti nepavyko. (" + errorInfo.getErrorMessage() + ")");
            } catch (Exception ex) {
                errorDAO.setCwfde_msg_text("Pateikto failo užkrauti nepavyko. Neteisingas failo formatas.)");
            }
            errorDAO.setCwfde_type(NtisCwFileErrType.CW_ERR.getCode());
            NtisCwFileDataErrsDBService.saveRecord(conn, errorDAO);
        }
        conn.commit();
        return cwFile;
    }

    public void validateFileErrs(Connection conn, Double cwfId, Double vandOrgId, Double usrId, Double sesOrgId, Languages language) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_UPDATE);
        NtisCwFilesDAO fileDAO = cwFilesDBService.loadRecordByParams(conn, "cwf_id = ?::int and cwf_org_id=?::int", new SelectParamValue(cwfId),
                new SelectParamValue(vandOrgId));
        if (fileDAO == null || fileDAO.getCwf_id() == null) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

        HashMap<String, String> params = dataLoaderJobRequest.getJobRequestParams();
        params.put(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID, "" + cwfId);

        params.put("USER_ID", "" + usrId);
        params.put("VAND_ORG_ID", "" + vandOrgId);
        params.put("SES_ORG_ID", "" + sesOrgId);
        params.put("SES_ORG_ID", "" + sesOrgId);
        if (this.isFormActionAssigned(conn, VAND_ADMIN_ACTIONS, NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE)) {
            params.put("ROLE", "" + "VAND_ADMIN");
        } else {
            params.put("ROLE", "" + "ADMIN");
        }

        Double jobRequestId = ntisCentralDataValidationJobRequest.createJobRequestWithAuthors(conn, usrId, sesOrgId, language, params);
        conn.commit();
        executorJob.execute(jobRequestId);
        conn.commit();
    }

    /**
     * Metodas ištrins centralizuoto nuotekų tvarkymo bylos failą ir su juo susijusius įrašus
     * @param conn - prisijungimas prie DB
     * @param file - ištrinamas failas
     * @throws Exception
     */
    public void deletePendingFile(Connection conn, SprFile file, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_DELETE);
        String decryptedKey = fileStorageService.decryptFileKey(file.getFil_key());
        SprFilesDAO sprFile = sprFilesDBService.loadRecordByKey(conn, decryptedKey);
        NtisCwFilesDAO cwFile = cwFilesDBService.loadRecordByFileId(conn, sprFile.getFil_id());
        if (orgId != null) {
            if (cwFile.getCwf_org_id().compareTo(orgId) != 0) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        cwFileDataErrsDBService.deleteRecordByCwfId(conn, cwFile.getCwf_id());
        cwFileDataDBService.deleteRecordByCwfId(conn, cwFile.getCwf_id());
        cwFilesDBService.deleteRecord(conn, cwFile.getCwf_id());
        fileStorageService.deleteFile(decryptedKey, sprFile.getFil_path());
        sprFilesDBService.deleteRecord(conn, sprFile.getFil_id());
    }

    /**
     * Pagal nurodytą įrašo id, metodas atnaujins importuotos bylos statusą į galutinį
     * @param conn - prisijungimas prie DB
     * @param cwfId - bylos importo įrašo id
     * @param orgId - sesijos organizacijos id
     * @throws Exception
     */
    public void updateExistingFile(Connection conn, String id, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_UPDATE);
        if (orgId != null) {
            NtisCwFilesDAO fileDAO = cwFilesDBService.loadRecordByParams(conn, "cwf_id = ?::int and cwf_org_id=?::int",
                    new SelectParamValue(Utils.getDouble(id)), new SelectParamValue(orgId));
            if (fileDAO == null || fileDAO.getCwf_id() == null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        StatementAndParams stmt = new StatementAndParams("CALL ntis.update_building_agreements(?::integer, ?::integer)");
        stmt.setWhereExists(true);
        stmt.addSelectParam(id);
        stmt.addSelectParam(usrId);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo bylos importo įrašą
     * @param conn - prisijungimas prie DB
     * @param cwfId - bylos importo įrašo id
     * @return NtisWastewaterDataImportFile objektas
     * @throws Exception
     */
    public NtisWastewaterDataImportFile getFileRecord(Connection conn, Double cwfId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT CW.CWF_ID, " //
                + "(SELECT COUNT(*) " //
                + "FROM NTIS.NTIS_CW_FILE_DATA_ERRS CDE " //
                + "WHERE CDE.CWFDE_CWF_ID = CW.CWF_ID " //
                + "GROUP BY CW.CWF_ID) AS TOTAL_ERRORS, " //
                + "(SELECT COUNT(*) " //
                + "FROM NTIS.NTIS_CW_FILE_DATA CFD " //
                + "WHERE CFD.CWFD_CWF_ID = CW.CWF_ID " //
                + "GROUP BY CW.CWF_ID) AS TOTAL_RECORDS, " //
                + "FIL.FIL_CONTENT_TYPE, " //
                + "FIL.FIL_KEY, " //
                + "FIL.FIL_NAME, " //
                + "FIL.FIL_SIZE, " //
                + "FIL.FIL_STATUS, " //
                + "TO_CHAR(FIL.FIL_STATUS_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS FIL_STATUS_DATE " //
                + "FROM NTIS.NTIS_CW_FILES CW "//
                + "INNER JOIN SPARK.SPR_FILES FIL ON FIL.FIL_ID = CW.CWF_FIL_ID AND CW.CWF_ID = ?::int");
        stmt.addSelectParam(cwfId);
        if (orgId != null) {
            stmt.addParam4WherePart("CW.CWF_ORG_ID = ?::int", orgId);
        }
        List<NtisWastewaterDataImportFile> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterDataImportFile.class);
        if (data != null && !data.isEmpty()) {
            data.get(0).setFil_key(fileStorageService.encryptFileKey(data.get(0).getFil_key()));
            return data.get(0);
        } else {
            throw new Exception("No file with id " + cwfId + " found");
        }
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo bylos importo įraše nuskaitytų klaidų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - sesijos organizacijos id
     * @return JSON objektas
     * @throws Exception
     */
    public String getErrorsList(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                     cwe.cwfde_id,
                     cwe.cwfde_column_nr,
                     cwe.cwfde_column_value,
                     cwe.cwfde_msg_code,
                     cwe.cwfde_msg_text,
                     (cwfd.cwfd_eil_nr + 1) as cwfd_eil_nr
                from ntis_cw_file_data_errs cwe
                inner join ntis_cw_files cw on cw.cwf_id = cwe.cwfde_cwf_id and cw.cwf_id = ?::int
                left join ntis_cw_file_data cwfd on cwfd.cwfd_cwf_id = cw.cwf_id and cwfd.cwfd_id = cwe.cwfde_cwfd_id
                """);
        stmt.setWhereExists(true);
        stmt.setStatementOrderPart("order by cwfd_eil_nr");
        stmt.addSelectParam(Utils.getDouble(params.getParamList().get("cwfId")));
        if (this.isFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.VAND_ADMIN_ACTIONS)) {
            stmt.addParam4WherePart("cw.cwf_org_id = ?::int ", orgId);
        }

        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        sqm.setFormActions(formActions);

        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);

    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo bylos importo įraše nuskaitytų eilučių sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - sesijos organizacijos id
     * @return JSON objektas
     * @throws Exception
     */
    public String getFileLinesList(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                            (cwd.cwfd_eil_nr + 1) as cwfd_eil_nr,
                            cwd.C03 as cwfd_pastato_kodas,
                            cwd.C02 as cwfd_patalpos_kodas,
                            cwd.C01 as cwfd_pastato_adr_kodas,
                            concat_ws(' ', cwd.cwfd_savivaldybe, cwd.cwfd_seniunija, cwd.cwfd_gyv_vieta, cwd.cwfd_gatve, cwd.cwfd_pastato_nr, cwd.cwfd_korpuso_nr, ('- ' || cwd.cwfd_buto_nr)) as cwfd_adresas,
                            coalesce(rfc_meaning, cwd.cwfd_nuot_salinimo_budas) as cwfd_nuot_salinimo_budas,
                            cwd.cwfd_prijungimo_data,
                            cwd.cwfd_atjungimo_data
                            from ntis_cw_file_data cwd
                            inner join ntis_cw_files cw on cw.cwf_id = cwd.cwfd_cwf_id and cw.cwf_id = ?::int
                            left join spr_ref_codes_vw on rfc_code = cwd.cwfd_nuot_salinimo_budas and rfc_domain = 'NTIS_TYPE_WASTEWATER_TREATMENT' and rft_lang = 'lt'
                        """);

        stmt.addSelectParam(Utils.getDouble(params.getParamList().get("cwfId")));
        if (this.isFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.VAND_ADMIN_ACTIONS)) {
            stmt.addParam4WherePart("cw.cwf_org_id = ?::int ", orgId);
        }
        if (params.getPagingParams().getOrder_clause() != null) {
            if (params.getPagingParams().getOrder_clause().contains("cwfd_adresas ASC")) {
                stmt.setStatementOrderPart("""
                            order by cwd.cwfd_savivaldybe ASC, cwd.cwfd_seniunija ASC, cwd.cwfd_gyv_vieta ASC, cwd.cwfd_gatve ASC,
                            nullif(regexp_replace(cwfd_pastato_nr, '[^0-9]','', 'g'), '')::numeric ASC,
                            nullif(regexp_replace(cwfd_buto_nr, '[^0-9]','', 'g'), '')::numeric ASC
                        """);
                params.getPagingParams().setOrder_clause(null);
            } else if (params.getPagingParams().getOrder_clause().contains("cwfd_adresas DESC")) {
                stmt.setStatementOrderPart("""
                            order by cwd.cwfd_savivaldybe DESC, cwd.cwfd_seniunija DESC, cwd.cwfd_gyv_vieta DESC, cwd.cwfd_gatve DESC,
                            nullif(regexp_replace(cwfd_pastato_nr, '[^0-9]','', 'g'), '')::numeric DESC,
                            nullif(regexp_replace(cwfd_buto_nr, '[^0-9]','', 'g'), '')::numeric DESC
                        """);
                params.getPagingParams().setOrder_clause(null);
            }
        } else {
            stmt.setStatementOrderPart("""
                        order by cwd.cwfd_eil_nr, cwd.cwfd_savivaldybe ASC, cwd.cwfd_seniunija ASC, cwd.cwfd_gyv_vieta ASC, cwd.cwfd_gatve ASC,
                        nullif(regexp_replace(cwfd_pastato_nr, '[^0-9]','', 'g'), '')::numeric ASC,
                        nullif(regexp_replace(cwfd_buto_nr, '[^0-9]','', 'g'), '')::numeric ASC
                    """);
        }
        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo adresų sąsajas
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - organizacijos id
     * @param lang - sesijos kalba
     * @return JSON objektas
     * @throws Exception
     */
    public String getAddressMappings(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        StatementAndParams stmt = this.ntisCommonMethods.getAddressMappings(conn, params, orgId, lang);
        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisCwDataList.ACTION_UPDATE, NtisCwDataList.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins NtisAdrMappingsDAO įrašą pagal perduodamą įrašo id. 
     * @param conn - prisijungimas prie DB
     * @param recordIdentifier - RecordIdentifier objektas su ieškomo įrašo id ir veiksmo tipu
     * @return NtisAdrMappingsDAO objektas
     * @throws Exception
     */
    public NtisAdrMappingsDAO getAdrMapping(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws NumberFormatException, Exception {
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_CREATE);
        } else {
            this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_READ);
        }
        return this.ntisCommonMethods.getAdrMapping(conn, recordIdentifier, orgId);
    }

    public NtisAdrMappingsDAO saveAdrMapping(Connection conn, NtisAdrMappingsDAO record, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? NtisCwDataList.ACTION_CREATE : NtisCwDataList.ACTION_UPDATE);
        return this.ntisCommonMethods.saveAdrMapping(conn, record, orgId);
    }

    public void deleteAdrMapping(Connection conn, Double id, Double orgId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_DELETE);
        this.ntisCommonMethods.deleteAdrMapping(conn, id, orgId);
    }

    public void setFileForErrProcessing(Connection conn, Double id, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_UPDATE);
        if (orgId != null) {
            NtisCwFilesDAO fileDAO = cwFilesDBService.loadRecordByParams(conn, "cwf_id = ?::int and cwf_org_id=?::int", new SelectParamValue(id),
                    new SelectParamValue(orgId));
            if (fileDAO == null || fileDAO.getCwf_id() == null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        cwFileDataErrsDBService.deleteRecordByCwfId(conn, id);
        NtisCwFilesDAO cwFile = cwFilesDBService.loadRecord(conn, id);
        cwFile.setCwf_status(NtisCwFilStatus.CW_FIL_ERR_PROCESSING.getCode());
        cwFilesDBService.saveRecord(conn, cwFile);
    }

    public void revalidateErrors(Connection conn, Double id, Double vandOrgId, Double usrId, Double sesOrgId, Languages language)
            throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataList.ACTION_UPDATE);
        NtisCwFilesDAO fileDAO = cwFilesDBService.loadRecordByParams(conn, "cwf_id = ?::int and cwf_org_id=?::int", new SelectParamValue(id),
                new SelectParamValue(vandOrgId));
        if (fileDAO == null || fileDAO.getCwf_id() == null) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        HashMap<String, String> params = dataLoaderJobRequest.getJobRequestParams();
        params.put(NtisCentralDataValidationJobRequest.SOURCE_FILE_ID, "" + id);

        params.put("USER_ID", "" + usrId);
        params.put("VAND_ORG_ID", "" + vandOrgId);
        params.put("SES_ORG_ID", "" + sesOrgId);
        params.put("SES_ORG_ID", "" + sesOrgId);
        if (this.isFormActionAssigned(conn, VAND_ADMIN_ACTIONS, NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE)) {
            params.put("ROLE", "" + "VAND_ADMIN");
        } else {
            params.put("ROLE", "" + "ADMIN");
        }

        Double jobRequestId = ntisCentralDataValidationJobRequest.createJobRequestWithAuthors(conn, usrId, sesOrgId, language, params);
        conn.commit();
        executorJob.execute(jobRequestId);
        conn.commit();
    }
}
