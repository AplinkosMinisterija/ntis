package lt.project.ntis.logic.forms.security;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityException;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

public class NtisCwDataSecurityManager extends QueryResultSecurityManager<SprBackendUserSession> {

    private final FileStorageService fileStorageService;

    public NtisCwDataSecurityManager(FileStorageService fileStorageService) {
        super();
        this.fileStorageService = fileStorageService;
    }

    /**
     * Encrypt file key.
     * @param row - record row.
     * @return JSON array of available actions for provided record.
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getRowActionsJSON(Map<String, String> row) throws QueryResultSecurityException {
        if (row.get("fil_key") != null) {
            row.put("fil_key", fileStorageService.encryptFileKey(row.get("fil_key")));
        }

        ArrayList<String> availableActions = new ArrayList<String>();
        availableActions.add(FormBase.ACTION_READ);
        availableActions.add(FormBase.ACTION_UPDATE);
        availableActions.add(FormBase.ACTION_DELETE);
        if (getFormActions() != null) {
            JSONArray jsonArray = new JSONArray(availableActions);
            return " \"availableActions\":" + jsonArray.toString();
        } else {
            return "  \"availableActions\":\"[]\"";
        }

    }

}
