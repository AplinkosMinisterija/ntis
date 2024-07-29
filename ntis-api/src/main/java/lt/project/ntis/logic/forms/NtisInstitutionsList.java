package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.security.NtisInstitutionsListBrowseSecurityManager;

/**
 * Klasė skirta formos "Institucijų sąrašas"  biznio logikai apibrėžti
 */

@Component
public class NtisInstitutionsList extends FormBase {

    public static String INVITE_PERSON = "INVITE_PERSON";

    public static String INVITE_PERSON_DESC = "Person invtitation right";

    public static String REMOVE_ADMIN = "REMOVE_ADMIN";

    public static String REMOVE_ADMIN_DESC = "Admin removal right";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprUsersDBService usersDbService;

    @Autowired
    SprOrgUsersDBService orgUsersDbService;

    @Autowired
    SprOrganizationsDBService organizationsDbService;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "NTIS_INSTITUTIONS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Institucijų sąrašas", "Institucijų sąrašo forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE);
        this.addFormAction(INVITE_PERSON, INVITE_PERSON_DESC, INVITE_PERSON);
    }

    public String getInstitutionsList(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisInstitutionsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                    org.org_id,
                    org.org_name,
                    org.org_code,
                    per.per_email,
                    coalesce(rfc_type.rfc_meaning, org.c01) as org_type,
                    usr.usr_id,
                    coalesce(rfc_muni.rfc_meaning, org.n02::text) as municipality
                from spr_organizations org
                left join spr_org_users ou on ou.ou_org_id = org.org_id and org.n05 = ou.ou_usr_id
                left join spr_users usr on ou.ou_usr_id = usr.usr_id
                left join spr_persons per on per.per_id = usr.usr_per_id
                left join spr_ref_codes_vw rfc_type on rfc_type.rfc_code = org.c01
                                                    and rfc_type.rfc_domain = 'NTIS_ORG_TYPE'
                                                    and rfc_type.rft_lang = ?
                left join spr_ref_codes_vw rfc_muni on rfc_muni.rfc_code::numeric = org.n02
                                                    and rfc_muni.rfc_domain = 'NTIS_MUNICIPALITIES'
                                                    and rfc_muni.rft_lang = ?
                where org.c01 in (?, ?)
                """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(NtisOrgType.INST.getCode());
        stmt.addSelectParam(NtisOrgType.INST_LT.getCode());
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), orgId, stmt, advancedParamList);

        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("org_code", StatementAndParams.PARAM_STRING, advancedParamList.get("org_code"));
        stmt.addParam4WherePart("org.c01", StatementAndParams.PARAM_STRING, advancedParamList.get("org_type"));
        stmt.addParam4WherePart("org.n02", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("municipality"));

        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("org_name", "org_code", "rfc_type.rfc_meaning", "rfc_muni.rfc_meaning"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by org_date_from desc, org_id desc");
        NtisInstitutionsListBrowseSecurityManager sqm = new NtisInstitutionsListBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisInstitutionsList.ACTION_READ, NtisInstitutionsList.ACTION_UPDATE, NtisInstitutionsList.ACTION_CREATE,
                NtisInstitutionsList.INVITE_PERSON, NtisInstitutionsList.REMOVE_ADMIN };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void inviteNewInstitutionAdmin(Connection conn, String id, String langCode) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, INVITE_PERSON);
        Double userId = Utils.getDouble(id);
        SprUsersDAO sprUsersDAO = usersDbService.loadRecord(conn, userId);
        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("userName", sprUsersDAO.getUsr_username());
        if (sprUsersDAO.getUsr_email() != null) {
            sprProcessRequest.createNewUserInformRequest(conn, sprUsersDAO.getUsr_id(), sprUsersDAO.getUsr_email(), Languages.getLanguageByCode(langCode),
                    context);
        } else {
            throw new SparkBusinessException(new S2Message("pages.sprUsersBrowse.emaiIsEmpty", SparkMessageType.ERROR, "User does not have an email"));
        }
    }

    public void removeInstAdmin(Connection conn, String userId, String organizationId) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, REMOVE_ADMIN);
        Double usrId = Utils.getDouble(userId);
        Double orgId = Utils.getDouble(organizationId);
        SprOrgUsersDAO orgUserDAO = orgUsersDbService.loadRecordByParams(conn, "ou_org_id = ?::int and ou_usr_id = ?::int ", new SelectParamValue(orgId),
                new SelectParamValue(usrId));
        if (orgUserDAO != null && orgUserDAO.getOu_id() != null) {
            StatementAndParams orgUserRolesStmt = new StatementAndParams("""
                    update spr_org_user_roles
                    set our_date_to = (current_date - INTERVAL '1 day')
                    where our_ou_id = ?::int
                    """);
            orgUserRolesStmt.addSelectParam(orgUserDAO.getOu_id());
            queryController.adjustRecordsInDB(conn, orgUserRolesStmt);

            StatementAndParams orgUserStmt = new StatementAndParams("""
                    update spr_org_users
                    set ou_date_to = (current_date - INTERVAL '1 day')
                    where ou_org_id = ?::int
                        and ou_usr_id = ?::int
                        and ou_id = ?::int
                    """);
            orgUserStmt.addSelectParam(orgId);
            orgUserStmt.addSelectParam(usrId);
            orgUserStmt.addSelectParam(orgUserDAO.getOu_id());
            queryController.adjustRecordsInDB(conn, orgUserStmt);
        }

        SprOrganizationsNtisDAO organizationDAO = (SprOrganizationsNtisDAO) organizationsDbService.loadRecord(conn, orgId);
        if (organizationDAO != null && organizationDAO.getOrg_id() != null) {
            organizationDAO.setAdminUsrId(null);
            organizationsDbService.saveRecord(conn, organizationDAO);
        }
    }
}
