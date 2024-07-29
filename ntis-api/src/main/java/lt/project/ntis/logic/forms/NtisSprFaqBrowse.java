package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprFaqBrowseSecurityManager;

@Component
public class NtisSprFaqBrowse extends SprFaqBrowse {

    private static final String ACTION_GET_LINK = "GET_LINK";

    private final BaseControllerJDBC queryController;

    private final DBStatementManager dbStatementManager;

    public NtisSprFaqBrowse(BaseControllerJDBC queryController, DBStatementManager dbStatementManager) {
        super(queryController, dbStatementManager);
        this.queryController = queryController;
        this.dbStatementManager = dbStatementManager;
    }

    /**
     * This function returns faq's by specified parameters in JSON format.
     * 
     * @param conn - Connection object used for database connection
     * @param params - SelectRequestParams object with search parameters
     * @return String with faq's in JSON format by specified search parameters
     * @throws Exception if an error occurs
     */
    @Override
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
        String[] recordActionMenu = { FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE, ACTION_GET_LINK };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
