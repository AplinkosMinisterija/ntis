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
import eu.itreegroup.spark.modules.admin.dao.SprUserRolesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.UserRoleDate;
import eu.itreegroup.spark.modules.admin.logic.forms.model.UserRoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprUserRolesBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprUserRolesDBService;

@Component
public class SprUserRolesBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprUserRolesBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprUserRolesDBService sprUserRolesDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_USER_ROLES_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD.
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark user roles list", "Spark user roles list");
        addFormActionCRUD();
    }

    /**
     * Method will return list of defined system parameters.
     * @param conn - connection to the db that will be used for data extraction
     * @param params - request parameters received from front end.
     * @return JSON array (string), that hold result of performed queries.
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprUserRolesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT UR.URO_ID," //
                + " TO_CHAR(UR.URO_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as URO_DATE_FROM, "//
                + " TO_CHAR(UR.URO_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as URO_DATE_TO, "//
                + " UR.URO_USR_ID," //
                + " SR.ROL_ID," //
                + " SR.ROL_CODE," //
                + " coalesce(RT.RFT_DISPLAY_CODE, SR.ROL_TYPE) as ROL_TYPE, " //
                + " SR.ROL_NAME," //
                + " SR.ROL_DESCRIPTION," //
                + " CASE WHEN UR.URO_ROL_ID IS NULL THEN NULL ELSE 'Y' END BELONGS" //
                + " FROM SPR_ROLES SR" //
                + " LEFT JOIN SPR_USER_ROLES UR ON (SR.ROL_ID = UR.URO_ROL_ID AND URO_USR_ID = ?)"//
                + " LEFT JOIN SPR_REF_CODES RC ON RC.RFC_CODE = SR.ROL_TYPE AND RC.RFC_DOMAIN = 'SPR_ROLE_TYPE' "//
                + " LEFT JOIN SPR_REF_TRANSLATIONS RT ON RT.RFT_RFC_ID = RC.RFC_ID");

        stmt.addParam4WherePart("RT.RFT_LANG = ? ", language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_usr_id")));
        stmt.addParam4WherePart("ROL_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rol_id"));
        stmt.addParam4WherePart("ROL_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_name"));
        stmt.addParam4WherePart("ROL_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_code"));
        stmt.addParam4WherePart("ROL_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_type"));
        stmt.addParam4WherePart("ROL_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_description"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("URO_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("uro_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("URO_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("uro_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ROL_ID", "ROL_NAME", "ROL_CODE", "ROL_TYPE", "ROL_DESCRIPTION",
                        "TO_CHAR(URO_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(URO_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprUserRolesBrowseSecurityManager sqm = new SprUserRolesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprUserRolesBrowse.ACTION_READ, SprUserRolesBrowse.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void delUserRoles(Connection conn, ArrayList<UserRoleRequest> records) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUserRolesBrowse.ACTION_CREATE);
        for (UserRoleRequest record : records) {
            if (record.getUpdate()) {
                if (record.getUro_id() == null || record.getUro_id().length() == 0) {
                    SprUserRolesDAO userRole = new SprUserRolesDAO();
                    userRole.setUro_usr_id(Utils.getDouble(record.getUser_id()));
                    userRole.setUro_rol_id(Utils.getDouble(record.getRol_id()));
                    userRole.setUro_date_from(record.getUro_date_from());
                    userRole.setUro_date_to(record.getUro_date_to());
                    if (record.getUro_date_from() != null) {
                        userRole.setUro_date_from(record.getUro_date_from());
                    } else {
                        userRole.setUro_date_from(new java.util.Date());
                    }
                    sprUserRolesDBService.saveRecord(conn, userRole);
                }
            } else {
                if (record.getUro_id() != null && record.getUro_id().length() != 0) {
                    sprUserRolesDBService.deleteRecord(conn, Utils.getDouble(record.getUro_id()));
                }
            }
            sprUserRolesDBService.refreshUserProfile(conn, Utils.getDouble(record.getUser_id()));
        }
    }

    public void updateUserRoles(Connection conn, ArrayList<UserRoleRequest> records) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUserRolesBrowse.ACTION_CREATE);
        for (UserRoleRequest record : records) {
            if (record.getUpdate()) {
                if ((record.getUro_id() == null || record.getUro_id().isEmpty()) && record.getRol_id() != null && !record.getRol_id().isEmpty()) {
                    SprUserRolesDAO userRole = new SprUserRolesDAO();
                    userRole.setUro_usr_id(Utils.getDouble(record.getUser_id()));
                    userRole.setUro_rol_id(Utils.getDouble(record.getRol_id()));
                    userRole.setUro_date_from(record.getUro_date_from());
                    userRole.setUro_date_to(record.getUro_date_to());
                    if (record.getUro_date_from() != null) {
                        userRole.setUro_date_from(Utils.getDate(record.getUro_date_from()));
                    } else {
                        userRole.setUro_date_from(Utils.getDate(new java.util.Date()));
                    }
                    userRole.setUro_date_to(Utils.getDate(record.getUro_date_to()));
                    sprUserRolesDBService.saveRecord(conn, userRole);
                }
            } else {
                if (record.getUro_id() != null && record.getUro_id().length() != 0) {
                    sprUserRolesDBService.deleteRecord(conn, Utils.getDouble(record.getUro_id()));
                }
            }
            sprUserRolesDBService.refreshUserProfile(conn, Utils.getDouble(record.getUser_id()));
        }
    }

    public void setUserRoleDate(Connection conn, UserRoleDate record) throws Exception {
        SprUserRolesDAO userRole = sprUserRolesDBService.loadRecord(conn, Utils.getDouble(record.getUro_id()));
        userRole.setUro_date_to(record.getUro_date_to());
        userRole.setUro_date_from(record.getUro_date_from());
        sprUserRolesDBService.saveRecord(conn, userRole);
    }
}
