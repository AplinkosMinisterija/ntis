package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgAvailableRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUserRolesDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.project.ntis.constants.NtisOrgStateConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.logic.forms.processRequests.SystemProcessRequest;
import lt.project.ntis.logic.forms.security.NtisServiceProvidersListSecurityManager;
import lt.project.ntis.models.NtisServiceProviderRejectionModel;
import lt.project.ntis.service.NtisOrdersDBService;

/**
 * Klasė skirta formos "Paslaugų teikėjų sąrašas" (formos kodas А1070)  biznio logikai apibrėžti
 */

@Component
public class NtisServiceProvidersList extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprOrganizationsDBService organizationsDbService;

    @Autowired
    SprOrgAvailableRolesDBService orgAvailableRolesDbService;

    @Autowired
    SprOrgUserRolesDBService orgUserRolesDbService;

    @Autowired
    SprOrgUsersDBService orgUsersDbService;

    @Autowired
    SprUserRolesDBService userRolesDbService;

    @Autowired
    SprRolesDBService rolesDbService;

    @Autowired
    NtisOrdersDBService ordersDbService;

    @Autowired
    SystemProcessRequest systemProcessRequest;

    @Autowired
    NtisNotificationsManager ntisNotifications;

    public static String DISABLE_ORGANIZATION = "DISABLE_ORGANIZATION";

    public static String DISABLE_ORGANIZATION_DESC = "Disable organization";

    public static String IMPORT_ORDERS = "IMPORT_ORDERS";

    public static String IMPORT_ORDERS_DESC = "Import orders";

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_PROVIDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų teikėjų sąrašas", "Paslaugų teikėjų sąrašo forma");
        addFormActions(FormBase.ACTION_CREATE);
        addFormActions(FormBase.ACTION_READ);
        this.addFormAction(DISABLE_ORGANIZATION, DISABLE_ORGANIZATION_DESC, DISABLE_ORGANIZATION);
    }

    /**
     * Metodas grąžins sistemoje registruotų ir išregistruotų paslaugų teikėjų bei vandentvarkos įmonių sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - sesijos organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public String getServiceProviders(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceProvidersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                            org.org_id,
                            coalesce(rfc.rfc_meaning, org.c01) as org_type,
                            org.org_name,
                            org.org_code,
                            org.org_email,
                            org.org_phone,
                            org.org_address,
                            org.org_delegation_person,
                                case
                                    when org.n01 = 1 then 'SRV_PRVD_REGISTERED'
                                    when org.n01 = 2 then 'SRV_PRVD_DEREGISTERED'
                                end as org_state_clsf,
                            coalesce(st.rfc_meaning, st.rfc_code) as org_state,
                            to_char(org.d01, '%s') as org_registered_date,
                            to_char(org.d02, '%s') as org_deregistered_date,
                                case
                                    when org.d03 is null and org.c01 in ('PASLAUG', 'PASLAUG_VANDEN') then 'N'
                                    when org.d03 is not null and org.d03 < now() and org.c01 in ('PASLAUG', 'PASLAUG_VANDEN') then 'Y' else null
                                end provides_installation,
                            string_agg(coalesce(rfs.rfc_meaning, sri.sri_service_type), ', ') as services_provided,
                            org.c04 as org_rejection_reason
                            from spr_organizations org
                            left join ntis_service_requests sr on sr.sr_org_id = org.org_id
                            left join ntis_service_req_items sri on sr.sr_id = sri.sri_sr_id and now() between sri.sri_registration_date
                                            and coalesce(sri.sri_removal_date, now()) and sr.sr_status in ('CONFIRMED','SUBMITTED')
                            left join ntis_services srv on sri.sri_srv_id = srv.srv_id and now() between srv.srv_date_from and coalesce(srv.srv_date_to, now())
                            left join spr_ref_codes_vw rfs on rfs.rfc_code = sri_service_type and rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rfs.rft_lang = ?
                            inner join spr_ref_codes_vw rfc on rfc.rfc_code = org.c01 and rfc.rfc_domain = 'NTIS_ORG_TYPE' and rfc.rfc_code in ('VANDEN', 'PASLAUG_VANDEN', 'PASLAUG') and rfc.rft_lang = ?
                            inner join spr_ref_codes_vw st on
                                        case
                                            when org.n01 = 1 then st.rfc_code = 'SRV_PRVD_REGISTERED'
                                            when org.n01 = 2 then st.rfc_code ='SRV_PRVD_DEREGISTERED'
                                        end and st.rft_lang = ?
                        """
                        .formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));

        stmt.setStatementOrderPart(" order by org.n01 asc, org.d01 desc, org.org_id desc ");
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.setStatementGroupPart("""
                org.org_id,
                                     rfc.rfc_meaning,
                                     org.c01,
                                     st.rfc_code,
                                     org.d01,
                                     org.d02,
                                     org.org_name,
                                     org.org_code,
                                     org.org_email,
                                     org.org_phone,
                                     org.org_address,
                                     org.org_delegation_person,
                                     org.c04,
                                     st.rfc_meaning
                """);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), orgId, stmt, advancedParamList);
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("org.c01", StatementAndParams.PARAM_STRING, advancedParamList.get("org_type"));
        stmt.addParam4WherePart("org_code", StatementAndParams.PARAM_STRING, advancedParamList.get("org_code"));
        stmt.addParam4WherePart("org_address || ' ' || org_house_number", StatementAndParams.PARAM_STRING, advancedParamList.get("org_address"));
        stmt.addParam4WherePart("org_email", StatementAndParams.PARAM_STRING, advancedParamList.get("org_email"));
        stmt.addParam4WherePart("org_phone", StatementAndParams.PARAM_STRING, advancedParamList.get("org_phone"));
        stmt.addParam4WherePart("org.c01", StatementAndParams.PARAM_STRING, advancedParamList.get("org_type"));
        stmt.addParam4WherePart("st.rfc_code", StatementAndParams.PARAM_STRING, advancedParamList.get("org_state"));
        stmt.addParam4WherePart("sri.sri_service_type", StatementAndParams.PARAM_STRING, advancedParamList.get("services_provided"));
        stmt.addParam4WherePart("org_delegation_person", StatementAndParams.PARAM_STRING, advancedParamList.get("org_delegation_person"));

        HashMap<String, String> paramList = params.getParamList();
        stmt.addParam4WherePart("org.c01 = ?", paramList.get("p_org_type"));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("org_name", "rfc.rfc_meaning", "coalesce(rfs.rfc_meaning, sri.sri_service_type)", "org_code",
                        "org_address", "org_email", "org_phone", "org_delegation_person", "st.rfc_meaning"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisServiceProvidersListSecurityManager sqm = new NtisServiceProvidersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisServiceProvidersList.ACTION_READ, NtisServiceProvidersList.IMPORT_ORDERS,
                NtisServiceProvidersList.DISABLE_ORGANIZATION };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Pagal perduodamą NtisServiceProviderRejectionModel, metodas pakeis pasirinktos organizacijos statustą į išregistruotą, organizacijai 
     * bus apribojama prieiga prie sistemos. Metodas atšauks visus aktyvius (ne atšauktus (ORD_STS_CANCELLED) ir ne užbaigtus (ORD_STS_FINISHED)) 
     * organizacijos teikiamų paslaugų užsakymus ir jiems suteiks statusą ORD_STS_CANCELLED_SYSTEM (atšauktas sistemos). Taip pat 
     * priklausomai nuo to, ar organizacija yra paslaugų teikėja, ar vandentvarkos įmonė, jai bus priskiriama PASL_ADMIN_DISABLED arba 
     * VAND_ADMIN_DISABLED rolė (prieinamos tos pačios formos, bet apriboti veiksmai - priskirtas tik READ), 
     * o lig tol priskirtoms rolėms bus atnaujinama OAR_DATE_TO - priskiriama einamos dienos data.
     * @param conn - prisijungimas prie DB
     * @param provider - NtisServiceProviderRejectionModel objektas
     * @throws Exception
     */
    public void setDisabled(Connection conn, NtisServiceProviderRejectionModel provider) throws Exception {
        this.checkIsFormActionAssigned(conn, DISABLE_ORGANIZATION);
        SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) organizationsDbService.loadRecord(conn, provider.getOrg_id());
        orgDao.setOrgState(NtisOrgStateConstants.DEREGISTERED);
        orgDao.setOrgRejectionReason(provider.getOrg_rejection_reason());
        organizationsDbService.saveRecord(conn, orgDao);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("UPDATE NTIS_ORDERS " + //
                "SET ORD_STATE = '" + NtisOrderStatus.ORD_STS_CANCELLED_SYSTEM + "', " + //
                "ORD_REJECTION_DATE = CURRENT_DATE " + //
                "WHERE ORD_STATE NOT IN ( '" + NtisOrderStatus.ORD_STS_CANCELLED + "', '" + NtisOrderStatus.ORD_STS_FINISHED + "', '"
                + NtisOrderStatus.ORD_STS_REJECTED + "') " + //
                "AND ORD_SRV_ID IN (SELECT SRV_ID FROM NTIS_SERVICES WHERE SRV_ORG_ID = ?::int )");
        stmt.addSelectParam(provider.getOrg_id());
        queryController.adjustRecordsInDB(conn, stmt);
    }

    /**
     * Metodas išsiųs pranešimus ir email'us vandentvarkos įmonei arba paslaugų teikėjui pagal pateiktą organizacijos id
     * @param conn - prisijungimas prie DB
     * @param orgId - vandentvarkos įmonės arba paslaugų teikėjo organizacijos id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);

        SprOrganizationsDAO serviceOrg = organizationsDbService.loadRecord(conn, orgId);
        List<String> serviceOrgEmails = null;
        if (serviceOrg.getC02() != null && serviceOrg.getC02().equals(DbConstants.BOOLEAN_TRUE) && serviceOrg.getOrg_email() != null
                && !serviceOrg.getOrg_email().isBlank()) {
            serviceOrgEmails = Arrays.asList(serviceOrg.getOrg_email().split("\\s*,\\s*"));
        }
        String roleCodes = """
                '%s', '%s'
                """.formatted(NtisRolesConstants.VAND_ADMIN_DISABLED, NtisRolesConstants.PASL_ADMIN_DISABLED);
        List<SprUsersDAO> serviceOrgUsers = getOrganizationUsers(conn, serviceOrg.getOrg_id(), roleCodes);

        Map<String, Object> context = new HashMap<String, Object>();
        if (serviceOrgEmails != null && !serviceOrgEmails.isEmpty()) {
            for (String email : serviceOrgEmails) {
                if (!email.isBlank()) {
                    systemProcessRequest.createRestrictedAccessRequest(conn, usrId, orgId, email, Languages.getLanguageByCode(lang));
                }
            }
        }
        for (SprUsersDAO user : serviceOrgUsers) {
            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orgId, "NTIS_SYS_ADMIN_NOTIF", "SRV_PROV_DISABLED_SUBJECT",
                    "SRV_PROV_DISABLED_BODY", context, NtisNtfRefType.SYSTEM.getCode(), NtisMessageSubject.MSG_SBJ_ACCESS_RESTRICTED.getCode(), new Date(),
                    user.getUsr_id(), serviceOrg.getOrg_id(), null);
        }
    }

    private List<SprUsersDAO> getOrganizationUsers(Connection conn, Double orgId, String roleCodes) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT distinct usr_id,
                       usr_email
                FROM spr_users usr
                JOIN spr_org_users ou ON usr_id = ou_usr_id
                JOIN spr_roles rol ON rol_code in (%s)
                JOIN spr_org_user_roles our ON our_ou_id = ou_id AND our_rol_id = rol.rol_id
                WHERE ou_org_id = ?::int
                AND now() BETWEEN ou_date_from AND COALESCE(ou_date_to, now())
                AND now() BETWEEN our_date_from AND COALESCE(our_date_to, now())
                                                """.formatted(roleCodes));
        stmt.addSelectParam(orgId);
        return queryController.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
    }
}
