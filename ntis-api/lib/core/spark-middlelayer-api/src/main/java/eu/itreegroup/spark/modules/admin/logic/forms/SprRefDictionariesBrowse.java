package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprPropertiesBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprRefDictionariesDBService;

@Component
public class SprRefDictionariesBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPropertiesBrowse.class);

    public static String SET_CODES_AND_TRANSLATIONS = "SET_CODES_AND_TRANSLATIONS";

    public static String SET_CODES_AND_TRANSLATIONS_DESC = "Set classifier codes and translations";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    protected SprRefDictionariesDBService dictionariesDBService;

    @Override
    public String getFormName() {
        return "SPR_REF_DICTIONARIES_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark dictionaries list", "Spark dictionaries list");
        addFormActionCRUD();
        this.addFormAction(SET_CODES_AND_TRANSLATIONS, SET_CODES_AND_TRANSLATIONS_DESC, SET_CODES_AND_TRANSLATIONS);

    }

    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprRefDictionariesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT RFD_ID, "//
                + "RFD_SUBSYSTEM, "//
                + "RFD_TABLE_NAME, "//
                + "RFD_NAME, "//
                + "RFD_DESCRIPTION, " //
                + "coalesce(Rt.Rft_Display_Code, RC.RFC_MEANING) as RFD_CODE_TYPE, "//
                + "RFD_CODE_LENGTH "//
                + " FROM SPR_REF_DICTIONARIES " //
                + "INNER JOIN SPR_REF_CODES RC ON RC.RFC_CODE = RFD_CODE_TYPE and RC.RFC_DOMAIN = 'SPR_CLSF_CODE_TYPE' "//
                + "LEFT JOIN SPR_REF_TRANSLATIONS RT ON RT.RFT_RFC_ID = RC.RFC_ID and RT.RFT_LANG = ? ");

        stmt.addSelectParam(lang);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("RFD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rfd_id"));
        stmt.addParam4WherePart("RFD_SUBSYSTEM", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_subsystem"));
        stmt.addParam4WherePart("RFD_TABLE_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_table_name"));
        stmt.addParam4WherePart("RFD_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_name"));
        stmt.addParam4WherePart("RFD_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_description"));
        stmt.addParam4WherePart("RFD_CODE_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_code_type"));
        stmt.addParam4WherePart("RFD_CODE_LENGTH", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rfd_code_length"));
        stmt.addParam4WherePart(dbstatementManager.colNamesToConcatString("RFD_ID", "RFD_SUBSYSTEM", "RFD_TABLE_NAME", "RFD_NAME", "RFD_DESCRIPTION",
                "RFD_CODE_TYPE", "RFD_CODE_LENGTH"), StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.addParam4WherePart("RFD_SUBSYSTEM", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_subsystem"));
        stmt.addParam4WherePart("RFD_CODE_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rfd_code_type"));
        SprPropertiesBrowseSecurityManager sqm = new SprPropertiesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] actionsOrder = {SET_CODES_AND_TRANSLATIONS, FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(actionsOrder);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void deleteClassifier(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprRefDictionariesBrowse.ACTION_DELETE);
        dictionariesDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
    }
}