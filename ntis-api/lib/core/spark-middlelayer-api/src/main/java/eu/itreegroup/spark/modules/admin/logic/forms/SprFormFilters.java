package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.model.PredefinedFilterStructure;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprFormDataFiltersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprFormFiltersSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprFormDataFiltersDBService;
import eu.itreegroup.spark.modules.admin.service.SprFormsDBService;

@Component
public class SprFormFilters extends FormBase {

    public static String ACTION_MANAGE_GLOBAL_FILTER = "GLOBAL_FILTER_MANAGER";

    public static String ACTION_MANAGE_GLOBAL_FILTER_DESC = "Possibility to manage globally defined filters";

    private static final Logger log = LoggerFactory.getLogger(SprFormFilters.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprFormDataFiltersDBService sprFormDataFiltersDBService;

    @Autowired
    SprFormsDBService formsDBService;

    private Gson gson = new Gson();

    /**
     * Method will return Angular form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_FORMS_DATA_FILTER";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Filters list for spark form", "Filters list for spark form");
        addFormActionCRUD();
        this.addFormAction(ACTION_MANAGE_GLOBAL_FILTER, ACTION_MANAGE_GLOBAL_FILTER_DESC, ACTION_MANAGE_GLOBAL_FILTER_DESC);
    }

    public String getFilters4Form(Connection conn, String formCode, Double userId, String language) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select t1.fdf_id, t1.fdf_code, t1.fdf_name, t1.fdf_description, t1.fdf_usr_id " //
                + "  from SPR_FORM_DATA_FILTERS t1 " //
                + "  join SPR_FORMS t2 on t2.frm_id = t1.fdf_frm_id " //
                + " where  " //
                + "  t2.frm_code = ? and  " //
                + "  coalesce(t1.fdf_usr_id, ? ) = ? ");
        stmt.addSelectParam(formCode);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(userId);

        SprFormFiltersSecurityManager sqm = new SprFormFiltersSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprFormsBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    public void delFilter4Form(Connection conn, Double filterId, Double userId) throws Exception {
        checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_DELETE);
        SprFormDataFiltersDAO sprFormDataFiltersDAO;
        try {
            checkIsFormActionAssigned(conn, SprFormFilters.ACTION_MANAGE_GLOBAL_FILTER);
            sprFormDataFiltersDAO = sprFormDataFiltersDBService.loadRecord(conn, filterId);
        } catch (Exception ex) {
            sprFormDataFiltersDAO = sprFormDataFiltersDBService.loadFilterForUser(conn, filterId, userId);
        }
        sprFormDataFiltersDBService.deleteRecord(conn, sprFormDataFiltersDAO.getFdf_id());
    }

    public SprFormDataFiltersDAO saveFilter4Form(Connection conn, ArrayList<AdvancedSearchParameterStatement> params, String predefinedCondition,
            String formCode, String filterName, String filterDescription, boolean isGlobal, Double userId) throws Exception {
        log.debug("Start save filterdata ");

        SprFormDataFiltersDAO sprFormDataFiltersDAO = sprFormDataFiltersDBService.loadRecordByParams(conn,
                " Fdf_code = ? and  fdf_name = ? and fdf_usr_id = ? ", new SelectParamValue(formCode), new SelectParamValue(filterName),
                new SelectParamValue(userId));
        if (sprFormDataFiltersDAO == null) {
            sprFormDataFiltersDAO = sprFormDataFiltersDBService.newRecord();
        }

        checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_CREATE);
        PredefinedFilterStructure predefinedStructure = new PredefinedFilterStructure();
        predefinedStructure.setPredefinedCondition(predefinedCondition);
        for (int i = params.size() - 1; i >= 0; i--) {
            if (params.get(i).isEmpty()) {
                params.remove(i);
            }
        }
        predefinedStructure.setExtendedParams(params);
        sprFormDataFiltersDAO.setFdf_content(gson.toJson(predefinedStructure));
        SprFormsDAO sprFormsDAO = formsDBService.loadRecordByCode(conn, formCode);
        sprFormDataFiltersDAO.setFdf_frm_id(sprFormsDAO.getFrm_id());

        if (isGlobal) {
            checkIsFormActionAssigned(conn, SprFormFilters.ACTION_MANAGE_GLOBAL_FILTER);
        } else {
            sprFormDataFiltersDAO.setFdf_usr_id(userId);
        }
        sprFormDataFiltersDAO.setFdf_code(formCode);
        sprFormDataFiltersDAO.setFdf_name(filterName);
        sprFormDataFiltersDAO.setFdf_description(filterDescription);
        sprFormDataFiltersDAO.setFdf_date_from(Utils.getDate());
        sprFormDataFiltersDAO = sprFormDataFiltersDBService.saveRecord(conn, sprFormDataFiltersDAO);
        return sprFormDataFiltersDAO;
    }

}
