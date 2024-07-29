package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.job.request.impl.CmdJobRequest;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.storage.impl.FileStorageServiceImpl;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
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
import eu.itreegroup.spark.modules.common.service.SprFilesDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisAgglomerationVersionsDAO;
import lt.project.ntis.dao.NtisAgglomerationsDAO;
import lt.project.ntis.enums.NtisAgloStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.model.NtisAggloRejectRequest;
import lt.project.ntis.logic.forms.model.NtisRejectedAggloVersion;
import lt.project.ntis.logic.forms.processRequests.AgglomerationEmailsProcessRequest;
import lt.project.ntis.models.NtisSubmitAggloData;
import lt.project.ntis.models.NtisSubmittedAggloData;
import lt.project.ntis.models.NtisSubmittedAggloDataGeom;
import lt.project.ntis.models.NtisSubmittedAggloDataVersion;
import lt.project.ntis.service.NtisAgglomerationVersionsDBService;
import lt.project.ntis.service.NtisAgglomerationsDBService;

/**
 * Klasė skirta formų "G5020", "G5030", "G5040", "G5050", "G5060", "G5070", "G5080" biznio logikai apibrėžti
 */
@Component
public class NtisAggloInfo extends FormBase {

    public static String ACTION_READ_ALL = "READ_ALL";

    public static String ACTION_READ_ALL_DESC = "Read all records right";

    public static String ACTION_UPDATE_STATE = "UPDATE_STATE";

    public static String ACTION_UPDATE_STATE_DESC = "Update state of record (confirm/reject) right";

    public static String MINISTRY_ORG_ID = "MINISTRY_ORG_ID";

    private static final Logger log = LoggerFactory.getLogger(NtisAggloInfo.class);

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBPropertyManager dbPropertyManager;

    private final SprOrganizationsDBServiceImpl sprOrganizationsDBServiceImpl;

    private final SprRefCodesDBService sprRefCodesDBService;

    private final AgglomerationEmailsProcessRequest agglomerationEmailsProcessRequest;

    private final SprUsersDBService sprUsersDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final NtisNotificationsManager ntisNotificationsManager;

    private final SprFilesDBServiceImpl sprFilesDBServiceImpl;

    private final NtisAgglomerationsDBService ntisAgglomerationsDBService;

    private final NtisAgglomerationVersionsDBService ntisAgglomerationVersionsDBService;

    private final DBStatementManager dbStatementManager;

    private final FileStorageServiceImpl fileStorageServiceImpl;

    private final CmdJobRequest cmdJobRequest;

    private final ExecutorJob executorJob;

    private final SprJobRequestsDBService sprJobRequestsDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisAggloInfo(BaseControllerJDBC baseControllerJDBC, DBPropertyManager dbPropertyManager,
            SprOrganizationsDBServiceImpl sprOrganizationsDBServiceImpl, SprRefCodesDBService sprRefCodesDBService,
            AgglomerationEmailsProcessRequest agglomerationEmailsProcessRequest, SprUsersDBService sprUsersDBService, SprPersonsDBService sprPersonsDBService,
            NtisNotificationsManager ntisNotificationsManager, SprFilesDBServiceImpl sprFilesDBServiceImpl,
            NtisAgglomerationsDBService ntisAgglomerationsDBService, NtisAgglomerationVersionsDBService ntisAgglomerationVersionsDBService,
            DBStatementManager dbStatementManager, FileStorageServiceImpl fileStorageServiceImpl, CmdJobRequest cmdJobRequest, ExecutorJob executorJob,
            SprJobRequestsDBService sprJobRequestsDBService, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbPropertyManager = dbPropertyManager;
        this.sprOrganizationsDBServiceImpl = sprOrganizationsDBServiceImpl;
        this.sprRefCodesDBService = sprRefCodesDBService;
        this.agglomerationEmailsProcessRequest = agglomerationEmailsProcessRequest;
        this.sprUsersDBService = sprUsersDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.ntisNotificationsManager = ntisNotificationsManager;
        this.sprFilesDBServiceImpl = sprFilesDBServiceImpl;
        this.ntisAgglomerationsDBService = ntisAgglomerationsDBService;
        this.ntisAgglomerationVersionsDBService = ntisAgglomerationVersionsDBService;
        this.dbStatementManager = dbStatementManager;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
        this.cmdJobRequest = cmdJobRequest;
        this.executorJob = executorJob;
        this.sprJobRequestsDBService = sprJobRequestsDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;

    }

    @Value("${scripts.path}")
    private String scriptsPath;

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Agglo info", "Agglo info");
        this.addFormActions(ACTION_READ, ACTION_UPDATE);
        addFormAction(ACTION_READ_ALL, ACTION_READ_ALL_DESC, ACTION_READ_ALL_DESC);
        addFormAction(ACTION_UPDATE_STATE, ACTION_UPDATE_STATE_DESC, ACTION_UPDATE_STATE_DESC);
    }

    @Override
    public String getFormName() {
        return "NTIS_AGGLO_INFO";
    }

    private ArrayList<NtisSubmittedAggloDataVersion> getAggloVersions(Connection conn, Double aggId, String lang) throws Exception {
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT av.av_id AS id,
                       TO_CHAR(av.av_created, ?) AS createdDate,
                       av.av_status AS statusCode,
                       COALESCE(rfc.rfc_meaning, av.av_status) AS status,
                       fil.fil_content_type AS fileContentType,
                       fil.fil_key AS fileKey,
                       fil.fil_name AS fileName,
                       per.per_name || ' ' || per.per_surname AS person,
                       TO_CHAR(av.av_admin_review_date, ?) AS adminReviewDate,
                       revper.per_name || ' ' || revper.per_surname AS adminReviewPerson,
                       CASE
                           WHEN ext.xmin IS NOT NULL
                               THEN array_to_json(array[ext.xmin, ext.ymin, ext.xmax, ext.ymax])::text
                           ELSE
                               NULL
                       END AS extent
                  FROM ntis.ntis_agglomeration_versions av
                  LEFT JOIN spark.spr_persons per ON per.per_id = av.av_per_id
                  LEFT JOIN spark.spr_persons revper ON revper.per_id = av.av_admin_review_per_id
                  LEFT JOIN spark.spr_files fil ON fil.fil_id = av.av_fil_id
                  LEFT JOIN spark.spr_ref_codes_vw rfc ON rfc.rfc_code = av.av_status AND rfc.rfc_domain = 'NTIS_AGLO_STATUS' AND rfc.rft_lang = ?
                  LEFT JOIN (SELECT ag_agg_id,
                                    ag_av_id,
                                    round(st_xmin(st_extent(st_transform(ag_geom, 4326)))::numeric, 4) AS xmin,
                                    round(st_ymin(st_extent(st_transform(ag_geom, 4326)))::numeric, 4) AS ymin,
                                    round(st_xmax(st_extent(st_transform(ag_geom, 4326)))::numeric, 4) AS xmax,
                                    round(st_ymax(st_extent(st_transform(ag_geom, 4326)))::numeric, 4) AS ymax
                               FROM ntis.ntis_agglomeration_geoms
                              WHERE ag_status = 'AGLO_STATUS_APPROVED'
                              GROUP BY ag_agg_id, ag_av_id) ext ON ext.ag_av_id = av.av_id AND ext.ag_agg_id = av.av_agg_id
                 WHERE av.av_agg_id = ?::int
                 ORDER BY av.av_created DESC
                """);
        statementAndParams.addSelectParam(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        statementAndParams.addSelectParam(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(aggId);

        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setFile", new StatementDataGetter[] { new StatementStringGetter("fileContentType"), new StatementStringGetter("fileKey"),
                new StatementStringGetter("fileName") }));
        Data2ObjectProcessor<NtisSubmittedAggloDataVersion> dataProcessor = new Data2ObjectProcessor<>(NtisSubmittedAggloDataVersion.class, methods);
        List<NtisSubmittedAggloDataVersion> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);

        if (queryResult != null) {
            for (NtisSubmittedAggloDataVersion queryResultItem : queryResult) {
                if (queryResultItem.getFile() != null) {
                    queryResultItem.getFile().setFil_key(fileStorageServiceImpl.encryptFileKey(queryResultItem.getFile().getFil_key()));
                }
            }
        }
        return new ArrayList<>(queryResult);
    }

    private ArrayList<String> getAggloLastCheckReport(Connection conn, Double aggId) throws SQLException {
        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "WITH av AS ( " //
                + "    SELECT av_id " //
                + "      FROM ntis.ntis_agglomeration_versions " //
                + "     WHERE av_agg_id = ?::int " //
                + "     ORDER BY av_created DESC, av_id DESC " //
                + "     LIMIT 1 " //
                + ") " //
                + "SELECT an_description " //
                + "  FROM av " //
                + " INNER JOIN ntis.ntis_agglomeration_notes an ON an_av_id = av.av_id " //
                + "ORDER BY an_created, an_id");
        statementAndParams.addSelectParam(aggId);
        List<HashMap<String, String>> queryResult = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, statementAndParams);
        if (queryResult != null) {
            ArrayList<String> result = new ArrayList<String>();
            for (HashMap<String, String> queryResultItem : queryResult) {
                result.add(queryResultItem.get("an_description"));
            }
            return result;
        }
        return null;
    }

    private List<NtisSubmittedAggloDataGeom> getAggloLastGeoms(Connection conn, Double aggId) throws Exception {
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT ag.ag_id AS id,
                       ag.ag_name AS name,
                       ST_AsGeoJSON(ST_Transform(ag.ag_geom, 3857)) AS geom
                  FROM ntis.ntis_agglomeration_geoms ag
                 INNER JOIN (SELECT av_id
                               FROM ntis.ntis_agglomeration_versions
                              WHERE av_agg_id = ?::int
                           ORDER BY av_id DESC
                              LIMIT 1) av
                         ON av.av_id = ag.ag_av_id
                                """);
        statementAndParams.addSelectParam(aggId);
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisSubmittedAggloDataGeom.class);
    }

    public NtisSubmittedAggloData getSubmittedAggloData(Connection conn, Double orgId, Double aggId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        if (orgId == null) {
            throw new Exception("No organization ID provided");
        }
        if (aggId == null) {
            throw new Exception("No agglomeration ID provided");
        }
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT agg.agg_id AS id,
                       COALESCE(mun_rfc.rfc_meaning, agg.agg_municipality::text) AS municipality,
                       agg.agg_state AS statusCode,
                       COALESCE(state_rfc.rfc_meaning, agg.agg_state) AS status,
                       TO_CHAR(agg.agg_confirmed_date, '%s') AS confirmDate,
                       agg.agg_confirmed_document_number AS confirmDocNo
                  FROM ntis.ntis_agglomerations agg
                  LEFT JOIN spark.spr_ref_codes_vw mun_rfc
                         ON mun_rfc.rfc_code = agg.agg_municipality::text
                        AND mun_rfc.rfc_domain = 'NTIS_MUNICIPALITIES'
                        AND mun_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw state_rfc
                         ON state_rfc.rfc_code = agg.agg_state
                        AND state_rfc.rfc_domain = 'NTIS_AGLO_STATUS'
                        AND state_rfc.rft_lang = ?
                  WHERE agg.agg_agglo_type = 'A'
                    AND agg.agg_id = ?::int """.formatted(this.dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(aggId);
        statementAndParams.setWhereExists(true);
        if (!this.isFormActionAssigned(conn, ACTION_READ_ALL)) {
            SprOrganizationsNtisDAO organization = (SprOrganizationsNtisDAO) sprOrganizationsDBServiceImpl.loadRecord(conn, orgId);
            if (organization == null) {
                throw new Exception("Organization with identifier " + orgId + " not found");
            }
            if (organization.getMunicipalityCode() == null) {
                throw new Exception("Municipality code is not set for organization with id " + orgId);
            }
            statementAndParams.addParam4WherePart("agg.agg_municipality = ?", organization.getMunicipalityCode());
        }
        List<NtisSubmittedAggloData> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams,
                new Data2ObjectProcessor<NtisSubmittedAggloData>(NtisSubmittedAggloData.class));
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No agglomeration found with id: " + aggId + " (organization id: " + orgId + ")");
        }
        NtisSubmittedAggloData result = queryResult.get(0);
        result.setVersions(this.getAggloVersions(conn, aggId, lang));
        if (this.isFormActionAssigned(conn, NtisAggloInfo.ACTION_UPDATE_STATE)) {
            result.setLastCheckReport(this.getAggloLastCheckReport(conn, aggId));
        }
        result.setGeoms(this.getAggloLastGeoms(conn, aggId));
        return result;
    }

    public NtisSubmittedAggloData rejectAgglo(Connection conn, Double orgId, Double perId, NtisAggloRejectRequest request, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisAggloInfo.ACTION_UPDATE_STATE);
        if (request.getAggId() == null || request.getDescription() == null) {
            throw new Exception("No aggId or description provided");
        }
        NtisAgglomerationVersionsDAO versionsDao = this.ntisAgglomerationVersionsDBService.loadRecordByParams(conn, "av_agg_id = ?::int AND av_status = ?",
                new SelectParamValue(request.getAggId()), new SelectParamValue(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode()));
        versionsDao.setAv_status(NtisAgloStatus.AGLO_STATUS_REJECTED.getCode());
        versionsDao.setAv_admin_review(request.getDescription());
        if (request.getFile() != null) {
            SprFilesDAO filesDao = this.sprFilesDBServiceImpl.loadRecordByKey(conn, fileStorageServiceImpl.decryptFileKey(request.getFile().getFil_key()));
            if (filesDao == null) {
                throw new Exception("No valid file provided");
            }
            sprFilesDBServiceImpl.markAsConfirmed(conn, filesDao);
            versionsDao.setAv_admin_review_fil_id(filesDao.getFil_id());
        }
        versionsDao.setAv_admin_review_per_id(perId);
        versionsDao.setAv_admin_review_date(new Date());
        this.ntisAgglomerationVersionsDBService.saveRecord(conn, versionsDao);

        String updateGeomsStatement = "" //
                + "UPDATE ntis.ntis_agglomeration_geoms " //
                + "   SET ag_status = ? " //
                + " WHERE ag_agg_id = ? " //
                + "   AND ag_av_id = ?::int ";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateGeomsStatement)) {
            preparedStatement.setString(1, NtisAgloStatus.AGLO_STATUS_CHECKING.getCode());
            preparedStatement.setDouble(2, request.getAggId());
            preparedStatement.setDouble(3, versionsDao.getAv_id());
            preparedStatement.execute();
        } catch (Exception ex) {
            throw ex;
        }

        NtisAgglomerationsDAO aggloDao = ntisAgglomerationsDBService.loadRecord(conn, request.getAggId());
        aggloDao.setAgg_state(NtisAgloStatus.AGLO_STATUS_REJECTED.getCode());
        aggloDao.setAgg_state_date(new Date());
        ntisAgglomerationsDBService.saveRecord(conn, aggloDao);

        return this.getSubmittedAggloData(conn, orgId, request.getAggId(), lang);
    }

    public NtisSubmittedAggloData approveAgglo(Connection conn, Double orgId, Double perId, Double usrId, Double aggId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisAggloInfo.ACTION_UPDATE_STATE);
        if (aggId == null) {
            throw new Exception("No aggId  provided");
        }
        NtisAgglomerationVersionsDAO versionsDao = this.ntisAgglomerationVersionsDBService.loadRecordByParams(conn, "av_agg_id = ?::int AND av_status = ?",
                new SelectParamValue(aggId), new SelectParamValue(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode()));
        versionsDao.setAv_status(NtisAgloStatus.AGLO_STATUS_APPROVED.getCode());
        versionsDao.setAv_admin_review_per_id(perId);
        versionsDao.setAv_admin_review_date(new Date());
        this.ntisAgglomerationVersionsDBService.saveRecord(conn, versionsDao);

        StatementAndParams updateGeomsStatement = new StatementAndParams("""
                UPDATE ntis.ntis_agglomeration_geoms
                   SET ag_status = ?
                 WHERE ag_agg_id = ?
                   AND ag_av_id = ?::int """);
        updateGeomsStatement.addSelectParam(NtisAgloStatus.AGLO_STATUS_APPROVED.getCode());
        updateGeomsStatement.addSelectParam(aggId);
        updateGeomsStatement.addSelectParam(versionsDao.getAv_id());
        this.baseControllerJDBC.adjustRecordsInDB(conn, updateGeomsStatement);

        NtisAgglomerationsDAO aggloDao = ntisAgglomerationsDBService.loadRecord(conn, aggId);
        aggloDao.setAgg_state(NtisAgloStatus.AGLO_STATUS_APPROVED.getCode());
        aggloDao.setAgg_state_date(new Date());
        ntisAgglomerationsDBService.saveRecord(conn, aggloDao);

        StatementAndParams deprecatedGeometriesStatement = new StatementAndParams("""
                 UPDATE ntis.ntis_agglomeration_geoms ag
                    SET ag_status = 'AGLO_STATUS_DEPRECATED'
                   FROM ntis.ntis_agglomerations agg
                 INNER JOIN ntis.ntis_agglomeration_versions av ON av.av_agg_id = agg.agg_id
                  WHERE ag.ag_av_id = av.av_id
                    AND agg.agg_state = 'AGLO_STATUS_APPROVED'
                    AND agg.agg_municipality = ?
                    AND agg.agg_id != ?::int
                    AND agg_agglo_type = 'A'
                """);
        deprecatedGeometriesStatement.addSelectParam(aggloDao.getAgg_municipality());
        deprecatedGeometriesStatement.addSelectParam(aggId);
        this.baseControllerJDBC.adjustRecordsInDB(conn, deprecatedGeometriesStatement);

        StatementAndParams deprecatedVersionsStatement = new StatementAndParams("""
                  UPDATE ntis.ntis_agglomeration_versions av
                     SET av_status = 'AGLO_STATUS_DEPRECATED'
                    FROM ntis.ntis_agglomerations agg
                   WHERE av.av_agg_id = agg.agg_id
                     AND agg.agg_state = 'AGLO_STATUS_APPROVED'
                     AND agg.agg_municipality = ?
                     AND agg.agg_id != ?::int
                     AND agg_agglo_type = 'A'
                """);
        deprecatedVersionsStatement.addSelectParam(aggloDao.getAgg_municipality());
        deprecatedVersionsStatement.addSelectParam(aggId);
        this.baseControllerJDBC.adjustRecordsInDB(conn, deprecatedVersionsStatement);

        StatementAndParams deprecatedAgglomerationsStatement = new StatementAndParams("""
                   UPDATE ntis.ntis_agglomerations
                     SET agg_state = 'AGLO_STATUS_DEPRECATED'
                   WHERE agg_state = 'AGLO_STATUS_APPROVED'
                     AND agg_municipality = ?
                     AND agg_id != ?::int
                     AND agg_agglo_type = 'A'
                """);
        deprecatedAgglomerationsStatement.addSelectParam(aggloDao.getAgg_municipality());
        deprecatedAgglomerationsStatement.addSelectParam(aggId);
        this.baseControllerJDBC.adjustRecordsInDB(conn, deprecatedAgglomerationsStatement);

        StatementAndParams statementAndParams = new StatementAndParams("SELECT ntis.post_approve_agglomeration(?::integer) as result");
        statementAndParams.addSelectParam(versionsDao.getAv_id());

        List<HashMap<String, String>> res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, statementAndParams);
        log.debug("   START  SELECT ntis.post_approve_agglomeration(?::integer) as result     ");
        log.debug(res.get(0).get("result"));
        log.debug("   END  SELECT ntis.post_approve_agglomeration(?::integer) as result     ");

        // this.baseControllerJDBC.adjustRecordsInDB(conn, statementAndParams);

        HashMap<String, String> jrqParams = new HashMap<>();
        jrqParams.put("av_id", Integer.toString(versionsDao.getAv_id().intValue()));
        Double jrq = this.cmdJobRequest.createJobRequest(conn, "PROCESS_APPROVED_AGGLO", usrId, Languages.LT, jrqParams);
        conn.commit();
        this.executorJob.execute(jrq);

        return this.getSubmittedAggloData(conn, orgId, aggId, lang);
    }

    public NtisRejectedAggloVersion getRejectionDetails(Connection conn, Double orgId, Double avId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisAggloInfo.ACTION_READ);
        StatementAndParams statementAndParams = new StatementAndParams("" + "SELECT av.av_id AS avId, " //
                + "       TO_CHAR(av.av_created, 'yyyy-mm-dd') AS uploadDate, " //
                + "       TO_CHAR(av.av_admin_review_date, 'yyyy-mm-dd') AS rejectDate, " //
                + "       av.av_status AS statusCode, " //
                + "       COALESCE(rfc.rfc_meaning, av.av_status) AS status, " //
                + "       per.per_name || ' ' || per.per_surname AS person, " //
                + "       av.av_admin_review AS description, " //
                + "       fil.fil_content_type AS fileContentType, " //
                + "       fil.fil_key AS fileKey, " //
                + "       fil.fil_name AS fileName, " //
                + "       COALESCE(( " //
                + "           SELECT ARRAY_TO_JSON(ARRAY_AGG(an.an_description)) " //
                + "            FROM ntis.ntis_agglomeration_notes an " //
                + "            WHERE an_av_id = av.av_id " //
                + "       ), '[]') AS notesJson " //
                + "  FROM ntis.ntis_agglomeration_versions av " //
                + " INNER JOIN ntis.ntis_agglomerations agg ON agg.agg_id = av.av_agg_id "
                + "  LEFT JOIN spark.spr_ref_codes_vw mun_rfc ON mun_rfc.rfc_code = agg.agg_municipality::text AND mun_rfc.rfc_domain = 'NTIS_MUNICIPALITIES' AND mun_rfc.rft_lang = ? " //
                + "  LEFT JOIN spark.spr_persons per ON per.per_id = av.av_per_id " //
                + "  LEFT JOIN spark.spr_files fil ON fil.fil_id = av.av_admin_review_fil_id " //
                + "  LEFT JOIN spark.spr_ref_codes_vw rfc ON rfc.rfc_code = av.av_status AND rfc.rfc_domain = 'NTIS_AGLO_STATUS' AND rfc.rft_lang = ? " //
                + " WHERE av.av_status = '" + NtisAgloStatus.AGLO_STATUS_REJECTED.getCode() + "' and agg.agg_agglo_type = 'A'");
        statementAndParams.setWhereExists(true);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addParam4WherePart("av.av_id = ?::int", avId);

        if (!this.isFormActionAssigned(conn, ACTION_READ_ALL)) {
            SprOrganizationsNtisDAO organization = (SprOrganizationsNtisDAO) sprOrganizationsDBServiceImpl.loadRecord(conn, orgId);
            if (organization == null) {
                throw new Exception("Organization with identifier " + orgId + " not found");
            }
            if (organization.getMunicipalityCode() == null) {
                throw new Exception("Municipality code is not set for organization with id " + orgId);
            }
            statementAndParams.addParam4WherePart("agg.agg_municipality = ?", organization.getMunicipalityCode());
        }

        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setFile", new StatementDataGetter[] { new StatementStringGetter("fileContentType"), new StatementStringGetter("fileKey"),
                new StatementStringGetter("fileName") }));
        Data2ObjectProcessor<NtisRejectedAggloVersion> dataProcessor = new Data2ObjectProcessor<NtisRejectedAggloVersion>(NtisRejectedAggloVersion.class,
                methods);
        List<NtisRejectedAggloVersion> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);
        NtisRejectedAggloVersion result = queryResult != null ? queryResult.get(0) : null;
        if (result != null && result.getFile() != null) {
            result.getFile().setFil_key(fileStorageServiceImpl.encryptFileKey(result.getFile().getFil_key()));
        }
        return result;
    }

    public NtisAgglomerationsDAO submitNewData(Connection conn, NtisSubmitAggloData data, Double orgId, Double perId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_UPDATE);
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
        SprFilesDAO filesDao = this.sprFilesDBServiceImpl.loadRecordByKey(conn, fileStorageServiceImpl.decryptFileKey(data.getDataDocument().getFil_key()));
        if (filesDao == null) {
            throw new Exception("No valid file provided");
        }

        NtisAgglomerationsDAO agglomerationsDao = this.ntisAgglomerationsDBService.loadRecord(conn, data.getAggId());
        if (!agglomerationsDao.getAgg_municipality().equals(organization.getMunicipalityCode())) {
            throw new Exception("Agglomeration and organization municipality codes don't match");
        }
        String oldAggState = agglomerationsDao.getAgg_state();
        Date oldAggStateDate = agglomerationsDao.getAgg_state_date();
        agglomerationsDao.setAgg_state(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode());
        agglomerationsDao.setAgg_state_date(new Date());
        this.ntisAgglomerationsDBService.saveRecord(conn, agglomerationsDao);

        NtisAgglomerationVersionsDAO versionsDao = ntisAgglomerationVersionsDBService.newRecord();
        versionsDao.setAv_status(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode());
        versionsDao.setAv_created(new Date());
        versionsDao.setAv_agg_id(agglomerationsDao.getAgg_id());
        versionsDao.setAv_per_id(perId);
        versionsDao.setAv_fil_id(filesDao.getFil_id());
        versionsDao = ntisAgglomerationVersionsDBService.saveRecord(conn, versionsDao);

        HashMap<String, String> jrqParams = new HashMap<>();
        jrqParams.put("av_id", Integer.toString(versionsDao.getAv_id().intValue()));
        Double jrq = this.cmdJobRequest.createJobRequest(conn, "AGGLO_LOAD_SHAPE", usrId, Languages.LT, jrqParams);
        conn.commit();
        this.executorJob.execute(jrq);

        SprJobRequestsDAO executedJrq = this.sprJobRequestsDBService.loadRecord(conn, jrq);
        if (SprJobRequestsDBService.REQUEST_STATUS_ERROR.equals(executedJrq.getJrq_status())) {
            this.ntisAgglomerationVersionsDBService.deleteRecord(conn, versionsDao.getAv_id());
            agglomerationsDao.setAgg_state(oldAggState);
            agglomerationsDao.setAgg_state_date(oldAggStateDate);
            this.ntisAgglomerationsDBService.saveRecord(conn, agglomerationsDao);
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
            agglomerationsDao.setAgg_state(oldAggState);
            agglomerationsDao.setAgg_state_date(oldAggStateDate);
            this.ntisAgglomerationsDBService.saveRecord(conn, agglomerationsDao);
            conn.commit();
            throw e;
        }
        sprFilesDBServiceImpl.markAsConfirmed(conn, filesDao);

        return agglomerationsDao;
    }

    /**
     * Metodas išsiųs aglomeracijos atnaujinto statuso pranešimus ir email'us aglomeraciją įkėlusiam žmogui ir aplinkos ministerijai pagal pateiktą aglomeracijos id
     * @param conn - prisijungimas prie DB
     * @param aggId - aglomeracijos id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotifications(Connection conn, Double aggId, Double usrId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        SprUsersDAO municipalityUserDAO = null;
        Double ministryOrgId = Utils.getDouble(dbPropertyManager.getPropertyByName(MINISTRY_ORG_ID, null));
        SprOrganizationsDAO ministryOrg = sprOrganizationsDBServiceImpl.loadRecord(conn, ministryOrgId);
        String ministryRoleCodes = """
                '%s', '%s'
                """.formatted(NtisRolesConstants.NTIS_ADMIN, NtisRolesConstants.GIS_ADMIN);
        List<SprUsersDAO> ministryOrgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, ministryOrg.getOrg_id(), ministryRoleCodes);
        NtisAgglomerationsDAO agglomerationDAO = this.ntisAgglomerationsDBService.loadRecord(conn, aggId);

        if (agglomerationDAO != null) {
            NtisAgglomerationVersionsDAO agglomerationVersionDAO = this.ntisAgglomerationVersionsDBService.loadRecordByParams(conn,
                    " av_agg_id = ?::int and av_status = ?", new SelectParamValue(aggId), new SelectParamValue(agglomerationDAO.getAgg_state()));
            SprRefCodesDAO municipality = this.sprRefCodesDBService.loadRecordByParams(conn, "rfc_code = ?::text and rfc_domain = 'NTIS_MUNICIPALITIES' ",
                    new SelectParamValue(agglomerationDAO.getAgg_municipality()));
            municipalityUserDAO = this.sprUsersDBService.loadRecordByParams(conn, "usr_per_id = ?::int ",
                    new SelectParamValue(agglomerationVersionDAO.getAv_per_id()));
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("aggId", aggId.intValue());
            context.put("municipality", municipality.getRfc_description());
            // Agglo was uploaded
            if (agglomerationDAO.getAgg_state().equalsIgnoreCase(NtisAgloStatus.AGLO_STATUS_CHECKING.getCode())) {
                //
                if (municipalityUserDAO != null) {
                    SprPersonsDAO perDAO = this.sprPersonsDBService.loadRecord(conn, agglomerationVersionDAO.getAv_per_id());
                    // sending email if user who submitted data has marked that they want to receive emails as well
                    if (perDAO != null && perDAO.getC01() != null && YesNo.valueOf(perDAO.getC01()).getBoolean()) {
                        agglomerationEmailsProcessRequest.createAgglomerationUploadedRequest(conn, usrId, aggId, perDAO.getPer_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF",
                            "AGGLO_UPLOADED_SUBJECT", "AGGLO_UPLOADED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGGLO_UPLOADED.getCode(), new Date(), usrId, orgId, null);
                }
                // handling notifications / email for admin users
                for (SprUsersDAO user : ministryOrgUsers) {
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF",
                            "AGGLO_UPLOADED_SUBJECT", "AGGLO_UPLOADED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGGLO_UPLOADED.getCode(), new Date(), user.getUsr_id(), user.getUsr_org_id(), null);
                    if (user.getUsr_email_confirmed() != null && user.getUsr_email_confirmed().equalsIgnoreCase(YesNo.Y.getCode())) {
                        agglomerationEmailsProcessRequest.createAgglomerationUploadedRequest(conn, user.getUsr_id(), aggId, user.getUsr_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                }
            }
            // Agglo was approved
            else if (agglomerationDAO.getAgg_state().equalsIgnoreCase(NtisAgloStatus.AGLO_STATUS_APPROVED.getCode())) {
                if (municipalityUserDAO != null) {
                    SprPersonsDAO perDAO = this.sprPersonsDBService.loadRecord(conn, agglomerationVersionDAO.getAv_per_id());
                    // sending email if user who submitted data has marked that they want to receive emails as well
                    if (perDAO != null && perDAO.getC01() != null && YesNo.valueOf(perDAO.getC01()).getBoolean()) {
                        agglomerationEmailsProcessRequest.createAgglomerationConfirmedRequest(conn, usrId, aggId, perDAO.getPer_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF",
                            "AGGLO_CONFIRMED_SUBJECT", "AGGLO_CONFIRMED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGGLO_CONFIRMED.getCode(), new Date(), usrId, orgId, null);
                }
                // handling notifications / email for admin users
                for (SprUsersDAO user : ministryOrgUsers) {
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF",
                            "AGGLO_CONFIRMED_SUBJECT", "AGGLO_CONFIRMED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGGLO_CONFIRMED.getCode(), new Date(), user.getUsr_id(), user.getUsr_org_id(), null);
                    if (user.getUsr_email_confirmed() != null && user.getUsr_email_confirmed().equalsIgnoreCase(YesNo.Y.getCode())) {
                        agglomerationEmailsProcessRequest.createAgglomerationConfirmedRequest(conn, user.getUsr_id(), aggId, user.getUsr_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                }
            }
            // Agglo was rejected
            else if (agglomerationDAO.getAgg_state().equalsIgnoreCase(NtisAgloStatus.AGLO_STATUS_REJECTED.getCode())) {
                if (municipalityUserDAO != null) {
                    SprPersonsDAO perDAO = this.sprPersonsDBService.loadRecord(conn, agglomerationVersionDAO.getAv_per_id());
                    // sending email if user who submitted data has marked that they want to receive emails as well
                    if (perDAO != null && perDAO.getC01() != null && YesNo.valueOf(perDAO.getC01()).getBoolean()) {
                        agglomerationEmailsProcessRequest.createAgglomerationRejectedRequest(conn, usrId, aggId, perDAO.getPer_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF",
                            "AGGLO_REJECTED_SUBJECT", "AGGLO_REJECTED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGGLO_REJECTED.getCode(), new Date(), usrId, orgId, null);
                }
                // handling notifications / email for admin users
                for (SprUsersDAO user : ministryOrgUsers) {
                    ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, aggId, "NTIS_AGGLO_NOTIF",
                            "AGGLO_REJECTED_SUBJECT", "AGGLO_REJECTED_BODY", context, NtisNtfRefType.AGGLOMERATION.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGGLO_REJECTED.getCode(), new Date(), user.getUsr_id(), user.getUsr_org_id(), null);
                    if (user.getUsr_email_confirmed() != null && user.getUsr_email_confirmed().equalsIgnoreCase(YesNo.Y.getCode())) {
                        agglomerationEmailsProcessRequest.createAgglomerationRejectedRequest(conn, user.getUsr_id(), aggId, user.getUsr_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                }
            }

        }
    }
}
