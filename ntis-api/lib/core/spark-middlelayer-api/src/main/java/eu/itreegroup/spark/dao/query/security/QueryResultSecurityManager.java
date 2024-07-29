package eu.itreegroup.spark.dao.query.security;

import java.util.Map;

import org.json.JSONArray;

import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.query.RecordData;
import eu.itreegroup.spark.dto.RecordBase;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

/**
 * Class responsible for available actions form selected form and record definition. This is main class. For
 * each form this class should be used ass base class.
 * 
 *
 */
public class QueryResultSecurityManager<BUS extends SprBackendUserSession> {

    private FormActions formActions;

    private BUS userSession;

    public void setFormActions(FormActions formActions) {
        this.formActions = formActions;
    }

    public FormActions getFormActions() {
        return this.formActions;
    }

    public void setBackendUserSession(BUS userSession) {
        this.userSession = userSession;
    }

    public BUS getBackendUserSession() {
        return this.userSession;
    }

    /**
    * Method is responsible for available actions definitions for record. 
    * This method will be called from BaseControllerJDBC.selectQueryAsJSON
    * @param row - row data for which should calculated available actions.
    * @return actions list for current row.
    */
    public String getRowActionsJSON(Map<String, String> row) throws QueryResultSecurityException {
        if (formActions != null) {
            JSONArray jsonArray = new JSONArray(formActions.getMenuAvailableActions());
            return " \"availableActions\":" + jsonArray.toString();
        } else {
            return "  \"availableActions\":[]";
        }
    }

    /**
    * Method is responsible for apply defined logic for received recod frm DB. 
    * This method will be called from Data2ObjectProcessor.getClassData
    * @param row - row data for which should calculated available actions and apply defined logic.
    * @return actions list for current row.
    */
    public void applySecurityLogicForDto(RecordBase record) throws QueryResultSecurityException {
        if (formActions != null) {
            record.setAvailableActions(formActions.getMenuAvailableActions());
        }
    }

    /**
     * Function should be used for data modification before data transfer to the client as JSON.
     * @param record - record data from db.
     */
    public void manageRecordData(RecordData record) {
    }
}
