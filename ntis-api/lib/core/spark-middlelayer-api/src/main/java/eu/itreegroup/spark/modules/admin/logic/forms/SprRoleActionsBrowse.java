package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprRoleActionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRoleDisabledActionsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Form;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Form4RoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Request4FormActions;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Request4FormState;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprRoleActionsBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprRoleActionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRoleDisabledActionsDBService;

@Component
public class SprRoleActionsBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprRoleActionsBrowse.class);

    public static String SET_FORM_DISABLED_ACTIONS = "SET_FORM_DISABLED_ACTIONS";

    public static String SET_FORM_DISABLED_ACTIONS_DESC = "Set form disabled actions";

    private static final String ROLE_ACTION_CACHE = "roleActions";

    private static final String USER_ACTION_CACHE = "userActions";

    private static final String ROLE_MENU_CACHE = "roleMenu";

    private static final String USER_MENU_CACHE = "userMenu";

    private static final String PUBLIC_MENU_CACHE = "publicMenu";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprRoleDisabledActionsDBService roleDisabledActionsDBService;

    @Autowired
    SprRoleActionsDBService roleActionsDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprCacheManager sprCacheManager;

    /**
     * Method will return Angular form name. Same name should be defined in DB as well 
     * (in case if enabled data synchronization with db it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_ROLE_ACTIONS_LIST";
    }

    /**
     * Method will define setup information that is related with current form. 
     * In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark role actions list", "Spark role actions list description");
        addFormActionCRUD();
        this.addFormAction(SET_FORM_DISABLED_ACTIONS, SET_FORM_DISABLED_ACTIONS_DESC, SET_FORM_DISABLED_ACTIONS);
    }

    /**
     * Method will check given date value, nullability of the field and will return a date to set in DB. 
     * @param dateVal - a date value - should be either a date or null.
     * @param isNullable - to define if form field is mandatory (false) or optional (true).
     * @return dateToSet - a date to set in form.
     * @throws Exception
     */
    public Date checkSetDate(Date dateVal, boolean isNullable) throws Exception {
        Date dateToSet = null;
        if (dateVal != null) {
            dateToSet = Utils.getDate(dateVal);
        } else {
            dateToSet = isNullable ? null : Utils.getDate();
        }
        return dateToSet;
    }

    /**
     * Method will check whether two dates are the same. This is used below to define 
     * if the date in DB differs from the incoming date value and whether it should be updated or not. 
     * @param dateOne - a date value.
     * @param dateTwo - a date value.
     * @return isTheSame - a boolean value - true (if dates match) or false (if dates do not match).
     * @throws Exception
     */
    public Boolean isSameDate(Date dateOne, Date dateTwo) throws Exception {
        Boolean isTheSame = false;
        if (dateOne == null && dateTwo == null) {
            isTheSame = true;
        } else if ((dateOne == null && dateTwo != null) || (dateOne != null && dateTwo == null)) {
            isTheSame = false;
        } else if (Utils.getDate(dateOne).equals(Utils.getDate(dateTwo))) {
            isTheSame = true;
        }
        return isTheSame;
    }

    /**
     * Method will return list of available actions to specified form. 
     * @param conn - connection to the DB that will be used for data extraction
     * @param params - request parameters received from front end.
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public String getFormActions(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        Double form_id = -1.1;

        if (recordIdentifier.getId() != null) {
            form_id = Double.parseDouble(recordIdentifier.getId());
        }

        stmt.setStatement("SELECT FRA_ID," //
                + "               FRA_CODE," //
                + "               FRA_NAME " //
                + "        FROM   SPR_FORM_ACTIONS " //
                + "        WHERE  FRA_FRM_ID= ?"); //

        stmt.addSelectParam(form_id);
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Method will return list of forms for the specified role. 
     * @param conn - connection to the DB that will be used for data extraction
     * @param params - request parameters received from front end.
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("WITH ROLE_FORMS AS (SELECT RA.ROA_ID,"//
                + " RA.ROA_FRM_ID, " //
                + " RA.ROA_DATE_FROM, "//
                + " RA.ROA_DATE_TO, "//
                + " RA.ROA_ROL_ID, " //
                + " RA.ROA_DEFAULT_MENU_ITEM " //
                + " FROM SPR_ROLE_ACTIONS RA"//
                + " WHERE RA.ROA_ROL_ID=?"//
                + " AND RA.ROA_TYPE = 'FRM1') "//
                + " SELECT RF.ROA_ID,"//
                + " FRM.FRM_ID,"//
                + " FRM.FRM_CODE,"//
                + " FRM.FRM_NAME,"//
                + " TO_CHAR(RF.ROA_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as ROA_DATE_FROM, "//
                + " TO_CHAR(RF.ROA_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as ROA_DATE_TO, "//
                + " CASE WHEN RF.ROA_FRM_ID IS NULL THEN NULL ELSE 'Y' END BELONGS, "//
                + " RF.ROA_DEFAULT_MENU_ITEM, " //
                + " RF.ROA_ROL_ID " //
                + " FROM SPR_FORMS FRM LEFT JOIN ROLE_FORMS RF ON (FRM.FRM_ID = RF.ROA_FRM_ID)");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        Double role_id = Double.parseDouble(paramList.get("p_rol_id"));
        stmt.addSelectParam(role_id);
        stmt.addParam4WherePart("FRM_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("frm_id"));
        stmt.addParam4WherePart("FRM_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("frm_name"));
        stmt.addParam4WherePart("FRM_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("frm_code"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("ROA_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("roa_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("ROA_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("roa_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("FRM_ID", "FRM_NAME", "FRM_CODE",
                        "TO_CHAR(ROA_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(ROA_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprRoleActionsBrowseSecurityManager sqm = new SprRoleActionsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprRoleActionsBrowse.SET_FORM_DISABLED_ACTIONS, SprRoleActionsBrowse.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));
        stmt.setStatementOrderPart(" RF.ROA_DEFAULT_MENU_ITEM,BELONGS ");
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Method will return all available and disabled actions for specified role and form
     * @param conn - DB connections
     * @param params - parameters that we get form front-end role_id and Form type array
     * @return Available and disabled actions for specified role and form
     * @throws Exception
     */
    public String getRec(Connection conn, Form4RoleRequest params) throws NumberFormatException, Exception {
        Double role_action_id = -1.1;
        Double form_id = -1.1;
        if (params.getRoa_id() != null) {
            role_action_id = Double.parseDouble(params.getRoa_id());
            form_id = Double.parseDouble(params.getFrm_id());
        }
        this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT FA.FRA_ID,"//
                + "               FA.FRA_CODE,"//
                + "               FA.FRA_NAME,"//
                + "               DA.RDA_FRA_ID"//
                + "        FROM   SPR_FORM_ACTIONS FA"//
                + "               LEFT JOIN SPR_ROLE_DISABLED_ACTIONS DA"//
                + "               ON FA.FRA_ID=DA.RDA_FRA_ID AND DA.RDA_ROA_ID=? "//
                + "        WHERE  FRA_FRM_ID=?");//
        stmt.addSelectParam(role_action_id);
        stmt.addSelectParam(form_id);
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Method is used for assigning or updating specific form and its actions through front end 
     * 'Actions -> Edit' dialog window. Will save provided object to the DB. 
     * Before save following actions will be performed:
     *      1. Check if form is already assigned or not - depending on that, 
     *         a new record will be created or an existing one will be updated.
     *      2. Check incoming dates, disabled and enabled actions: if any changes were made,
     *         role form data will be either assigned, deleted or updated.
     *      3. Check if user is granted to perform required action.
     *      3. Validate provided data
     *      4. Save data to the DB.
     * @param conn - connection to the DB, that will be used for data saving.
     * @param params - object that should be stored in DB.
     * @return - saved object.
     * @throws Exception
     */
    public SprRoleDisabledActionsDAO saveAction(Connection conn, Request4FormActions params) throws Exception {
        SprRoleActionsDAO answerObj = null;
        Double role_id = Double.parseDouble(params.getRol_id());
        Double form_id = Double.parseDouble(params.getForm().getFrm_id());
        Date date_from = params.getForm().getRoa_date_from();
        Date date_to = params.getForm().getRoa_date_to();
        boolean formWasAlreadyAssigned = false;

        if (params.getForm().getRoa_id().isEmpty()) {
            this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_CREATE);
            answerObj = roleActionsDBService.newRecord();
            answerObj.setRoa_type("FRM1");
            answerObj.setRoa_rol_id(role_id);
            answerObj.setRoa_frm_id(form_id);
            answerObj.setRoa_date_from(checkSetDate(date_from, false));
            answerObj.setRoa_date_to(checkSetDate(date_to, true));
        } else {
            formWasAlreadyAssigned = true;
            answerObj = roleActionsDBService.loadRecord(conn, role_id, form_id);
            Date db_date_from = answerObj.getRoa_date_from();
            Date db_date_to = answerObj.getRoa_date_to();

            if (!isSameDate(db_date_from, date_from)) {
                this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_UPDATE);
                answerObj.setRoa_date_from(checkSetDate(date_from, false));
            }
            if (!isSameDate(db_date_to, date_to)) {
                this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_UPDATE);
                answerObj.setRoa_date_to(date_to);
            }
        }
        answerObj = roleActionsDBService.saveRecord(conn, answerObj);
        roleActionsDBService.removeCachedRole(role_id);

        SprRoleActionsDAO roleActionObj = roleActionsDBService.loadRecord(conn, role_id, form_id);
        Double roa_id = roleActionObj.getRoa_id();
        int disabledActionsCnt = params.getForm().getDisabled_actions().length;
        int enabledActionsCnt = params.getForm().getEnabled_actions().length;
        int availableActionsCnt = Integer.parseInt(params.getavailableActionsCnt());

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT RDA_FRA_ID FROM SPR_ROLE_DISABLED_ACTIONS WHERE RDA_ROA_ID=?");
        stmt.addSelectParam(roa_id);
        List<HashMap<String, String>> data = queryController.selectQueryAsDataArrayList(conn, stmt);

        Set<String> assignedIds = params.getForm().getSet();
        List<Integer> new_rda_list = new ArrayList<Integer>();
        for (String rda : assignedIds) {
            int new_rda = Integer.parseInt(rda);
            new_rda_list.add(new_rda);
        }
        List<Integer> db_rda_list = new ArrayList<Integer>();
        for (HashMap<String, String> rec : data) {
            int existing_rda = Integer.parseInt(rec.get("rda_fra_id"));
            db_rda_list.add(existing_rda);
        }
        Collections.sort(new_rda_list);
        Collections.sort(db_rda_list);

        boolean actionChangesWereMade = !new_rda_list.equals(db_rda_list);

        if ((disabledActionsCnt != 0) && ((enabledActionsCnt != availableActionsCnt) && (disabledActionsCnt != availableActionsCnt))) {
            if (actionChangesWereMade) {
                this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.SET_FORM_DISABLED_ACTIONS);
                for (HashMap<String, String> rec : data) {
                    String rda_fra_id = rec.get("rda_fra_id");
                    assignedIds.remove(rda_fra_id);
                    roleDisabledActionsDBService.deleteSpecifiedRoleFormActions(conn, Double.parseDouble(rda_fra_id), roa_id);
                }
                for (String fra_id : params.getForm().getDisabled_actions()) {
                    SprRoleDisabledActionsDAO disabledAction = roleDisabledActionsDBService.newRecord();
                    disabledAction.setRda_roa_id(roa_id);
                    disabledAction.setRda_fra_id(Double.parseDouble(fra_id));
                    disabledAction.setRda_date_from(checkSetDate(params.getForm().getRoa_date_from(), false));
                    disabledAction.setRda_date_to(checkSetDate(params.getForm().getRoa_date_to(), true));
                    roleDisabledActionsDBService.saveRecord(conn, disabledAction);
                }
            }
        } else if ((disabledActionsCnt == 0) && (enabledActionsCnt == availableActionsCnt)) {
            if (formWasAlreadyAssigned && actionChangesWereMade) {
                this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.SET_FORM_DISABLED_ACTIONS);
                roleDisabledActionsDBService.deleteAllRoleFormActions(conn, roa_id);
            } else if (!formWasAlreadyAssigned && actionChangesWereMade) {
                this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.SET_FORM_DISABLED_ACTIONS);
                this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_CREATE);
                roleDisabledActionsDBService.deleteAllRoleFormActions(conn, roa_id);
            }
        } else if ((enabledActionsCnt == 0) && (disabledActionsCnt == availableActionsCnt)) {
            this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.SET_FORM_DISABLED_ACTIONS);
            this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_DELETE);
            roleDisabledActionsDBService.deleteAllRoleFormActions(conn, roa_id);
            roleActionsDBService.deleteDisabledForms(conn, role_id, form_id);
        }
        clearRelatedCaches(conn);
        return null;
    }

    /**
     * Method will save provided objects to the DB. Before save, following actions will be performed:
     *      1. Check if user is granted to perform this action (create or delete).
     *      2. Validate provided data
     *      3. Save data to the DB.
     * @param conn - connection to the DB, that will be used for data saving.
     * @param params - objects that should be stored in DB.
     * @return - saved object.
     * @throws Exception
     */
    public SprRoleDisabledActionsDAO saveActions(Connection conn, Request4FormState params) throws Exception {
        Double rol_id = Double.parseDouble(params.getRol_id());
        if (params.getEnabled_forms().length != 0) {
            this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_CREATE);
            for (Form form : params.getEnabled_forms()) {
                if (form.getRoa_id() == null) {
                    SprRoleActionsDAO answerObj = roleActionsDBService.newRecord();
                    answerObj.setRoa_type("FRM1");
                    answerObj.setRoa_rol_id(rol_id);
                    answerObj.setRoa_frm_id(Double.parseDouble(form.getFrm_id()));
                    answerObj.setRoa_date_from(checkSetDate(form.getRoa_date_from(), false));
                    answerObj.setRoa_date_to(checkSetDate(form.getRoa_date_to(), true));
                    roleActionsDBService.saveRecord(conn, answerObj);
                }
            }
        }
        if (params.getDisabled_forms().length != 0) {
            this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_DELETE);
            for (Form4RoleRequest disabled_form : params.getDisabled_forms()) {
                Double form_id = Double.parseDouble(disabled_form.getFrm_id());
                roleDisabledActionsDBService.deleteAllRoleFormActions(conn, Double.parseDouble(disabled_form.getRoa_id()));
                roleActionsDBService.deleteDisabledForms(conn, rol_id, form_id);
            }
        }
        clearRelatedCaches(conn);
        return null;
    }

    /**
     * Function will clear with user roles related caches.
     * @param conn - connection to the db.
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

    /**
     * Function will return role actions for the given role ID
     * @param conn - connection to the DB
     * @param roleId - given role ID
     * @return JSON object
     * @throws Exception
     */
    public String getRoleActions(Connection conn, String roleId) throws Exception {
        this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ROA_ID," //
                + "               ROA_DEFAULT_MENU_ITEM," //
                + "               ROA_ROL_ID, " //
                + "               FRM_NAME " //
                + "        FROM   SPR_ROLE_ACTIONS " //
                + "        INNER JOIN SPR_FORMS ON FRM_ID = ROA_FRM_ID " //
                + "        WHERE  ROA_ROL_ID = ? "); //
        stmt.addSelectParam(Utils.getDouble(roleId));
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Function will change the default role form according to given roa ids
     * @param conn - connection to the DB
     * @param record - RoleDefaultFormReq object that contains previous default role form roa_id and new default role form roa_id
     * @throws Exception
     */
    public void setDefaultRoleForm(Connection conn, RoleRequest record) throws Exception {
        this.checkIsFormActionAssigned(conn, SprRoleActionsBrowse.ACTION_UPDATE);
        if (record.getRoa_id() != null) {
            this.clearPreviousDefaultForm(conn, Utils.getDouble(record.getRoa_rol_id()));
            SprRoleActionsDAO newActionDAO = roleActionsDBService.loadRecord(conn, Utils.getDouble(record.getRoa_id()));
            newActionDAO.setRoa_default_menu_item(DbConstants.BOOLEAN_TRUE);
            roleActionsDBService.updateRecord(conn, newActionDAO);
        } else {
            this.clearPreviousDefaultForm(conn, Utils.getDouble(record.getRoa_rol_id()));
        }
    }

    private void clearPreviousDefaultForm(Connection conn, Double roleId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT ROA_ID AS ID "//
                + "         FROM   SPR_ROLE_ACTIONS "//
                + "         WHERE  ROA_ROL_ID = ? AND ROA_DEFAULT_MENU_ITEM = 'Y' ");//
        stmt.addSelectParam(roleId);
        List<RecordIdentifier> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt,
                new Data2ObjectProcessor<RecordIdentifier>(RecordIdentifier.class));
        if (!queryResult.isEmpty()) {
            SprRoleActionsDAO oldActionDAO = roleActionsDBService.loadRecord(conn, Utils.getDouble(queryResult.get(0).getId()));
            oldActionDAO.setRoa_default_menu_item(null);
            roleActionsDBService.updateRecord(conn, oldActionDAO);
        }
    }
}