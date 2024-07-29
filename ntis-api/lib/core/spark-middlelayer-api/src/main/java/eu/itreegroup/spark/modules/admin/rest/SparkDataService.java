package eu.itreegroup.spark.modules.admin.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.s2.server.rest.model.CreateNewUserRequest;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dto.BrowseData;
import eu.itreegroup.spark.modules.admin.dao.SprApiKeysDAO;
import eu.itreegroup.spark.modules.admin.dao.SprAuditableTablesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprFormDataFiltersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprMenuStructuresDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRefDictionariesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRefTranslationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprApiKeyEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprApiKeysBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprAuditableTablesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprCacheManager;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqThemesList;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFormFilters;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFormsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFormsEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprJobEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprJobRequestsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprJobsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprMenuStructuresBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprMenuStructuresEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprMenuStructuresTree;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrgUserRolesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrgUsersList;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrganizationRolesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrganizationsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrganizationsEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprPersonList;
import eu.itreegroup.spark.modules.admin.logic.forms.SprPersonsEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprProfileSettings;
import eu.itreegroup.spark.modules.admin.logic.forms.SprPropertiesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprPropertiesEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRefClassifier;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRefClassifierEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRefDictionariesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRefDictionariesEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRoleActionsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRoleActionsRole;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRoleOrganizationsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRoleUsersBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRolesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprRolesEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprSessionsClosedBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprSessionsOpenBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprTemplateEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprTemplatesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUserOrgsList;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUserRolesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUserRolesEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUsersBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUsersEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.model.ApiKey;
import eu.itreegroup.spark.modules.admin.logic.forms.model.CacheInfo;
import eu.itreegroup.spark.modules.admin.logic.forms.model.CodeDictionaryModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.FaqModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Form4RoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.logic.forms.model.MenuStructureRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.OrgUserRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.OrgUserRoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.OrganizationRoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RefTranslationsObj;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Request4FormActions;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Request4FormState;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Request4RefCode;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRolesDate;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RoleRolesRecord;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprJobAvailableTemplate;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprProfile;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprPropertiesModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprTemplate;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SwitchStatus;
import eu.itreegroup.spark.modules.admin.logic.forms.model.UserRoleDate;
import eu.itreegroup.spark.modules.admin.logic.forms.model.UserRoleRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.model.list.FormListRec;
import eu.itreegroup.spark.modules.admin.rest.model.PredefinedFilterRestDataModel;
import eu.itreegroup.spark.modules.admin.rest.model.Request4FormsDefinedData;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@RestController
@RequestMapping("/spark")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SparkDataService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SparkDataService.class);

    @Autowired
    SprRefClassifier sprRefClassifier;

    @Autowired
    SprRefClassifierEdit sprRefClassifierEdit;

    @Autowired
    SprPropertiesBrowse sprPropertiesBrowse;

    @Autowired
    SprRefDictionariesBrowse sprRefDictionariesBrowse;

    @Autowired
    SprRefDictionariesEdit sprRefDictionariesEdit;

    @Autowired
    SprPropertiesEdit sprPropertiesEdit;

    @Autowired
    SprOrganizationsBrowse sprOrganizationsBrowse;

    @Autowired
    SprOrganizationsEdit sprOrganizationsEdit;

    @Autowired
    SprOrganizationRolesBrowse sprOrganizationRolesBrowse;

    @Autowired
    SprSessionsOpenBrowse sprSessionsOpenBrowse;

    @Autowired
    SprSessionsClosedBrowse sprSessionsClosedBrowse;

    @Autowired
    SprRolesBrowse sprRolesBrowse;

    @Autowired
    SprRolesEdit sprRolesEdit;

    @Autowired
    SprUserRolesBrowse sprUserRolesBrowse;

    @Autowired
    SprUserRolesEdit sprUserRolesEdit;

    @Autowired
    SprFormsBrowse sprFormsBrowse;

    @Autowired
    SprFormsEdit sprFormsEdit;

    @Autowired
    SprMenuStructuresBrowse sprMenuStructuresBrowse;

    @Autowired
    SprMenuStructuresEdit sprMenuStructuresEdit;

    @Autowired
    private SprCacheManager sprCacheManager;

    @Autowired
    SprPersonList sprPersonList;

    @Autowired
    SprPersonsEdit sprPersonEdit;

    @Autowired
    SprRoleActionsBrowse sprRoleActionsBrowse;

    @Autowired
    SprRoleOrganizationsBrowse sprRoleOrganizationsBrowse;

    @Autowired
    SprUsersBrowse sprUsersBrowse;

    @Autowired
    SprUsersEdit sprUsersEdit;

    @Autowired
    SprMenuStructuresTree sprMenuStructuresTree;

    @Autowired
    SprProfileSettings<SprBackendUserSession> sprProfileSettings;

    @Autowired
    SprRoleActionsRole sprRoleActionsRole;

    @Autowired
    SprJobsBrowse sprJobsBrowse;

    @Autowired
    SprJobRequestsBrowse sprJobRequestsBrowse;

    @Autowired
    SprJobEdit sprJobEdit;

    @Autowired
    SprTemplatesBrowse sprTemplatesBrowse;

    @Autowired
    SprTemplateEdit sprTemplateEdit;

    @Autowired
    SprAuditableTablesBrowse sprAuditableTablesBrowse;

    @Autowired
    SprRoleUsersBrowse sprRoleUsersBrowse;

    @Autowired
    SprOrgUserRolesBrowse sprOrgUserRolesBrowse;

    @Autowired
    SprFormFilters sprFormFilters;

    @Autowired
    SprOrgUsersList sprOrgUsersList;

    @Autowired
    SprUserOrgsList sprUserOrgsList;

    @Autowired
    SprFaqThemesList sprFaqThemesList;

    @Autowired
    SprFaqBrowse sprFaqBrowse;

    @Autowired
    SprFaqEdit sprFaqEdit;

    @Autowired
    SprApiKeysBrowse sprApiKeysBrowse;

    @Autowired
    SprApiKeyEdit sprApiKeyEdit;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    @RequestMapping(value = "/find-persons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findPersons(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(sprUsersEdit.findPersons(this.getDBConnection(), foreignKeyParams));
    }

    @RequestMapping(value = "/del-spark-person", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkPersonr(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprPersonEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-persons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkPersons(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprPersonList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-person", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprPersonsDAO> getSparkPerson(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(this.sprPersonEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-spark-person", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprPersonsDAO> setSparkPerson(@RequestBody SprPersonsDAO record) throws Exception {
        return okResponse(sprPersonEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/get-spark-param", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprPropertiesModel> getSparkParamater(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprPropertiesEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/get-spark-params", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkParamaters(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprPropertiesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/check-param-constraints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<S2ViolatedConstraint>> checkParamConstraints(@RequestBody SprPropertiesModel record) throws Exception {
        return okResponse(sprPropertiesEdit.getViolatedConstraints(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/set-spark-param", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprPropertiesModel> setSparkParamater(@RequestBody SprPropertiesModel record) throws Exception {
        return okResponse(sprPropertiesEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/del-spark-param", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkParamater(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprPropertiesEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-spark-dictionary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRefDictionariesDAO> getSparkDictionary(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprRefDictionariesEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/get-dictionaries-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDictionariesList() throws Exception {
        return okResponse(sprRefDictionariesEdit.getDictionaries(this.getDBConnection()));
    }

    @RequestMapping(value = "/set-spark-dictionary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRefDictionariesDAO> setSparkDictionary(@RequestBody SprRefDictionariesDAO record) throws Exception {
        return okResponse(sprRefDictionariesEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/del-spark-dictionary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkDictionary(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprRefDictionariesEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-spark-dictionaries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkDictionaries(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprRefDictionariesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-codes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkCodes(@RequestBody SelectRequestParams params) throws Exception {
        ResponseEntity<String> answer = okResponse(
                sprRefClassifier.getCodesData(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
        return answer;
    }

    @RequestMapping(value = "/get-code-dictionary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CodeDictionaryModel> getCodeDictionary(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprRefClassifierEdit.loadDictionary(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/get-spark-translations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<RefTranslationsObj>> getSparkTranslations(@RequestBody String p_table_name) throws Exception {
        ResponseEntity<List<RefTranslationsObj>> answer = okResponse(sprRefClassifier.getTranslationsData(this.getDBConnection(), p_table_name));
        return answer;
    }

    @RequestMapping(value = "/update-rft", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRefTranslationsDAO> updateRft(@RequestBody SprRefTranslationsDAO record) throws Exception {
        sprRefClassifierEdit.saveRft(this.getDBConnection(), record);
        return okResponse(sprRefClassifierEdit.saveRft(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/get-ref-code", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRefCodesDAO> getRefCode(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ResponseEntity<SprRefCodesDAO> answer = okResponse(sprRefClassifierEdit.getRefCode(this.getDBConnection(), recordIdentifier));
        return answer;
    }

    @RequestMapping(value = "/set-ref-code", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRefCodesDAO> setRefCode(@RequestBody SprRefCodesDAO refCode) throws Exception {
        ResponseEntity<SprRefCodesDAO> answer = okResponse(sprRefClassifierEdit.setRefCode(this.getDBConnection(), refCode));
        return answer;
    }

    @PostMapping(value = "/set-ref-codes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setRefCodes(@RequestBody List<SprRefCodesDAO> refCodes) throws Exception {
        sprRefClassifierEdit.setRefCodes(this.getDBConnection(), refCodes);
        return okResponse(null);
    }

    @RequestMapping(value = "/del-ref-code", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delRefCode(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprRefClassifierEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/del-rft", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delRft(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprRefClassifierEdit.deleteRft(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    // ------------------------------------------------------------------------------------------------------------------------
    // spark cache management (start)
    // ------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/get-spark-cache-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<CacheInfo>> getSparkcacheList() throws Exception {
        ArrayList<CacheInfo> caches = sprCacheManager.getAllUsedCaches(this.getDBConnection());
        return okResponse(caches);
    }

    @RequestMapping(value = "/clear-all-spark-caches", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<CacheInfo>> clearAllSparkCaches() throws Exception {
        sprCacheManager.clearAllCaches(this.getDBConnection());
        ArrayList<CacheInfo> caches = sprCacheManager.getAllUsedCaches(this.getDBConnection());
        return okResponse(caches);
    }

    @RequestMapping(value = "/clear-spark-cache", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<CacheInfo>> clearSparkCache(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprCacheManager.clearCache(this.getDBConnection(), recordIdentifier.getId());
        ArrayList<CacheInfo> caches = sprCacheManager.getAllUsedCaches(this.getDBConnection());
        return okResponse(caches);
    }

    // ------------------------------------------------------------------------------------------------------------------------
    // spark cache management (end)
    // ------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/get-open-sessions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOpenSessions(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprSessionsOpenBrowse.getSessionList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/kill-sessions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> killSessions(@RequestBody List<String> sesList) throws Exception {
        this.sprSessionsOpenBrowse.killSessions(this.getDBConnection(), sesList);
        return okResponse(null);
    }

    @RequestMapping(value = "/kill-all-sessions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> killAllSessions() throws Exception {
        this.sprSessionsOpenBrowse.killAllSessions(this.getDBConnection());
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkRoles(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprRolesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRolesDAO> getSparkRole(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprRolesEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-spark-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprRolesDAO> setSparkRole(@RequestBody SprRolesDAO record) throws Exception {
        return okResponse(sprRolesEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/del-spark-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkRole(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprRolesEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-spark-form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprFormsDAO> getSparkForm(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprFormsEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/get-spark-orgs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrgList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprOrganizationsBrowse.getOrgList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
        		this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-spark-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprOrganizationsDAO> getSparkOrg(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprOrganizationsEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-spark-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprOrganizationsDAO> setSparkOrg(@RequestBody SprOrganizationsDAO record) throws Exception {
        return okResponse(sprOrganizationsEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/del-spark-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkOrg(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprOrganizationsEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-parent-organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getParentOrg(@RequestBody Map<String, String> orgData) throws Exception {
        return okResponse(sprOrganizationsEdit.getParentOrganization(this.getDBConnection(), orgData.get("orgId"), orgData.get("orgName")));
    }

    @RequestMapping(value = "/check-organization-constraints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<S2ViolatedConstraint>> checkOrganizationConstraints(@RequestBody SprOrganizationsDAO record) throws Exception {
        return okResponse(sprOrganizationsEdit.getViolatedConstraints(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/find-closed-sessions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getClosedSessions(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprSessionsClosedBrowse.getSessionList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-forms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrowseData<FormListRec>> getForms(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprFormsBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/set-spark-form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprFormsDAO> setSparkForm(@RequestBody SprFormsDAO record) throws Exception {
        return okResponse(sprFormsEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/del-spark-form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkForm(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprFormsEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-user-roles-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkUserRoles(@RequestBody UserRoleDate record) throws Exception {
        sprUserRolesBrowse.setUserRoleDate(this.getDBConnection(), record);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-spark-usr-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sprSparkUsrRole(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprUserRolesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-org-usr-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sprSparkOrgUsrRole(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprOrgUserRolesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/set-spark-user-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkUserRoles(@RequestBody ArrayList<UserRoleRequest> records) throws Exception {
        sprUserRolesBrowse.updateUserRoles(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-org-user-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrgUserRoleRequest[]> setSparkOrgUserRoles(@RequestBody OrgUserRoleRequest[] records) throws Exception {
        return okResponse(sprOrgUserRolesBrowse.updateOrgUserRoles(this.getDBConnection(), records));
    }

    @RequestMapping(value = "/del-spark-org-user-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkOrgUserRoles(@RequestBody OrgUserRoleRequest[] records) throws Exception {
        sprOrgUserRolesBrowse.delOrgUserRoles(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-role-actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkRoleActions(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprRoleActionsBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-role-action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkRoleAction(@RequestBody Form4RoleRequest params) throws Exception {
        return okResponse(sprRoleActionsBrowse.getRec(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-spark-role-actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRoleActions(@RequestBody String roleId) throws Exception {
        return okResponse(sprRoleActionsBrowse.getRoleActions(this.getDBConnection(), roleId));
    }

    /**
     * Function will set default role form
     * @param record - RoleDefaultFormReq object that contains previous default role form roa_id and new default role form roa_id
     * @throws Exception
     */
    @RequestMapping(value = "/set-default-role-form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setDefaultRoleForm(@RequestBody RoleRequest record) throws Exception {
        sprRoleActionsBrowse.setDefaultRoleForm(this.getDBConnection(), record);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-spark-role-organizations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkRoleOrganizations(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprRoleOrganizationsBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-role-actions-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkRoleActionRole(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprRoleActionsRole.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-roles-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkRolesRole(@RequestBody RoleRolesRecord roleRolesRecord) throws Exception {
        ResponseEntity<String> answer = okResponse(sprRoleActionsRole.getRec(this.getDBConnection(), roleRolesRecord));
        return answer;
    }

    @RequestMapping(value = "/set-spark-role-actions-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkRoleActionsRole(@RequestBody ArrayList<RoleRequest> records) throws Exception {
        sprRoleActionsRole.updateRoles(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-role-actions-role-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkRoleActionRole(@RequestBody RoleRolesDate record) throws Exception {
        sprRoleActionsRole.setRoleRolesDate(this.getDBConnection(), record);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-role-action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveSparkRoleAction(@RequestBody Request4FormActions params) throws Exception {
        this.sprRoleActionsBrowse.saveAction(this.getDBConnection(), params);
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-form-actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkFormActions(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprRoleActionsBrowse.getFormActions(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-spark-role-actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveSparkRoleActions(@RequestBody Request4FormState params) throws Exception {
        this.sprRoleActionsBrowse.saveActions(this.getDBConnection(), params);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-spark-role-users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sprSparkRolUser(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprRoleUsersBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkUsers(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprUsersBrowse.getUsersList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-spark-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprUsersDAO> getSparkUser(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprUsersEdit.get(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/check-spark-user-constraints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<S2ViolatedConstraint>> checkSparkUserConstraints(@RequestBody SprUsersDAO record) throws Exception {
        return okResponse(sprUsersEdit.getViolatedConstraints(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/set-spark-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprUsersDAO> setSparkUser(@RequestBody SprUsersDAO record) throws Exception {
        return okResponse(sprUsersEdit.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/del-spark-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkUser(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprUsersEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-user-orgs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkOrganizations() throws Exception {
        return okResponse(sprUsersEdit.getOrganizations(this.getDBConnection()));
    }

    @RequestMapping(value = "/get-spark-org-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkOrgRoles(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprOrganizationRolesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/set-spark-org-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkOrganizationRoles(@RequestBody ArrayList<OrganizationRoleRequest> records) throws Exception {
        sprOrganizationRolesBrowse.updateOrganizationRoles(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-org-roles-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkOrganizationRolesDate(@RequestBody OrganizationRoleRequest record) throws Exception {
        sprOrganizationRolesBrowse.setOrganizationRoleDate(this.getDBConnection(), record);
        return okResponse(null);
    }

    // Organization users:
    @RequestMapping(value = "/get-spark-org-users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkOrgUsers(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprOrgUsersList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/set-spark-org-users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkOrgUsers(@RequestBody ArrayList<OrgUserRequest> records) throws Exception {
        sprOrgUsersList.updateOrgUsers(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-org-user-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkOrgUsers(@RequestBody OrgUserRequest record) throws Exception {
        sprOrgUsersList.setOrgUserDate(this.getDBConnection(), record);
        return okResponse(null);
    }

    // User organizations:
    @RequestMapping(value = "/get-spark-user-orgs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkUserOrgs(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprUserOrgsList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/set-spark-user-orgs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkUserOrgs(@RequestBody ArrayList<OrgUserRequest> records) throws Exception {
        sprUserOrgsList.updateUserOrgs(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-spark-user-org-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setSparkUserOrgs(@RequestBody OrgUserRequest record) throws Exception {
        sprUserOrgsList.setUserOrgDate(this.getDBConnection(), record);
        return okResponse(null);
    }

    @RequestMapping(value = "/change-spark-user-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeSparkUserStatus(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprUsersBrowse.changeStatus(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/inform-spark-new-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> informSparkNewUser(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprUsersBrowse.informSparkNewUser(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/inform-spark-user-password-reset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> informSparkUserPasswordReset(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprUsersBrowse.informSparkUserPasswordReset(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-menu-structures", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkMenuStructures(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprMenuStructuresBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-spark-menu-structure", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprMenuStructuresDAO> getSparkMenuStructure(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprMenuStructuresEdit.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-spark-menu-structure", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprMenuStructuresDAO> setSparkMenuStructure(@RequestBody SprMenuStructuresDAO record) throws Exception {
        return okResponse(sprMenuStructuresEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/update-spark-menu-structure-tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateSparkMenuStructure(@RequestBody ArrayList<MenuStructureRequest> records) throws Exception {
        sprMenuStructuresTree.updateMenuStructureTree(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/update-spark-unassigned-page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUnassginedPages(@RequestBody ArrayList<Double> records) throws Exception {
        sprMenuStructuresTree.updateUnassginedPages(this.getDBConnection(), records);
        return okResponse(null);
    }

    @RequestMapping(value = "/del-spark-menu-structure", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkMenuStructure(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprMenuStructuresEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/find-spark-menu-structures-tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkMenuStructuresTree(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprMenuStructuresTree.getRecList(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/find-spark-not-assigned-menu-pages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkNotAssignedMenuPages(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprMenuStructuresTree.getNotAssignedMenuPages(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/find-spark-menu-forms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkMenuForms() throws Exception {
        return okResponse(sprMenuStructuresEdit.getForms(this.getDBConnection()));
    }

    // Jobs:
    @RequestMapping(value = "/get-jobs-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJobsList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprJobsBrowse.getJobsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-job-requests-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJobRequests(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprJobRequestsBrowse.getJobRequestsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-job-requests-names-list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJobNames() throws Exception {
        return okResponse(this.sprJobRequestsBrowse.getJobNames(this.getDBConnection()));
    }

    @RequestMapping(value = "/del-job", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delJob(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprJobsBrowse.deleteJob(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-job", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprJobDefinitionsDAO> getJob(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprJobEdit.getJob(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-job", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprJobDefinitionsDAO> setJob(@RequestBody SprJobDefinitionsDAO job) throws Exception {
        return okResponse(sprJobEdit.setJob(this.getDBConnection(), job));
    }

    @RequestMapping(value = "/get-job-available-templates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<SprJobAvailableTemplate>> getJobAvailableTemplatesList() throws Exception {
        return okResponse(this.sprJobEdit.getAvailableTemplatesList(this.getDBConnection()));
    }

    // Templates:
    @RequestMapping(value = "/get-templates-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTemplatesList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprTemplatesBrowse.getTemplatesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/del-template", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delTemplate(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprTemplatesBrowse.deleteTemplate(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-template", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprTemplate> getTemplate(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprTemplateEdit.getTemplate(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-template", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprTemplate> setTemplate(@RequestBody SprTemplate template) throws Exception {
        return okResponse(sprTemplateEdit.setTemplate(this.getDBConnection(), template));
    }

    @RequestMapping(value = "/check-template-constraints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<S2ViolatedConstraint>> checkTemplateConstraints(@RequestBody SprTemplate record) throws Exception {
        return okResponse(sprTemplateEdit.getViolatedConstraints(this.getDBConnection(), record));
    }

    // Profile:
    @RequestMapping(value = "/get-spark-profile-settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprProfile> getSparkProfileSettings() throws Exception {
        Double sessionOrgId = this.requestContext.getUserSession().getSes_org_id();
        Double sessionUsrId = this.requestContext.getUserSession().getSes_usr_id();
        return okResponse(sprProfileSettings.getProfileSettings(this.getDBConnection(), sessionOrgId, sessionUsrId));
    }

    @RequestMapping(value = "/update-spark-profile-settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateSparkProfileSettings(@RequestBody SprProfile profile) throws Exception {
        sprProfileSettings.updateProfileSettings(this.getDBConnection(), profile);
        return okResponse(null);
    }

    @RequestMapping(value = "/link-accounts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void linkAccounts(@RequestBody CreateNewUserRequest data) throws SparkBusinessException, Exception {
        sprProfileSettings.linkAccounts(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_usr_type(), data);
    }

    @RequestMapping(value = "/send-email-confirm-link", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void linkAccounts() throws SparkBusinessException, Exception {
        sprProfileSettings.sendConfirmationLink(getDBConnection(), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/generate-api-key", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprApiKeysDAO> generateApiKey() throws Exception {
        Double sessionUsrId = this.requestContext.getUserSession().getSes_usr_id();
        Double sessionOrgId = this.requestContext.getUserSession().getSes_org_id();
        return okResponse(sprProfileSettings.saveApiKey(this.getDBConnection(), sessionUsrId, sessionOrgId));
    }

    @RequestMapping(value = "/regenerate-api-key", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprApiKeysDAO> regenerateApiKey(@RequestBody Double id) throws Exception {
        Double sessionUsrId = this.requestContext.getUserSession().getSes_usr_id();
        Double sessionOrgId = this.requestContext.getUserSession().getSes_org_id();
        return okResponse(sprProfileSettings.regenerateApiKey(this.getDBConnection(), id, sessionUsrId, sessionOrgId));
    }

    // Audit:
    @RequestMapping(value = "/get-auditable-tables-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAuditableList(@RequestBody SelectRequestParams params) throws Exception {
        ResponseEntity<String> answer = okResponse(
                this.sprAuditableTablesBrowse.getAuditableTablesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
        return answer;
    }

    @RequestMapping(value = "/get-auditable-table-rec-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAuditableRecList(@RequestBody SelectRequestParams params) throws Exception {
        ResponseEntity<String> answer = okResponse(
                this.sprAuditableTablesBrowse.getAuditableTablesRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id()));
        return answer;
    }

    @RequestMapping(value = "/set-spark-auditable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprAuditableTablesDAO> setAuditableTable(@RequestBody SprAuditableTablesDAO params) throws Exception {
        return okResponse(sprAuditableTablesBrowse.save(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-spark-auditable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprAuditableTablesDAO> getAuditableTable(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprAuditableTablesBrowse.get(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-table-audit-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setSparkAuditStatus(@RequestBody SwitchStatus params) throws Exception {
        this.sprAuditableTablesBrowse.setAuditEnabled(this.getDBConnection(), params);
        return okResponse(null);
    }

    // Form filters
    @RequestMapping(value = "/get-form-predefined-filters", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFormFilters(@RequestBody Request4FormsDefinedData formsDefinedData) throws Exception {
        String language = formsDefinedData.getLanguage();
        if (language == null) {
            language = this.requestContext.getUserSession().getSes_language();
        }
        return okResponse(sprFormFilters.getFilters4Form(this.getDBConnection(), formsDefinedData.getFormCode(),
                this.requestContext.getUserSession().getSes_usr_id(), language));
    }

    @RequestMapping(value = "/set-form-predefined-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprFormDataFiltersDAO> setFormFilters(@RequestBody PredefinedFilterRestDataModel predefinedFileterData) throws Exception {
        return okResponse(sprFormFilters.saveFilter4Form(this.getDBConnection(), predefinedFileterData.getExtendedParams(),
                predefinedFileterData.getPredefinedCondition(), predefinedFileterData.getFormCode(), predefinedFileterData.getFilterName(),
                predefinedFileterData.getFilterDescription(), predefinedFileterData.isGlobalFilter(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/del-form-predefined-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delFormFilters(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprFormFilters.delFilter4Form(this.getDBConnection(), Utils.getDouble(recordIdentifier.getId()), this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-profile-report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprFilesDAO> getProfileReport(@RequestBody Request4RefCode domainParams) throws Exception {
        return okResponse(sprPersonEdit.generateProfileReport(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    // Frequently asked questions:
    @RequestMapping(value = "/get-faq-themes-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFaqThemesList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprFaqThemesList.getThemesList(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-faq-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFaqListForAdmin(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprFaqBrowse.getFaqList(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-faq-name", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<IdKeyValuePair> getGroupName(@RequestBody String code) throws Exception {
        return okResponse(sprFaqBrowse.getGroupName(this.getDBConnection(), code));
    }

    @RequestMapping(value = "/set-question-answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setQuestionAnswer(@RequestBody FaqModel formData) throws Exception {
        sprFaqEdit.saveQuestionAnswer(getDBConnection(), formData, this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/get-question-answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FaqModel> getRec(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprFaqEdit.getQuestionAnswer(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/del-question-answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteQuestionAnswer(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprFaqEdit.deleteQuestionAnswer(this.getDBConnection(), recordIdentifier);
    }

    /**
     * Handles POST requests to retrieve a list of APIs.
     *
     * @param params The request parameters for filtering the API list.
     * @return A ResponseEntity containing the list of APIs as a string.
     * @throws Exception If there's an error during the API list retrieval.
     */
    @PostMapping(value = "/get-api-list")
    public ResponseEntity<String> getApiList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprApiKeysBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getLanguage(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
     * Handles POST requests to set an API key info.
     *
     * @param formData The API key data to be saved.
     * @throws Exception If there's an error while saving the API key.
     */
    @PostMapping(value = "/set-api-key")
    public void setApiKey(@RequestBody ApiKey formData) throws Exception {
        sprApiKeyEdit.saveApiKey(getDBConnection(), formData, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
    }

    /**
     * Handles POST requests to retrieve a  API key by ID.
     *
     * @param recordIdentifier The identifier of the API key to retrieve.
     * @return A ResponseEntity containing the API key details.
     * @throws Exception If there's an error during API key retrieval.
     */
    @PostMapping(value = "/get-api-key")
    public ResponseEntity<ApiKey> getApiKey(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprApiKeyEdit.getApiKey(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
     * Handles POST requests to delete an API key.
     *
     * @param recordIdentifier The identifier of the API key to be deleted.
     * @throws Exception If there's an error while deleting the API key.
     */
    @PostMapping(value = "/del-api-key")
    public void deleteApiKey(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprApiKeyEdit.deleteApiKey(getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
    }

    /**
     * Handles POST requests to change sttus for API key.
     *
     * @param recordIdentifier The identifier of the API key.
     * @throws Exception If there's an error while changing the api status.
     */
    @PostMapping(value = "/change-api-key-status")
    public ResponseEntity<String> changeApiKeyStatus(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprApiKeyEdit.changeStatus(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    /**
     * Handles POST requests to get organization list.
     *
     * @param foreignKeyParams Filter parameters
     * @throws Exception If there's an error while geting organization list.
     */
    @PostMapping(value = "/get-org-list")
    public ResponseEntity<List<SprListIdKeyValue>> getOrgList(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(
                sprApiKeyEdit.getOrganizationsList(this.getDBConnection(), foreignKeyParams, this.requestContext.getUserSession().getSes_org_id(), false));
    }

    /**
     * Handles POST requests to get organization user list.
     *
     * @param recordIdentifier Filter parameters
     * @throws Exception If there's an error while geting organization list.
     */
    @PostMapping(value = "/get-org-users-list")
    public ResponseEntity<List<SprListIdKeyValue>> getOrgUserList(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprApiKeyEdit.getOrganizationsUser(this.getDBConnection(), recordIdentifier.getIdAsDouble(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
     * Handles POST requests to get  user list.
     *
     * @param recordIdentifier Filter parameters
     * @throws Exception If there's an error while geting organization list.
     */
    @PostMapping(value = "/get-api-users-list")
    public ResponseEntity<List<SprListIdKeyValue>> getApiUserList(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(sprApiKeyEdit.getUsersList(getDBConnection(), foreignKeyParams, null, this.requestContext.getUserSession().getSes_org_id()));
    }

}