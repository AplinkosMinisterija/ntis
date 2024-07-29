package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprRoleActionsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRolesDate;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRolesRecord;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprRoleActionsRoleBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprRoleActionsDBService;

@Component
public class SprRoleActionsRole extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprRoleActionsRole.class);

    private static final String ROLE_ACTION_CACHE = "roleActions";

    private static final String USER_ACTION_CACHE = "userActions";

    private static final String ROLE_MENU_CACHE = "roleMenu";

    private static final String USER_MENU_CACHE = "userMenu";

    private static final String PUBLIC_MENU_CACHE = "publicMenu";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprRoleActionsDBService sprRoleActionsDBService;

    @Autowired
    SprCacheManager sprCacheManager;

    @Override
    public String getFormName() {
        return "SPR_ROLE_ACTIONS_ROLE_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark role actions role list", "Spark role actions role list description");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprRoleActionsRole.ACTION_READ);
        HashMap<String, String> paramList = params.getParamList();
        Double rolId = Utils.getDouble(paramList.get("p_role_id"));
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                WITH ROLE_ROLES AS (SELECT SRA.ROA_ID,
                SRA.ROA_DATE_FROM,
                SRA.ROA_DATE_TO,
                SRA.ROA_ASSIGNED_ROL_ID,
                SRA.ROA_ROL_ID,
                SRA.ROA_FRM_ID
                FROM SPR_ROLE_ACTIONS SRA
                WHERE SRA.ROA_ROL_ID = ? and SRA.ROA_ASSIGNED_ROL_ID is not null)
                SELECT RR.ROA_ID,
                TO_CHAR(RR.ROA_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as ROA_DATE_FROM,
                TO_CHAR(RR.ROA_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as ROA_DATE_TO,
                RR.ROA_ASSIGNED_ROL_ID,
                RR.ROA_FRM_ID,
                SR.ROL_ID,
                CASE WHEN RT.RFT_DISPLAY_CODE IS NOT NULL THEN RT.RFT_DISPLAY_CODE ELSE RC.RFC_MEANING END AS ROL_TYPE,
                SR.ROL_CODE,
                SR.ROL_DESCRIPTION,
                SR.ROL_NAME,
                CASE WHEN RR.ROA_ASSIGNED_ROL_ID IS NULL  THEN NULL ELSE 'Y' END BELONGS
                FROM SPR_ROLES SR
                LEFT JOIN ROLE_ROLES RR ON (SR.ROL_ID = RR.ROA_ASSIGNED_ROL_ID)
                INNER JOIN SPR_REF_CODES RC ON RC.RFC_CODE = SR.ROL_TYPE and RC.RFC_DOMAIN ='SPR_ROLE_TYPE'
                LEFT JOIN SPR_REF_TRANSLATIONS RT ON RT.RFT_RFC_ID = RC.RFC_ID
                """);
        stmt.addParam4WherePart("RT.RFT_LANG = ? ", language);
        stmt.addParam4WherePart("SR.ROL_ID != ? ", rolId);
        stmt.addSelectParam(rolId);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("ROL_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rol_id"));
        stmt.addParam4WherePart("ROL_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_name"));
        stmt.addParam4WherePart("ROL_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_code"));
        stmt.addParam4WherePart("ROL_TYPE = ?", paramList.get("p_rol_type"));
        stmt.addParam4WherePart("ROL_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_description"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("ROA_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("roa_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("ROA_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("roa_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ROL_ID", "ROL_CODE", "ROL_NAME", "ROL_DESCRIPTION", "ROL_TYPE",
                        "TO_CHAR(ROA_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(ROA_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprRoleActionsRoleBrowseSecurityManager sqm = new SprRoleActionsRoleBrowseSecurityManager();
        stmt.setStatementOrderPart(" ORDER BY " + params.getPagingParams().getOrder_clause());
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void updateRoles(Connection conn, ArrayList<RoleRequest> records) throws Exception {
        for (RoleRequest record : records) {
            if (record.getUpdate()) {
                if (record.getRoa_id() == null || record.getRoa_id().equals("")) {
                    this.checkIsFormActionAssigned(conn, SprRoleActionsRole.ACTION_CREATE);
                    SprRoleActionsDAO rolesRole = sprRoleActionsDBService.newRecord();
                    rolesRole.setRoa_type("ROLE1");
                    rolesRole.setRoa_rol_id(Utils.getDouble(record.getRoa_rol_id()));
                    rolesRole.setRoa_assigned_rol_id(Utils.getDouble(record.getRoa_assigned_rol_id()));
                    rolesRole.setRoa_date_from(Utils.getDate(new java.util.Date()));
                    sprRoleActionsDBService.insertRecord(conn, rolesRole);
                    sprRoleActionsDBService.saveRecord(conn, rolesRole);
                }
            } else {
                if (record.getRoa_id() != null && record.getRoa_id().length() != 0) {
                    this.checkIsFormActionAssigned(conn, SprRoleActionsRole.ACTION_DELETE);
                    sprRoleActionsDBService.deleteRecord(conn, record.parseRoa_idToDouble());
                }
            }
        }
        clearRelatedCaches(conn);

    }

    public void setRoleRolesDate(Connection conn, RoleRolesDate record) throws Exception {
        this.checkIsFormActionAssigned(conn, SprRoleActionsRole.ACTION_UPDATE);
        SprRoleActionsDAO rolesRole = sprRoleActionsDBService.newRecord();
        rolesRole.setForceUpdate(true);
        rolesRole.setRoa_type("ROLE1");
        rolesRole.setRoa_id(Utils.getDouble(record.getRoa_id()));
        rolesRole.setRoa_date_to(Utils.getDate(record.getRoa_date_to()));
        rolesRole.setRoa_date_from(Utils.getDate(record.getRoa_date_from()));
        sprRoleActionsDBService.saveRecord(conn, rolesRole);
        clearRelatedCaches(conn);
    }

    /**
     * Function will clear with user roles related caches.
     * @param conn - connection to th db.
     */
    private void clearRelatedCaches(Connection conn) {
        try {
            sprCacheManager.clearCache(conn, ROLE_ACTION_CACHE);
            sprCacheManager.clearCache(conn, USER_ACTION_CACHE);
            sprCacheManager.clearCache(conn, ROLE_MENU_CACHE);
            sprCacheManager.clearCache(conn, USER_MENU_CACHE);
            sprCacheManager.clearCache(conn, PUBLIC_MENU_CACHE);
        } catch (Exception ex) {
            log.error("Error while trying to clear cache", ex);
        }
    }

    public String getRec(Connection conn, RoleRolesRecord roleRolesRecord) throws Exception {
        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("SELECT ROA_ID" //
                + " FROM SPR_ROLE_ACTIONS " //
                + " WHERE ROA_ASSIGNED_ROL_ID = ? " //
                + " and ROA_ROL_ID = ? "); //

        stmt.addSelectParam(roleRolesRecord.getRoa_assigned_rol_id());
        stmt.addSelectParam(roleRolesRecord.getRoa_rol_id());
        return queryController.selectQueryAsJSON(conn, stmt);
    }
}
