package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisOrderTypeConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.processRequests.OrderProcessRequest;
import lt.project.ntis.models.NtisOrderDetails;
import lt.project.ntis.models.NtisServiceManagementItem;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisServicesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formos "Ne NTIS portale užsakytos paslaugos duomenų įvedimo forma" biznio logikai apibrėžti
 *
 */
@Component
public class NtisOrderOutsideNtisCreate extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final NtisOrdersDBService ntisOrdersDBService;

    private final NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService;

    private final SprOrganizationsDBServiceImpl organizationsDBService;

    private final NtisServicesDBService ntisServicesDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final OrderProcessRequest orderProcessRequest;

    private final NtisNotificationsManager ntisNotifications;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;
    
    private final NtisFacilityOwnersDBService ntisFacilityOwnersDBService;
    
    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    public NtisOrderOutsideNtisCreate(BaseControllerJDBC baseControllerJDBC, NtisOrdersDBService ntisOrdersDBService,
            NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService, SprOrganizationsDBServiceImpl organizationsDBService,
            NtisServicesDBService ntisServicesDBService, SprPersonsDBService sprPersonsDBService, OrderProcessRequest orderProcessRequest,
            NtisNotificationsManager ntisNotifications, SprOrgUsersDBServiceImpl sprOrgUsersDBService,  NtisFacilityOwnersDBService ntisFacilityOwnersDBService,
            NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.ntisOrdersDBService = ntisOrdersDBService;
        this.ntisOrderCompletedWorksDBService = ntisOrderCompletedWorksDBService;
        this.organizationsDBService = organizationsDBService;
        this.ntisServicesDBService = ntisServicesDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.orderProcessRequest = orderProcessRequest;
        this.ntisNotifications = ntisNotifications;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisFacilityOwnersDBService = ntisFacilityOwnersDBService;
        this.ntisWastewaterTreatmentFaciDBService= ntisWastewaterTreatmentFaciDBService;

    }

    @Override
    public String getFormName() {
        return "ORDER_RECEIVED_OUTSIDE_NTIS_CREATE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Ne NTIS portale užsakytos paslaugos", "Ne NTIS portale užsakytos paslaugos duomenų įvedimo forma");
        addFormActions(FormBase.ACTION_READ);
        addFormActions(FormBase.ACTION_CREATE);
    }

    /**
     * Metodas grąžins teikiamų paslaugų sąrašą pagal nurodytą organizacijos ID
     * @param conn - prisijungimas prie DB
     * @param orgId - organizacijos ID
     * @param usrId - sesijos naudotojo ID
     * @return NtisServiceManagementItem objektų masyvas
     * @throws Exception
     */
    public List<NtisServiceManagementItem> getOrgServices(Connection conn, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        StatementAndParams statementAndParams = new StatementAndParams(
                """
                             SELECT services.*,
                        statusrfc.rfc_meaning AS statusName
                        FROM
                        (
                            SELECT rfc.rfc_code as serviceType,
                                   rfc.rfc_meaning AS serviceName,
                                         CASE
                                             WHEN s.sr_status IS NULL OR s.sr_status = 'REJECTED' THEN 'SRV_STS_INACTIVE'
                                             WHEN s.sr_status = 'CONFIRMED' AND rfc.rfc_code = 'TYRIMAI' AND s.sri_removal_date IS NULL THEN 'SRV_STS_ACTIVE'
                                             WHEN s.sr_status in ('CONFIRMED','SUBMITTED') AND rfc.rfc_code in ('PRIEZIURA','VEZIMAS') AND s.sri_removal_date IS NULL THEN 'SRV_STS_ACTIVE'
                                             WHEN s.sri_removal_date IS NOT NULL THEN 'SRV_STS_SUSPENDED'
                                             ELSE 'SRV_STS_INACTIVE'
                                        END AS status,
                                   s.sr_id AS srId,
                                   s.sri_id AS sriId,
                                   s.srv_id AS srvId,
                                   s.sri_removal_date as removalDate,
                                       CASE
                                           WHEN s.sri_removal_date is null AND s.sr_status = 'CONFIRMED' THEN s.srv_available_in_ntis_portal
                                           ELSE null
                                       END AS srv_available_in_ntis_portal,
                                       CASE
                                           WHEN s.sri_removal_date is null AND s.sr_status = 'CONFIRMED' THEN s.srv_contract_available
                                           ELSE null
                                       END AS srv_contract_available,
                                       CASE
                                           WHEN s.sri_removal_date is null AND s.sr_status = 'CONFIRMED' THEN s.srv_lithuanian_level
                                           ELSE null
                                       END AS srv_lithuanian_level,
                                       CASE
                                           WHEN s.sr_status in ('CONFIRMED','SUBMITTED') AND rfc.rfc_code in ('PRIEZIURA','VEZIMAS') AND s.sri_removal_date IS NULL THEN s.is_confirmed
                                           WHEN s.sr_status = 'CONFIRMED' AND rfc.rfc_code = 'TYRIMAI' AND s.sri_removal_date IS NULL THEN s.is_confirmed
                                           ELSE null
                                       END AS is_confirmed
                               FROM spr_ref_codes rfc
                               LEFT JOIN
                               (
                                  SELECT sri.sri_service_type,
                                         sr.sr_id,
                                         sr.sr_status,
                                         sri.sri_id,
                                         srv.srv_id,
                                         srv.srv_date_to,
                                         srv.srv_available_in_ntis_portal,
                                         srv.srv_contract_available,
                                         srv.srv_lithuanian_level,
                                         CASE WHEN srv.srv_date_from IS NULL THEN 'N' ELSE 'Y' END AS is_confirmed,
                                         sri.sri_removal_date
                                         FROM ntis_service_req_items sri
                                     INNER JOIN ntis_service_requests sr ON sr.sr_id = sri.sri_sr_id AND sr.sr_org_id = ?::int AND sr.sr_status in ('CONFIRMED','SUBMITTED')
                                     INNER JOIN spr_org_users ou on ou.ou_org_id = sr.sr_org_id and ou.ou_usr_id = ?::int and CURRENT_DATE between ou.ou_date_from and COALESCE(ou.ou_date_to, now())
                                     LEFT JOIN ntis_services srv ON srv.srv_id = sri.sri_srv_id
                               ) s ON s.sri_service_type = rfc.rfc_code
                                 WHERE rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE'
                                  AND rfc.rfc_code <> 'VALYMAS'
                            ) AS services
                             LEFT JOIN spr_ref_codes_vw statusrfc ON statusrfc.rfc_code = services.status AND statusrfc.rfc_domain = 'SERVICE_STATUS' AND statusrfc.rft_lang = ?
                             ORDER BY services.serviceType, services.removalDate desc nulls first
                            """);
        statementAndParams.addSelectParam(orgId);
        statementAndParams.addSelectParam(usrId);
        statementAndParams.addSelectParam(lang);

        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(
                new MethodCaller("setAvailableInNtisPortalString", new StatementDataGetter[] { new StatementStringGetter("srv_available_in_ntis_portal") }));
        methods.add(new MethodCaller("setContractAvailableString", new StatementDataGetter[] { new StatementStringGetter("srv_contract_available") }));
        methods.add(new MethodCaller("setLithuanianLevelString", new StatementDataGetter[] { new StatementStringGetter("srv_lithuanian_level") }));
        methods.add(new MethodCaller("setIsConfirmedString", new StatementDataGetter[] { new StatementStringGetter("is_confirmed") }));
        Data2ObjectProcessor<NtisServiceManagementItem> dataProcessor = new Data2ObjectProcessor<NtisServiceManagementItem>(NtisServiceManagementItem.class,
                methods);
        List<NtisServiceManagementItem> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);
        if (queryResult.size() == 3) {
            return new ArrayList<NtisServiceManagementItem>(queryResult);
        } else {
            ArrayList<NtisServiceManagementItem> filteredServices = new ArrayList<NtisServiceManagementItem>();
            filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.PRIEZIURA.getCode()))
                    .findFirst().orElse(null));
            filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode()))
                    .findFirst().orElse(null));
            filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.VEZIMAS.getCode()))
                    .findFirst().orElse(null));
            return filteredServices;
        }
    }

    /**
     * Pagal nurodytą NtisOrderDetails objektą, metodas į duomenų bazę išsaugos NtisOrdersDAO objektą 
     *  ir (nebūtinai - priklauso nuo paduoto objekto ord_state) NtisOrderCompletedWorksDAO objektą.
     *  Prieš išsaugant metodas patikrins ar naudotojas turi teisę atlikti šį veiksmą.
     * @param record - NtisOrderDetails objektas
     * @param orgId - sesijos organizacijos ID
     * @param usrId - sesijos naudotojo ID
     * @return NtisOrderDetails objektas
     * @throws Exception
     */
    public NtisOrderDetails save(Connection conn, NtisOrderDetails record, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrderOutsideNtisCreate.ACTION_CREATE);
        NtisServicesDAO serviceDAO = ntisServicesDBService.loadRecordByParams(conn, " srv_id = ?::int and srv_org_id = ?::int ",
                new SelectParamValue(record.getOrd_srv_id()), new SelectParamValue(orgId));
        if (serviceDAO != null && serviceDAO.getSrv_id() != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            NtisOrdersDAO orderDAO = ntisOrdersDBService.newRecord();
            orderDAO.setOrd_state(record.getOrd_state());
            orderDAO.setOrd_type(NtisOrderTypeConstants.SERVICE);
            orderDAO.setOrd_created_in_ntis_portal(DbConstants.BOOLEAN_FALSE);
            if (record.getOrd_created() == null) {
                orderDAO.setOrd_created(new Date());
            } else {
                orderDAO.setOrd_created(record.getOrd_created());
            }
            orderDAO.setOrd_wtf_id(record.getOrd_wtf_id());
            orderDAO.setOrd_srv_id(record.getOrd_srv_id());
            orderDAO.setOrd_email(record.getOrd_email());
            orderDAO.setOrd_additional_description(record.getOrd_additional_description());
            orderDAO.setOrd_phone_number(record.getOrd_phone_number());
            ntisOrdersDBService.saveRecord(conn, orderDAO);
            NtisOrdersDAO savedOrderDAO = ntisOrdersDBService.saveRecord(conn, orderDAO);
            record.setOrd_id(savedOrderDAO.getOrd_id());
            if (record.getOrd_state().equals(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                NtisOrderCompletedWorksDAO completedOrderDAO = ntisOrderCompletedWorksDBService.newRecord();
                completedOrderDAO.setOcw_discharged_sludge_amount(record.getOcw_discharged_sludge_amount());
                completedOrderDAO.setOcw_completed_date(record.getOcw_completed_date());
                completedOrderDAO.setOcw_ord_id(savedOrderDAO.getOrd_id());
                completedOrderDAO.setOcw_cr_id(record.getOcw_cr_id());
                completedOrderDAO.setOcw_completed_works_description(record.getOcw_completed_works_description());
                if (serviceDAO.getSrv_type().equalsIgnoreCase(NtisServiceItemType.VEZIMAS.getCode())) {
                    orderDAO.setOrd_removed_sewage_date(completedOrderDAO.getOcw_completed_date());
                    ntisOrdersDBService.saveRecord(conn, orderDAO);
                }
                ntisOrderCompletedWorksDBService.saveRecord(conn, completedOrderDAO);
            }
            ntisFacilityOwnersDBService.manageWtfOwners(conn, orderDAO.getOrd_wtf_id(), serviceDAO.getSrv_org_id(), null, null, new Date(), null,
                    NtisFacilityOwnerType.SERVICE_PROVIDER);
            NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn,  orderDAO.getOrd_wtf_id());
            if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode()) && orderDAO.getOrd_state().equalsIgnoreCase(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
                ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
            }
            return record;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    /**
     * Metodas išsiųs pranešimus ir email'us INTS savininkui, pagal pateiktą užsakymo id
     * @param conn - prisijungimas prie DB
     * @param orderId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double orderId, Double usrId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        if (sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            NtisOrdersDAO orderDAO = ntisOrdersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, orderId);
            List<NtisFacilityOwnersDAO> facilityOwners = getFacilityOwners(conn, orderDAO);
            if (!facilityOwners.isEmpty() && facilityOwners != null) {
                Locale localeLT = new Locale("lt", "LT");
                SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy 'm.' MMMMM dd 'd.'", localeLT);
                Map<String, String> orderService = getOrderService(conn, orderDAO.getOrd_srv_id(), lang);

                Map<String, Object> context = new HashMap<String, Object>();
                context.put("serviceType", orderService.get("rfc_meaning"));
                context.put("orderId", orderId.intValue());
                context.put("orderDate", dateFormatLT.format(orderDAO.getOrd_created()));
                context.put("serviceProvider", organizationsDBService
                        .loadRecord(conn, ntisServicesDBService.loadRecord(conn, orderDAO.getOrd_srv_id()).getSrv_org_id()).getOrg_name());
                context.put("wtfAddress", getWtfAddress(conn, orderDAO.getOrd_wtf_id()));
                if (orderService.get("srv_type").equals(NtisServiceItemType.PRIEZIURA.getCode())) {
                    context.put("orderUrl", "tech-support/owner-tech-orders/service-order/");
                } else if (orderService.get("srv_type").equals(NtisServiceItemType.VEZIMAS.getCode())) {
                    context.put("orderUrl", "tech-support/owner-disposal-orders/service-order/");
                }
                String templateCode = "NTIS_ORD_STS_NOTIF";
                String templateSubject = "ORD_STS_REGISTER_SUBJECT";
                String templateBody = "ORD_STS_REGISTER_BODY";
                String msgSubject = orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CONFIRMED.getCode())
                        ? NtisMessageSubject.MSG_SBJ_ORDER_CONFIRMED.getCode()
                        : NtisMessageSubject.MSG_SBJ_ORDER_FINISHED.getCode();
                for (NtisFacilityOwnersDAO owner : facilityOwners) {
                    if (owner.getFo_org_id() != null) {
                        String roleCodes = """
                                '%s', '%s', '%s'
                                """.formatted(NtisRolesConstants.PASL_ADMIN, NtisRolesConstants.VAND_ADMIN, NtisRolesConstants.INTS_OWNER);
                        List<SprUsersDAO> orgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, owner.getFo_org_id(), roleCodes);
                        for (SprUsersDAO orgUser : orgUsers) {
                            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), templateCode,
                                    templateSubject, templateBody, context, NtisNtfRefType.ORDER.getCode(), msgSubject, new Date(), orgUser.getUsr_id(),
                                    owner.getFo_org_id(), null);
                        }
                        SprOrganizationsDAO orgDAO = organizationsDBService.loadRecord(conn, owner.getFo_org_id());
                        if (orgDAO.getC02() != null && orgDAO.getC02().equals(DbConstants.BOOLEAN_TRUE) && orgDAO.getOrg_email() != null
                                && !orgDAO.getOrg_email().isBlank()) {
                            List<String> orgEmails = Arrays.asList(orgDAO.getOrg_email().split("\\s*,\\s*"));
                            for (String email : orgEmails) {
                                orderProcessRequest.createOrderRegisteredRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang),
                                        context);
                            }
                        }
                    } else if (owner.getFo_per_id() != null) {
                        List<SprUsersDAO> personUsers = getPersonUsers(conn, owner.getFo_per_id());
                        for (SprUsersDAO perUser : personUsers) {
                            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), templateCode,
                                    templateSubject, templateBody, context, NtisNtfRefType.ORDER.getCode(), msgSubject, new Date(), perUser.getUsr_id(),
                                    perUser.getUsr_org_id(), null);
                        }
                        SprPersonsDAO personDAO = sprPersonsDBService.loadRecord(conn, owner.getFo_per_id());
                        if (personDAO.getPer_email() != null && personDAO.getPer_email_confirmed() != null
                                && personDAO.getPer_email_confirmed().equals(DbConstants.BOOLEAN_TRUE) && personDAO.getC01() != null
                                && personDAO.getC01().equals(DbConstants.BOOLEAN_TRUE)) {
                            orderProcessRequest.createOrderRegisteredRequest(conn, usrId, orderDAO.getOrd_id(), personDAO.getPer_email(),
                                    Languages.getLanguageByCode(lang), context);
                        }
                    }
                }
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    private List<NtisFacilityOwnersDAO> getFacilityOwners(Connection conn, NtisOrdersDAO order) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT fo_id,
                       fo_org_id,
                       fo_per_id
                FROM ntis_facility_owners
                WHERE fo_wtf_id = ?::int
                AND fo_owner_type in ('OWNER','MANAGER')
                AND now() BETWEEN fo_date_from AND COALESCE(fo_date_to, now())
                                """);
        stmt.addSelectParam(order.getOrd_wtf_id());
        List<NtisFacilityOwnersDAO> result = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisFacilityOwnersDAO.class);
        return result;
    }

    private Map<String, String> getOrderService(Connection conn, Double srvId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT srv_type.rfc_meaning,
                       srv_type
                FROM ntis_services srv
                JOIN spr_ref_codes_vw srv_type ON srv_type.rfc_code = srv.srv_type and srv_type.rfc_domain = 'NTIS_SRV_ITEM_TYPE' and srv_type.rft_lang = ?
                WHERE srv_id = ?::int
                                """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(srvId);
        HashMap<String, String> result = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt).get(0);
        return result;
    }

    private String getWtfAddress(Connection conn, Double wtfId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT
                    CASE
                        WHEN WTF.WTF_AD_ID IS NULL
                        THEN WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || '.'
                        ELSE WAV.FULL_ADDRESS_TEXT
                    END AS address
                FROM NTIS_WASTEWATER_TREATMENT_FACI WTF
                LEFT JOIN NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID
                WHERE wtf_id = ?::int
                                """);
        stmt.addSelectParam(wtfId);
        HashMap<String, String> result = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt).get(0);
        return result.get("address");
    }

    private List<SprUsersDAO> getPersonUsers(Connection conn, Double perId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                     SELECT usr_id,
                            usr_org_id
                     FROM spr_users usr
                     JOIN spr_persons per ON per_id = usr_per_id AND per_id = ?::int
                     WHERE now() BETWEEN usr_date_from AND COALESCE(usr_date_to, now()) AND (usr_lock_date is null OR now() < usr_lock_date)
                """);
        stmt.addSelectParam(perId);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
    }
}
