package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import eu.itreegroup.spark.modules.admin.logic.forms.model.RefTranslationsObj;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprPropertiesBrowseSecurityManager;

@Component
public class SprRefClassifier extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPropertiesBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_REF_CLASSIFIER_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark ref classifier list", "Spark ref classifier list");
        addFormActionCRUD();
    }

    public String getCodesData(Connection conn, SelectRequestParams params, Double userId, String lang) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprRefClassifier.ACTION_UPDATE);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT RFC.RFC_ID, "//
                + "RFC.RFC_DOMAIN, "//
                + "RFC.RFC_CODE, "//
                + "COALESCE(RFT.RFT_DISPLAY_CODE, RFC.RFC_MEANING) AS RFC_MEANING, "//
                + "COALESCE(RFT.RFT_DESCRIPTION, RFC.RFC_DESCRIPTION) AS RFC_DESCRIPTION, "//
                + "RFC.RFC_PARENT_DOMAIN, "//
                + "RFC.RFC_PARENT_DOMAIN_CODE, "//
                + "TO_CHAR(RFC.RFC_DATE_FROM,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as RFC_DATE_FROM, "//
                + "TO_CHAR(RFC.RFC_DATE_TO,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as RFC_DATE_TO, "//
                + "RFC.RFC_ORDER, "//
                + "RFT.RFT_ID, "//
                + "RFT.RFT_LANG, "//
                + "RFT.RFT_DISPLAY_CODE, "//
                + "RFT.RFT_DESCRIPTION, "//
                + "RFT.RFT_RFC_ID"//
                + " FROM SPR_REF_CODES RFC"//
                + " LEFT JOIN SPR_REF_TRANSLATIONS RFT ON RFC.RFC_ID = RFT.RFT_RFC_ID and RFT.RFT_LANG = ?");
        	
        stmt.addParam4WherePart(" RFC.RFC_DOMAIN = ?", params.getParamList().get("p_table_name"));
        stmt.addSelectParam(lang);  
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("RFC_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("rfc_code"));
        stmt.addParam4WherePart("RFC_MEANING", StatementAndParams.PARAM_STRING, advancedParamList.get("rfc_meaning"));
        stmt.addParam4WherePart("RFC_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rfc_description"));
        stmt.addParam4WherePart("RFC_ORDER", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rfc_order"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("RFC_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("rfc_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("RFC_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("rfc_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("RFC_CODE", "RFC_MEANING", "RFC_DESCRIPTION", "RFC_ORDER",
                        "TO_CHAR(RFC_DATE_FROM,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(RFC_DATE_TO,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprPropertiesBrowseSecurityManager sqm = new SprPropertiesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprRefClassifier.ACTION_READ, SprRefClassifier.ACTION_UPDATE, SprRefClassifier.ACTION_DELETE, };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));

        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public List<RefTranslationsObj> getTranslationsData(Connection conn, String p_table_name) throws Exception {
        log.debug("Start extract information from db");
        List<RefTranslationsObj> result = new ArrayList<RefTranslationsObj>();
        checkIsFormActionAssigned(conn, SprRefClassifier.ACTION_UPDATE);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select rft.rft_id, "//
                + "rft.rft_lang, "//
                + "rft.rft_display_code, "//
                + "rft.rft_description, "//
                + "rft.rft_rfc_id, "//
                + "rfc.rfc_id, " //
                + "rfc.rfc_domain" //
                + " from spr_ref_translations rft " //
                + " join spr_ref_codes rfc on rfc.rfc_id = rft.rft_rfc_id");
        stmt.addParam4WherePart(" rfc.rfc_domain = ?", p_table_name);
        List<HashMap<String, String>> data = queryController.selectQueryAsDataArrayList(conn, stmt);
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, String> row = data.get(i);
            RefTranslationsObj translationsRow = new RefTranslationsObj();
            translationsRow.setRft_id(Utils.getDouble(row.get("rft_id")));
            translationsRow.setRft_lang(row.get("rft_lang"));
            translationsRow.setRft_display_code(row.get("rft_display_code"));
            translationsRow.setRft_description(row.get("rft_description"));
            translationsRow.setRft_rfc_id(Long.parseLong(row.get("rft_rfc_id")));
            result.add(translationsRow);
        }
        return result;
    }
}