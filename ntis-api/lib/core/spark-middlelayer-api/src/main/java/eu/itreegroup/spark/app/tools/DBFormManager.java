package eu.itreegroup.spark.app.tools;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.PredefinedFilterStructure;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprFormActionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.FormPredefinedData;
import eu.itreegroup.spark.modules.admin.logic.forms.model.PredefinedFilter;
import eu.itreegroup.spark.modules.admin.service.SprFormActionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprFormsDBService;

@Component
public class DBFormManager {

    private static final Logger log = LoggerFactory.getLogger(DBFormManager.class);

    ArrayList<FormBase> forms;

    @Autowired
    DataSource dataSource;

    @Autowired
    SprFormsDBService sprFormDBServie;

    @Autowired
    SprFormActionsDBService sprFormActionsDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Value("${app.init.forms.on.start}")
    private boolean initFormActions;

    private Gson gson;

    public DBFormManager() {
        forms = new ArrayList<FormBase>();
        gson = new Gson();
    }

    @EventListener({ ContextRefreshedEvent.class })
    public void afterContextRefresh() {
        log.debug("Start DBFormManager.afterContextRefresh");
        log.debug("Forms setup creation in DB on system startup is " + (initFormActions ? "enabled" : "disabled"));
        if (initFormActions) {
            registerFormsInDB();
        }
    }

    public void registerFormsInDB() {
        log.debug("Start sychronize forms info in DB");
        try {
            Connection conn = dataSource.getConnection();
            for (int i = 0; i < forms.size(); i++) {
                FormBase frm = forms.get(i);
                log.debug("will process class by name: " + frm);
                log.debug("check form by name: " + frm.getSprFormsDAO().getFrm_code());
                SprFormsDAO frmDAO = null;
                try {
                    frmDAO = sprFormDBServie.loadRecordByCode(conn, frm.getSprFormsDAO().getFrm_code());
                    if (frmDAO == null) {
                        throw new Exception("Form not found");
                    }
                } catch (Exception ex) {
                    try {
                        frmDAO = sprFormDBServie.saveRecord(conn, frm.getSprFormsDAO());
                    } catch (Exception ex1) {
                        log.error("Error while insert form: " + frm.getSprFormsDAO().getFrm_code(), ex1);
                    }
                }
                ArrayList<SprFormActionsDAO> formActions = frm.getFormActions();
                for (int j = 0; j < formActions.size(); j++) {
                    SprFormActionsDAO frmAction = formActions.get(j);
                    frmAction.setFra_frm_id(frmDAO.getFrm_id());
                    try {
                        sprFormActionsDBService.loadRecordByFormIdAndCode(conn, frmDAO.getFrm_id(), frmAction.getFra_code());
                    } catch (Exception ex) {
                        try {
                            sprFormActionsDBService.saveRecord(conn, frmAction);
                        } catch (Exception ex1) {
                            log.error("Error while insert form: " + frm.getSprFormsDAO().getFrm_code() + " action: " + frmAction.getFra_code(), ex1);
                        }
                    }
                }
            }
            conn.close();
            log.debug("End sychronize forms info in DB");
        } catch (Exception e) {
            log.error("Something wrong", e);
        }
    }

    public void addFormToList(FormBase form) {
        forms.add(form);
    }

    /**
     * Function will get list of predefined filters for provided form. 
     * @param conn - connection to the db
     * @param formcode - form code for which should be returned filters
     * @param userId - user identifier for selecting filter defined by this user in this form
     * @param language - language, in what should be returned filter description.
     * @return list of defined filters for provided form code.
     * @throws Exception
     */
    public FormPredefinedData getPredefinedFormData(Connection conn, String formCode, Double userId, String language) throws Exception {
        FormPredefinedData answer = new FormPredefinedData();
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                select t.fdf_id, T.fdf_code, T.fdf_name, t.fdf_usr_id , t.fdf_content
                  from SPR_FORM_DATA_FILTERS t
                  join SPR_FORMS f on f.frm_id = t.fdf_frm_id
                where f.frm_code = ?
                and coalesce(t.fdf_usr_id, ? ) = ?
                """);
        stmt.addSelectParam(formCode);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(userId);
        List<HashMap<String, String>> data = queryController.selectQueryAsDataArrayList(conn, stmt);
        List<PredefinedFilter> filterDataList = new ArrayList<>();
        data.forEach(rec -> {
            PredefinedFilter filter = new PredefinedFilter();
            filter.setId(rec.get("fdf_id"));
            filter.setFilterCode(rec.get("fdf_code"));
            filter.setFilterName(rec.get("fdf_name"));
            filter.setUserId(rec.get("fdf_usr_id"));
            filter.setFilterContent(rec.get("fdf_content"));
            filterDataList.add(filter);
        });
        answer.setPredefinedFilters(filterDataList);
        return answer;
    }

    /**
     * Function will return predefined filter data. Filter data will be selected by proveded filter identifier
     * @param conn - connection to the db
     * @param predefinedFilterId - filter identifier
     * @param userId - user identifier, that request filter data
     * @return - selected filter data. In case if filter with provided identifier not found will return null.
     * @throws Exception
     */
    public PredefinedFilterStructure getPredefinedFormFilterData(Connection conn, String predefinedFilterId, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        Double filterId = Utils.getDouble(predefinedFilterId);
        log.debug("getPredefinedFormFilterData: " + filterId);
        if (filterId != null) {
            stmt.setStatement("select t.fdf_content from SPR_FORM_DATA_FILTERS t where t.fdf_id = ? and coalesce(t.fdf_usr_id, ?) = ?");
            stmt.addSelectParam(filterId);
            stmt.addSelectParam(userId);
            stmt.addSelectParam(userId);
            String filterJSON = null;
            List<HashMap<String, String>> data = queryController.selectQueryAsDataArrayList(conn, stmt);
            if (data.size() > 0) {
                filterJSON = data.get(data.size() - 1).get("fdf_content");
                log.debug("filterJSON: " + filterJSON);
                return gson.fromJson(filterJSON, PredefinedFilterStructure.class);
            }
        }
        return null;
    }
}
