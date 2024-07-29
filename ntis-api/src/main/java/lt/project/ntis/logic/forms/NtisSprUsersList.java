package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUsersBrowse;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.project.ntis.logic.forms.security.NtisSprUsersListSecurityManager;

/**
 * Klasė skirta formos "Naudotojai" biznio logikai apibrėžti
 */
@Component
public class NtisSprUsersList extends SprUsersBrowse {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprUsersDBService sprUsersDbService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    private static final Logger log = LoggerFactory.getLogger(NtisSprUsersList.class);

    /**
     * Function will perform users search id DB and provide result as json string.
     * 
     * @param conn     - connection to the db
     * @param params   - search parameters
     * @param language - language in which should be returned result
     * @return JSON string that represent list of users.
     * @throws Exception
     */
    @Override
    public String getUsersList(Connection conn, SelectRequestParams params, String language, Double userId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUsersBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        String statementSQL = " SELECT U.USR_ID, "//
                + "U.OU_ID, "//
                + "U.NEW_USER, "//
                + "U.PER_CODE, "//
                + "U.PER_DATA_CONFIRMED, "//
                + "U.USR_USERNAME, "//
                + "U.USR_EMAIL, "//
                + "U.USR_PERSON_NAME, "//
                + "U.USR_PERSON_SURNAME, "//
                + "U.ORG_NAME  ORG_NAME, "//
                + "to_char(U.USR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as USR_DATE_FROM, "//
                + "to_char(U.USR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as USR_DATE_TO,"//
                + "USR_TYPE as USR_TYPE_CODE, " //
                + "USR_DISPLAY_TYPE USR_TYPE, "//
                + "to_char(U.USR_LOCK_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') USR_LOCK_DATE, "//
                + "NEW_USER, " //
                + "USER_DISABLED, "//
                + "ORG_LIST " //
                + (isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL) ? "FROM SPR_USERS_VW U  " : " FROM SPR_ORG_USERS_VW U  ") //
                + " where  rft_lang=? ";
        stmt.addSelectParam(language);
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            statementSQL = statementSQL + " and u.org_id = ?::int ";
            stmt.addSelectParam(orgId);
        }
        stmt.setStatement(statementSQL);
        stmt.setWhereExists(true);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);

        stmt.addParam4WherePart("USR_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("usr_id"));
        stmt.addParam4WherePart("RFT_LANG", StatementAndParams.PARAM_STRING, advancedParamList.get("lang"));
        stmt.addParam4WherePart("USR_USERNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_username"));
        stmt.addParam4WherePart("USR_EMAIL", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_email"));
        stmt.addParam4WherePart("USR_PERSON_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_name"));
        stmt.addParam4WherePart("USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_surname"));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("USR_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_type"));
        if (advancedParamList.get("usr_reg_type_status") != null) {
            switch (advancedParamList.get("usr_reg_type_status").getParamValue().getValue()) {
                case "VIISP_ACTIVE": {
                    stmt.addCondition4WherePart(" PER_CODE is not null ", " and ");
                    stmt.addCondition4WherePart(" PER_DATA_CONFIRMED = 'Y' ", " and ");
                    break;
                }
                case "VIISP_INACTIVE": {
                    stmt.addCondition4WherePart(" PER_CODE is not null ", " and ");
                    stmt.addCondition4WherePart(" PER_DATA_CONFIRMED is null ", " and ");
                    break;
                }
                case "PASSWORD_ACTIVE": {
                    stmt.addCondition4WherePart(" USR_USERNAME is not null ", " and ");
                    stmt.addCondition4WherePart(" NEW_USER = 0 ", " and ");
                    break;
                }
                case "PASSWORD_INACTIVE": {
                    stmt.addCondition4WherePart(" USR_USERNAME is not null ", " and ");
                    stmt.addCondition4WherePart(" NEW_USER = 1 ", " and ");
                    break;
                }
            }
        }

        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("USR_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("usr_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("USR_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("usr_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("U.USR_ID", "U.USR_USERNAME", "U.USR_EMAIL", "U.USR_PERSON_NAME", "U.USR_PERSON_SURNAME",
                        "U.ORG_NAME", "to_char(U.USR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "to_char(U.USR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        HashMap<String, String> paramList = params.getParamList();
        stmt.addParam4WherePart("U.USR_TYPE = ? ", paramList.get("p_usr_type"));
        NtisSprUsersListSecurityManager sqm = new NtisSprUsersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = null;
        if (isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            recordActionMenu = new String[] { SprUsersBrowse.ACTION_UPDATE, SprUsersBrowse.ACTION_READ, SprUsersBrowse.ACTION_COPY,
                    SprUsersBrowse.ACTION_DELETE, SprUsersBrowse.ASSIGN_ROLE_ACTION, SprUsersBrowse.ASSIGN_ORG_ACTION, SprUsersBrowse.INFORM_NEW_USER,
                    SprUsersBrowse.RESET_PASSWORD };
        } else {
            recordActionMenu = new String[] { SprUsersBrowse.ACTION_UPDATE, SprUsersBrowse.ACTION_READ, SprUsersBrowse.ACTION_COPY,
                    SprUsersBrowse.ACTION_DELETE, SprUsersBrowse.ASSIGN_ROLE_ACTION, SprUsersBrowse.INFORM_NEW_USER, SprUsersBrowse.RESET_PASSWORD };
        }
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        if (params.getPagingParams().getOrder_clause() != null && params.getPagingParams().getOrder_clause().contains("usr_reg_type_status")) {
            if (params.getPagingParams().getOrder_clause().contains("ASC")) {
                params.getPagingParams().setOrder_clause("usr_username ASC");
            } else {
                params.getPagingParams().setOrder_clause("usr_username DESC");
            }
        }
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public String getUsersWithoutRoles(Connection conn, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUsersBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select usr_id,
                         case when usr.c03 = 'Y' then usr.usr_person_name || ' ' || usr.usr_person_surname || ' (id '|| usr.usr_id || ') ' else
                               usr.usr_person_name || ' ' || usr.usr_person_surname  || ' (id ' || usr.usr_id || ')'
                               end as usr_username,
                               usr.usr_person_name,
                               usr.usr_person_surname,
                               ou.ou_id,
                               org.org_name
                        from spr_users usr
                        inner join spr_org_users ou on ou.ou_usr_id = usr.usr_id and ou.ou_org_id = ?::int and now() between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                        left join spr_organizations org on org.org_id = ou.ou_org_id
                        where now() between usr.usr_date_from and coalesce(usr.usr_date_to, now()) and (usr.usr_lock_date is null or usr.usr_lock_date < current_date)
                        and not exists (select 1
                                    from spr_org_user_roles our
                                    where our.our_ou_id = ou.ou_id
                                          and now() between our.our_date_from and coalesce(our.our_date_to, now()))
                         order by usr.usr_id
                        """);
        stmt.addSelectParam(orgId);
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            return queryController.selectQueryAsJSON(conn, stmt);
        } else {
            return null;
        }
    }

    public Boolean checkIfOrgUserRolesAreAssigned(Connection conn, Double orgId) throws Exception {
        if (this.isFormActionAssigned(conn, SprUsersBrowse.ACTION_READ)) {
            StatementAndParams stmt = new StatementAndParams(
                    """
                            select usr_id
                            from spr_organizations org
                            inner join spr_org_users ou on ou.ou_org_id = org.org_id and ou.ou_org_id = ?::int and now() between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                            inner join spr_users usr on ou.ou_usr_id = usr.usr_id
                            where now() between usr.usr_date_from and coalesce(usr.usr_date_to, now()) and (usr.usr_lock_date is null or usr.usr_lock_date < current_date)
                            and not exists (select 1
                                        from spr_org_user_roles our
                                        where our.our_ou_id = ou.ou_id
                                              and now() between our.our_date_from and coalesce(our.our_date_to, now()))
                            and org.c01 in ('PASLAUG_VANDEN', 'PASLAUG', 'VANDEN', 'INST', 'INST_LT')
                            """);
            stmt.addSelectParam(orgId);
            List<SprUsersDAO> usersList = queryController.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
            if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
                return usersList.isEmpty() || usersList == null || usersList.size() == 0;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            SprOrgUsersDAO sprOrgUserDAO = sprOrgUsersDBService.loadRecordByParams(conn, "ou_usr_id = ?::int and ou_org_id = ?::int",
                    new SelectParamValue(recordIdentifier.getIdAsDouble()), new SelectParamValue(orgId));
            sprOrgUsersDBService.deleteRecord(conn, sprOrgUserDAO);
        }
    }

    public SprUsersDAO save(Connection conn, SprUsersDAO record, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        SprUsersDAO userDAO = null;
        boolean addEmailNotifications = false;
        if (record.getRecordIdentifier() == null) {
            if (record.getUsr_date_from() == null) {
                record.setUsr_date_from(new Date());
            }
            if (record.getUsr_email_confirmed() == null) {
                record.setUsr_email_confirmed("N");
            }
        } else {
            record = sprUsersDbService.restoreSystemData(conn, record);
        }
        if (record.getUsr_id() != null) {
            userDAO = sprUsersDbService.loadRecord(conn, record.getUsr_id());
        }
        if (record.getUsr_language() == null || record.getUsr_language().trim() == "") {
            record.setUsr_language(Languages.LT.getCode());
        }
        if (record.getUsr_id() == null) {
            addEmailNotifications = true;
        }
        sprUsersDbService.saveRecord(conn, record);

        if (addEmailNotifications == true && record.getUsr_per_id() != null) {
            SprPersonsDAO personDAO = sprPersonsDBService.loadRecord(conn, record.getUsr_per_id());
            personDAO.setC01(YesNo.Y.getCode());
            personDAO.setPerformSyncWithUser(false);
            sprPersonsDBService.saveRecord(conn, personDAO);
        }
        boolean manageOrganizations = false;
        SprOrgUsersDAO sprOrgUsersDAO = null;
        if (this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            if (record.getUsr_org_id() != null) {
                sprOrgUsersDAO = sprOrgUsersDBService.loadRecordByParams(conn, "  ou_usr_id = ?::int and ou_org_id = ?::int",
                        new SelectParamValue(record.getUsr_id()), new SelectParamValue(record.getUsr_org_id()));
                manageOrganizations = true;
            }
        } else {
            sprOrgUsersDAO = sprOrgUsersDBService.loadRecordByParams(conn, "  ou_usr_id = ?::int and ou_org_id = ?::int",
                    new SelectParamValue(record.getUsr_id()), new SelectParamValue(orgId));
            manageOrganizations = true;
        }
        if (manageOrganizations && sprOrgUsersDAO == null) {
            sprOrgUsersDAO = sprOrgUsersDBService.newRecord();
            sprOrgUsersDAO.setOu_usr_id(record.getUsr_id());
            if (this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
                sprOrgUsersDAO.setOu_org_id(record.getUsr_org_id());
            } else {
                sprOrgUsersDAO.setOu_org_id(orgId);
            }
            sprOrgUsersDAO.setOu_date_from(record.getUsr_date_from());
            sprOrgUsersDAO.setOu_date_to(record.getUsr_date_to());
            sprOrgUsersDBService.saveRecord(conn, sprOrgUsersDAO);
        } else if (manageOrganizations && sprOrgUsersDAO != null) {
            sprOrgUsersDAO.setOu_date_from(record.getUsr_date_from());
            sprOrgUsersDAO.setOu_date_to(record.getUsr_date_to());
            sprOrgUsersDBService.saveRecord(conn, sprOrgUsersDAO);
        }
        if (userDAO != null && !this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            record.setUsr_date_from(userDAO.getUsr_date_from());
            record.setUsr_date_to(userDAO.getUsr_date_to());
            record.setUsr_lock_date(userDAO.getUsr_lock_date());
            sprUsersDbService.saveRecord(conn, record);
        }
        return record;
    }

    public SprUsersDAO get(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws NumberFormatException, Exception {
        SprUsersDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
            answerObj = sprUsersDbService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
            if (this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
                answerObj = sprUsersDbService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            } else {
                SprOrgUsersDAO sprOrgUsersDAO = sprOrgUsersDBService.loadRecordByParams(conn, "  ou_usr_id = ?::int and ou_org_id = ?::int",
                        new SelectParamValue(Double.parseDouble(recordIdentifier.getId())), new SelectParamValue(orgId));
                if (sprOrgUsersDAO != null) {
                    answerObj = sprUsersDbService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
                    answerObj.setUsr_date_from(sprOrgUsersDAO.getOu_date_from());
                    answerObj.setUsr_date_to(sprOrgUsersDAO.getOu_date_to() != null ? sprOrgUsersDAO.getOu_date_to() : answerObj.getUsr_date_to());
                }
            }
            answerObj.setFormActions(getFormActions(conn));
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setUsr_id(null);
                answerObj.setRec_create_timestamp(null);
                answerObj.setRec_timestamp(null);
                answerObj.setRec_userid(null);
                answerObj.setRec_version(null);
            }
            answerObj.hideSystemData();
        }
        return answerObj;
    }
}