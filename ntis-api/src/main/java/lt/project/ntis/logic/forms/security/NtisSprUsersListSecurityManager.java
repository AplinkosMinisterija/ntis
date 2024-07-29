package lt.project.ntis.logic.forms.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import eu.itreegroup.spark.dao.query.security.QueryResultSecurityException;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUsersBrowse;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

public class NtisSprUsersListSecurityManager extends QueryResultSecurityManager<SprBackendUserSession> {

    private static final String IS_NEW_USER = "1";

    private static final String IS_USER_IDSABLED = "1";

    private static final String PRIVATE = "PRIVATE";

    @Override
    public String getRowActionsJSON(Map<String, String> row) throws QueryResultSecurityException {
        if (getFormActions() != null) {
            String newUser = row.get("new_user");
            String useDisabled = row.get("user_disabled");
            String userType = row.get("usr_type_code");
            @SuppressWarnings("unchecked")
            List<String> availableActions = (ArrayList<String>) getFormActions().getMenuAvailableActions().clone();
            if (IS_USER_IDSABLED.equalsIgnoreCase(useDisabled)) {
                availableActions.remove(SprUsersBrowse.INFORM_NEW_USER);
                availableActions.remove(SprUsersBrowse.RESET_PASSWORD);
            } else {
                availableActions.remove(IS_NEW_USER.equalsIgnoreCase(newUser) ? SprUsersBrowse.RESET_PASSWORD : SprUsersBrowse.INFORM_NEW_USER);
            }
            if (userType.equalsIgnoreCase(PRIVATE)) {
                availableActions.remove(SprUsersBrowse.ASSIGN_ORG_ACTION);
            }
            JSONArray jsonArray = new JSONArray(availableActions);
            return " \"availableActions\":" + jsonArray.toString();
        } else {
            return "  \"availableActions\":\"[]\"";
        }
    }
}
