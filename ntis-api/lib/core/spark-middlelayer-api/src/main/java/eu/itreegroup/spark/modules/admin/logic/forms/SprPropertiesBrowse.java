package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprPropertiesBrowseSecurityManager;

@Component
public class SprPropertiesBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPropertiesBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_PROPERTIES_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark properties list", "Spark properties list");
        addFormActionCRUD();
    }

    /**
     * Method will return list of defined system parameters. 
     * @param conn - connection to the db that will be used for data extraction
     * @param params - request parameters received from front end.
     * @param language - language in which should be returned information.
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT PRP_ID, "//
                + "PRP_NAME, "//
                + "PRP_DESCRIPTION, "//
                + "PRP_VALUE, "//
                + "CASE WHEN srt.rft_description IS NOT NULL THEN srt.rft_description ELSE src.rfc_meaning end PRP_TYPE, "//
                + "PRP_GUID, "//
                + "PRP_INSTALL_INSTANCE "//
                + "FROM SPR_PROPERTIES prp "//
                + "join SPR_REF_CODES src on src.rfc_domain = 'SPR_PROP_TYPE' and prp.PRP_TYPE = src.rfc_code " //
                + "left join SPR_REF_TRANSLATIONS srt on src.rfc_id = srt.rft_rfc_id and srt.rft_lang = ? ");
        stmt.addSelectParam(language);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("PRP_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("prp_id"));
        stmt.addParam4WherePart("PRP_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("prp_name"));
        stmt.addParam4WherePart("PRP_VALUE", StatementAndParams.PARAM_STRING, advancedParamList.get("prp_value"));
        stmt.addParam4WherePart("PRP_GUID", StatementAndParams.PARAM_STRING, advancedParamList.get("prp_guid"));
        stmt.addParam4WherePart("PRP_INSTALL_INSTANCE", StatementAndParams.PARAM_STRING, advancedParamList.get("prp_install_instance"));
        stmt.addParam4WherePart("PRP_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("prp_description"));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("PRP_ID", "PRP_NAME", "PRP_VALUE", "PRP_GUID", "PRP_INSTALL_INSTANCE", "PRP_DESCRIPTION"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        SprPropertiesBrowseSecurityManager sqm = new SprPropertiesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprPropertiesBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
