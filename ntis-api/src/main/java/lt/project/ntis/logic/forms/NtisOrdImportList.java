package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.job.executor.model.ErrorInfo;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
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
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.app.job.request.impl.NtisDisposalOrdersImportLoaderJobRequest;
import lt.project.ntis.app.job.request.impl.NtisResearchOrdersImportLoaderJobRequest;
import lt.project.ntis.app.job.request.impl.NtisTechOrdersImportLoaderJobRequest;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
import lt.project.ntis.dao.NtisOrderFileDataErrsDAO;
import lt.project.ntis.dao.NtisOrderFilesDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.enums.NtisCwFileErrLevel;
import lt.project.ntis.enums.NtisCwFileErrType;
import lt.project.ntis.enums.NtisOrdFilStatus;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.security.NtisCwDataSecurityManager;
import lt.project.ntis.models.OrgDetailsForOrderImport;
import lt.project.ntis.service.NtisOrderFileDataDBService;
import lt.project.ntis.service.NtisOrderFileDataErrsDBService;
import lt.project.ntis.service.NtisOrderFilesDBService;
import lt.project.ntis.service.NtisServicesDBService;

/**
 * Klasė skirta Importuotų užsakymų sąrašo formos biznio logikai apibrėžti, kai
 * naudotojas turi ntis administratoriaus teises
 */
@Component("ntisOrdImportList")
public class NtisOrdImportList extends FormBase {

    @Override
    public String getFormName() {
        return "NTIS_ORD_IMPORT_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Importuoti užsakymai", "Importuotų užsakymų sąrašo forma administratoriui");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
    }

    private final BaseControllerJDBC baseControllerJDBC;

    private final SprFilesDBService sprFilesDBService;

    private final FileStorageService fileStorageService;

    private final NtisOrderFilesDBService ntisOrderFilesDBService;

    private final NtisOrderFileDataDBService ntisOrderFileDataDBService;

    private final NtisOrderFileDataErrsDBService ntisOrderFileDataErrsDBService;

    private final DBStatementManager dbStatementManager;

    private final SprJobRequestsDBService sprJobRequestsDBService;

    private final ExecutorJob executorJob;

    private final NtisTechOrdersImportLoaderJobRequest ntisTechOrdersImportLoaderJobRequest;

    private final NtisDisposalOrdersImportLoaderJobRequest ntisDisposalOrdersImportLoaderJobRequest;

    private final NtisResearchOrdersImportLoaderJobRequest ntisResearchOrdersImportLoaderJobRequest;

    private final NtisServicesDBService ntisServicesDBService;

    private final NtisCommonMethods ntisCommonMethods;

    public NtisOrdImportList(BaseControllerJDBC baseControllerJDBC, SprFilesDBService sprFilesDBService, FileStorageService fileStorageService,
            NtisOrderFilesDBService ntisOrderFilesDBService, NtisOrderFileDataDBService ntisOrderFileDataDBService,
            NtisOrderFileDataErrsDBService ntisOrderFileDataErrsDBService, DBStatementManager dbStatementManager,
            SprJobRequestsDBService sprJobRequestsDBService, ExecutorJob executorJob, NtisTechOrdersImportLoaderJobRequest ntisTechOrdersImportLoaderJobRequest,
            NtisDisposalOrdersImportLoaderJobRequest ntisDisposalOrdersImportLoaderJobRequest,
            NtisResearchOrdersImportLoaderJobRequest ntisResearchOrdersImportLoaderJobRequest, NtisServicesDBService ntisServicesDBService,
            NtisCommonMethods ntisCommonMethods) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.sprFilesDBService = sprFilesDBService;
        this.fileStorageService = fileStorageService;
        this.ntisOrderFilesDBService = ntisOrderFilesDBService;
        this.ntisOrderFileDataDBService = ntisOrderFileDataDBService;
        this.ntisOrderFileDataErrsDBService = ntisOrderFileDataErrsDBService;
        this.dbStatementManager = dbStatementManager;
        this.sprJobRequestsDBService = sprJobRequestsDBService;
        this.executorJob = executorJob;
        this.ntisTechOrdersImportLoaderJobRequest = ntisTechOrdersImportLoaderJobRequest;
        this.ntisDisposalOrdersImportLoaderJobRequest = ntisDisposalOrdersImportLoaderJobRequest;
        this.ntisResearchOrdersImportLoaderJobRequest = ntisResearchOrdersImportLoaderJobRequest;
        this.ntisServicesDBService = ntisServicesDBService;
        this.ntisCommonMethods = ntisCommonMethods;
    }

    /**
     * Metodas grąžins importuotų užsakymų failų sąrašą
     * 
     * @param conn   - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId  - organizacijos id
     * @param lang   - naudotojo sesijos kalba
     * @return JSON objektas
     * @throws Exception
     */
    public String getOrdersImportFiles(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                    select orf.orf_id,
                           to_char(orf.orf_import_date, '%s') as orf_import_date,
                           orf.orf_status as orf_status_clsf,
                           coalesce(rfc.rfc_meaning, orf.orf_status) as orf_status,
                           orf.orf_fil_id,
                           usr.usr_person_name,
                           usr.usr_person_surname,
                           usr.usr_email,
                           usr.usr_phone_number,
                           fil.fil_name,
                           fil.fil_content_type,
                           fil.fil_key,
                           fil.fil_size,
                           fil.fil_status,
                           coalesce(rfv.rfc_meaning, srv.srv_type) as srv_type,
                               (select count(*)
                                  from ntis_order_file_data_errs orfde
                                  where orf.orf_id = orfde.orfde_orf_id
                                  group by orf.orf_id) as total_errors
                      from ntis.ntis_order_files orf
                inner join ntis.ntis_services srv on srv.srv_id = orf.orf_srv_id and srv.srv_org_id = orf.orf_org_id and orf.orf_org_id = ?::int
                inner join spark.spr_files fil on fil.fil_id = orf.orf_fil_id
                 left join spark.spr_ref_codes_vw rfc on rfc.rfc_code = orf.orf_status and rfc.rfc_domain = 'NTIS_ORD_FIL_STATUS' and rfc.rft_lang = ?
                 left join spark.spr_ref_codes_vw rfv on rfv.rfc_code = srv.srv_type and rfv.rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rfv.rft_lang = rfc.rft_lang
                 left join spark.spr_users usr on usr.usr_id = orf.orf_usr_id
                    """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setFilKeyEncrypted", new StatementDataGetter[] {
                new JavaObjectGetter("fileStorageService", fileStorageService, FileStorageService.class), new StatementStringGetter("fil_key") }));

        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        stmt.setStatementOrderPart("orf.orf_id desc");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart("orf.orf_status", StatementAndParams.PARAM_STRING, advancedParamList.get("orf_status"));
        stmt.addParam4WherePart("fil.fil_name", StatementAndParams.PARAM_STRING, advancedParamList.get("fil_name"));
        stmt.addParam4WherePart("srv.srv_type", StatementAndParams.PARAM_STRING, advancedParamList.get("srv_type"));
        stmt.addParam4WherePart("orf.orf_import_date", StatementAndParams.PARAM_DATE, advancedParamList.get("orf_import_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("fil.fil_name", "rfc.rfc_meaning", "rfv.rfc_meaning",
                        "TO_CHAR(orf.orf_import_date, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisCwDataSecurityManager sqm = new NtisCwDataSecurityManager(fileStorageService);
        sqm.setFormActions(this.getFormActions(conn));
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);

        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins organizacijos pavadinimą ir duomenis apie jos teikiamas
     * paslaugas
     * 
     * @param conn - prisijungimas prie DB
     * @param id   - organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public List<OrgDetailsForOrderImport> getOrgDetails(Connection conn, Double id) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_CREATE);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                        org.org_id as orgId,
                        concat_ws(', ', org.org_name , org.org_type , org.org_code) as orgName,
                        srv.srv_id as srvId,
                        srv.srv_type as srvTypeClsf,
                        (select 1 from ntis_cars where cr_org_id = ?::int and now() between cr_date_from and coalesce(cr_date_to, now()) group by cr_org_id) as car_exists
                        from spr_organizations org
                        left join ntis_services srv on srv.srv_org_id = org.org_id and current_date between srv.srv_date_from and coalesce((srv.srv_date_to) - INTERVAL '1 DAY', now())
                        where org_id = ?::int
                        """);
        stmt.addSelectParam(id);
        stmt.addSelectParam(id);
        List<OrgDetailsForOrderImport> result = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, OrgDetailsForOrderImport.class);
        return result;
    }

    /**
     * Pagal per parametrus perduodama failo rakt, metodas sukurs naują užsakymų
     * importo failo įrašą NTIS_ORDER_FILES lentelėje.
     * 
     * @param conn    - prisijungimas prie DB
     * @param fileKey - failo raktas
     * @param srvId   - paslaugų teikėjo registruotos paslaugos, kuriai įkeliami
     *                duomenys, id
     * @param usrId   - naudotojo id, kuris užkrauna failą.
     * @param orgId   - organizacijos id
     * @return JSON objektas
     * @throws Exception
     *
     */
    public Double saveNewFile(Connection conn, String fileKey, Double srvId, Double usrId, Double orgId, Languages language) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_CREATE);
        SprFilesDAO sprFile = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileKey));
        sprFilesDBService.markAsConfirmed(conn, sprFile);
        NtisOrderFilesDAO orderFile = ntisOrderFilesDBService.newRecord();
        orderFile.setOrf_fil_id(sprFile.getFil_id());
        orderFile.setOrf_import_date(new Date());
        orderFile.setOrf_org_id(orgId);
        orderFile.setOrf_status(NtisOrdFilStatus.ORD_FIL_ERR_PROCCESSING.getCode());
        orderFile.setOrf_status_date(Utils.getDate(new Date()));
        orderFile.setOrf_srv_id(srvId);
        orderFile.setOrf_usr_id(usrId);
        ntisOrderFilesDBService.saveRecord(conn, orderFile);

        String srvType = this.ntisServicesDBService
                .loadRecordByParams(conn, "srv_id = ?::int and srv_org_id = ?::int", new SelectParamValue(srvId), new SelectParamValue(orgId)).getSrv_type();
        SprJobRequestsDAO jobRequestDAO = null;
        if (srvType.equalsIgnoreCase(NtisServiceItemType.PRIEZIURA.getCode())) {
            HashMap<String, String> params = ntisTechOrdersImportLoaderJobRequest.getJobRequestParams();
            params.put(NtisTechOrdersImportLoaderJobRequest.SOURCE_FILE_ID, "" + sprFile.getFil_id().intValue());
            params.put("ORF_ID", "" + orderFile.getOrf_id().intValue());
            params.put("ORG_ID", "" + orderFile.getOrf_org_id().intValue());
            Double jobRequestId = ntisTechOrdersImportLoaderJobRequest.createJobRequestWithAuthors(conn, usrId, orgId, language, params);
            conn.commit();
            executorJob.execute(jobRequestId);
            conn.commit();
            jobRequestDAO = sprJobRequestsDBService.loadRecord(conn, jobRequestId);
        } else if (srvType.equalsIgnoreCase(NtisServiceItemType.VEZIMAS.getCode())) {
            HashMap<String, String> params = ntisDisposalOrdersImportLoaderJobRequest.getJobRequestParams();
            params.put(NtisDisposalOrdersImportLoaderJobRequest.SOURCE_FILE_ID, "" + sprFile.getFil_id().intValue());
            params.put("ORF_ID", "" + orderFile.getOrf_id().intValue());
            params.put("ORG_ID", "" + orderFile.getOrf_org_id().intValue());
            Double jobRequestId = ntisDisposalOrdersImportLoaderJobRequest.createJobRequestWithAuthors(conn, usrId, orgId, language, params);
            conn.commit();
            executorJob.execute(jobRequestId);
            conn.commit();
            jobRequestDAO = sprJobRequestsDBService.loadRecord(conn, jobRequestId);
        } else if (srvType.equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
            HashMap<String, String> params = ntisResearchOrdersImportLoaderJobRequest.getJobRequestParams();
            params.put(NtisResearchOrdersImportLoaderJobRequest.SOURCE_FILE_ID, "" + sprFile.getFil_id().intValue());
            params.put("ORF_ID", "" + orderFile.getOrf_id().intValue());
            params.put("ORG_ID", "" + orderFile.getOrf_org_id().intValue());
            Double jobRequestId = ntisResearchOrdersImportLoaderJobRequest.createJobRequestWithAuthors(conn, usrId, orgId, language, params);
            conn.commit();
            executorJob.execute(jobRequestId);
            conn.commit();
            jobRequestDAO = sprJobRequestsDBService.loadRecord(conn, jobRequestId);
        }

        if (SprJobRequestsDBService.REQUEST_STATUS_ERROR.equals(jobRequestDAO.getJrq_status())) {
            NtisOrderFileDataErrsDAO errorDAO = ntisOrderFileDataErrsDBService.newRecord();
            errorDAO.setOrfde_orf_id(orderFile.getOrf_id());
            errorDAO.setOrfde_level(NtisCwFileErrLevel.CW_FILE_LEVEL.getCode());
            errorDAO.setOrfde_msg_code("FILE_FORMAT_ERROR");
            Gson gson = new Gson();
            ErrorInfo errorInfo = gson.fromJson(jobRequestDAO.getJrq_error(), ErrorInfo.class);
            errorDAO.setOrfde_msg_text("Pateikto failo užkrauti nepavyko. (" + errorInfo.getErrorMessage() + ")");
            errorDAO.setOrfde_type(NtisCwFileErrType.CW_ERR.getCode());
            ntisOrderFileDataErrsDBService.saveRecord(conn, errorDAO);
        }
        conn.commit();
        return orderFile.getOrf_id();
    }

    public void validateFileErrs(Connection conn, Double orfId, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_UPDATE);
        NtisOrderFilesDAO fileDAO = ntisOrderFilesDBService.loadRecordByParams(conn, "orf_id = ?::int and orf_org_id=?::int", new SelectParamValue(orfId),
                new SelectParamValue(orgId));
        if (fileDAO == null || fileDAO.getOrf_id() == null) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        } else {
            NtisServicesDAO serviceDAO = ntisServicesDBService.loadRecord(conn, fileDAO.getOrf_srv_id());
            try {

                StatementAndParams stmt = new StatementAndParams("CALL ntis.validate_orders_import(?::integer, ?, ?::integer)");
                stmt.addSelectParam(orfId);
                stmt.addSelectParam(serviceDAO.getSrv_type());
                stmt.addSelectParam(usrId);
                PreparedStatement ps = conn.prepareStatement(stmt.getStatemenWithParams());
                stmt.setValues(ps);
                ps.execute();
            } catch (Exception ex) {
                conn.rollback();
                StatementAndParams stmt2 = new StatementAndParams("update ntis.ntis_order_files set orf_status = 'ORD_FIL_PENDING_ERR' where orf_id = ?::int");
                stmt2.addSelectParam(orfId);
                baseControllerJDBC.adjustRecordsInDB(conn, stmt2);
                conn.commit();
                throw new SparkBusinessException(new S2Message("common.error.fileProcessingError", SparkMessageType.ERROR));
            }
        }
    }

    /**
     * Metodas ištrins nepatvirtintą užsakymų importo failą ir su juo susijusius
     * įrašus
     * 
     * @param conn  - prisijungimas prie DB
     * @param orfId - ištrinamas failo id
     * @throws Exception
     */
    public void deletePendingFile(Connection conn, Double orfId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_DELETE);
        NtisOrderFilesDAO orderFile = ntisOrderFilesDBService.loadRecord(conn, orfId);
        if (orgId != null) {
            if (orderFile.getOrf_org_id().compareTo(orgId) != 0) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        SprFilesDAO sprFileDAO = sprFilesDBService.loadRecord(conn, orderFile.getOrf_fil_id());
        ntisOrderFileDataErrsDBService.deleteRecordByOrfId(conn, orfId);
        ntisOrderFileDataDBService.deleteRecordByOrfId(conn, orfId);
        ntisOrderFilesDBService.deleteRecord(conn, orfId);
        fileStorageService.deleteFile(sprFileDAO.getFil_key(), sprFileDAO.getFil_path());
        sprFilesDBService.deleteRecord(conn, sprFileDAO.getFil_id());
    }

    /**
     * Pagal nurodytą įrašo id, metodas atnaujins importuoto failo statusą į
     * galutinį
     * 
     * @param conn  - prisijungimas prie DB
     * @param orfId - importuoto įrašo id
     * @param orgId - sesijos organizacijos id
     * @throws Exception
     */
    public void updateExistingFile(Connection conn, Double orfId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_UPDATE);
        NtisOrderFilesDAO orderFileDAO = null;
        if (orgId != null) {
            orderFileDAO = ntisOrderFilesDBService.loadRecordByParams(conn, "orf_id = ?::int and orf_org_id = ?::int", new SelectParamValue(orfId),
                    new SelectParamValue(orgId));
            if (orderFileDAO == null || orderFileDAO.getOrf_id() == null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        } else {
            orderFileDAO = ntisOrderFilesDBService.loadRecordByParams(conn, "orf_id = ?::int", new SelectParamValue(orfId));
        }
        StatementAndParams stmt = new StatementAndParams();
        NtisServicesDAO serviceDAO = ntisServicesDBService.loadRecord(conn, orderFileDAO.getOrf_srv_id());
        if (serviceDAO.getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
            stmt.setStatement("CALL ntis.create_research_orders(?::integer)");
            stmt.addSelectParam(orfId);

        } else {
            stmt.setStatement("CALL ntis.create_imported_orders(?::integer, ?)");
            stmt.addSelectParam(orfId);
            stmt.addSelectParam(serviceDAO.getSrv_type());
        }

        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

    /**
     * Metodas grąžins adresų sąsajas
     * 
     * @param conn   - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId  - organizacijos id
     * @param lang   - sesijos kalba
     * @return JSON objektas
     * @throws Exception
     */
    public String getAddressMappings(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_READ);
        StatementAndParams stmt = this.ntisCommonMethods.getAddressMappings(conn, params, orgId, lang);
        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisOrdImportList.ACTION_UPDATE, NtisOrdImportList.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins NtisAdrMappingsDAO įrašą pagal perduodamą įrašo id.
     * 
     * @param conn             - prisijungimas prie DB
     * @param recordIdentifier - RecordIdentifier objektas su ieškomo įrašo id ir
     *                         veiksmo tipu
     * @return NtisAdrMappingsDAO objektas
     * @throws Exception
     */
    public NtisAdrMappingsDAO getAdrMapping(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws NumberFormatException, Exception {
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_CREATE);
        } else {
            this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_READ);
        }
        return this.ntisCommonMethods.getAdrMapping(conn, recordIdentifier, orgId);
    }

    public NtisAdrMappingsDAO saveAdrMapping(Connection conn, NtisAdrMappingsDAO record, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? NtisOrdImportList.ACTION_CREATE : NtisOrdImportList.ACTION_UPDATE);
        return this.ntisCommonMethods.saveAdrMapping(conn, record, orgId);
    }

    public void deleteAdrMapping(Connection conn, Double id, Double orgId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_DELETE);
        this.ntisCommonMethods.deleteAdrMapping(conn, id, orgId);
    }

    public void setFileForErrProcessing(Connection conn, Double id, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_UPDATE);
        if (orgId != null) {
            NtisOrderFilesDAO fileDAO = ntisOrderFilesDBService.loadRecordByParams(conn, "orf_id = ?::int and orf_org_id=?::int", new SelectParamValue(id),
                    new SelectParamValue(orgId));
            if (fileDAO == null || fileDAO.getOrf_id() == null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        ntisOrderFileDataErrsDBService.deleteRecordByOrfId(conn, id);
        NtisOrderFilesDAO fileDAO = ntisOrderFilesDBService.loadRecord(conn, id);
        fileDAO.setOrf_status(NtisOrdFilStatus.ORD_FIL_ERR_PROCCESSING.getCode());
        ntisOrderFilesDBService.saveRecord(conn, fileDAO);
    }

    public void revalidateErrors(Connection conn, Double id, Double orgId, Double usrId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportList.ACTION_UPDATE);
        NtisOrderFilesDAO fileDAO = ntisOrderFilesDBService.loadRecordByParams(conn, "orf_id = ?::int and orf_org_id=?::int", new SelectParamValue(id),
                new SelectParamValue(orgId));
        String srvType = null;
        if (fileDAO == null || fileDAO.getOrf_id() == null) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        } else {
            srvType = ntisServicesDBService.loadRecord(conn, fileDAO.getOrf_srv_id()).getSrv_type();
        }
        try {
            StatementAndParams stmt = new StatementAndParams("CALL ntis.validate_orders_import(?::integer, ?, ?::integer)");
            stmt.addSelectParam(id);
            stmt.addSelectParam(srvType);
            stmt.addSelectParam(usrId);
            PreparedStatement ps = conn.prepareStatement(stmt.getStatemenWithParams());
            stmt.setValues(ps);
            ps.execute();
        } catch (Exception ex) {
            conn.rollback();
            StatementAndParams stmt2 = new StatementAndParams("update ntis.ntis_order_files set orf_status = 'ORD_FIL_PENDING_ERR' where orf_id = ?::int");
            stmt2.addSelectParam(id);
            baseControllerJDBC.adjustRecordsInDB(conn, stmt2);
            conn.commit();
            throw new SparkBusinessException(new S2Message("common.error.fileProcessingError", SparkMessageType.ERROR));
        }

    }
}
