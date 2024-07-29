package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

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
import eu.itreegroup.spark.modules.admin.dao.SprOrgAvailableRolesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.OrganizationRoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprOrganizationRolesBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprOrgAvailableRolesDBService;

@Component
public class SprOrganizationRolesBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprOrganizationRolesBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_ORGANIZATION_ROLES_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark organization roles list", "Spark organization roles list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract organization roles from db");
        this.checkIsFormActionAssigned(conn, SprOrganizationRolesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("WITH ORGANIZATION_ROLES AS (SELECT OAR_ID, "//
                + "OAR_DATE_FROM, "//
                + "OAR_DATE_TO, "//
                + "OAR_ROL_ID, "//
                + "OAR_ORG_ID " //
                + "FROM SPR_ORG_AVAILABLE_ROLES "//
                + "WHERE OAR_ORG_ID = ?)" //
                + "SELECT OAR_ID, " //
                + "OAR_ORG_ID, " //
                + "TO_CHAR(OAR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as OAR_DATE_FROM, "//
                + "TO_CHAR(OAR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as OAR_DATE_TO, "//
                + "SR.ROL_ID, " //
                + "SR.ROL_CODE, " //
                + "coalesce(RT.RFT_DISPLAY_CODE, SR.ROL_TYPE) as ROL_TYPE, " //
                + "SR.ROL_NAME, " //
                + "SR.ROL_DESCRIPTION, " //
                + " CASE WHEN OAR_ROL_ID IS NULL THEN NULL ELSE 'Y' END BELONGS " //
                + "FROM SPR_ROLES SR " //
                + "LEFT JOIN ORGANIZATION_ROLES ON (SR.ROL_ID = OAR_ROL_ID) " //
                + "INNER JOIN SPR_REF_CODES RC ON RC.RFC_CODE = SR.ROL_TYPE AND RC.RFC_DOMAIN = 'SPR_ROLE_TYPE' "//
                + "INNER JOIN SPR_REF_TRANSLATIONS RT ON RT.RFT_RFC_ID = RC.RFC_ID");

        stmt.addParam4WherePart("RT.RFT_LANG = ? ", language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_org_id")));
        stmt.addParam4WherePart("ROL_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rol_id"));
        stmt.addParam4WherePart("ROL_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_name"));
        stmt.addParam4WherePart("ROL_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_code"));
        stmt.addParam4WherePart("ROL_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_type"));
        stmt.addParam4WherePart("ROL_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_description"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OAR_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("oar_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OAR_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("oar_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ROL_ID", "ROL_NAME", "ROL_CODE", "ROL_TYPE", "ROL_DESCRIPTION",
                        "TO_CHAR(OAR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(OAR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprOrganizationRolesBrowseSecurityManager sqm = new SprOrganizationRolesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprOrganizationRolesBrowse.ACTION_READ, SprOrganizationRolesBrowse.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void updateOrganizationRoles(Connection conn, ArrayList<OrganizationRoleRequest> records) throws Exception {
        for (OrganizationRoleRequest record : records) {
            if (record.getIsSelected()) {
                if (record.getOar_id() == null || record.getOar_id().length() == 0) {
                    this.checkIsFormActionAssigned(conn, SprOrganizationRolesBrowse.ACTION_CREATE);
                    SprOrgAvailableRolesDAO orgRole = sprOrgAvailableRolesDBService.newRecord();
                    orgRole.setOar_org_id(Utils.getDouble(record.getOrg_id()));
                    orgRole.setOar_rol_id(Utils.getDouble(record.getRol_id()));
                    if (record.getOar_date_from() != null) {
                        orgRole.setOar_date_from(record.getOar_date_from());
                    } else {
                        orgRole.setOar_date_from(Utils.getDate());
                    }
                    orgRole.setOar_date_to(record.getOar_date_to());
                    sprOrgAvailableRolesDBService.saveRecord(conn, orgRole);
                }
            } else {
                if (record.getOar_id() != null && record.getOar_id().length() != 0) {
                    this.checkIsFormActionAssigned(conn, SprOrganizationRolesBrowse.ACTION_DELETE);
                    sprOrgAvailableRolesDBService.deleteRecord(conn, Utils.getDouble(record.getOar_id()));
                }
            }

        }
    }

    public void setOrganizationRoleDate(Connection conn, OrganizationRoleRequest record) throws Exception {
        this.checkIsFormActionAssigned(conn, SprOrganizationRolesBrowse.ACTION_UPDATE);
        SprOrgAvailableRolesDAO orgRole = sprOrgAvailableRolesDBService.loadRecord(conn, Utils.getDouble(record.getOar_id()));
        orgRole.setOar_date_to(record.getOar_date_to());
        orgRole.setOar_date_from(record.getOar_date_from());
        sprOrgAvailableRolesDBService.saveRecord(conn, orgRole);
    }

}
