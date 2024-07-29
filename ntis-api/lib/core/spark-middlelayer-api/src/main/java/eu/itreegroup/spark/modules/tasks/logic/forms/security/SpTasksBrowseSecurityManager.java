package eu.itreegroup.spark.modules.tasks.logic.forms.security;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityException;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

public class SpTasksBrowseSecurityManager extends QueryResultSecurityManager<SprBackendUserSession> {

    /**
     * In case if task priority (tas_priority) is more then 2 deletion of this task is disallowed.
     * @param row - record row.
     * @return JSON array of available actions for provided record.
     */
    @Override
    public String getRowActionsJSON(Map<String, String> row) throws QueryResultSecurityException {
        ArrayList<String> availableActions = new ArrayList<String>();
        availableActions.add(FormBase.ACTION_READ);
        availableActions.add(FormBase.ACTION_UPDATE);
        availableActions.add(FormBase.ACTION_COPY);
        if (getFormActions() != null) {
            String tas_priority = row.get("tas_priority");
            if (Integer.parseInt(tas_priority) > 2) {
                JSONArray jsonArray = new JSONArray(availableActions);
                return " \"availableActions\":" + jsonArray.toString();
            } else {
                availableActions.add(FormBase.ACTION_DELETE);
                JSONArray jsonArray = new JSONArray(availableActions);
                return " \"availableActions\":" + jsonArray.toString();
            }
        } else {
            return "  \"availableActions\":\"[]\"";
        }
    }
}
