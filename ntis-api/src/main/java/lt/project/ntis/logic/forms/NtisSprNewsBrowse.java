package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.SprNewsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprNewsBrowseSecurityManager;

@Component
public class NtisSprNewsBrowse extends SprNewsBrowse {

    @Autowired
    protected BaseControllerJDBC queryController;

    @Autowired
    protected DBStatementManager dbStatementManager;

    /**
     * This function returns news by specified parameters in JSON format.
     * 
     * @param conn - Connection object used for database connection
     * @param params - SelectRequestParams object with search parameters
     * @param language - language by which news will be returned
     * @param isPublic - boolean type parameter - show only public news or all
     * @return String with news in JSON format by specified search parameters
     * @throws Exception if an error occurs
     */
    public String getRecList(Connection conn, SelectRequestParams params, String language, Boolean isPublic) throws Exception {

        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT nw_id,
                    nw_publication_date,
                    nw_summary,
                    nw_title,
                    nw_lang,
                    nw_type,
                    c01 as is_public,
                    c02 as is_template,
                    c03 as page_template,
                    rc.rft_display_code as page_template_meaning
                  FROM spr_news
                  LEFT JOIN spr_ref_codes_vw rc ON rc.rfc_code = c03 AND rc.rfc_domain = 'NTIS_PAGE_TEMPLATE_TYPE' AND rc.rft_lang = ?
                		        """);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        language = language != null ? language : advancedParamList.get("p_lang").getParamValue().getValue();
        stmt.addSelectParam(language);
        if (isPublic) {
            stmt.addParam4WherePart("c01 = ?", DbConstants.BOOLEAN_TRUE);
            stmt.addParam4WherePart("c02 != ?", DbConstants.BOOLEAN_TRUE);
            stmt.addCondition4WherePart(" c03 is null ", " and ");
        }
        stmt.addParam4WherePart("nw_type", StatementAndParams.PARAM_STRING, advancedParamList.get("nw_type"));
        stmt.addParam4WherePart("nw_title", StatementAndParams.PARAM_STRING, advancedParamList.get("nw_title"));
        stmt.addParam4WherePart("nw_summary", StatementAndParams.PARAM_STRING, advancedParamList.get("nw_summary"));
        stmt.addParam4WherePart("case when c01 = 'Y' then 'Y' else 'N' end", StatementAndParams.PARAM_STRING, advancedParamList.get("is_public"));
        stmt.addParam4WherePart("case when c02 = 'Y' then 'Y' else 'N' end", StatementAndParams.PARAM_STRING, advancedParamList.get("is_template"));
        if (advancedParamList.get("page_template") != null) {
            stmt.addCondition4WherePart(" c03 is not null ", " and ");
        } else {
            stmt.addCondition4WherePart(" c03 is null ", " and ");
        }
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
