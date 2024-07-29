package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;

@Component
public class SprFaqThemesList extends FormBase {

    private final BaseControllerJDBC queryController;

    private final DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_FAQ_THEMES_LIST";
    }

    public SprFaqThemesList(BaseControllerJDBC queryController, DBStatementManager dbStatementManager) {
        super();
        this.queryController = queryController;
        this.dbStatementManager = dbStatementManager;
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "DUK temų sąrašas", "DUK temų sąrašo forma");
        this.addFormActions(FormBase.ACTION_READ);
    }

    public String getThemesList(Connection conn, SelectRequestParams params) throws Exception {
        this.checkIsFormActionAssigned(conn, SprFaqThemesList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT rfc_meaning, rfc_code
                FROM spr_ref_codes
                WHERE rfc_domain = 'FAQ_GROUP'
                """);
        stmt.setWhereExists(true);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("rfc_meaning"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));
        return queryController.selectQueryAsJSON(conn, stmt, params, null);
    }

}
