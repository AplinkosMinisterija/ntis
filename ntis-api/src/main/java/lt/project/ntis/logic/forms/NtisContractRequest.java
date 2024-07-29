package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.util.Value;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.dao.NtisContractCommentsDAO;
import lt.project.ntis.dao.NtisContractServicesDAO;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.enums.NtisContractStatus;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.model.NtisNewContractRequest;
import lt.project.ntis.logic.forms.model.NtisNewContractRequestInfo;
import lt.project.ntis.logic.forms.model.NtisSubmitContractRequestInfo;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.processRequests.ContractProcessRequest;
import lt.project.ntis.service.NtisContractCommentsDBService;
import lt.project.ntis.service.NtisContractServicesDBService;
import lt.project.ntis.service.NtisContractsDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisSelectedFacilitiesDBService;

/**
 * Klasė skirta formos "S1020" biznio logikai apibrėžti
 */
@Component
public class NtisContractRequest extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    NtisContractsDBService ntisContractsDBService;

    @Autowired
    NtisContractServicesDBService ntisContractServicesDBService;

    @Autowired
    NtisContractCommentsDBService ntisContractCommentsDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    ContractProcessRequest contractProcessRequest;

    @Autowired
    NtisNotificationsManager ntisNotificationsManager;

    @Autowired
    NtisSelectedFacilitiesDBService selectedFacilitiesDBService;

    @Autowired
    NtisINTSOwnerDashboardPage ntisINTSOwnerDashboardPage;

    @Value("${app.host}")
    private String appHostUrl;

    @Override
    public String getFormName() {
        return "NTIS_CONTRACT_REQUEST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Prašymas sudaryti paslaugų teikimo sutartį", "Pateikti prašymą sudaryti paslaugų teikimo sutartį");
        addFormActions(ACTION_CREATE);
    }

    public NtisNewContractRequestInfo loadNewRequestInfo(Connection conn, Double orgId, Double perId, Double usrId, NtisNewContractRequest record, String lang)
            throws Exception, SparkBusinessException {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        StatementAndParams mainStatementAndParams = new StatementAndParams(
                """
                        SELECT org.org_id AS orgId,
                               org.org_name AS orgName,
                               org.org_email AS orgEmail,
                               org.org_phone AS orgPhone,
                               available_services.available_services_json
                        FROM spr_organizations org
                        JOIN ( SELECT COALESCE(JSON_AGG(JSON_BUILD_OBJECT('type', srv.srv_type, 'name', rfc.rfc_meaning, 'description', srv.srv_description)), '[]') as available_services_json,
                                        srv_org_id
                                     FROM ntis.ntis_services srv
                                    INNER JOIN spr_ref_codes_vw rfc ON rfc.rfc_code = srv.srv_type AND rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE' AND rfc.rft_lang = ?
                                      AND srv.srv_contract_available = '%s'
                                      AND %s
                                      GROUP BY srv_org_id
                               ) AS available_services ON srv_org_id = org.org_id
                        WHERE org.org_id = ? AND org.c01 in ('%s', '%s')
                                                    """
                        .formatted(DbConstants.BOOLEAN_TRUE,
                                dbStatementManager.getPeriodValidationForCurrentDateStr("srv.srv_date_from", "srv.srv_date_to", false), NtisOrgType.PASLAUG,
                                NtisOrgType.PASLAUG_VANDEN));
        mainStatementAndParams.addSelectParam(lang);
        mainStatementAndParams.addSelectParam(record.getSrvProvId());

        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setAvailableServicesJson", new StatementDataGetter[] { new StatementStringGetter("available_services_json") }));
        Data2ObjectProcessor<NtisNewContractRequestInfo> dataProcessor = new Data2ObjectProcessor<NtisNewContractRequestInfo>(NtisNewContractRequestInfo.class,
                methods);
        List<NtisNewContractRequestInfo> mainQueryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, mainStatementAndParams, dataProcessor);
        if (mainQueryResult == null || mainQueryResult.isEmpty()) {
            throw new Exception("No service provider with organization id " + record.getSrvProvId() + " was found");
        }
        NtisNewContractRequestInfo result = mainQueryResult.get(0);

        SprPersonsDAO sprPersonsDAO = sprPersonsDBService.loadRecord(conn, perId);
        result.setApplicantEmail(sprPersonsDAO.getPer_email());
        result.setApplicantPhone(sprPersonsDAO.getPer_phone_number());

        // load wtf info:
        Double selectedWtf = selectedFacilitiesDBService.getSelecteFacility(conn, usrId, orgId);
        result.setWtfInfo(getFacilityDetails(conn, perId, orgId, record.getWtfId() != null ? record.getWtfId() : selectedWtf, lang));
        return result;
    }

    public String submitContractRequest(Connection conn, Double orgId, Double perId, Double usrId, NtisSubmitContractRequestInfo requestInfo)
            throws Exception, SparkBusinessException {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        if (orgId == null && perId == null) {
            throw new Exception("Contract request without organization id and person id was attempted to submit");
        }
        if (requestInfo.getServices().isEmpty()) {
            throw new Exception("Contract request for organization with id " + requestInfo.getOrgId()
                    + " with no selected services was attempted to submit by person with id " + perId);
        }

        // save contract
        NtisContractsDAO ntisContractsDAO = ntisContractsDBService.newRecord();
        ntisContractsDAO.setCot_created_in_ntis_portal(DbConstants.BOOLEAN_TRUE);
        ntisContractsDAO.setCot_state(NtisContractStatus.SUBMITTED.getCode());
        ntisContractsDAO.setCot_from_date(
                Utils.getDateFromString(requestInfo.getStartDate(), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
        ntisContractsDAO
                .setCot_to_date(Utils.getDateFromString(requestInfo.getEndDate(), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
        if (orgId != null) {
            ntisContractsDAO.setCot_org_id(orgId);
        } else {
            ntisContractsDAO.setCot_per_id(perId);
        }
        ntisContractsDAO.setCot_wtf_id(requestInfo.getWtfId());
        ntisContractsDAO.setCot_created(new Date());
        ntisContractsDAO.setCot_client_email(requestInfo.getApplicantEmail());
        ntisContractsDAO.setCot_client_phone_no(requestInfo.getApplicantPhone());
        NtisContractsDAO savedNtisContractsDAO = ntisContractsDBService.saveRecord(conn, ntisContractsDAO);

        // save contract services
        String srvTypeInQuestionMarks = "";
        for (int i = 0; i < requestInfo.getServices().size(); i++) {
            srvTypeInQuestionMarks = srvTypeInQuestionMarks + (i > 0 ? ", ?" : "?");
        }
        StatementAndParams servicesStmt = new StatementAndParams("" + "SELECT srv_id " //
                + "  FROM ntis_services " //
                + " WHERE srv_org_id = ? " //
                + "   AND srv_contract_available = '" + DbConstants.BOOLEAN_TRUE + "'"//
                + "   AND srv_type IN (" + srvTypeInQuestionMarks + ")");
        servicesStmt.addSelectParam(requestInfo.getOrgId());
        for (String service : requestInfo.getServices()) {
            servicesStmt.addSelectParam(service);
        }
        List<HashMap<String, String>> servicesResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, servicesStmt);
        for (HashMap<String, String> serviceResult : servicesResult) {
            NtisContractServicesDAO ntisContractServicesDAO = ntisContractServicesDBService.newRecord();
            ntisContractServicesDAO.setCs_srv_id(Utils.getDouble(serviceResult.get("srv_id")));
            ntisContractServicesDAO.setCs_cot_id(savedNtisContractsDAO.getCot_id());
            ntisContractServicesDBService.saveRecord(conn, ntisContractServicesDAO);
        }

        // save contract comments
        for (String comment : requestInfo.getComments()) {
            NtisContractCommentsDAO ntisContractCommentsDAO = ntisContractCommentsDBService.newRecord();
            ntisContractCommentsDAO.setCc_message(comment);
            ntisContractCommentsDAO.setCc_cot_id(savedNtisContractsDAO.getCot_id());
            ntisContractCommentsDAO.setCc_org_id(orgId);
            ntisContractCommentsDAO.setCc_per_id(perId);
            ntisContractCommentsDAO.setCc_created(new Date());
            ntisContractCommentsDBService.saveRecord(conn, ntisContractCommentsDAO);
        }

        this.checkIsFacilityOwner(conn, savedNtisContractsDAO.getCot_wtf_id(), usrId, perId, orgId);
        this.notifyOfNewContractRequest(conn, ntisContractsDAO.getCot_id(), usrId);
        return null;
    }

    private void notifyOfNewContractRequest(Connection conn, Double cotId, Double sessionUsrId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        Double orgId = Utils.getDouble(this.getServiceProvider(conn, cotId).get("org_id"));
        StatementAndParams stmt = new StatementAndParams("""
                select usr_id,
                    usr_email,
                    org_email,
                    cs_cot_id as cot_id,
                    o.c02 as requests_email
                    from spr_users
                    inner join spr_org_users on ou_usr_id = usr_id and current_date between ou_date_from and coalesce(ou_date_to, now())
                    inner join spr_organizations o on ou_org_id = org_id and org_id = ?
                    inner join spr_org_user_roles on our_ou_id = ou_id and current_date between ou_date_from and coalesce(ou_date_to, now())
                    inner join spr_roles on our_rol_id = rol_id and rol_code in ('PASL_ADMIN', 'CONTRACT_MANAGER')
                    inner join ntis_services on srv_org_id = org_id
                    inner join ntis_contract_services on cs_srv_id = srv_id and cs_cot_id = ?
                    group by usr_id, usr_email, org_email, o.c02, cs_cot_id
                """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(cotId);

        List<HashMap<String, String>> users = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        List<String> srvPrvdEmails = new ArrayList<>();
        if (users != null && users.size() > 0) {
            if (users.get(0).get("requests_email") != null && users.get(0).get("requests_email").equalsIgnoreCase(YesNo.Y.getCode())
                    && users.get(0).get("org_email") != null) {
                srvPrvdEmails = Arrays.asList(users.get(0).get("org_email").split("\\s*,\\s*"));
            }
        } else {
            srvPrvdEmails = null;
        }
        boolean emailSent = false;
        for (HashMap<String, String> user : users) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("homeUrl", this.appHostUrl);
            params.put("contractId", user.get("cot_id"));
            params.put("contractUrl", user.get("cot_id"));
            if (!emailSent) {
                for (String email : srvPrvdEmails) {
                    this.contractProcessRequest.createContractRequestedByOwnerRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                }
                emailSent = true;
            }
            this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                    "COT_REQUESTED_BY_OWNER_SUBJECT", "COT_REQUESTED_BY_OWNER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                    NtisMessageSubject.MSG_SBJ_AGREEMENT_REQUESTED.getCode(), null, Utils.getDouble(user.get("usr_id")), orgId, null);
        }
    }

    private HashMap<String, String> getServiceProvider(Connection conn, Double cotId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select org_name,
                org_id
                from spr_organizations
                inner join ntis_services on srv_org_id = org_id
                inner join ntis_contract_services on cs_srv_id = srv_id and cs_cot_id = ?
                """);
        stmt.addSelectParam(cotId);
        List<HashMap<String, String>> data = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        HashMap<String, String> provider = null;
        if (data != null && !data.isEmpty()) {
            provider = data.get(0);
        }
        return provider;
    }

    private NtisWtfInfo getFacilityDetails(Connection conn, Double perId, Double orgId, Double wtfId, String lang) throws Exception {
        NtisWtfInfo facility = new NtisWtfInfo();
        StatementAndParams stmt = new StatementAndParams("""
                       select wtf.wtf_id as id,
                       coalesce(wav.full_address_text, wtf.wtf_facility_latitude ||', '|| wtf.wtf_facility_longitude) as address,
                       coalesce(typ.rft_display_code, typ.rfc_meaning) as type,
                       wtf.wtf_technical_passport_id as technicalPassport,
                       coalesce(mnf.rft_display_code, mnf.rfc_meaning) as manufacturer,
                       coalesce(mdl.rft_display_code, mdl.rfc_meaning) as model,
                       to_char(wtf.wtf_installation_date, '%s') as installationDate,
                       wtf.wtf_type as typeClsf,
                       wtf.wtf_distance as distance,
                       coalesce(cap.rft_display_code, cap.rfc_meaning) capacity
                from ntis_wastewater_treatment_faci wtf
                left join ntis_address_vw wav on wtf.wtf_ad_id = wav.address_id
                left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                left join spr_ref_codes_vw mnf on mnf.rfc_code = wtf.wtf_manufacturer and mnf.rfc_domain = 'NTIS_FACIL_MANUFA' and mnf.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw mdl on mdl.rfc_code = wtf.wtf_model and mdl.rfc_domain = 'NTIS_FACIL_MODEL' and mdl.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw cap on cap.rfc_code = wtf.wtf_capacity and cap.rfc_domain = 'NTIS_FACIL_CAPACITY' and cap.rft_lang = typ.rft_lang
                where wtf.wtf_id::int = ?
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wtfId);
        ArrayList<NtisWtfInfo> data = (ArrayList<NtisWtfInfo>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWtfInfo.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
            return facility;
        } else {
            throw new Exception("No wastewater facility with id " + wtfId + " found");
        }
    }

    private void checkIsFacilityOwner(Connection conn, Double wtfId, Double usrId, Double perId, Double orgId) throws Exception {
        Boolean isOwner;
        if (orgId != null) {
            isOwner = ntisFacilityOwnersDBService.isOrganizationAnOwnerOfFacility(conn, orgId, wtfId);
        } else {
            isOwner = ntisFacilityOwnersDBService.isPersonAnOwnerOfFacility(conn, perId, wtfId);
        }

        if (!isOwner) {
            NtisFacilityOwnersDAO daoObject = new NtisFacilityOwnersDAO();
            daoObject.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
            daoObject.setFo_date_from(new Date());
            daoObject.setFo_org_id(orgId);
            daoObject.setFo_per_id(perId);
            daoObject.setFo_wtf_id(wtfId);
            ntisFacilityOwnersDBService.insertRecord(conn, daoObject);

            ntisINTSOwnerDashboardPage.updateSelectedWtf(conn, usrId, orgId, wtfId);
        }
    }
}
