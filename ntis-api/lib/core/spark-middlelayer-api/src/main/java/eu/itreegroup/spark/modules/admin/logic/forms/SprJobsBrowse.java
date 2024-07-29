package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

@Component
public class SprJobsBrowse extends FormBase {

    final private BaseControllerJDBC queryController;

    final private SprJobDefinitionsDBService sprJobDefinitionsDBService;

    final private SprJobRequestsDBService sprJobRequestsDBService;

    final private DBStatementManager dbStatementManager;

    public SprJobsBrowse(BaseControllerJDBC baseControllerJDBC, SprJobDefinitionsDBService sprJobDefinitionsDBService,
            SprJobRequestsDBService sprJobRequestsDBService, DBStatementManager dbStatementManager) {
        this.queryController = baseControllerJDBC;
        this.sprJobDefinitionsDBService = sprJobDefinitionsDBService;
        this.sprJobRequestsDBService = sprJobRequestsDBService;
        this.dbStatementManager = dbStatementManager;
    }

    private static final Logger log = LoggerFactory.getLogger(SprJobsBrowse.class);

    @Override
    public String getFormName() {
        return "SPR_JOBS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark jobs list", "Spark jobs browse page");
        addFormActionCRUD();
    }

    public String getJobsList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprJobsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("" //
                + "SELECT job.jde_id, " //
                + "       job.jde_name, " //
                + "       job.jde_description, " //
                + "       job.jde_code, " //
                + "       CASE WHEN type_rft.rft_display_code IS NOT NULL THEN type_rft.rft_display_code ELSE type_rfc.rfc_meaning END AS jde_type, " //
                + "       CASE WHEN status_rft.rft_display_code IS NOT NULL THEN status_rft.rft_display_code ELSE status_rfc.rfc_meaning END AS jde_status, " //
                + "       to_char(job.jde_last_action_time,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB)
                + "') AS jde_last_action_time, " //
                + "       to_char(job.jde_next_action_time,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB)
                + "') AS jde_next_action_time " //
                + "  FROM spr_job_definitions job " //
                + "  LEFT JOIN spr_ref_codes type_rfc ON type_rfc.rfc_code = job.jde_type AND type_rfc.rfc_domain = 'SPR_JOB_DEF_TYPE' " //
                + "  LEFT JOIN spr_ref_translations type_rft ON type_rfc.rfc_id = type_rft.rft_rfc_id AND type_rft.rft_lang = ? " //
                + "  LEFT JOIN spr_ref_codes status_rfc ON status_rfc.rfc_code = job.jde_status AND status_rfc.rfc_domain = 'SPR_JOB_DEF_STATUS' " //
                + "  LEFT JOIN spr_ref_translations status_rft ON status_rfc.rfc_id = status_rft.rft_rfc_id AND status_rft.rft_lang = ?");
        stmt.addSelectParam(language);
        stmt.addSelectParam(language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("job.jde_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("jde_id"));
        stmt.addParam4WherePart("job.jde_code", StatementAndParams.PARAM_STRING, advancedParamList.get("jde_code"));
        stmt.addParam4WherePart("job.jde_name", StatementAndParams.PARAM_STRING, advancedParamList.get("jde_name"));
        stmt.addParam4WherePart("job.jde_description", StatementAndParams.PARAM_STRING, advancedParamList.get("jde_description"));
        stmt.addParam4WherePart("job.jde_last_action_time", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("jde_last_action_time"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart("job.jde_next_action_time", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("jde_next_action_time"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("job.jde_id", "job.jde_name", "job.jde_description", "job.jde_code", "job.jde_last_action_time",
                        "job.jde_next_action_time", "type_rft.rft_display_code", "status_rft.rft_display_code"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.addParam4WherePart("job.jde_type", StatementAndParams.PARAM_STRING, advancedParamList.get("jde_type"));
        stmt.addParam4WherePart("job.jde_status", StatementAndParams.PARAM_STRING, advancedParamList.get("jde_status"));

        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(FormBase.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void deleteJob(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        try {
            sprJobDefinitionsDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
        } catch (SQLIntegrityConstraintViolationException e) {
            sprJobRequestsDBService.deleteRecordsAndChildrenByParent(conn, recordIdentifier.getIdAsDouble());
            sprJobDefinitionsDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
        }
    }

}
