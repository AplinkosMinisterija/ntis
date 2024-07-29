package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprApiKeysBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprPropertiesBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;

@Component
public class NtisSprApiKeysBrowse extends SprApiKeysBrowse {
    
    private static final Logger log = LoggerFactory.getLogger(NtisSprOrgUserRolesBrowse.class);
    
    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;
    
    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;
    
    @Override
    public String getRecList(Connection conn, SelectRequestParams params, String language, Double usrId, Double orgId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprApiKeysBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                WITH formatted_data AS (
                          SELECT
                              api_id,
                              JSON_BUILD_OBJECT(
                                  'api_id',  api.api_id::text,
                                  'api_key', case when ou2.ou_org_id = ? or api.api_usr_id = ? then api.api_key else '-' end::text,
                                  'api_date_from', api.api_date_from::date,
                                  'api_date_to', api.api_date_to::date,
                                  'last_api_date', api.d01::date
                              ) AS api_list
                          FROM spr_api_keys api
                           left join spr_org_users ou2 on api_ou_id = ou2.ou_id )
                      SELECT max(api.api_id) api_id,
                             coalesce(usr.usr_username, ousr.usr_username) usr_username,
                             coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name ) per_name,
                             org_name,
                             max(api.d01)::date last_date,
                             JSON_AGG(fd.api_list) AS api_list
                      FROM formatted_data fd
                      left join spr_api_keys api on fd.api_id = api.api_id
                      left join spr_users usr on api_usr_id = usr.usr_id
                      left join spr_persons per on usr.usr_per_id = per.per_id
                      left join spr_org_users on api_ou_id = ou_id
                      left join spr_users ousr on ou_usr_id = ousr.usr_id
                      left join spr_persons oper on ousr.usr_per_id = oper.per_id
                      left join spr_organizations on ou_org_id = org_id 
                """);

        stmt.setStatementGroupPart("""
                api.api_usr_id,
                api.api_ou_id,
                coalesce(usr.usr_username, ousr.usr_username),
                coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name ),
                org_name
                """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            stmt.addParam4WherePart(" org_id = ? ", orgId);
        }
        stmt.addParam4WherePart("coalesce(api.api_id)", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("api_id"));
        stmt.addParam4WherePart("coalesce(usr.usr_username, ousr.usr_username)", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_username"));
        stmt.addParam4WherePart("coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name )", StatementAndParams.PARAM_STRING,
                advancedParamList.get("per_name"));
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("coalesce(api.api_usr_id, api.api_ou_id) ", "coalesce(usr.usr_username, ousr.usr_username) ",
                        "coalesce(per.per_surname,oper.per_surname) || ' ' || coalesce(per.per_name,oper.per_name )", "org_name "),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        SprPropertiesBrowseSecurityManager sqm = new SprPropertiesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprApiKeysBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
    
    public Double getApiUsrId(Connection conn, Double ouId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        Double usrId = null;
        SprOrgUsersDAO orgUserDAO = this.sprOrgUsersDBService.loadRecordByParams(conn, "ou_id = ?::int and ou_org_id = ?::int ", new SelectParamValue(ouId), new SelectParamValue(orgId));
        if(orgUserDAO != null) {
            usrId = orgUserDAO.getOu_usr_id();
        }
        return usrId;
        
    }

}
