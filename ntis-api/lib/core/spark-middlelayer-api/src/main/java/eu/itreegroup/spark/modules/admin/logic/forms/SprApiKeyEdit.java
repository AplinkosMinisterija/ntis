package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.util.JwtUtil;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprApiKeysDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.ApiKey;
import eu.itreegroup.spark.modules.admin.service.SprApiKeysDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@Component
public class SprApiKeyEdit extends FormBase {

    public static final String NO_LIMIT_ON_ORG_LEVEL = "NO_LIMIT_ON_ORG_LEVEL";

    public static final String NO_LIMIT_ON_ORG_LEVEL_NAME = "No limitation on user organization level";

    @Autowired
    SprApiKeysDBService sprApiKeysDBService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_API_KEY_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark API key edit", "Manage user API key");
        addFormActionCRUD();
        addFormAction(NO_LIMIT_ON_ORG_LEVEL, NO_LIMIT_ON_ORG_LEVEL_NAME, NO_LIMIT_ON_ORG_LEVEL_NAME);

    }

    /**
     * Checks privileges based on API and user identifiers.
     *
     * @param conn      The database connection.
     * @param apiId     The identifier of the API.
     * @param sesUsrId  The session user identifier.
     * @param apiUsrId  The API user identifier.
     * @param apiOrgId  The API organization identifier.
     * @param sesOrgId  The session organization identifier.
     */
    private void checkApiPrivilages(Connection conn, Double sesUsrId, Double apiUsrId, Double apiOrgId, Double sesOrgId) throws SparkBusinessException {
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {

            if ((sesOrgId == null && apiUsrId == null) || (sesOrgId != null && !sesOrgId.equals(apiOrgId)))
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized perform this action"));
        }

    };

    /**
     * Saves an API key to the database.
     *
     * @param conn      The database connection to use for the operation.
     * @param formData  An object containing the API key and its associated data to be saved.
     * @param usrId     The session user identifier.
     * @param orgId     The session organization identifier.
     * @throws Exception If an error occurs during the database operation.
     */
    public void saveApiKey(Connection conn, ApiKey formData, Double usrId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, formData.getApi_id() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);

        SprApiKeysDAO apiKeysDAO = null;
        SprOrgUsersDAO orgUsr = sprOrgUsersDBService.newRecord();
        if (formData.getApi_id() == null) {
            apiKeysDAO = sprApiKeysDBService.newRecord();
            SprUsersDAO user;

            // Need create API user, org user records
            if (formData.getApi_org_id() != null) {
                user = sprUsersDBService.loadRecord(conn, formData.getApi_usr_id()); 
                if(formData.getApi_ou_id() != null) {
                	orgUsr = sprOrgUsersDBService.loadRecord(conn, formData.getApi_ou_id());  
                } else {
                	orgUsr = sprOrgUsersDBService.loadRecordByParams(conn, "ou_usr_id = ? and ou_org_id = ? ", new SelectParamValue(user.getUsr_id()), new SelectParamValue(formData.getApi_org_id()));
                }                            
                apiKeysDAO.setApi_type_code("ORG");
                apiKeysDAO.setApi_ou_id(orgUsr.getOu_id());
            } else {
                user = sprUsersDBService.loadRecord(conn, formData.getApi_usr_id());
                apiKeysDAO.setApi_type_code("USR");
                apiKeysDAO.setApi_usr_id(user.getUsr_id());
            }
            checkApiPrivilages(conn, usrId, apiKeysDAO.getApi_usr_id(), orgUsr.getOu_org_id(), orgId);
            apiKeysDAO.setApi_date_from(formData.getApi_date_from());
            apiKeysDAO.setApi_date_to(formData.getApi_date_to());
            sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
            apiKeysDAO.setApi_key(generateApiJwtsToken(user.getUsr_username(), apiKeysDAO.getApi_id()));
            sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
        } else {
            apiKeysDAO = sprApiKeysDBService.loadRecord(conn, formData.getApi_id());
            if (apiKeysDAO.getApi_ou_id() != null) {
                orgUsr = sprOrgUsersDBService.loadRecord(conn, apiKeysDAO.getApi_ou_id());

            }
            checkApiPrivilages(conn, usrId, apiKeysDAO.getApi_usr_id(), orgUsr.getOu_org_id(), orgId);
            apiKeysDAO.setApi_date_from(formData.getApi_date_from());
            apiKeysDAO.setApi_date_to(formData.getApi_date_to());
            sprApiKeysDBService.saveRecord(conn, apiKeysDAO).getApi_id();
        }

    }

    /**
     * Retrieves an API key from the database based on a given record identifier.
     *
     * @param conn             The database connection to use for the retrieval.
     * @param recordIdentifier An object containing information to identify the API key.
     * @return An instance of the ApiKeys class representing the retrieved API key, or null if not found.
     * @throws Exception If an error occurs during the database retrieval.
     */
    public ApiKey getApiKey(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        SprApiKeysDAO loadedData = null;
        ApiKey returnData = new ApiKey();
        if (recordIdentifier.getIdAsDouble() != null) {
            loadedData = sprApiKeysDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
            returnData.setApi_id(loadedData.getApi_id());
            returnData.setApi_ou_id(loadedData.getApi_ou_id());
            if (loadedData.getApi_ou_id() != null) {
                returnData.setApi_org_id(sprOrgUsersDBService.loadRecord(conn, loadedData.getApi_ou_id()).getOu_org_id());
            }
            returnData.setApi_usr_id(loadedData.getApi_usr_id());
            returnData.setApi_date_from(loadedData.getApi_date_from());
            returnData.setApi_date_to(loadedData.getApi_date_to());
        } else {
            if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
                returnData.setApi_org_id(orgId);
            }
        }
        if (returnData.getApi_org_id() != null) {
            returnData.setOrgInfo(getOrganizationsList(conn, null, returnData.getApi_org_id(), true));
        }
        if (returnData.getApi_usr_id() != null) {
            returnData.setUserInfo(getUsersList(conn, null, loadedData.getApi_usr_id(), orgId));
        }

        return returnData;
    }

    /**
     * Deletes an API key record from the database.
     *
     * @param conn             The database connection.
     * @param recordIdentifier The identifier of the API key record to delete.
     * @param usrId     The session user identifier.
     * @param orgId     The session organization identifier.
     * @throws NumberFormatException If the record identifier cannot be parsed as a number.
     * @throws Exception             If an error occurs while deleting the API key.
     */
    public void deleteApiKey(Connection conn, RecordIdentifier recordIdentifier, Double usrId, Double orgId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        SprApiKeysDAO loadedData = sprApiKeysDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
        SprOrgUsersDAO orgUsr = sprOrgUsersDBService.newRecord();
        if (loadedData.getApi_ou_id() != null) {
            orgUsr = sprOrgUsersDBService.loadRecord(conn, loadedData.getApi_ou_id());
        }
        checkApiPrivilages(conn, usrId, loadedData.getApi_usr_id(), orgUsr.getOu_org_id(), orgId);

        sprApiKeysDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
    }

    /**
     * Generates a JWT (JSON Web Token) for API authentication and authorization.
     *
     * @param username The username of the user associated with the token.
     * @param apiId The API's unique identifier.
     * @return A JWT token string containing user and API information.
     */
    public String generateApiJwtsToken(String username, Double apiId) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("apiId", apiId);
        return jwtUtil.generateJwtTokenByClaimsMap(username, claimsMap);
    }

    /**
     * Changes the status of a record identified by the provided {@code recordIdentifier}.
     *
     * @param conn              The database connection to use for the operation.
     * @param usrId     The session user identifier.
     * @param orgId     The session organization identifier.
     * @param recordIdentifier  The identifier of the record whose status should be changed.
     * @throws Exception        If an error occurs while changing the status of the record.
     */
    public void changeStatus(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        Double apiId = Double.valueOf(recordIdentifier.getId());
        SprApiKeysDAO apiKeysDAO = sprApiKeysDBService.loadRecord(conn, apiId);
        if (apiKeysDAO.getApi_date_to() == null) {
            apiKeysDAO.setApi_date_to(new Date());
        } else {
            apiKeysDAO.setApi_date_to(null);
        }
        sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
    }

    /**
     * Retrieves a list of organizations based on privilages and filter parameter parameters.
     *
     * @param conn              The database connection to use for the query.
     * @param foreignKeyParam   The parameters specifying the foreign key constraints.
     * @return                  A list of {@code SprListIdKeyValue} objects representing organizations.
     * @throws Exception        If an error occurs while retrieving the organizations.
     */
    public List<SprListIdKeyValue> getOrganizationsList(Connection conn, ForeignKeyParams foreignKeyParam, Double orgId, boolean editMode) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        SELECT org.org_id as id,
                               org.org_id as key,
                               org.org_name as  display
                         FROM (select org_id, org_name
                                 from spr_organizations
                                where
                                 """
                        + (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL) || editMode ? " org_id = ? "
                                : " spr_translate_latin(org_name || org_code, 'Y')  like '%'||?||'%' ")
                        + dbStatementManager.getRecordLimitString(100.0) + ") org");
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL) || editMode) {
            stmt.addSelectParam(orgId);
        } else {
            if (foreignKeyParam != null && foreignKeyParam.getFilterValue() != null) {
                stmt.addSelectParam(Utils.replaceNationalCharacters(foreignKeyParam.getFilterValue().toLowerCase().trim()));
            }
        }
        return new ArrayList<>(queryController.selectQueryAsObjectArrayList(conn, stmt, SprListIdKeyValue.class));
    }

    /**
     * Retrieves a list of organizations for a user.
     *
     * @param conn     The database connection.
     * @param orgId    The ID of the organization.
     * @param sesOrgId The session organization ID.
     * @return A list of organizations as key-value pairs.
     * @throws Exception If an error occurs while retrieving the organizations.
     */
    public List<SprListIdKeyValue> getOrganizationsUser(Connection conn, Double orgId, Double sesOrgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                 SELECT  ou_id as id,
                         ou_id as  key,
                         usr_username || COALESCE(case when per_id is not null then ' ( ' || per_name|| ' '||per_surname||' )' 
                         							   else ' ( ' || usr_person_name||' '||usr_person_surname||' )' end, ' ')  as  display
                    FROM spr_users usr
                    join spr_persons per on usr_per_id = per_Id
                    JOIN spr_org_users  on usr_id = ou_usr_id
                where ou_org_id = ?
                    """);

        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            stmt.addSelectParam(sesOrgId);
        } else {
            stmt.addSelectParam(orgId);
        }

        return new ArrayList<>(queryController.selectQueryAsObjectArrayList(conn, stmt, SprListIdKeyValue.class));
    }

    /**
     * Retrieves a list of users  based on the provided filter parameters..
     *
     * @param conn              The database connection to use for the query.
     * @param foreignKeyParam   The filter parameters.
     * @return                  A list of {@code SprListIdKeyValue} objects representing organizations.
     * @throws Exception        If an error occurs while retrieving the organizations.
     */
    public List<SprListIdKeyValue> getUsersList(Connection conn, ForeignKeyParams foreignKeyParam, Double usrId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        if (isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            if (usrId == null) {
                StatementAndParams stmt = new StatementAndParams("""
                        SELECT USR_ID AS ID,
                               USR_ID AS KEY,
                               USR_USERNAME || COALESCE(CASE 
                                                   WHEN PER_ID IS NOT NULL THEN ' ( ' || PER_NAME || ' ' || PER_SURNAME || ' )'
                                                       ELSE ' ( ' || USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME || ' ) ' END, ' ') AS DISPLAY
                           FROM
                               (SELECT USR_ID, USR_USERNAME, USR_PERSON_NAME, USR_PERSON_SURNAME, PER_ID, PER_NAME, PER_SURNAME
                                   FROM SPR_USERS USR
                                   JOIN SPR_PERSONS PER ON USR_PER_ID = PER_ID
                                   WHERE SPR_TRANSLATE_LATIN(USR_USERNAME || COALESCE(CASE  
                                                   WHEN PER_ID IS NOT NULL THEN ' ( ' || PER_NAME || ' ' || PER_SURNAME || ' )'
                                                       ELSE ' ( ' || USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME || ' ) ' END, ' '), 'Y') like '%' || ? || '%'
                                   """ + dbStatementManager.getRecordLimitString(100.0) + """
                        ) U
                         WHERE NOT EXISTS
                                  (SELECT 1
                                      FROM SPR_API_KEYS
                                      WHERE API_USR_ID = USR_ID )
                                       """);
                stmt.addSelectParam(Utils.replaceNationalCharacters(foreignKeyParam.getFilterValue().toLowerCase().trim()));
                return new ArrayList<>(queryController.selectQueryAsObjectArrayList(conn, stmt, SprListIdKeyValue.class));
            } else {
                StatementAndParams stmt = new StatementAndParams("""
                        SELECT USR_ID AS ID,
                               USR_ID AS KEY,
                               USR_USERNAME || COALESCE(CASE
                                                   WHEN PER_ID IS NOT NULL THEN ' ( ' || PER_NAME || ' ' || PER_SURNAME || ' )'
                                                       ELSE ' ( ' || USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME || ' ) ' END, ' ') AS DISPLAY
                         FROM SPR_USERS USR
                          JOIN SPR_PERSONS PER ON USR_PER_ID = PER_ID
                            WHERE USR_ID = ?
                                        """);
                stmt.addSelectParam(usrId);
                return new ArrayList<>(queryController.selectQueryAsObjectArrayList(conn, stmt, SprListIdKeyValue.class));
            }

        } else {
        	StatementAndParams stmt = new StatementAndParams("""
                    SELECT USR_ID AS ID,
                           USR_ID AS KEY,
                           USR_USERNAME || COALESCE(CASE
                                               WHEN PER_ID IS NOT NULL THEN ' ( ' || PER_NAME || ' ' || PER_SURNAME || ' )'
                                                   ELSE ' ( ' || USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME || ' ) ' END, ' ') AS DISPLAY
                     FROM SPR_USERS USR
                    LEFT JOIN SPR_PERSONS PER ON USR_PER_ID = PER_ID
                    INNER JOIN SPR_ORG_USERS OU ON OU.OU_USR_ID = USR.USR_ID
	                WHERE OU_ORG_ID = ? 
	                AND SPR_TRANSLATE_LATIN(USR_USERNAME || COALESCE(CASE  
                                                   WHEN PER_ID IS NOT NULL THEN ' ( ' || PER_NAME || ' ' || PER_SURNAME || ' )'
                                                       ELSE ' ( ' || USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME || ' ) ' END, ' '), 'Y') like '%' || ? || '%'
                                    """);
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(Utils.replaceNationalCharacters(foreignKeyParam.getFilterValue().toLowerCase().trim()));
            stmt.setWhereExists(true);
            stmt.addParam4WherePart(" usr.usr_id = ? ", usrId);
            return new ArrayList<>(queryController.selectQueryAsObjectArrayList(conn, stmt, SprListIdKeyValue.class));
        }
    }
}
