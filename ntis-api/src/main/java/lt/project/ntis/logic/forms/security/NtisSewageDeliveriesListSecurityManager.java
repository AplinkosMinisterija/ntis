package lt.project.ntis.logic.forms.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityException;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUsersBrowse;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.project.ntis.logic.forms.NtisSewageDeliveriesList;

/**
 * Klasė skirta formos "Nuotekų tvarkymo užsakymų sąrašas (pasl. teikejas)"  biznio logikai apibrėžti
 */

public class NtisSewageDeliveriesListSecurityManager extends QueryResultSecurityManager<SprBackendUserSession> {
    
    @Override
    public String getRowActionsJSON(Map<String, String> row) throws QueryResultSecurityException {
        if (getFormActions() != null) {
            String hasChild = row.get("has_child");
            @SuppressWarnings("unchecked")
            List<String> availableActions = (ArrayList<String>) getFormActions().getMenuAvailableActions().clone();
            availableActions.add(NtisSewageDeliveriesList.ACTION_CANCEL);
            if (hasChild != null && "Y".equals(hasChild)) {
                availableActions.remove(FormBase.ACTION_COPY);
            } 
            JSONArray jsonArray = new JSONArray(availableActions);
            return " \"availableActions\":" + jsonArray.toString();
        } else {
            return "  \"availableActions\":\"[]\"";
        }
    }
    

}
