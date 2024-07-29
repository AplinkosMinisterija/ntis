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
public class SprApiKeysBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprApiKeysBrowse.class);

    public static final String NO_LIMIT_ON_ORG_LEVEL = "NO_LIMIT_ON_ORG_LEVEL";

    public static final String NO_LIMIT_ON_ORG_LEVEL_NAME = "No limitation on user organization level";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    /**
     * Method will return Angular form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_API_KEYS_BROWSE";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark API keys", "Spark users api keys administration");
        addFormActionCRUD();
        addFormAction(NO_LIMIT_ON_ORG_LEVEL, NO_LIMIT_ON_ORG_LEVEL_NAME, NO_LIMIT_ON_ORG_LEVEL_NAME);

    }

    /**
     * Method will return list of api keys. 
     * @param conn - connection to the db that will be used for data extraction
     * @param params - request parameters received from front end.
     * @param language - language in which should be returned information.
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String language, Double usrId, Double orgId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprApiKeysBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("apiKeysList"));

        stmt.setStatementGroupPart("""
                api.api_usr_id,
                api.api_ou_id,
                coalesce(usr.usr_username, ousr.usr_username),
                coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name ),
                org_name
                """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            stmt.addParam4WherePart(" org_id = ? ", orgId);
        }
        stmt.addParam4WherePart("coalesce(api.api_id)", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("api_id"));
        stmt.addParam4WherePart("coalesce(usr.usr_username, ousr.usr_username)", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_username"));
        stmt.addParam4WherePart("coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name )", StatementAndParams.PARAM_STRING,
                advancedParamList.get("per_name"));
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("coalesce(api.api_usr_id, api.api_ou_id) ", "coalesce(usr.usr_username, ousr.usr_username) ",
                        "coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name )", "org_name "),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        SprPropertiesBrowseSecurityManager sqm = new SprPropertiesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprApiKeysBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
