package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprNewsBrowseSecurityManager;

@Component
public class SprNewsBrowse extends FormBase {

    @Autowired
    protected BaseControllerJDBC queryController;

    @Autowired
    protected DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_NEWS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark news list", "Spark news list");
        addFormActionCRUD();
    }

    /**
     * This function returns news by specified parameters in JSON format.
     * 
     * @param conn - Connection object used for database connection
     * @param params - SelectRequestParams object with search parameters
     * @param language - language by which news will be returned
     * @return String with news in JSON format by specified search parameters
     * @throws Exception if an error occurs
     */
    public String getList(Connection conn, SelectRequestParams params, String language) throws Exception {

        checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT nw_id,
                    nw_publication_date,
                    nw_summary,
                    nw_title,
                    nw_lang,
                    nw_type
                  FROM spr_news
                		        """);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();

        stmt.addParam4WherePart("nw_type", StatementAndParams.PARAM_STRING, advancedParamList.get("nw_type"));
        stmt.addParam4WherePart("nw_title", StatementAndParams.PARAM_STRING, advancedParamList.get("nw_title"));
        stmt.addParam4WherePart("nw_summary", StatementAndParams.PARAM_STRING, advancedParamList.get("nw_summary"));
        stmt.addParam4WherePart("nw_lang = ?", language);
        stmt.addParam4WherePart("nw_publication_date", StatementAndParams.PARAM_DATE, advancedParamList.get("nw_publication_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.setStatementOrderPart("ORDER BY nw_publication_date DESC");

        AdvancedSearchParameterStatement quickSearchParam = advancedParamList.get("quickSearch");
        if (quickSearchParam != null && quickSearchParam.getParamValue() != null) {
            String str = quickSearchParam.getParamValue().getValue();
            quickSearchParam.getParamValue().setValue(Utils.replaceNationalCharacters(str).toLowerCase());
            stmt.addParam4WherePart("nw_content_for_search", StatementAndParams.PARAM_STRING, quickSearchParam);
        }

        SprNewsBrowseSecurityManager sqm = new SprNewsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(FormBase.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));

        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
