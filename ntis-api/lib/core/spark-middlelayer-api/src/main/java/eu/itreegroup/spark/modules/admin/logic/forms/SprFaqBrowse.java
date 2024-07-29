package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprFaqBrowseSecurityManager;

@Component
public class SprFaqBrowse extends FormBase {

    private final BaseControllerJDBC queryController;

    private final DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_FAQ_LIST";
    }

    public SprFaqBrowse(BaseControllerJDBC queryController, DBStatementManager dbStatementManager) {

        super();
        this.queryController = queryController;
        this.dbStatementManager = dbStatementManager;
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark FAQ list", "Spark FAQ list form");
        addFormActionCRUD();
    }

    public String getFaqList(Connection conn, SelectRequestParams params) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("getFaqListWithFiles"));
        stmt.setWhereExists(true);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(paramList.get("fac_group"));
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("fac_question", "fac_answer"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));
        SprFaqBrowseSecurityManager sqm = new SprFaqBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public IdKeyValuePair getGroupName(Connection conn, String code) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                    SELECT rfc_id AS id, rfc_meaning AS value
                    FROM spark.spr_ref_codes
                    WHERE rfc_domain = 'FAQ_GROUP' AND rfc_code = ?
                """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(code);
        List<IdKeyValuePair> queryResult = this.queryController.selectQueryAsObjectArrayList(conn, stmt, IdKeyValuePair.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No info for announced selection with code " + code + " found");
        }
        return queryResult.get(0);
    }

}
