package eu.itreegroup.spark.app.common;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.model.PredefinedFilterStructure;
import eu.itreegroup.spark.app.model.RoleFormsActions;
import eu.itreegroup.spark.app.tools.DBFormManager;
import eu.itreegroup.spark.app.tools.RoleManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprFormActionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

public abstract class FormBase {

    private static final Logger log = LoggerFactory.getLogger(FormBase.class);

    public static String ACTION_CREATE = "CREATE";

    public static String ACTION_READ = "READ";

    public static String ACTION_UPDATE = "UPDATE";

    public static String ACTION_DELETE = "DELETE";

    public static String ACTION_COPY = "COPY";

    public static String PREDEFINED_FILTER_PARAM = "predefinedFilter";

    public static String ACTION_CREATE_DESC = "Record creation right";

    public static String ACTION_READ_DESC = "Record read right";

    public static String ACTION_UPDATE_DESC = "Record update right";

    public static String ACTION_DELETE_DESC = "Record delete right";

    public static String ACTION_COPY_DESC = "Record copy right";

    public static String[] DEFAULT_ACTIONS_ORDER = { FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_COPY, FormBase.ACTION_DELETE };

    @SuppressWarnings("rawtypes")
    @Autowired
    SprAuthorization sprAuthorization;

    @Autowired
    DBFormManager dbFormManager;

    @Value("${app.db.type}")
    private String dbType;

    @Value("${app.init.forms.on.start}")
    private boolean initFormActions;

    protected ArrayList<SprFormActionsDAO> formActions = new ArrayList<SprFormActionsDAO>();

    protected SprFormsDAO sprFormsDAO = new SprFormsDAO();

    /**
     * Method will return Angular form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    public abstract String getFormName();

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    public abstract void defineFormAndActions();

    public ArrayList<SprFormActionsDAO> getFormActions() {
        return formActions;
    }

    public SprFormsDAO getSprFormsDAO() {
        return this.sprFormsDAO;
    }

    @PostConstruct
    public void initFormInDB() {
        log.debug("---------------------------------------------------------------");
        log.debug("-from initFormInDB()");
        log.debug("-dbFormManager: " + dbFormManager);
        log.debug("-dbType: " + dbType);
        log.debug("---------------------------------------------------------------");
        defineFormAndActions();
        dbFormManager.addFormToList(this);
    }

    protected void setFormInfo(String formCode, String formName, String formDescription) {
        sprFormsDAO.setFrm_code(formCode);
        sprFormsDAO.setFrm_name(formName);
        sprFormsDAO.setFrm_description(formDescription);
    }

    protected void addFormActionCRUD() {
        if (initFormActions) {
            addFormAction(FormBase.ACTION_CREATE, FormBase.ACTION_CREATE_DESC, FormBase.ACTION_CREATE_DESC);
            addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
            addFormAction(FormBase.ACTION_UPDATE, FormBase.ACTION_UPDATE_DESC, FormBase.ACTION_UPDATE_DESC);
            addFormAction(FormBase.ACTION_DELETE, FormBase.ACTION_DELETE_DESC, FormBase.ACTION_DELETE_DESC);
            addFormAction(FormBase.ACTION_COPY, FormBase.ACTION_COPY_DESC, FormBase.ACTION_COPY_DESC);
        }
    }

    protected void addFormAction(String code, String name, String description) {
        if (initFormActions) {
            SprFormActionsDAO obj = new SprFormActionsDAO();
            obj.setFra_code(code);
            obj.setFra_name(name);
            obj.setFra_description(description);
            formActions.add(obj);
        }
    }

    protected void addFormActions(String... codes) {
        for (String code : codes) {
            if (FormBase.ACTION_CREATE.equals(code)) {
                addFormAction(FormBase.ACTION_CREATE, FormBase.ACTION_CREATE_DESC, FormBase.ACTION_CREATE_DESC);
            } else if (FormBase.ACTION_READ.equals(code)) {
                addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
            } else if (FormBase.ACTION_UPDATE.equals(code)) {
                addFormAction(FormBase.ACTION_UPDATE, FormBase.ACTION_UPDATE_DESC, FormBase.ACTION_UPDATE_DESC);
            } else if (FormBase.ACTION_DELETE.equals(code)) {
                addFormAction(FormBase.ACTION_DELETE, FormBase.ACTION_DELETE_DESC, FormBase.ACTION_DELETE_DESC);
            } else if (FormBase.ACTION_COPY.equals(code)) {
                addFormAction(FormBase.ACTION_COPY, FormBase.ACTION_COPY_DESC, FormBase.ACTION_COPY_DESC);
            }
        }
    }

    /**
     * Function will check if user has right access to the provided form action. In case if user has right function will return true otherwise false
     * @param conn - connection to the db
     * @param action - action code
     * @return true if user has access right, otherwise false
     */
    protected boolean isFormActionAssigned(Connection conn, String action) {
        return isFormActionAssigned(conn, action, getFormName());
    }

    /**
     * Function will check if user has right access to the provided form action. In case if user has right function will return true otherwise false
     * @param conn - connection to the db
     * @param action - action code
     * @param formName - form name for which will be checked access right.
     * @return true if user has access right, otherwise false
     */
    protected boolean isFormActionAssigned(Connection conn, String action, String formName) {
        boolean answer = false;
        try {
            log.debug("Start check action for form: " + formName);
            RoleFormsActions roleFormsActions = sprAuthorization.getForms4Role(conn);
            if (roleFormsActions != null) {
                log.debug("Check action: " + action + " for form: " + formName);
                answer = roleFormsActions.isActionAvailable(formName, action);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return answer;
    }

    public FormActions getFormActions(Connection conn) {
        try {
            RoleFormsActions roleFormsActions = sprAuthorization.getForms4Role(conn);
            return roleFormsActions.getFormActions(getFormName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Function will load user role manager, that contains information about assigned roles.
     * @param conn - connection to the db
     * @return RoleManager has information about assigned roles to the user.
     */
    public RoleManager getUserRoleManager(Connection conn) {
        try {
            return sprAuthorization.getUserRoleManager(conn);
        } catch (Exception ex) {
            return new RoleManager();
        }
    }

    /**
     * Function will check if current user has provided role as parameter.
     * @param conn - connection to the DB
     * @param userRoleCode - user role code which should be checked in user role set
     * @return true if user has provided role code otherwise false
     */
    public boolean hasUserRole(Connection conn, String userRoleCode) {
        return getUserRoleManager(conn).userHasRole(userRoleCode);
    }

    protected void checkIsFormActionAssigned(Connection conn, String action) throws SparkBusinessException {
        checkIsFormActionAssigned(conn, action, this.getFormName());
    }

    protected void checkIsFormActionAssigned(Connection conn, String action, String formName) throws SparkBusinessException {
        if (!isFormActionAssigned(conn, action, formName)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    protected void managePredefinedFilterStructure(Connection conn, AdvancedSearchParameterStatement searchParam, Double userId, StatementAndParams stmt,
            HashMap<String, AdvancedSearchParameterStatement> advancedParamList) throws Exception {
        if (searchParam != null) {
            PredefinedFilterStructure predefinedStructure = dbFormManager.getPredefinedFormFilterData(conn, searchParam.getParamValue().getValue(), userId);
            if (predefinedStructure != null) {
                ArrayList<AdvancedSearchParameterStatement> getExtendedParams = predefinedStructure.getExtendedParams();
                if (getExtendedParams != null) {
                    for (int i = 0; i < getExtendedParams.size(); i++) {
                        AdvancedSearchParameterStatement parameter = getExtendedParams.get(i);
                        if (!advancedParamList.containsKey(parameter.getParamName())) {
                            advancedParamList.put(parameter.getParamName(), parameter);
                        } else {
                            AdvancedSearchParameterStatement advancedParam = advancedParamList.get(parameter.getParamName());
                            if (advancedParam.isEmpty()) {
                                advancedParamList.replace(parameter.getParamName(), parameter);
                            }

                        }
                    }
                }
                stmt.setPredefinedConditionStetement(predefinedStructure.getPredefinedCondition());
            }
        }
    }

    /**
     * Function will return SprAuthorization class instance.
     * @return
     */
    protected SprAuthorization<?> getSprAuthorization() {
        return this.sprAuthorization;
    }

}