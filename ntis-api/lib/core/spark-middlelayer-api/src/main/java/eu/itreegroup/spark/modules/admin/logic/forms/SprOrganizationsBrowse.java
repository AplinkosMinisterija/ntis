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
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprOrganizationsBrowseSecurityManager;

@Component
public class SprOrganizationsBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprOrganizationsBrowse.class);

    public static String ASSIGN_ROLE = "ASSIGN_ROLE";

    public static String ASSIGN_ROLE_DESC = "Assign role";

    public static final String ASSIGN_USERS = "ASSIGN_USERS";

    public static final String ASSIGN_USERS_NAME = "Assign users to the selected organization";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_ORGS_BROWSE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark organizations list", "Spark organizations list");
        addFormActionCRUD();
        this.addFormAction(ASSIGN_ROLE, ASSIGN_ROLE_DESC, ASSIGN_ROLE);
        addFormAction(ASSIGN_USERS, ASSIGN_USERS_NAME, ASSIGN_USERS_NAME);
    }

    public String getOrgList(Connection conn, SelectRequestParams params, Double userId, String lang) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprOrganizationsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
        		SELECT org_id,
        			   coalesce(rfc.rfc_meaning, org_type) as org_type,
        			   org_code,
        			   org_name,
        			   org_region,
        			   org_address,
        			   org_email,
        			   org_phone
        		  FROM spr_organizations
        	 LEFT JOIN spr_ref_codes_vw rfc on rfc.rfc_code = org_type and rfc.rfc_domain = 'SPR_ORG_TYPE' and rfc.rft_lang = ?
        		          		""");
       stmt.addSelectParam(lang);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("org_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("org_id"));
        stmt.addParam4WherePart("org_code", StatementAndParams.PARAM_STRING, advancedParamList.get("org_code"));
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("org_region", StatementAndParams.PARAM_STRING, advancedParamList.get("org_region"));
        stmt.addParam4WherePart("org_address", StatementAndParams.PARAM_STRING, advancedParamList.get("org_address"));
        stmt.addParam4WherePart("org_email", StatementAndParams.PARAM_STRING, advancedParamList.get("org_email"));
        stmt.addParam4WherePart("org_phone", StatementAndParams.PARAM_STRING, advancedParamList.get("org_phone"));
        stmt.addParam4WherePart(
                "case when  ( org_date_from is null or org_date_from <= " + dbstatementManager.getSysdateCommand() + " ) and  "
                        + " ( org_date_to is null or org_date_to >= " + dbstatementManager.getSysdateCommand() + " )  then 'Y' else 'N' end",
                StatementAndParams.PARAM_STRING, advancedParamList.get("org_active"));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("org_id", "org_type", "org_code", "org_name", "org_region", "org_address", "org_email", "org_phone"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.addParam4WherePart("org_type", StatementAndParams.PARAM_STRING, advancedParamList.get("org_type"));

        SprOrganizationsBrowseSecurityManager sqm = new SprOrganizationsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprOrganizationsBrowse.ACTION_READ, SprOrganizationsBrowse.ACTION_UPDATE, SprOrganizationsBrowse.ACTION_COPY,
                SprOrganizationsBrowse.ACTION_DELETE, SprOrganizationsBrowse.ASSIGN_ROLE, SprOrganizationsBrowse.ASSIGN_USERS };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);

    }

}
