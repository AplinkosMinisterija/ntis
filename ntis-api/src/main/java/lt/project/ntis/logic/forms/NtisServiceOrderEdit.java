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
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisOrderTypeConstants;
import lt.project.ntis.constants.NtisOrgStateConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisContractServicesDAO;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisResearchesDAO;
import lt.project.ntis.dao.NtisSelectedFacilitiesDAO;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.processRequests.OrderProcessRequest;
import lt.project.ntis.models.NtisNewOrderDetails;
import lt.project.ntis.models.NtisOrderDetails;
import lt.project.ntis.models.NtisOrdersRequest;
import lt.project.ntis.models.NtisServiceDescriptionDetails;
import lt.project.ntis.models.NtisServiceOrderRequest;
import lt.project.ntis.models.ResearchRequestedCriteriaModel;
import lt.project.ntis.service.NtisContractServicesDBService;
import lt.project.ntis.service.NtisContractsDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisResearchesDBService;
import lt.project.ntis.service.NtisSelectedFacilitiesDBService;
import lt.project.ntis.service.NtisServiceReqItemsDBService;
import lt.project.ntis.service.NtisServicesDBService;

/**
 * Klasė skirta formų "Užsakyti paslaugą (išvežimas)", "Užsakyti paslaugą (tech. priežiūra) biznio logikai apibrėžti
 */

@Component
public class NtisServiceOrderEdit extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbstatementManager;

    private final NtisOrdersDBService ordersDBService;

    private final NtisContractsDBService contractsDBService;

    private final NtisContractServicesDBService contractServicesDBService;

    private final SprOrganizationsDBServiceImpl organizationsDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final NtisServiceReqItemsDBService serviceReqItemsDBService;

    private final NtisServicesDBService servicesDBService;

    private final NtisSelectedFacilitiesDBService ntisSelectedFacilitiesDBService;

    private final NtisFacilityOwnersDBService facilityOwnersDBService;

    private final NtisResearchesDBService researchesDBService;

    private final SprFilesDBService filesDBService;

    private final FileStorageService fileStorageService;

    private final NtisNotificationsManager ntisNotifications;

    private final OrderProcessRequest orderProcessRequest;

    private final NtisINTSOwnerDashboardPage ntisINTSOwnerDashboardPage;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisServiceOrderEdit(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbstatementManager, NtisOrdersDBService ordersDBService,
            NtisContractsDBService contractsDBService, NtisContractServicesDBService contractServicesDBService,
            SprOrganizationsDBServiceImpl organizationsDBService, SprPersonsDBService sprPersonsDBService,
            NtisServiceReqItemsDBService serviceReqItemsDBService, NtisServicesDBService servicesDBService,
            NtisSelectedFacilitiesDBService ntisSelectedFacilitiesDBService, NtisFacilityOwnersDBService facilityOwnersDBService,
            NtisResearchesDBService researchesDBService, SprFilesDBService filesDBService, FileStorageService fileStorageService,
            NtisNotificationsManager ntisNotifications, OrderProcessRequest orderProcessRequest, NtisINTSOwnerDashboardPage ntisINTSOwnerDashboardPage,
            SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbstatementManager = dbstatementManager;
        this.ordersDBService = ordersDBService;
        this.contractsDBService = contractsDBService;
        this.contractServicesDBService = contractServicesDBService;
        this.organizationsDBService = organizationsDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.serviceReqItemsDBService = serviceReqItemsDBService;
        this.servicesDBService = servicesDBService;
        this.ntisSelectedFacilitiesDBService = ntisSelectedFacilitiesDBService;
        this.facilityOwnersDBService = facilityOwnersDBService;
        this.researchesDBService = researchesDBService;
        this.filesDBService = filesDBService;
        this.fileStorageService = fileStorageService;
        this.ntisNotifications = ntisNotifications;
        this.orderProcessRequest = orderProcessRequest;
        this.ntisINTSOwnerDashboardPage = ntisINTSOwnerDashboardPage;
        this.sprOrgUsersDBService = sprOrgUsersDBService;

    }

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_ORDER_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų užsakymas", "Paslaugų užsakymo redagavimo forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE);
    }

    /**
     * Metodas grąžins pasirinktos paslaugos ir pasirinkto nuotekų tvarkymo įrenginio duomenis naujo paslaugos užsakymo sukūrimui
     * Jeigu per parametrus perduodamas ordId, naujas užsakymas sukuriamas jau sukurto užsakymo pagrindu, prieš tai patikrinus
     * ar paslaugą vis dar galima užsakyti
     * @param conn - prisijungimas prie DB
     * @param record - informacija apie įrašą, perduodamas ID, wtf ID ir veiksmų tipas
     * @param usrId - sesijos naudotojo id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public NtisServiceOrderRequest getOrder(Connection conn, NtisOrdersRequest record, Double usrId, Double perId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceOrderEdit.ACTION_READ);
        NtisServiceOrderRequest orderRequest = new NtisServiceOrderRequest();
        if (record.getActionType() != null && record.getActionType().equalsIgnoreCase(ACTION_COPY)) {
            NtisOrdersDAO order = ordersDBService.loadRecord(conn, record.getOrd_srv_id());
            NtisServicesDAO serviceDao = servicesDBService.loadRecord(conn, order.getOrd_srv_id());
            SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) organizationsDBService.loadRecord(conn, serviceDao.getSrv_org_id());
            NtisServiceReqItemsDAO srvReqDao = serviceReqItemsDBService.loadRecordBySrvId(conn, order.getOrd_srv_id());
            if (order.getOrd_cs_id() != null) {
                NtisContractServicesDAO contractServiceDao = contractServicesDBService.loadRecord(conn, order.getOrd_cs_id());
                NtisContractsDAO contractDao = contractsDBService.loadRecordByParams(conn,
                        "cot_id = ?::int and current_date between cot_from_date and coalesce(cot_to_date, now()) and cot_state in ('SIGNED_BY_BOTH','CONFIRMED', 'VALID') ",
                        new SelectParamValue(contractServiceDao.getCs_cot_id()));
                if (contractDao == null || contractDao.getCot_id() == null) {
                    throw new SparkBusinessException(new S2Message("common.error.contractExpired", SparkMessageType.ERROR, "Contract expired"));
                }
                orderRequest.setServiceDescription(getService(conn, order.getOrd_srv_id(), lang, order.getOrd_cs_id(), orgId, perId));
                orderRequest.setOrderDetails(getOrderDetails(conn, perId, orgId, order.getOrd_id(), null, usrId));
                orderRequest.getOrderDetails().setOrd_completion_request_date(null);
                orderRequest.getOrderDetails().setOrd_prefered_date_from(null);
                orderRequest.getOrderDetails().setOrd_prefered_date_to(null);
                orderRequest.setWastewaterFacility(getFacilityDetails(conn, perId, orgId, order.getOrd_wtf_id(), lang, order.getOrd_cs_id()));
            } else if (orgDao.getOrgState() != null && orgDao.getOrgState().equals(NtisOrgStateConstants.DEREGISTERED)) {
                throw new SparkBusinessException(
                        new S2Message("common.error.deregisteredServiceProvider", SparkMessageType.ERROR, "Service provider is suspended"));
            } else if (srvReqDao != null && srvReqDao.getSri_removal_date() != null && srvReqDao.getSri_removal_date().before(new Date())) {
                throw new SparkBusinessException(
                        new S2Message("common.error.serviceDeregistered", SparkMessageType.ERROR, "Service is unlisted by the service provider"));
            } else if (!YesNo.getEnumByCode(serviceDao.getSrv_available_in_ntis_portal()).getBoolean()) {
                throw new SparkBusinessException(
                        new S2Message("common.error.notAvailableInNtis", SparkMessageType.ERROR, "Service is not available in NTIS portal"));
            } else if (serviceDao.getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
                orderRequest.setSelectedResearchTypes(getResearchRequestedCriteria(conn, order.getOrd_id(), lang));
                orderRequest.setServiceDescription(getService(conn, order.getOrd_srv_id(), lang, null, orgId, perId));
                orderRequest.setOrderDetails(getOrderDetails(conn, perId, orgId, order.getOrd_id(), null, usrId));
                orderRequest.setWastewaterFacility(getFacilityDetails(conn, perId, orgId, order.getOrd_wtf_id(), lang, null));
                orderRequest.getOrderDetails().setOrd_completion_request_date(null);
                orderRequest.getOrderDetails().setOrd_prefered_date_from(null);
                orderRequest.getOrderDetails().setOrd_prefered_date_to(null);
            } else {
                orderRequest.setServiceDescription(getService(conn, order.getOrd_srv_id(), lang, null, orgId, perId));
                orderRequest.setOrderDetails(getOrderDetails(conn, perId, orgId, order.getOrd_id(), null, usrId));
                orderRequest.setWastewaterFacility(getFacilityDetails(conn, perId, orgId, order.getOrd_wtf_id(), lang, null));
                orderRequest.getOrderDetails().setOrd_completion_request_date(null);
            }
        } else if (record.getOrd_cs_id() != null && (record.getActionType() == null || !record.getActionType().equalsIgnoreCase(ACTION_COPY))) {
            NtisContractServicesDAO contractServiceDao = contractServicesDBService.loadRecord(conn, record.getOrd_cs_id());
            NtisContractsDAO contractDao = contractsDBService.loadRecordByParams(conn,
                    "cot_id = ?::int and current_date between cot_from_date and coalesce(cot_to_date, now())",
                    new SelectParamValue(contractServiceDao.getCs_cot_id()));
            if (contractDao == null || contractDao.getCot_id() == null) {
                throw new SparkBusinessException(new S2Message("common.error.contractExpired", SparkMessageType.ERROR, "Contract expired"));
            }
            orderRequest.setServiceDescription(getService(conn, record.getOrd_srv_id(), lang, record.getOrd_cs_id(), orgId, perId));
            orderRequest.setWastewaterFacility(getFacilityDetails(conn, perId, orgId, null, lang, record.getOrd_cs_id()));
            orderRequest.setOrderDetails(getOrderDetails(conn, perId, orgId, null, record.getOrd_cs_id(), usrId));

        } else {
            NtisServiceDescriptionDetails service = getService(conn, record.getOrd_srv_id(), lang, null, orgId, perId);
            orderRequest.setServiceDescription(service);
            orderRequest.setOrderDetails(getOrderDetails(conn, perId, orgId, null, null, usrId));
            Double selectedWtf = ntisSelectedFacilitiesDBService.getSelecteFacility(conn, usrId, orgId);
            orderRequest.setWastewaterFacility(
                    getFacilityDetails(conn, perId, orgId, record.getOrd_wtf_id() != null ? record.getOrd_wtf_id() : selectedWtf, lang, null));
        }
        if (orderRequest.getServiceDescription().getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
            orderRequest.setResearchFile(getResearchFile(conn, orderRequest.getServiceDescription().getSrv_id()));
        }
        // updating selected wtf
        NtisSelectedFacilitiesDAO selectedFacility = null;
        if (orgId != null) {
            selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id = ?::int ",
                    new SelectParamValue(usrId), new SelectParamValue(orgId));
        } else {
            selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id is null ",
                    new SelectParamValue(usrId));
        }
        if (selectedFacility == null) {
            selectedFacility = ntisSelectedFacilitiesDBService.newRecord();
            selectedFacility.setFs_usr_id(usrId);
            selectedFacility.setFs_org_id(orgId);
        }
        selectedFacility.setFs_wtf_id(Utils.getDouble(orderRequest.getWastewaterFacility().getId()));
        ntisSelectedFacilitiesDBService.saveRecord(conn, selectedFacility);

        return orderRequest;
    }

    private NtisServiceDescriptionDetails getService(Connection conn, Double srvId, String lang, Double csId, Double orgId, Double perId) throws Exception {
        NtisServiceDescriptionDetails service = new NtisServiceDescriptionDetails();
        StatementAndParams stmt = new StatementAndParams();
        if (csId == null) {
            stmt.setStatement("""
                    select
                        srv.srv_id,
                        srv.srv_email,
                        srv.srv_phone_no,
                        srv.srv_description,
                        srv.srv_price_from,
                        srv.srv_price_to,
                        srv.srv_org_id,
                        srv.srv_completion_in_days_from,
                        srv.srv_completion_in_days_to,
                        coalesce(rfc.rft_display_code, srv.srv_type) as srv_name,
                        srv.srv_type,
                        org.org_name as srv_org_name,
                        org.org_address as srv_address
                      from ntis_services srv
                      left join spr_ref_codes_vw rfc on rfc.rfc_code = srv.srv_type and rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rfc.rft_lang = ?
                      inner join spr_organizations org on srv.srv_org_id = org.org_id and srv.srv_id = ?::int
                    """);
        } else {
            stmt.setStatement("""
                    select
                        srv.srv_id,
                        srv.srv_email,
                        srv.srv_phone_no,
                        srv.srv_description,
                        cs.cs_price as srv_price_from,
                        cs.cs_id,
                        srv.srv_org_id,
                        srv.srv_completion_in_days_from,
                        srv.srv_completion_in_days_to,
                        coalesce(rfc.rft_display_code, srv.srv_type) as srv_name,
                        srv.srv_type,
                        org.org_name as srv_org_name,
                        org.org_address as srv_address
                      from ntis_services srv
                      inner join ntis_contract_services cs on srv.srv_id = cs.cs_srv_id
                      inner join ntis_contracts cot on cs.cs_cot_id = cot.cot_id and cs_id = ?::int
                                                   and current_date between cot_from_date and coalesce(cot_to_date, now())
                      left join spr_ref_codes_vw rfc on rfc.rfc_code = srv.srv_type and rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rfc.rft_lang = ?
                      inner join spr_organizations org on srv.srv_org_id = org.org_id and srv.srv_id = ?::int
                    """);
            stmt.addSelectParam(csId);
            if (orgId != null) {
                stmt.addParam4WherePart("cot.cot_org_id = ?::int ", orgId);
            } else {
                stmt.addParam4WherePart("cot.cot_per_id = ?::int ", perId);
            }
        }
        stmt.addSelectParam(lang);
        stmt.addSelectParam(srvId);
        ArrayList<NtisServiceDescriptionDetails> data = (ArrayList<NtisServiceDescriptionDetails>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisServiceDescriptionDetails.class);
        if (data != null && !data.isEmpty()) {
            service = data.get(0);
            return service;
        } else {
            throw new Exception("No service description for service with id " + srvId + " found");
        }
    }

    private ArrayList<String> getResearchRequestedCriteria(Connection conn, Double ordId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchOrderPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                    rfc_code as code,
                    rfc_meaning as display,
                    case when res_id is not null
                        then 'Y'
                        else 'N'
                        end belongs
                from spr_ref_codes_vw
                left join ntis_researches on res_reserch_type = rfc_code and res_ord_id = ?::int
                where rfc_domain = 'NTIS_RESEARCH_TYPE' and
                      rft_lang = ?
                                """);
        stmt.addSelectParam(ordId);
        stmt.addSelectParam(lang);
        stmt.setWhereExists(true);
        ArrayList<ResearchRequestedCriteriaModel> data = (ArrayList<ResearchRequestedCriteriaModel>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                ResearchRequestedCriteriaModel.class);
        ArrayList<String> criteria = new ArrayList<String>();
        for (ResearchRequestedCriteriaModel crit : data) {
            if (crit.getBelongs().equals(YesNo.Y.getCode())) {
                criteria.add(crit.getCode());
            }
        }
        return criteria;
    }

    private NtisOrderDetails getOrderDetails(Connection conn, Double perId, Double orgId, Double ordId, Double csId, Double usrId) throws Exception {
        NtisOrderDetails orderDetails = new NtisOrderDetails();
        StatementAndParams stmt = new StatementAndParams();
        if (ordId != null) {
            stmt.setStatement("SELECT " + //
                    "COALESCE(ORG.ORG_NAME, SP.PER_NAME || ' '|| SP.PER_SURNAME) AS ORD_NAME, " + //
                    "CASE WHEN ORD.ORD_EMAIL IS NOT NULL " + //
                    "   THEN ORD.ORD_EMAIL ELSE " + //
                    "   COALESCE(ORG.ORG_EMAIL, SP.PER_EMAIL) END ORD_EMAIL, " + //
                    "CASE WHEN ORD.ORD_PHONE_NUMBER IS NOT NULL " + //
                    "   THEN ORD.ORD_PHONE_NUMBER ELSE " + //
                    "   COALESCE (ORG.ORG_PHONE, SP.PER_PHONE_NUMBER) END ORD_PHONE_NUMBER, " + //
                    "ORD.ORD_COMPLETION_REQUEST_DATE, " + //
                    "ORD.ORD_ADDITIONAL_DESCRIPTION, " + //
                    "ORD.ORD_CS_ID " + //
                    "FROM NTIS.NTIS_ORDERS ORD " + //
                    "LEFT JOIN SPARK.SPR_PERSONS SP ON ORD.ORD_PER_ID = SP.PER_ID " + //
                    "LEFT JOIN SPARK.SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = ORD.ORD_ORG_ID " + //
                    "LEFT JOIN SPR_ORG_USERS ON ORG.ORG_ID = OU_ORG_ID ");
            stmt.addParam4WherePart("ord.ord_id = ?::int ", ordId);
            if (orgId != null) {
                stmt.addParam4WherePart("ORD.ORD_ORG_ID = ?::int ", orgId);
                stmt.addParam4WherePart("OU_USR_ID = ?::int and current_date between ou_date_from and coalesce(ou_date_to, now()) ", usrId);
            } else {
                stmt.addParam4WherePart("ORD.ORD_PER_ID = ?::int ", perId);
            }
        } else if (csId != null) {
            stmt.setStatement("""
                    select
                    coalesce(org_name, per_name || ' ' || per_surname) as ord_name,
                    cot_client_phone_no as ord_phone_number,
                    cot_client_email as ord_email,
                    cs_id as ord_cs_id
                    from ntis_contracts
                    inner join ntis_contract_services on cot_id = cs_cot_id and cs_id = ?::int
                    left join spr_organizations on cot_org_id = org_id
                    left join spr_org_users on org_id = ou_org_id
                    left join spr_persons on cot_per_id = per_id
                    """);
            stmt.addSelectParam(csId);
            if (orgId != null) {
                stmt.addParam4WherePart("cot_org_id = ?::int ", orgId);
                stmt.addParam4WherePart("ou_usr_id = ?::int and current_date between ou_date_from and coalesce(ou_date_to, now()) ", usrId);
            } else {
                stmt.addParam4WherePart("cot_per_id = ?::int ", perId);
            }
        } else {
            if (orgId != null) {
                stmt.setStatement("SELECT " + //
                        "ORG.ORG_NAME as ORD_NAME, " + //
                        "ORG.ORG_EMAIL as ORD_EMAIL, " + //
                        "ORG.ORG_PHONE as ORD_PHONE_NUMBER " + //
                        "FROM SPARK.SPR_ORGANIZATIONS ORG " + //
                        "INNER JOIN SPR_ORG_USERS ON OU_ORG_ID = ORG_ID AND OU_USR_ID = ?::int and current_date between ou_date_from and coalesce(ou_date_to, now()) "
                        + //
                        "WHERE ORG.ORG_ID = ?::int ");
                stmt.addSelectParam(usrId);
                stmt.addSelectParam(orgId);
            } else {
                stmt.setStatement("SELECT " + //
                        "SP.PER_NAME || ' '|| SP.PER_SURNAME AS ORD_NAME, " + //
                        "SP.PER_EMAIL as  ORD_EMAIL, " + //
                        "SP.PER_PHONE_NUMBER as ORD_PHONE_NUMBER " + //
                        "FROM SPARK.SPR_PERSONS SP " + //
                        "WHERE SP.PER_ID = ?::int ");
                stmt.addSelectParam(perId);
            }
        }

        ArrayList<NtisNewOrderDetails> data = (ArrayList<NtisNewOrderDetails>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisNewOrderDetails.class);
        if (data != null && !data.isEmpty()) {
            orderDetails.setOrd_name(data.get(0).getOrd_name());
            orderDetails.setOrd_email(data.get(0).getOrd_email());
            orderDetails.setOrd_phone_number(data.get(0).getOrd_phone_number());
            if (ordId != null) {
                orderDetails.setOrd_completion_request_date(data.get(0).getOrd_completion_request_date());
                orderDetails.setOrd_additional_description(data.get(0).getOrd_additional_description());
            }
        }
        return orderDetails;
    }

    private NtisWtfInfo getFacilityDetails(Connection conn, Double perId, Double orgId, Double wtfId, String lang, Double csId) throws Exception {
        NtisWtfInfo facility = new NtisWtfInfo();
        StatementAndParams stmt = new StatementAndParams();
        if (wtfId != null && csId == null) {
            stmt.setStatement(
                    """
                                   select wtf.wtf_id as id,
                                   coalesce(wav.full_address_text, wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude) as address,
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
                            where wtf.wtf_id = ?::int
                            """
                            .formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
            stmt.setWhereExists(true);
            stmt.addSelectParam(lang);
            stmt.addSelectParam(wtfId);
        } else {
            stmt.setStatement(
                    """
                                   select wtf.wtf_id as id,
                                   wav.full_address_text as address,
                                   coalesce(typ.rft_display_code, typ.rfc_meaning) as type,
                                   wtf.wtf_technical_passport_id as technicalPassport,
                                   coalesce(mnf.rft_display_code, mnf.rfc_meaning) as manufacturer,
                                   coalesce(mdl.rft_display_code, mdl.rfc_meaning) as model,
                                   to_char(wtf.wtf_installation_date, '%s') as installationDate,
                                   wtf.wtf_type as typeClsf,
                                   wtf.wtf_distance as distance,
                                   coalesce(cap.rft_display_code, cap.rfc_meaning) capacity
                            from ntis_wastewater_treatment_faci wtf
                            inner join ntis_contracts cot on cot.cot_wtf_id = wtf.wtf_id
                            inner join ntis_contract_services cs on cs.cs_cot_id = cot.cot_id and cs.cs_id = ?::int
                            left join ntis_address_vw wav on wtf.wtf_ad_id = wav.address_id
                            left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                            left join spr_ref_codes_vw mnf on mnf.rfc_code = wtf.wtf_manufacturer and mnf.rfc_domain = 'NTIS_FACIL_MANUFA' and mnf.rft_lang = typ.rft_lang
                            left join spr_ref_codes_vw mdl on mdl.rfc_code = wtf.wtf_model and mdl.rfc_domain = 'NTIS_FACIL_MODEL' and mdl.rft_lang = typ.rft_lang
                            left join spr_ref_codes_vw cap on cap.rfc_code = wtf.wtf_capacity and cap.rfc_domain = 'NTIS_FACIL_CAPACITY' and cap.rft_lang = typ.rft_lang
                            """
                            .formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
            stmt.addSelectParam(csId);
            stmt.addSelectParam(lang);
            if (orgId != null) {
                stmt.addParam4WherePart("cot.cot_org_id = ?::int ", orgId);
            } else {
                stmt.addParam4WherePart("cot.cot_per_id = ?::int ", perId);
            }
        }
        ArrayList<NtisWtfInfo> data = (ArrayList<NtisWtfInfo>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWtfInfo.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
            return facility;
        } else {
            throw new Exception("No wastewater facility with id " + wtfId + " found");
        }
    }

    private SprFile getResearchFile(Connection conn, Double srvId) throws Exception {
        SprFile file = new SprFile();
        NtisServicesDAO researchService = servicesDBService.loadRecord(conn, srvId);
        if (researchService != null && researchService.getSrv_lab_instr_fil_id() != null) {
            SprFilesDAO fileDAO = filesDBService.loadRecord(conn, researchService.getSrv_lab_instr_fil_id());
            if (fileDAO != null) {
                file.setFil_content_type(fileDAO.getFil_content_type());
                file.setFil_name(fileDAO.getFil_name());
                file.setFil_size(fileDAO.getFil_size());
                file.setFil_status(fileDAO.getFil_status());
                file.setFilKey4Encript(fileStorageService, fileDAO.getFil_key());
            }

        }
        return file;
    }

    /**
     * Pagal nurodytą NtisServiceOrderRequest objektą, metodas į duomenų bazę išsaugos NtisOrdersDAO objektą 
     * prieš tai patikrinęs ar naudotojas turi teisę išsaugoti šiuos duomenis
     * @param order - NtisServiceOrderRequest objektas
     * @param usrId - sesijos naudotojo id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @return NtisServiceOrderRequest objektas
     * @throws Exception
     */
    public NtisServiceOrderRequest saveOrder(Connection conn, NtisServiceOrderRequest order, Double usrId, Double perId, Double orgId)
            throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceOrderEdit.ACTION_CREATE);
        boolean belongs = true;
        if (orgId != null) {
            belongs = sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId);
        }
        if (belongs) {
            NtisOrdersDAO orderDAO = ordersDBService.newRecord();
            orderDAO.setOrd_created(new Date());
            orderDAO.setOrd_state(NtisOrderStatus.ORD_STS_SUBMITTED.getCode());
            if (order.getServiceDescription().getSrv_type().equalsIgnoreCase(NtisServiceItemType.PRIEZIURA.getCode())
                    || order.getServiceDescription().getSrv_type().equalsIgnoreCase(NtisServiceItemType.VEZIMAS.getCode())) {
                orderDAO.setOrd_type(NtisOrderTypeConstants.SERVICE);
                orderDAO.setOrd_completion_request_date(Utils.getDate(order.getOrderDetails().getOrd_completion_request_date()));
            } else {
                orderDAO.setOrd_type(NtisOrderTypeConstants.ANALYSIS);
                orderDAO.setOrd_prefered_date_from(Utils.getDate(order.getOrderDetails().getOrd_prefered_date_from()));
                orderDAO.setOrd_prefered_date_to(Utils.getDate(order.getOrderDetails().getOrd_prefered_date_to()));
                orderDAO.setOrd_completion_request_date(Utils.getDate(order.getOrderDetails().getOrd_prefered_date_from()));
            }
            orderDAO.setOrd_email(order.getOrderDetails().getOrd_email());
            orderDAO.setOrd_phone_number(order.getOrderDetails().getOrd_phone_number());
            orderDAO.setOrd_additional_description(order.getOrderDetails().getOrd_additional_description());
            orderDAO.setOrd_usr_id(usrId);
            if (orgId != null) {
                orderDAO.setOrd_org_id(orgId);
            } else {
                orderDAO.setOrd_per_id(perId);
            }
            orderDAO.setOrd_srv_id(order.getServiceDescription().getSrv_id());
            orderDAO.setOrd_wtf_id(Utils.getDouble(order.getWastewaterFacility().getId()));
            orderDAO.setOrd_created_in_ntis_portal(YesNo.Y.getCode());
            orderDAO.setOrd_cs_id(order.getServiceDescription().getCs_id());

            NtisOrdersDAO savedOrder = ordersDBService.saveRecord(conn, orderDAO);
            this.checkIsFacilityOwner(conn, savedOrder.getOrd_wtf_id(), usrId, perId, orgId);
            if (order.getServiceDescription().getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
                if (order.getSelectedResearchTypes().size() == 0) {
                    throw new SparkBusinessException(new S2Message("common.error.noCriteriaSelected", SparkMessageType.ERROR, "No criteria selected"));

                } else {
                    for (String orderedResearchType : order.getSelectedResearchTypes()) {
                        NtisResearchesDAO orderedResearch = researchesDBService.newRecord();
                        orderedResearch.setRes_ord_id(savedOrder.getOrd_id());
                        orderedResearch.setRes_reserch_type(orderedResearchType);
                        researchesDBService.saveRecord(conn, orderedResearch);
                    }
                }
            }
            NtisServicesDAO serviceDao = servicesDBService.loadRecord(conn, savedOrder.getOrd_srv_id());
            facilityOwnersDBService.manageWtfOwners(conn, savedOrder.getOrd_wtf_id(), serviceDao.getSrv_org_id(), null, null, new Date(), null,
                    NtisFacilityOwnerType.SERVICE_PROVIDER);

            NtisServiceOrderRequest result = new NtisServiceOrderRequest();
            result.setServiceDescription(order.getServiceDescription());
            result.setWastewaterFacility(order.getWastewaterFacility());
            result.setOrderDetails(order.getOrderDetails());
            result.getOrderDetails().setOrd_id(savedOrder.getOrd_id());
            return result;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    private void checkIsFacilityOwner(Connection conn, Double wtfId, Double usrId, Double perId, Double orgId) throws Exception {
        Boolean isOwner;
        if (orgId != null) {
            isOwner = facilityOwnersDBService.isOrganizationAnOwnerOfFacility(conn, orgId, wtfId);
        } else {
            isOwner = facilityOwnersDBService.isPersonAnOwnerOfFacility(conn, perId, wtfId);
        }

        if (!isOwner) {
            NtisFacilityOwnersDAO daoObject = new NtisFacilityOwnersDAO();
            daoObject.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
            daoObject.setFo_date_from(new Date());
            daoObject.setFo_org_id(orgId);
            daoObject.setFo_per_id(perId);
            daoObject.setFo_wtf_id(wtfId);
            facilityOwnersDBService.insertRecord(conn, daoObject);

            ntisINTSOwnerDashboardPage.updateSelectedWtf(conn, usrId, orgId, wtfId);
        }
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui pagal pateiktą užsakymo id
     * @param conn - prisijungimas prie DB
     * @param orderId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param perId - sesijos asmens id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double orderId, Double usrId, Double orgId, Double perId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        Locale localeLT = new Locale("lt", "LT");
        SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy 'm.' MMMMM dd 'd.'", localeLT);

        NtisOrdersDAO orderDAO = new NtisOrdersDAO();
        if (orgId != null) {
            orderDAO = ordersDBService.loadRecordByIdAndOrgId(conn, orgId, orderId);
        } else if (perId != null) {
            orderDAO = ordersDBService.loadRecordByIdAndPerId(conn, perId, orderId);
        }
        SprOrganizationsDAO serviceProvider = organizationsDBService.loadRecord(conn,
                servicesDBService.loadRecord(conn, orderDAO.getOrd_srv_id()).getSrv_org_id());
        Map<String, String> orderService = getOrderService(conn, orderDAO.getOrd_srv_id(), lang);

        String clientName = "";
        if (orderDAO.getOrd_org_id() != null) {
            clientName = organizationsDBService.loadRecord(conn, orderDAO.getOrd_org_id()).getOrg_name();
        } else if (orderDAO.getOrd_per_id() != null) {
            SprPersonsDAO clientPer = sprPersonsDBService.loadRecord(conn, orderDAO.getOrd_per_id());
            clientName = clientPer.getPer_name() + " " + clientPer.getPer_surname();
        }

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("serviceType", orderService.get("rfc_meaning"));
        context.put("orderId", orderDAO.getOrd_id().intValue());
        context.put("orderDate", dateFormatLT.format(orderDAO.getOrd_created()));
        context.put("clientName", clientName);
        if (orderService.get("srv_type").equals(NtisServiceItemType.TYRIMAI.getCode())) {
            context.put("orderUrl", "research/sp-research-list/research-order/");
        } else if (orderService.get("srv_type").equals(NtisServiceItemType.PRIEZIURA.getCode())) {
            context.put("orderUrl", "tech-support/tech-orders/service-order/");
        } else if (orderService.get("srv_type").equals(NtisServiceItemType.VEZIMAS.getCode())) {
            context.put("orderUrl", "tech-support/disposal-orders/service-order/");
        }

        if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_SUBMITTED.getCode())) {
            // notifications
            String roleCodes = """
                    '%s', '%s'
                    """.formatted(NtisRolesConstants.PASL_ADMIN,
                    orderService.get("srv_type").equals(NtisServiceItemType.VEZIMAS.getCode()) ? NtisRolesConstants.CAR_SPECIALIST
                            : orderService.get("srv_type").equals(NtisServiceItemType.PRIEZIURA.getCode()) ? NtisRolesConstants.TECH_SPECIALIST
                                    : NtisRolesConstants.LAB_SPECIALIST);
            List<SprUsersDAO> orgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, serviceProvider.getOrg_id(), roleCodes);
            for (SprUsersDAO orgUser : orgUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), "NTIS_ORD_STS_NOTIF",
                        "ORD_STS_SUBMIT_SUBJECT", "ORD_STS_SUBMIT_BODY", context, NtisNtfRefType.ORDER.getCode(),
                        NtisMessageSubject.MSG_SBJ_ORDER_SUBMITTED.getCode(), new Date(), orgUser.getUsr_id(), serviceProvider.getOrg_id(), null);
            }
            // emails
            if (serviceProvider.getC02() != null && serviceProvider.getC02().equals(DbConstants.BOOLEAN_TRUE) && serviceProvider.getOrg_email() != null
                    && !serviceProvider.getOrg_email().isBlank()) {
                List<String> orgEmails = Arrays.asList(serviceProvider.getOrg_email().split("\\s*,\\s*"));
                for (String email : orgEmails) {
                    orderProcessRequest.createOrderSubmittedRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang), context);
                }
            }
        }
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
}
