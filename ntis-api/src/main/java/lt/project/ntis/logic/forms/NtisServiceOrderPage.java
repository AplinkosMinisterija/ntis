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
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisReviewsDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.processRequests.OrderProcessRequest;
import lt.project.ntis.models.NtisDeliveryDetailsForOrder;
import lt.project.ntis.models.NtisOrderCarSelection;
import lt.project.ntis.models.NtisOrderDetails;
import lt.project.ntis.models.NtisServiceDescriptionDetails;
import lt.project.ntis.models.NtisServiceOrderRequest;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisReviewsDBService;
import lt.project.ntis.service.NtisServicesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formų 
 * "Peržiūrėti gautą paslaugos užsakymą (išvežimas)",
 * "Peržiūrėti gautą paslaugos užsakymą (tech. priež.)",
 * "Peržiūrėti pateiktą paslaugos užsakymą (išvežimas)",
 * "Peržiūrėti pateiktą paslaugos užsakymą (tech. priež.)"
 *  biznio logikai apibrėžti
 */

@Component
public class NtisServiceOrderPage extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisOrdersDBService ordersDBService;

    private final NtisOrderCompletedWorksDBService completedOrderDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final NtisNotificationsManager ntisNotifications;

    private final OrderProcessRequest orderProcessRequest;

    private final SprOrganizationsDBService organizationsDBService;

    private final NtisServicesDBService servicesDBService;

    private final NtisCommonMethods ntisCommonMethods;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    private final NtisReviewsDBService ntisReviewsDBService;

    public NtisServiceOrderPage(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, NtisOrdersDBService ordersDBService,
            NtisOrderCompletedWorksDBService completedOrderDBService, SprPersonsDBService sprPersonsDBService, NtisNotificationsManager ntisNotifications,
            OrderProcessRequest orderProcessRequest, SprOrganizationsDBService organizationsDBService, NtisServicesDBService servicesDBService,
            NtisCommonMethods ntisCommonMethods, SprOrgUsersDBServiceImpl sprOrgUsersDBService,
            NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService, NtisReviewsDBService ntisReviewsDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ordersDBService = ordersDBService;
        this.completedOrderDBService = completedOrderDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.ntisNotifications = ntisNotifications;
        this.orderProcessRequest = orderProcessRequest;
        this.organizationsDBService = organizationsDBService;
        this.servicesDBService = servicesDBService;
        this.ntisCommonMethods = ntisCommonMethods;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisWastewaterTreatmentFaciDBService = ntisWastewaterTreatmentFaciDBService;
        this.ntisReviewsDBService = ntisReviewsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_ORDER_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugos užsakymo peržiūra", "Paslaugos užsakymo peržiūros forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE);
        this.addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS);
        this.addFormAction(NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC,
                NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS);
    }

    /**
     * Metodas grąžins užsakymo duomenis
     * @param conn - prisijungimas prie DB
     * @param ordId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public NtisServiceOrderRequest getOrder(Connection conn, String ordId, Double perId, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceOrderPage.ACTION_READ);
        NtisServiceOrderRequest orderRequest = new NtisServiceOrderRequest();
        NtisOrderDetails order = getOrderDetails(conn, ordId, perId, orgId, lang, usrId);
        if (order.getOrd_id() == null) {
            throw new SparkBusinessException(
                    new S2Message("common.error.noOrderFound", SparkMessageType.ERROR, "User does not have an order with this order ID"));
        } else {
            NtisWtfInfo facility = getFacilityDetails(conn, order.getOrd_wtf_id(), perId, orgId, lang);
            NtisServiceDescriptionDetails service = getService(conn, order.getOrd_srv_id(), lang, order.getOrd_cs_id(), orgId, perId);

            ArrayList<NtisOrderCarSelection> carList = new ArrayList<NtisOrderCarSelection>(getOrganizationCars(conn, service.getSrv_org_id()));
            ArrayList<NtisDeliveryDetailsForOrder> deliveryDetails = new ArrayList<NtisDeliveryDetailsForOrder>(
                    getDeliveryDetails(conn, order.getOrd_id(), lang));
            orderRequest.setServiceDescription(service);
            orderRequest.setWastewaterFacility(facility);
            orderRequest.setOrderDetails(order);
            orderRequest.setDeliveryDetails(deliveryDetails);
            orderRequest.setCarSelection(carList);
            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                NtisReviewsDAO reviewDAO = this.getReviewInfo(conn, order.getOrd_id(), usrId);
                if (reviewDAO != null) {
                    orderRequest.setRevId(reviewDAO.getRev_id());
                    orderRequest.setRevScore(reviewDAO.getRev_score());
                    orderRequest.setRevComment(reviewDAO.getRev_comment());
                }
            }
            return orderRequest;
        }
    }

    private NtisReviewsDAO getReviewInfo(Connection conn, Double ordId, Double usrId) throws Exception {
        NtisReviewsDAO reviewDAO = this.ntisReviewsDBService.loadRecordByParams(conn, "rev_ord_id = ?::int and rev_usr_id = ?::int ",
                new SelectParamValue(ordId), new SelectParamValue(usrId));
        if (reviewDAO != null && reviewDAO.getRev_id() != null) {
            return reviewDAO;
        } else {
            return null;
        }
    }

    private NtisOrderDetails getOrderDetails(Connection conn, String ordId, Double perId, Double orgId, String lang, Double usrId) throws Exception {
        NtisOrderDetails order = new NtisOrderDetails();
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT
                    ORD.ORD_ID,
                    ord.ord_per_id,
                    COALESCE(ORG.ORG_NAME, SP.PER_NAME || ' '|| SP.PER_SURNAME) AS ORD_NAME,
                    ORD.ORD_EMAIL,
                    ORD.ORD_PHONE_NUMBER,
                    ORD.ORD_COMPLETION_REQUEST_DATE,
                    ORD.ORD_ADDITIONAL_DESCRIPTION,
                    ORD.ORD_REJECTION_DATE,
                    ORD.ORD_USR_ID,
                    ORD.ORD_WTF_ID,
                    ORD.ORD_SRV_ID,
                    ORD.ORD_ORG_ID,
                    ORD.ORD_STATE AS ORD_STATE_CLSF,
                    ORD.ORD_REJECTION_REASON,
                    ORD.ORD_PLANNED_WORKS_DESCRIPTION,
                    ORD.ORD_COMPLETION_ESTIMATE_DATE,
                    ORD.ORD_CREATED,
                    OCW.OCW_ID,
                    CRS.CR_MODEL || ' (' || CRS.CR_REG_NO || ')' AS CAR_NAME ,
                    OCW.OCW_COMPLETED_WORKS_DESCRIPTION,
                    OCW.OCW_COMPLETED_DATE,
                    OCW.OCW_CR_ID,
                    OCW.OCW_DISCHARGED_SLUDGE_AMOUNT,
                    ORD.ORD_CS_ID,
                    COALESCE(RFC.RFT_DISPLAY_CODE, RFC.RFC_MEANING) AS ORD_STATE
                FROM NTIS.NTIS_ORDERS ORD
                INNER JOIN NTIS.NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID
                LEFT JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = ORD.ORD_STATE AND RFC.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RFC.RFT_LANG = ?
                LEFT JOIN SPARK.SPR_PERSONS SP ON ORD.ORD_PER_ID = SP.PER_ID
                LEFT JOIN SPARK.SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = ORD.ORD_ORG_ID
                LEFT JOIN NTIS.NTIS_ORDER_COMPLETED_WORKS OCW ON ORD.ORD_ID = OCW.OCW_ORD_ID
                LEFT JOIN NTIS.NTIS_CARS CRS ON CRS.CR_ID = OCW.OCW_CR_ID
                """);
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("ORD.ord_id = ?::int ", Utils.getDouble(ordId));
        // depending on whether the user is ints owner or service provider, different parameter is added to select statement
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            // matching user id if the user is ints owner
            this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        } else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            // matching organization id if the user is service provider
            stmt.addParam4WherePart("SRV_ORG_ID = ?::int", orgId);
        }
        ArrayList<NtisOrderDetails> data = (ArrayList<NtisOrderDetails>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisOrderDetails.class);
        if (data != null && !data.isEmpty()) {
            order = data.get(0);
        }
        return order;
    }

    private ArrayList<NtisDeliveryDetailsForOrder> getDeliveryDetails(Connection conn, Double ordId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT " + //
                "WD.WD_ID, " + //
                "COALESCE(RFC.RFT_DISPLAY_CODE, RFC.RFC_MEANING) AS WD_STATE_MEANING, " + //
                "WD.WD_STATE, " + //
                "WD.WD_REJECTION_REASON, " + //
                "ORG.ORG_NAME, " + //
                "WTO.WTO_NAME || ', ' || WTO.WTO_ADDRESS AS WTO_NAME, " + //
                "TO_CHAR(WD.WD_DELIVERY_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS WD_DELIVERY_DATE " + //
                "FROM NTIS.NTIS_WASTEWATER_DELIVERIES WD " + //
                "INNER JOIN NTIS.NTIS_DELIVERY_FACILITIES DF ON DF.DF_WD_ID = WD.WD_ID AND DF.DF_ORD_ID = ?::int " + //
                "INNER JOIN NTIS.NTIS_WASTEWATER_TREATMENT_ORG WTO ON WTO.WTO_ID = WD.WD_WTO_ID " + //
                "INNER JOIN SPARK.SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = WTO.WTO_ORG_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = WD.WD_STATE AND RFC.RFC_DOMAIN = 'SEWAGE_DELIV_STATUS' AND RFC.RFT_LANG = ? ");

        stmt.addSelectParam(ordId);
        stmt.addSelectParam(lang);

        ArrayList<NtisDeliveryDetailsForOrder> data = (ArrayList<NtisDeliveryDetailsForOrder>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisDeliveryDetailsForOrder.class);
        return data;
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
                      inner join ntis_contracts cot on cs.cs_cot_id = cot.cot_id and cs.cs_id = ?::int
                      left join spr_ref_codes_vw rfc on rfc.rfc_code = srv.srv_type and rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rfc.rft_lang = ?
                      inner join spr_organizations org on srv.srv_org_id = org.org_id and srv.srv_id = ?::int
                    """);
            stmt.addSelectParam(csId);
            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                if (orgId != null) {
                    stmt.addParam4WherePart("cot.cot_org_id = ?::int ", orgId);
                } else {
                    stmt.addParam4WherePart("cot.cot_per_id = ?::int ", perId);
                }
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

    private NtisWtfInfo getFacilityDetails(Connection conn, Double wtfId, Double perId, Double orgId, String lang) throws Exception {
        NtisWtfInfo facility = new NtisWtfInfo();
        StatementAndParams stmt = new StatementAndParams("""
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
                from  ntis_wastewater_treatment_faci wtf
                left join ntis_facility_owners fo on fo.fo_wtf_id = wtf.wtf_id
                left join ntis_address_vw wav on wtf.wtf_ad_id = wav.address_id
                left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                left join spr_ref_codes_vw mnf on mnf.rfc_code = wtf.wtf_manufacturer and mnf.rfc_domain = 'NTIS_FACIL_MANUFA' and mnf.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw mdl on mdl.rfc_code = wtf.wtf_model and mdl.rfc_domain = 'NTIS_FACIL_MODEL' and mdl.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw cap on cap.rfc_code = wtf.wtf_capacity and cap.rfc_domain = 'NTIS_FACIL_CAPACITY' and cap.rft_lang = typ.rft_lang
                where wtf.wtf_id = ?::int
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.setWhereExists(true);
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            if (orgId != null) {
                stmt.addParam4WherePart("FO.FO_ORG_ID = ? ", orgId);
            } else {
                stmt.addParam4WherePart("FO.FO_PER_ID = ? ", perId);
            }
        }
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

    /**
     * Pagal nurodytą NtisOrderDetails objektą, metodas į duomenų bazę išsaugos NtisOrdersDAO 
     * ir NtisOrderCompletedWorksDAO (jei užsakymas įvykdytas) objektus prieš tai patikrinęs ar naudotojas turi teisę išsaugoti šiuos duomenis
     * @param order - NtisOrderDetails objektas
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @return NtisOrderDetails objektas
     * @throws Exception
     */
    public NtisOrderDetails updateOrder(Connection conn, NtisOrderDetails order, Double perId, Double orgId, String lang, Double usrId)
            throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceOrderPage.ACTION_UPDATE);
        NtisOrdersDAO orderDAO = null;
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            orderDAO = ordersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, order.getOrd_id());
        } else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            if (orgId != null) {
                orderDAO = ordersDBService.loadRecordByIdAndOrgId(conn, orgId, order.getOrd_id());
            } else {
                orderDAO = ordersDBService.loadRecordByIdAndPerId(conn, perId, order.getOrd_id());
            }
        }
        if (orderDAO != null) {
            orderDAO.setOrd_state(order.getOrd_state_clsf());
            orderDAO.setOrd_planned_works_description(order.getOrd_planned_works_description());
            orderDAO.setOrd_completion_estimate_date(order.getOrd_completion_estimate_date());
            orderDAO.setOrd_rejection_reason(order.getOrd_rejection_reason());
            orderDAO.setOrd_rejection_date(new Date());
            if (order.getOcw_completed_date() != null && order.getOcw_discharged_sludge_amount() != null) {
                orderDAO.setOrd_removed_sewage_date(order.getOcw_completed_date());
            }
            ordersDBService.saveRecord(conn, orderDAO);
            if (order.getOcw_completed_date() != null) {
                NtisOrderCompletedWorksDAO completed = completedOrderDBService.newRecord();
                if (order.getOcw_id() != null && order.getOcw_id() != 0) {
                    completed.setOcw_id(order.getOcw_id());
                }
                completed.setOcw_completed_date(order.getOcw_completed_date());
                completed.setOcw_completed_works_description(order.getOcw_completed_works_description());
                completed.setOcw_ord_id(order.getOrd_id());
                completed.setOcw_discharged_sludge_amount(order.getOcw_discharged_sludge_amount());
                if (order.getOcw_cr_id() != null && order.getOcw_cr_id() != 0) {
                    completed.setOcw_cr_id(order.getOcw_cr_id());
                }
                completedOrderDBService.saveRecord(conn, completed);
            }
            order = getOrderDetails(conn, order.getOrd_id().toString(), perId, orgId, lang, usrId);
            NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, orderDAO.getOrd_wtf_id());
            if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode())
                    && orderDAO.getOrd_state().equalsIgnoreCase(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
                ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
            }
            return order;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    private ArrayList<NtisOrderCarSelection> getOrganizationCars(Connection conn, Double orgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT CR_ID, " + //
                "CR_MODEL || ' ' || CR_REG_NO  AS CR_NAME " + //
                "FROM NTIS.NTIS_CARS " + //
                "WHERE date_trunc('day',CR_DATE_FROM) < now() AND date_trunc('day', CR_DATE_TO) > now() OR CR_DATE_TO IS NULL");
        stmt.setWhereExists(true);
        stmt.addParam4WherePart("cr_org_id = ?::int ", orgId);
        ArrayList<NtisOrderCarSelection> data = (ArrayList<NtisOrderCarSelection>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisOrderCarSelection.class);
        return data;
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui arba INTS savininkui, pagal pateiktą užsakymo id
     * @param conn - prisijungimas prie DB
     * @param orderId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param perId - sesijos asmens id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, RecordIdentifier orderId, Double usrId, Double orgId, Double perId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        Locale localeLT = new Locale("lt", "LT");
        SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy 'm.' MMMMM dd 'd.'", localeLT);

        // order info
        NtisOrdersDAO orderDAO = ordersDBService.loadRecord(conn, orderId.getIdAsDouble());
        if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CANCELLED.getCode())) {
            // load by order org_id or per_id (client info)
            orderDAO = orgId != null ? ordersDBService.loadRecordByIdAndOrgId(conn, orgId, orderId.getIdAsDouble())
                    : ordersDBService.loadRecordByIdAndPerId(conn, perId, orderId.getIdAsDouble());
        } else {
            // load by order service org_id (service provider info)
            orderDAO = ordersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, orderId.getIdAsDouble());
        }
        SprOrganizationsDAO serviceProvider = organizationsDBService.loadRecord(conn,
                servicesDBService.loadRecord(conn, orderDAO.getOrd_srv_id()).getSrv_org_id());
        Map<String, String> orderService = getOrderService(conn, orderDAO.getOrd_srv_id(), lang);

        // client info
        String clientName = "";
        List<String> clientEmails = new ArrayList<>();
        if (orderDAO.getOrd_org_id() != null) {
            SprOrganizationsDAO clientOrg = organizationsDBService.loadRecord(conn, orderDAO.getOrd_org_id());
            clientName = clientOrg.getOrg_name();
            if (orderDAO.getOrd_email() != null) {
                clientEmails.add(orderDAO.getOrd_email());
            } else if (clientOrg.getC02() != null && clientOrg.getC02().equals(YesNo.Y.getCode()) && clientOrg.getOrg_email() != null) {
                clientEmails = Arrays.asList(clientOrg.getOrg_email().split("\\s*,\\s*"));
            }
        } else if (orderDAO.getOrd_per_id() != null) {
            SprPersonsDAO clientPer = sprPersonsDBService.loadRecord(conn, orderDAO.getOrd_per_id());
            clientName = clientPer.getPer_name() + " " + clientPer.getPer_surname();
            if (orderDAO.getOrd_email() != null && !orderDAO.getOrd_email().isBlank()) {
                clientEmails.add(orderDAO.getOrd_email());
            } else if (clientPer.getC01() != null && clientPer.getC01().equals(DbConstants.BOOLEAN_TRUE)) {
                clientEmails.add(clientPer.getPer_email());
            }
        }

        // service provider info
        String roleCodes = """
                '%s', '%s'
                """.formatted(NtisRolesConstants.PASL_ADMIN,
                orderService.get("srv_type").equals(NtisServiceItemType.VEZIMAS.getCode()) ? NtisRolesConstants.CAR_SPECIALIST
                        : NtisRolesConstants.TECH_SPECIALIST);
        List<SprUsersDAO> orgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, serviceProvider.getOrg_id(), roleCodes);
        List<String> orgEmails = new ArrayList<>();
        if (serviceProvider.getC02() != null && serviceProvider.getC02().equals(YesNo.Y.getCode())
                && serviceProvider.getOrg_email() != null & !serviceProvider.getOrg_email().isBlank()) {
            orgEmails = Arrays.asList(serviceProvider.getOrg_email().split("\\s*,\\s*"));
        }

        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("serviceType", orderService.get("rfc_meaning"));
        context.put("orderId", orderDAO.getOrd_id().intValue());
        if (orderDAO.getOrd_rejection_date() != null) {
            context.put("orderCancelDate", dateFormatLT.format(orderDAO.getOrd_rejection_date()));
        }
        context.put("clientName", clientName);
        if (orderService.get("srv_type").equals(NtisServiceItemType.PRIEZIURA.getCode())) {
            if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CANCELLED.getCode())) {
                context.put("orderUrl", "tech-support/tech-orders/service-order/");
            } else {
                context.put("orderUrl", "tech-support/owner-tech-orders/service-order/");
            }
        } else if (orderService.get("srv_type").equals(NtisServiceItemType.VEZIMAS.getCode())) {
            if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CANCELLED.getCode())) {
                context.put("orderUrl", "tech-support/disposal-orders/service-order/");
            } else {
                context.put("orderUrl", "tech-support/owner-disposal-orders/service-order/");
            }
        }

        // send notifications and emails
        if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CANCELLED.getCode())) {
            for (SprUsersDAO orgUser : orgUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), "NTIS_ORD_STS_NOTIF",
                        "ORD_STS_CANCEL_SUBJECT", "ORD_STS_CANCEL_BODY", context, NtisNtfRefType.ORDER.getCode(),
                        NtisMessageSubject.MSG_SBJ_ORDER_CANCELLED.getCode(), new Date(), orgUser.getUsr_id(), serviceProvider.getOrg_id(), null);
            }
            for (String email : orgEmails) {
                orderProcessRequest.createOrderCancelledRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang), context);
            }
        } else {
            String templateSubject = "";
            String templateBody = "";
            String messageSubject = "";
            if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                NtisReviewsDAO reviewDAO = ntisReviewsDBService.loadRecordByParams(conn, " rev_ord_id = ?::int ", new SelectParamValue(orderDAO.getOrd_id()));
                if (reviewDAO == null || reviewDAO.getRev_id() == null) {
                    reviewDAO = ntisReviewsDBService.newRecord();
                    reviewDAO.setRev_ord_id(orderDAO.getOrd_id());
                    reviewDAO.setRev_pasl_org_id(orgId);
                    reviewDAO.setRev_usr_id(orderDAO.getOrd_usr_id());
                    reviewDAO.setRev_admin_read(YesNo.N.getCode());
                    reviewDAO.setRev_receiver_read(YesNo.N.getCode());
                    ntisReviewsDBService.saveRecord(conn, reviewDAO);
                }
                context.put("revId", reviewDAO.getRev_id().toString());
                templateSubject = "ORD_STS_FINISH_SUBJECT";
                templateBody = "ORD_STS_FINISH_BODY";
                messageSubject = NtisMessageSubject.MSG_SBJ_ORDER_FINISHED.getCode();
                if (!clientEmails.isEmpty()) {
                    for (String email : clientEmails) {
                        orderProcessRequest.createOrderFinishedRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang), context);
                    }
                }
            } else if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_REJECTED.getCode())) {
                templateSubject = "ORD_STS_REJECT_SUBJECT";
                templateBody = "ORD_STS_REJECT_BODY";
                messageSubject = NtisMessageSubject.MSG_SBJ_ORDER_REJECTED.getCode();
                if (!clientEmails.isEmpty()) {
                    for (String email : clientEmails) {
                        orderProcessRequest.createOrderRejectedRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang), context);
                    }
                }
            } else if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CONFIRMED.getCode())) {
                if (orderId.getActionType() != null && orderId.getActionType().equals("update")) {
                    templateSubject = "ORD_STS_UPDATE_SUBJECT";
                    templateBody = "ORD_STS_UPDATE_BODY";
                    messageSubject = NtisMessageSubject.MSG_SBJ_ORDER_UPDATED.getCode();
                    if (!clientEmails.isEmpty()) {
                        for (String email : clientEmails) {
                            orderProcessRequest.createOrderUpdatedRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang), context);
                        }
                    }
                } else {
                    templateSubject = "ORD_STS_CONFIRM_SUBJECT";
                    templateBody = "ORD_STS_CONFIRM_BODY";
                    messageSubject = NtisMessageSubject.MSG_SBJ_ORDER_CONFIRMED.getCode();
                    if (!clientEmails.isEmpty()) {
                        for (String email : clientEmails) {
                            orderProcessRequest.createOrderConfirmedRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang),
                                    context);
                        }
                    }
                }
            }
            if (orderDAO.getOrd_usr_id() != null) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), "NTIS_ORD_STS_NOTIF",
                        templateSubject, templateBody, context, NtisNtfRefType.ORDER.getCode(), messageSubject, new Date(), orderDAO.getOrd_usr_id(),
                        orderDAO.getOrd_org_id(), null);
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
