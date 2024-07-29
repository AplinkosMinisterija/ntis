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
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisResearchesDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.logic.forms.processRequests.OrderProcessRequest;
import lt.project.ntis.models.NtisResearchOrderModel;
import lt.project.ntis.models.NtisServiceDescriptionDetails;
import lt.project.ntis.models.NtisWastewaterTreatmentFacility;
import lt.project.ntis.models.ResearchCriteriaResultsModel;
import lt.project.ntis.models.ResearchRequestedCriteriaModel;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisResearchesDBService;
import lt.project.ntis.service.NtisServicesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formos "Tyrimo užsakymo suvedimas laboratorijai" (formos kodas T3080)  biznio logikai apibrėžti
 */
@Component
public class ResearchOutsideNtisCreate extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisOrdersDBService ordersDbService;

    private final NtisResearchesDBService researchesDbService;

    private final NtisOrderCompletedWorksDBService orderCompletedWorksDbService;

    private final SprFilesDBService filesDbService;

    private final FileStorageService fileStorageService;

    private final SprOrganizationsDBServiceImpl organizationsDBService;

    private final NtisServicesDBService ntisServicesDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final OrderProcessRequest orderProcessRequest;

    private final NtisNotificationsManager ntisNotifications;

    private final NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    public ResearchOutsideNtisCreate(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, NtisOrdersDBService ordersDbService,
            NtisResearchesDBService researchesDbService, NtisOrderCompletedWorksDBService orderCompletedWorksDbService, SprFilesDBService filesDbService,
            FileStorageService fileStorageService, SprOrganizationsDBServiceImpl organizationsDBService, NtisServicesDBService ntisServicesDBService,
            SprPersonsDBService sprPersonsDBService, OrderProcessRequest orderProcessRequest, NtisNotificationsManager ntisNotifications,
            NtisFacilityOwnersDBService ntisFacilityOwnersDBService, SprOrgUsersDBServiceImpl sprOrgUsersDBService,
            NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ordersDbService = ordersDbService;
        this.researchesDbService = researchesDbService;
        this.orderCompletedWorksDbService = orderCompletedWorksDbService;
        this.filesDbService = filesDbService;
        this.fileStorageService = fileStorageService;
        this.organizationsDBService = organizationsDBService;
        this.ntisServicesDBService = ntisServicesDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.orderProcessRequest = orderProcessRequest;
        this.ntisNotifications = ntisNotifications;
        this.ntisFacilityOwnersDBService = ntisFacilityOwnersDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisWastewaterTreatmentFaciDBService = ntisWastewaterTreatmentFaciDBService;
    }

    @Override
    public String getFormName() {
        return "RESEARCH_OUTSIDE_NTIS_CREATE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimo užsakymo suvedimas laboratorijai", "Tyrimo užsakymo suvedimo forma laboratorijai");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE);
    }

    /**
     * Metodas grąžins pagrindiniams tyrimų kriterijams galiojančias normas pagal pasirinkto įrenginio įrengimo datą
     * @param conn - prisijungimas prie DB
     * @param wtfId - nuoroda į nuotekų tvarkymo įrenginį
     * @param lang - sesijos kalba
     * @return ArrayList<ResearchCriteriaResultsModel>
     * @throws Exception
     */
    public ArrayList<ResearchCriteriaResultsModel> getResearchNorms(Connection conn, Double wtfId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ResearchOutsideNtisCreate.ACTION_READ);
        NtisWastewaterTreatmentFacility facility = getOrderFacility(conn, wtfId);
        if (facility != null && facility.getWtf_id() != null) {
            StatementAndParams stmt = new StatementAndParams("""
                    select
                        rfc_code as code,
                        coalesce(rfc_meaning, rn_research_type) as display,
                        rn_research_norm as norm,
                        rn_id
                    from ntis.ntis_research_norms
                    left join spark.spr_ref_codes_vw on rfc_code = rn_research_type and rfc_domain = 'NTIS_RESEARCH_TYPE' and rft_lang = ?
                    where current_date between rn_date_from and coalesce (rn_date_to, now())
                    and case
                        when rn_facility_installation_date is not null
                             and ?::date >= '2018-09-26'::date
                        then rn_facility_installation_date = '>20180926'
                        when (rn_facility_installation_date is not null
                             and ?::date < '2018-09-26'::date)
                          or (rn_facility_installation_date is not null and ?::date is null)
                             then rn_facility_installation_date = '<20180926'
                        else rn_facility_installation_date is null end
                                    """);
            stmt.addSelectParam(lang);
            stmt.addSelectParam(facility.getWtf_installation_date() != null ? facility.getWtf_installation_date() : null);
            stmt.addSelectParam(facility.getWtf_installation_date() != null ? facility.getWtf_installation_date() : null);
            stmt.addSelectParam(facility.getWtf_installation_date() != null ? facility.getWtf_installation_date() : null);
            stmt.setWhereExists(true);
            ArrayList<ResearchCriteriaResultsModel> data = (ArrayList<ResearchCriteriaResultsModel>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                    ResearchCriteriaResultsModel.class);
            return data;
        } else {
            throw new Exception("No wastewater facility found");
        }
    }

    public Double checkLabSrvId(Connection conn, Double orgId, Double usrId) throws Exception {
        Double srvId = null;
        this.checkIsFormActionAssigned(conn, ResearchOutsideNtisCreate.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select srv_id
                        from ntis_services
                        inner join spr_org_users ou on ou.ou_org_id = srv_org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                        where srv_type = 'TYRIMAI'
                        and current_date between srv_date_from and coalesce(srv_date_to, now())
                        and srv_org_id = ?::int
                        """);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        ArrayList<NtisServiceDescriptionDetails> data = (ArrayList<NtisServiceDescriptionDetails>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisServiceDescriptionDetails.class);
        if (data != null && !data.isEmpty()) {
            srvId = data.get(0).getSrv_id();
        }
        return srvId;
    }

    /**
     * Metodas true reikšmę, jeigu organizacija, kuriai priklauso prisijungęs naudotojas, turi registruotą laboratorinių tyrimų paslaugą
     * @param conn - prisijungimas prie DB
     * @param orgId - nuoroda į prisijungusio naudotojo organizaciją
     * @param usrId - prisijungusio naudotojo sesijos id
     * @return boolean reikšmė
     * @throws Exception
     */
    public Boolean checkIfHasResearchService(Connection conn, Double orgId, Double usrId) throws Exception {

        StatementAndParams stmt = new StatementAndParams("""
                                    select sr_id
                from ntis_service_requests
                inner join ntis_service_req_items on sri_sr_id = sr_id
                left join ntis_services on sri_srv_id = srv_id
                where sr_type = 'PASLAUG'
                  and sri_service_type = 'TYRIMAI'
                  and sr_org_id = ?::int
                  and sr_status not in ('REJECTED', 'SUBMITTED')
                  and (srv_date_to is null or srv_date_to >= current_date)

                                    """);
        stmt.addSelectParam(orgId);
        ArrayList<NtisServiceDescriptionDetails> data = (ArrayList<NtisServiceDescriptionDetails>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisServiceDescriptionDetails.class);

        return (data != null && !data.isEmpty());
    }

    private NtisWastewaterTreatmentFacility getOrderFacility(Connection conn, Double wtfId) throws Exception {
        NtisWastewaterTreatmentFacility facility = new NtisWastewaterTreatmentFacility();
        StatementAndParams stmt = new StatementAndParams("""
                select
                    wtf.wtf_id,
                    to_char(wtf.wtf_installation_date, '%s') as wtf_installation_date
                from ntis.ntis_wastewater_treatment_faci wtf
                where wtf.wtf_id = ?
                            """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(wtfId);
        stmt.setWhereExists(true);
        ArrayList<NtisWastewaterTreatmentFacility> data = (ArrayList<NtisWastewaterTreatmentFacility>) baseControllerJDBC.selectQueryAsObjectArrayList(conn,
                stmt, NtisWastewaterTreatmentFacility.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
            return facility;
        } else {
            throw new Exception("No wastewater facility found");
        }
    }

    /**
     * Pagal nurodytą NtisResearchOrderModel objektą, metodas sukurs naujo užsakymo DAO objektus ir išsaugos juos duomenų bazėje
     * prieš tai patikrinęs ar naudotojas turi teisę išsaugoti šiuos duomenis
     * @param researchOrder - NtisResearchOrderModel objektas
     * @param usrId - sesijos naudotojo id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @throws Exception
     */
    public NtisOrdersDAO saveNewOrder(Connection conn, NtisResearchOrderModel researchOrder, Double userId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, ResearchOutsideNtisCreate.ACTION_CREATE);
        Double srvId = checkLabSrvId(conn, orgId, userId);
        if (srvId != null) {
            NtisServicesDAO srvDao = ntisServicesDBService.loadRecord(conn, srvId);
            NtisOrdersDAO orderDAO = ordersDbService.newRecord();
            orderDAO.setOrd_state(researchOrder.getStatusClsf());
            orderDAO.setOrd_completion_estimate_date(researchOrder.getCompletionEstimate());
            orderDAO.setOrd_usr_id(userId);
            orderDAO.setOrd_additional_description(researchOrder.getComment());
            orderDAO.setOrd_created(researchOrder.getOrdCreated());
            orderDAO.setOrd_srv_id(srvId);
            orderDAO.setOrd_wtf_id(researchOrder.getWtfId());
            orderDAO.setOrd_created_in_ntis_portal(YesNo.N.getCode());
            orderDAO.setOrd_type("ORD_TYPE_ANALYSIS");
            orderDAO.setOrd_email(researchOrder.getOrdererEmail());
            orderDAO.setOrd_phone_number(researchOrder.getOrdererPhone());
            orderDAO.setC01(researchOrder.getOrdererName());
            ordersDbService.saveRecord(conn, orderDAO);

            NtisOrderCompletedWorksDAO completedWorksDAO = orderCompletedWorksDbService.newRecord();
            completedWorksDAO.setOcw_ord_id(researchOrder.getOrdId());
            completedWorksDAO.setOcw_res_person(researchOrder.getResponsiblePerson());
            completedWorksDAO.setOcw_completed_date(Utils.getDate(new Date()));
            completedWorksDAO.setOcw_ord_id(orderDAO.getOrd_id());
            completedWorksDAO.setOcw_smp_person(researchOrder.getSamplePerson());
            completedWorksDAO.setOcw_rsr_person(researchOrder.getResearchPerson());
            completedWorksDAO.setOcw_completed_date(Utils.getDate(new Date()));
            completedWorksDAO.setOcw_completed_works_description(researchOrder.getResearchComments());
            completedWorksDAO.setOcw_usr_id(userId);
            if (researchOrder.getResultsFile() != null && researchOrder.getResultsFile().getFil_key() != null) {
                SprFilesDAO sprFile = filesDbService.loadRecordByKey(conn, fileStorageService.decryptFileKey(researchOrder.getResultsFile().getFil_key()));
                filesDbService.markAsConfirmed(conn, sprFile);
                completedWorksDAO.setOcw_fil_id(sprFile.getFil_id());
            }
            orderCompletedWorksDbService.saveRecord(conn, completedWorksDAO);
            if (researchOrder.getStatusClsf().equalsIgnoreCase(NtisOrderStatus.ORD_STS_CONFIRMED.getCode())) {
                for (ResearchRequestedCriteriaModel orderedResearchType : researchOrder.getSelectedCriteria()) {
                    if (orderedResearchType.getIsSelected()) {
                        NtisResearchesDAO orderedResearch = researchesDbService.newRecord();
                        orderedResearch.setRes_ord_id(orderDAO.getOrd_id());
                        orderedResearch.setRes_reserch_type(orderedResearchType.getCode());
                        researchesDbService.saveRecord(conn, orderedResearch);
                    }
                }
            }
            if (researchOrder.getStatusClsf().equalsIgnoreCase(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                boolean complies = true;
                for (ResearchCriteriaResultsModel result : researchOrder.getResults()) {
                    if (result.getResult() != null) {
                        NtisResearchesDAO research = researchesDbService.newRecord();
                        research.setRes_ord_id(orderDAO.getOrd_id());
                        research.setRes_reserch_type(result.getCode());
                        research.setRes_value(result.getResult());
                        research.setRes_research_date(researchOrder.getResearchDate());
                        research.setRes_sample_date(researchOrder.getSampleDate());
                        research.setRes_created(Utils.getDate(new Date()));
                        research.setRes_rn_id(result.getRn_id());
                        researchesDbService.saveRecord(conn, research);
                        if (result.getResult().compareTo(result.getNorm()) > 0) {
                            complies = false;
                        }
                    }
                }
                if (!complies) {
                    orderDAO.setOrd_compliance_norms("RESEARCH_NORM_NOT_COMPLIES");
                } else {
                    orderDAO.setOrd_compliance_norms("RESEARCH_NORM_COMPLIES");
                }
                ordersDbService.saveRecord(conn, orderDAO);
            }
            ntisFacilityOwnersDBService.manageWtfOwners(conn, orderDAO.getOrd_wtf_id(), srvDao.getSrv_org_id(), null, null, new Date(), null,
                    NtisFacilityOwnerType.SERVICE_PROVIDER);
            NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, orderDAO.getOrd_wtf_id());
            if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode()) && orderDAO.getOrd_state().equalsIgnoreCase(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
                ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
            }
            return orderDAO;
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

        NtisOrdersDAO orderDAO = ordersDbService.loadRecordByIdAndSrvOrgId(conn, orgId, orderId);
        List<NtisFacilityOwnersDAO> facilityOwners = getFacilityOwners(conn, orderDAO);

        Locale localeLT = new Locale("lt", "LT");
        SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy 'm.' MMMMM dd 'd.'", localeLT);
        Map<String, String> orderService = getOrderService(conn, orderDAO.getOrd_srv_id(), lang);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("serviceType", orderService.get("rfc_meaning"));
        context.put("orderId", orderId.intValue());
        context.put("orderDate", dateFormatLT.format(orderDAO.getOrd_created()));
        context.put("serviceProvider",
                organizationsDBService.loadRecord(conn, ntisServicesDBService.loadRecord(conn, orderDAO.getOrd_srv_id()).getSrv_org_id()).getOrg_name());
        context.put("wtfAddress", getWtfAddress(conn, orderDAO.getOrd_wtf_id()));
        context.put("orderUrl", "research/owner-research-list/research-order/");

        if (orderDAO.getOrd_email() != null) {
            orderProcessRequest.createOrderRegisteredRequest(conn, usrId, orderDAO.getOrd_id(), orderDAO.getOrd_email(), Languages.getLanguageByCode(lang),
                    context);
        }

        if (!facilityOwners.isEmpty() && facilityOwners != null) {
            for (NtisFacilityOwnersDAO owner : facilityOwners) {
                String templateCode = "NTIS_ORD_STS_NOTIF";
                String templateSubject = "ORD_STS_REGISTER_SUBJECT";
                String templateBody = "ORD_STS_REGISTER_BODY";
                String msgSubject = orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CONFIRMED.getCode())
                        ? NtisMessageSubject.MSG_SBJ_ORDER_CONFIRMED.getCode()
                        : NtisMessageSubject.MSG_SBJ_ORDER_FINISHED.getCode();
                if (owner.getFo_org_id() != null) {
                    String roleCodes = """
                            '%s', '%s', '%s'
                            """.formatted(NtisRolesConstants.PASL_ADMIN, NtisRolesConstants.VAND_ADMIN, NtisRolesConstants.INTS_OWNER);
                    List<SprUsersDAO> orgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, owner.getFo_org_id(), roleCodes);
                    for (SprUsersDAO orgUser : orgUsers) {
                        ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), templateCode,
                                templateSubject, templateBody, context, NtisNtfRefType.RESEARCH.getCode(), msgSubject, new Date(), orgUser.getUsr_id(),
                                owner.getFo_org_id(), null);
                    }
                    if (orderDAO.getOrd_email() == null) {
                        SprOrganizationsDAO orgDAO = organizationsDBService.loadRecord(conn, owner.getFo_org_id());
                        if (orgDAO.getC02() != null && orgDAO.getC02().equals(DbConstants.BOOLEAN_TRUE) && orgDAO.getOrg_email() != null
                                && !orgDAO.getOrg_email().isBlank()) {
                            List<String> orgEmails = Arrays.asList(orgDAO.getOrg_email().split("\\s*,\\s*"));
                            for (String email : orgEmails) {
                                orderProcessRequest.createOrderRegisteredRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang),
                                        context);
                            }
                        }
                    }
                } else if (owner.getFo_per_id() != null) {
                    List<SprUsersDAO> personUsers = getPersonUsers(conn, owner.getFo_per_id());
                    for (SprUsersDAO perUser : personUsers) {
                        ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), templateCode,
                                templateSubject, templateBody, context, NtisNtfRefType.RESEARCH.getCode(), msgSubject, new Date(), perUser.getUsr_id(),
                                perUser.getUsr_org_id(), null);
                    }
                    SprPersonsDAO personDAO = sprPersonsDBService.loadRecord(conn, owner.getFo_per_id());
                    if (personDAO.getPer_email() != null && personDAO.getPer_email_confirmed().equals(DbConstants.BOOLEAN_TRUE) && personDAO.getC01() != null
                            && personDAO.getC01().equals(DbConstants.BOOLEAN_TRUE)) {
                        orderProcessRequest.createOrderRegisteredRequest(conn, usrId, orderDAO.getOrd_id(), personDAO.getPer_email(),
                                Languages.getLanguageByCode(lang), context);
                    }
                }
            }
        }
    }

    private List<NtisFacilityOwnersDAO> getFacilityOwners(Connection conn, NtisOrdersDAO order) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT fo_id,
                       fo_org_id,
                       fo_per_id
                FROM ntis_facility_owners
                WHERE fo_wtf_id = ?
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
                WHERE srv_id = ?
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
                WHERE wtf_id = ?
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
                     JOIN spr_persons per ON per_id = usr_per_id AND per_id = ?
                     WHERE now() BETWEEN usr_date_from AND COALESCE(usr_date_to, now()) AND (usr_lock_date is null OR now() < usr_lock_date)
                """);
        stmt.addSelectParam(perId);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
    }
}
