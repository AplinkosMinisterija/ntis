package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

@Component
public class SprJobRequestsBrowse extends FormBase {

    final private BaseControllerJDBC queryController;

    final private DBStatementManager dbStatementManager;

    public SprJobRequestsBrowse(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager) {
        this.queryController = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
    }

    private static final Logger log = LoggerFactory.getLogger(SprJobRequestsBrowse.class);

    @Override
    public String getFormName() {
        return "SPR_JOB_REQUESTS";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark job requests list", "Spark job requests browse page");
        addFormActionCRUD();
    }

    public String getJobRequestsList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprJobRequestsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("getJobRequestsInfo"));
        String dateFormat = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB);

        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(dateFormat);
        stmt.addSelectParam(dateFormat);
        stmt.addSelectParam(dateFormat);
        stmt.addSelectParam(language);
        stmt.addSelectParam(dateFormat);
        stmt.addSelectParam(dateFormat);
        stmt.addSelectParam(dateFormat);
        stmt.addSelectParam(language);
        stmt.addSelectParam(paramList.get("isArchiveSearch"));

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("rq.jrq_jde_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("jrq_jde_id"));
        stmt.addParam4WherePart("rq.jrq_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("jrq_id"));
        stmt.addParam4WherePart("rq.jrq_start_date", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("jrq_start_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart("rq.jrq_end_date", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("jrq_end_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart("rq.jrq_executer_name", StatementAndParams.PARAM_STRING, advancedParamList.get("jrq_executer_name"));
        stmt.addParam4WherePart("rq.jrq_jde_id ", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("jde_name"));
        stmt.addParam4WherePart("rq.jrq_status", StatementAndParams.PARAM_STRING, advancedParamList.get("jrq_code"));
        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("rq.jrq_id", "rq.jrq_code", "rq.jrq_start_date", "rq.jrq_end_date",
                "rq.jrq_executer_name", "rq.jde_name"), StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        stmt.addJsonColumn("executions");
        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(FormBase.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));

        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public String getJobNames(Connection conn) throws Exception {
        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("SELECT jde_id, " //
                + "               jde_name " //
                + "          FROM spr_job_definitions ");//
        return queryController.selectQueryAsJSON(conn, stmt);
    }

}
