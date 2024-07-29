package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.OrgUserRoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprUserRolesBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;

@Component
public class SprOrgUserRolesBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprOrgUserRolesBrowse.class);

    public SprOrgUserRolesBrowse(BaseControllerJDBC queryController, SprOrgUserRolesDBService sprOrgUserRolesDBService, DBStatementManager dbstatementManager,
            SprOrgUsersDBService sprOrgUserDBService) {
        super();
        this.queryController = queryController;
        this.sprOrgUserRolesDBService = sprOrgUserRolesDBService;
        this.dbstatementManager = dbstatementManager;
        this.sprOrgUserDBService = sprOrgUserDBService;
    }

    private final BaseControllerJDBC queryController;

    private final SprOrgUserRolesDBService sprOrgUserRolesDBService;

    private final DBStatementManager dbstatementManager;

    private final SprOrgUsersDBService sprOrgUserDBService;

    /**
     * Method will return Angular form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_ORG_USER_ROLES_LIST";
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
     * @param lang - session language
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, Double userId, String lang) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprUserRolesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                SELECT OUR.OUR_ID,
                       TO_CHAR(OUR.OUR_DATE_FROM, '%s') as OUR_DATE_FROM,
                       TO_CHAR(OUR.OUR_DATE_TO, '%s') as OUR_DATE_TO,
                       OU.OU_USR_ID as OUR_USR_ID,
                       SR.ROL_ID,
                       OU.OU_ID,
                       ROL_CODE,
                       coalesce(RT.RFT_DISPLAY_CODE, SR.ROL_TYPE) as ROL_TYPE,
                       SR.ROL_NAME,
                       SR.ROL_DESCRIPTION
                  FROM SPR_ROLES SR
                  JOIN SPR_ORG_AVAILABLE_ROLES OAR ON SR.ROL_ID = OAR.OAR_ROL_ID
                  JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = OAR.OAR_ORG_ID AND OU.OU_ID = ? AND %s
                  LEFT JOIN SPR_ORG_USER_ROLES OUR ON SR.ROL_ID = OUR.OUR_ROL_ID AND OUR.OUR_OU_ID = OU.OU_ID
                  LEFT JOIN SPR_REF_CODES RFCT ON RFCT.RFC_CODE = SR.ROL_TYPE AND RFCT.RFC_DOMAIN = 'SPR_ROLE_TYPE'
                  LEFT JOIN SPR_REF_TRANSLATIONS RT ON RFCT.RFC_ID = RT.RFT_RFC_ID AND RT.RFT_LANG = ?
                """.formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbstatementManager.getPeriodValidationForCurrentDateStr("OAR.OAR_DATE_FROM", "OAR.OAR_DATE_TO", true)));
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_ou_id")));
        stmt.addSelectParam(lang);
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
                        "TO_CHAR(OUR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(OUR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprUserRolesBrowseSecurityManager sqm = new SprUserRolesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprUserRolesBrowse.ACTION_READ, SprUserRolesBrowse.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public OrgUserRoleRequest[] updateOrgUserRoles(Connection conn, OrgUserRoleRequest[] records) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUserRolesBrowse.ACTION_CREATE);
        Double usrId = null;
        Double orgId = null;
        Double ouId = null;

        for (OrgUserRoleRequest record : records) {
            if (usrId == null || !record.getOu_id().equals(ouId)) {
                ouId = record.getOu_id();
                SprOrgUsersDAO orgUserDAO = sprOrgUserDBService.loadRecord(conn, ouId);
                usrId = orgUserDAO.getOu_usr_id();
                orgId = orgUserDAO.getOu_org_id();
            }
            SprOrgUserRolesDAO userOrgRole = null;

            if (record.getOur_id() == null) {
                userOrgRole = new SprOrgUserRolesDAO();
                userOrgRole.setOur_ou_id(record.getOu_id());
                userOrgRole.setOur_rol_id(record.getRol_id());
            } else {
                userOrgRole = sprOrgUserRolesDBService.loadRecord(conn, record.getOur_id());
            }
            userOrgRole.setOur_date_to(record.getOur_date_to() != null ? DateUtils.truncate(record.getOur_date_to(), Calendar.DATE) : null);
            if (record.getOur_date_from() != null) {
                userOrgRole.setOur_date_from(DateUtils.truncate(record.getOur_date_from(), Calendar.DATE));
            } else {
                userOrgRole.setOur_date_from(DateUtils.truncate(new java.util.Date(), Calendar.DATE));
            }
            sprOrgUserRolesDBService.saveRecord(conn, userOrgRole);
            record.setOur_id(userOrgRole.getOur_id());
            record.setOur_date_from(DateUtils.truncate(userOrgRole.getOur_date_from(), Calendar.DATE));
        }
        if (records.length > 0) {
            sprOrgUserRolesDBService.refreshUserOrgProfile(conn, orgId, usrId);
        }
        return records;
    }

    public void delOrgUserRoles(Connection conn, OrgUserRoleRequest[] records) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUserRolesBrowse.ACTION_CREATE);
        Double usrId = null;
        Double orgId = null;
        Double ouId = null;

        for (OrgUserRoleRequest record : records) {
            if (usrId == null || !record.getOu_id().equals(ouId)) {
                ouId = record.getOu_id();
                usrId = record.getUser_id();
                orgId = sprOrgUserDBService.loadRecord(conn, record.getOu_id()).getOu_org_id();
            }
            sprOrgUserRolesDBService.deleteRecord(conn, record.getOur_id());
        }
        sprOrgUserRolesDBService.refreshUserOrgProfile(conn, orgId, usrId);
    }
}
