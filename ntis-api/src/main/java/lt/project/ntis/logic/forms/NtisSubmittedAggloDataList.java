package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.job.request.impl.CmdJobRequest;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisAgglomerationVersionsDAO;
import lt.project.ntis.dao.NtisAgglomerationsDAO;
import lt.project.ntis.enums.NtisAgloStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.processRequests.AgglomerationEmailsProcessRequest;
import lt.project.ntis.models.NtisSubmitAggloData;
import lt.project.ntis.service.NtisAgglomerationVersionsDBService;
import lt.project.ntis.service.NtisAgglomerationsDBService;

/**
 * Klasė skirta formų G5000 ir "AGLOMERACIJOS DUOMENŲ VALDYMAS" (nėra kodo) biznio logikai apibrėžti
 * 
 */

@Component
public class NtisSubmittedAggloDataList extends FormBase {

    public static String ACTION_READ_ALL = "READ_ALL";

    public static String ACTION_READ_ALL_DESC = "Read all records right";

    public static String MINISTRY_ORG_ID = "MINISTRY_ORG_ID";

    private static final Logger log = LoggerFactory.getLogger(NtisSubmittedAggloDataList.class);

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final DBPropertyManager dbPropertyManager;

    private final SprOrganizationsDBServiceImpl sprOrganizationsDBServiceImpl;

    private final SprUsersDBService sprUsersDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final SprRefCodesDBService sprRefCodesDBService;

    private final NtisAgglomerationsDBService ntisAgglomerationsDBService;

    private final NtisAgglomerationVersionsDBService ntisAgglomerationVersionsDBService;

    private final SprFilesDBService sprFilesDBService;

    private final FileStorageService fileStorageService;

    private final CmdJobRequest cmdJobRequest;

    private final ExecutorJob executorJob;

    private final AgglomerationEmailsProcessRequest agglomerationEmailsProcessRequest;

    private final NtisNotificationsManager ntisNotificationsManager;

    private final SprJobRequestsDBService sprJobRequestsDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisSubmittedAggloDataList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, DBPropertyManager dbPropertyManager,
            SprOrganizationsDBServiceImpl sprOrganizationsDBServiceImpl, SprUsersDBService sprUsersDBService, SprPersonsDBService sprPersonsDBService,
            SprRefCodesDBService sprRefCodesDBService, NtisAgglomerationsDBService ntisAgglomerationsDBService,
            NtisAgglomerationVersionsDBService ntisAgglomerationVersionsDBService, SprFilesDBService sprFilesDBService, FileStorageService fileStorageService,
            CmdJobRequest cmdJobRequest, ExecutorJob executorJob, AgglomerationEmailsProcessRequest agglomerationEmailsProcessRequest,
            NtisNotificationsManager ntisNotificationsManager, SprJobRequestsDBService sprJobRequestsDBService, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.dbPropertyManager = dbPropertyManager;
        this.sprOrganizationsDBServiceImpl = sprOrganizationsDBServiceImpl;
        this.sprUsersDBService = sprUsersDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.sprRefCodesDBService = sprRefCodesDBService;
        this.ntisAgglomerationsDBService = ntisAgglomerationsDBService;
        this.ntisAgglomerationVersionsDBService = ntisAgglomerationVersionsDBService;
        this.sprFilesDBService = sprFilesDBService;
        this.fileStorageService = fileStorageService;
        this.cmdJobRequest = cmdJobRequest;
        this.executorJob = executorJob;
        this.agglomerationEmailsProcessRequest = agglomerationEmailsProcessRequest;
        this.ntisNotificationsManager = ntisNotificationsManager;
        this.sprJobRequestsDBService = sprJobRequestsDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Klasė skirta formų G5000 ir \"AGLOMERACIJOS DUOMENŲ VALDYMAS\" biznio logikai apibrėžti",
                "Klasė skirta formų G5000 ir \"AGLOMERACIJOS DUOMENŲ VALDYMAS\" biznio logikai apibrėžti");
        addFormActions(FormBase.ACTION_CREATE, FormBase.ACTION_READ);
        addFormAction(NtisSubmittedAggloDataList.ACTION_READ_ALL, NtisSubmittedAggloDataList.ACTION_READ_ALL_DESC,
                NtisSubmittedAggloDataList.ACTION_READ_ALL_DESC);
    }

    @Override
    public String getFormName() {
        return "NTIS_SUBMITTED_AGGLO_DATA_LIST";
    }

    public String getDataList(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("" //
                + "SELECT agl.agg_id AS id, " //
                + "       COALESCE(mun_rfc.rfc_meaning, agl.agg_municipality::text) AS municipality, " //
                + "       agl.agg_state AS status_code, " //
                + "       COALESCE(state_rfc.rfc_meaning, agl.agg_state) AS status, " //
                + "       TO_CHAR(agl.agg_state_date, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS status_date " //
                + "  FROM ntis.ntis_agglomerations agl " //
                + "  LEFT JOIN spark.spr_ref_codes_vw mun_rfc ON mun_rfc.rfc_code = agl.agg_municipality::text AND mun_rfc.rfc_domain = 'NTIS_MUNICIPALITIES' AND mun_rfc.rft_lang = ? " //
                + "  LEFT JOIN spark.spr_ref_codes_vw state_rfc ON state_rfc.rfc_code = agl.agg_state AND state_rfc.rfc_domain = 'NTIS_AGLO_STATUS' AND state_rfc.rft_lang = ? " //
                + "  WHERE agl.agg_agglo_type = 'A'");
        stmt.setWhereExists(true);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart("agl.agg_municipality::text", StatementAndParams.PARAM_STRING, advancedParamList.get("municipality"));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("agl.agg_state_date"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("status_date"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("agl.agg_state", StatementAndParams.PARAM_STRING, advancedParamList.get("status"));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("agl.agg_id", "COALESCE(mun_rfc.rfc_meaning, agl.agg_municipality::text)",
                        "COALESCE(state_rfc.rfc_meaning, agl.agg_state)",
                        "TO_CHAR(agl.agg_state_date, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        if (!this.isFormActionAssigned(conn, ACTION_READ_ALL)) {
            SprOrganizationsNtisDAO organization = (SprOrganizationsNtisDAO) sprOrganizationsDBServiceImpl.loadRecord(conn, orgId);
            if (organization == null) {
                throw new Exception("Organization with identifier " + orgId + " not found");
            }
            if (organization.getMunicipalityCode() == null) {
                throw new SparkBusinessException(new S2Message("intsData.agglomerations.pages.submittedAggloDataList.orgTypeCode", SparkMessageType.ERROR,
                        "Municipality code is not set for organization with id \" + orgId"));
            }
            stmt.addParam4WherePart("agl.agg_municipality = ?", organization.getMunicipalityCode());
        }
        stmt.setStatementOrderPart("ORDER BY agl.agg_id DESC");
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, null);
    }

    public NtisAgglomerationsDAO submit(Connection conn, NtisSubmitAggloData data, Double orgId, Double perId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        if (orgId == null) {
            throw new Exception("No orgId provided");
        }
        SprOrganizationsNtisDAO organization = (SprOrganizationsNtisDAO) sprOrganizationsDBServiceImpl.loadRecord(conn, orgId);
        if (organization == null) {
            throw new Exception("Organization with identifier " + orgId + " not found");
        }
        if (organization.getMunicipalityCode() == null) {
            throw new Exception("Municipality code is not set for organization with id " + orgId);
        }
        SprFilesDAO filesDao = this.sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(data.getDataDocument().getFil_key()));
        if (filesDao == null) {
            throw new Exception("No valid file provided");
        }
        sprFilesDBService.markAsConfirmed(conn, filesDao);
        NtisAgglomerationsDAO agglomerationsDao = this.ntisAgglomerationsDBService.newRecord();
        agglomerationsDao.setAgg_state(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode());
        agglomerationsDao.setAgg_state_date(new Date());
        agglomerationsDao.setAgg_created(new Date());
        agglomerationsDao.setAgg_municipality(organization.getMunicipalityCode());
        agglomerationsDao.setAgg_confirmed_date(data.getApprovalDate());
        agglomerationsDao.setAgg_confirmed_document_number(data.getApprovalDocNo());
        agglomerationsDao.setAgg_agglo_type("A");
        NtisAgglomerationsDAO savedAgglo = this.ntisAgglomerationsDBService.saveRecord(conn, agglomerationsDao);
        NtisAgglomerationVersionsDAO versionsDao = ntisAgglomerationVersionsDBService.newRecord();
        versionsDao.setAv_status(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode());
        versionsDao.setAv_created(new Date());
        versionsDao.setAv_agg_id(savedAgglo.getAgg_id());
        versionsDao.setAv_per_id(perId);
        versionsDao.setAv_fil_id(filesDao.getFil_id());
        versionsDao = ntisAgglomerationVersionsDBService.saveRecord(conn, versionsDao);

        HashMap<String, String> jrqParams = new HashMap<String, String>();
        jrqParams.put("av_id", Integer.toString(versionsDao.getAv_id().intValue()));
        Double jrq = this.cmdJobRequest.createJobRequest(conn, "AGGLO_LOAD_SHAPE", usrId, Languages.LT, jrqParams);
        conn.commit();
        this.executorJob.execute(jrq);

        SprJobRequestsDAO executedJrq = this.sprJobRequestsDBService.loadRecord(conn, jrq);
        if (SprJobRequestsDBService.REQUEST_STATUS_ERROR.equals(executedJrq.getJrq_status())) {
            this.ntisAgglomerationVersionsDBService.deleteRecord(conn, versionsDao.getAv_id());
            this.ntisAgglomerationsDBService.deleteRecord(conn, savedAgglo.getAgg_id());
            conn.commit();
            log.error("AGGLO_LOAD_SHAPE loadAgglomerationShape.sh script execution failed (jrq_id: " + jrq + " )");
            if ("2".equals(executedJrq.getJrq_data())) {
                throw new SparkBusinessException(new S2Message("intsData.agglomerations.error.shapeLoading", SparkMessageType.ERROR));
            } else if ("3".equals(executedJrq.getJrq_data())) {
                throw new SparkBusinessException(new S2Message("intsData.agglomerations.error.invalidShapeFormat", SparkMessageType.ERROR));
            } else {
                throw new SparkBusinessException(new S2Message("intsData.agglomerations.error.shapeLoadingUnknown", SparkMessageType.ERROR));
            }
        }

        StatementAndParams statementAndParams = new StatementAndParams("SELECT ntis.post_upload_agglomeration(?::integer)");
        statementAndParams.addSelectParam(jrq);
        try {
            this.baseControllerJDBC.adjustRecordsInDB(conn, statementAndParams);
        } catch (Exception e) {
            this.ntisAgglomerationVersionsDBService.deleteRecord(conn, versionsDao.getAv_id());
            this.ntisAgglomerationsDBService.deleteRecord(conn, savedAgglo.getAgg_id());
            conn.commit();
            throw e;
        }
        return agglomerationsDao;
    }

    /**
     * Metodas išsiųs pranešimus ir email'us aglomeraciją įkėlusiam žmogui ir aplinkos ministerijai pagal pateiktą aglomeracijos id
     * @param conn - prisijungimas prie DB
     * @param aggId - aglomeracijos id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @param perId - sesijos asmens id
     * @throws Exception
     */
    public void sendNotifications(Connection conn, Double aggId, Double usrId, Double orgId, String lang, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);

        SprUsersDAO municipalityUserDAO = null;
        Double ministryOrgId = Utils.getDouble(dbPropertyManager.getPropertyByName(MINISTRY_ORG_ID, null));
        SprOrganizationsDAO ministryOrg = sprOrganizationsDBServiceImpl.loadRecord(conn, ministryOrgId);
        String ministryRoleCodes = """
                '%s', '%s'
                """.formatted(NtisRolesConstants.NTIS_ADMIN, NtisRolesConstants.GIS_ADMIN);
        List<SprUsersDAO> ministryOrgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, ministryOrg.getOrg_id(), ministryRoleCodes);
        NtisAgglomerationsDAO agglomerationDAO = this.ntisAgglomerationsDBService.loadRecord(conn, aggId);
        if (agglomerationDAO != null) {
            SprRefCodesDAO municipality = this.sprRefCodesDBService.loadRecordByParams(conn, "rfc_code = ?::text and rfc_domain = 'NTIS_MUNICIPALITIES' ",
                    new SelectParamValue(agglomerationDAO.getAgg_municipality()));
            municipalityUserDAO = this.sprUsersDBService.loadRecordByParams(conn, "usr_per_id = ?::int and usr_id = ?::int", new SelectParamValue(perId),
                    new SelectParamValue(usrId));
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("aggId", aggId.intValue());
            context.put("municipality", municipality.getRfc_description());
            // handling notifications / email for user who submitted agglo info
            if (municipalityUserDAO != null) {
                SprPersonsDAO perDAO = this.sprPersonsDBService.loadRecord(conn, perId);
                // sending email if user who submitted data has marked that they want to receive emails as well
                if (perDAO != null && perDAO.getC01() != null && YesNo.valueOf(perDAO.getC01()).getBoolean()) {
                    agglomerationEmailsProcessRequest.createAgglomerationUploadedRequest(conn, usrId, aggId, perDAO.getPer_email(),
                            Languages.getLanguageByCode(lang), context);
                }
                ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF", "AGGLO_UPLOADED_SUBJECT",
                        "AGGLO_UPLOADED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(), NtisMessageSubject.MSG_SBJ_AGGLO_UPLOADED.getCode(), new Date(),
                        usrId, orgId, null);
            }
            // handling notifications / email for admin users
            for (SprUsersDAO user : ministryOrgUsers) {
                ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF", "AGGLO_UPLOADED_SUBJECT",
                        "AGGLO_UPLOADED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(), NtisMessageSubject.MSG_SBJ_AGGLO_UPLOADED.getCode(), new Date(),
                        user.getUsr_id(), user.getUsr_org_id(), null);
                if (user.getUsr_email_confirmed() != null && user.getUsr_email_confirmed().equalsIgnoreCase(YesNo.Y.getCode())) {
                    agglomerationEmailsProcessRequest.createAgglomerationUploadedRequest(conn, user.getUsr_id(), aggId, user.getUsr_email(),
                            Languages.getLanguageByCode(lang), context);
                }
            }
        }
    }
}
