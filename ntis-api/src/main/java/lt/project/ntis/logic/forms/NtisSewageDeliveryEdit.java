package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisDeliveryFacilitiesDAO;
import lt.project.ntis.dao.NtisUsedSludgesDAO;
import lt.project.ntis.dao.NtisWastewaterDeliveriesDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentOrgDAO;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisSewageDeliveryStatus;
import lt.project.ntis.enums.NtisSludgeDeliveryInfo;
import lt.project.ntis.logic.forms.processRequests.DeliveryProcessRequest;
import lt.project.ntis.models.NtisOrderCarSelection;
import lt.project.ntis.models.NtisSewageOriginFacility;
import lt.project.ntis.models.NtisUsedSewageFacility;
import lt.project.ntis.models.NtisWastewaterDeliveryEditModel;
import lt.project.ntis.models.NtisWaterManagerSelectionModel;
import lt.project.ntis.service.NtisDeliveryFacilitiesDBService;
import lt.project.ntis.service.NtisUsedSludgesDBService;
import lt.project.ntis.service.NtisWastewaterDeliveriesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentOrgDBService;

/**
 * Klasė skirta formų "Sukurti nuotekų tvarkymo pristatymą" ir "Redaguoti nuotekų tvarkymo pristatymą (pasl. teikėjas)"  biznio logikai apibrėžti
 */

@Component
public class NtisSewageDeliveryEdit extends FormBase {

    private final NtisDeliveryFacilitiesDBService deliveryFacilitiesDbService;

    private final NtisWastewaterDeliveriesDBService deliveriesDbService;

    private final NtisUsedSludgesDBService usedSludgeDbService;

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisWastewaterDeliveriesDBService ntisWastewaterDeliveriesDBService;

    private final NtisWastewaterTreatmentOrgDBService ntisWastewaterTreatmentOrgDBService;

    private final SprOrganizationsDBService organizationsDBService;

    private final NtisNotificationsManager ntisNotifications;

    private final DeliveryProcessRequest deliveryProcessRequest;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final NtisSludgeTreatmentDeliveriesList ntisSludgeTreatmentDeliveriesList;

    private static final String AUTO_ACCEPT = "Automatinis priėmimas";

    public NtisSewageDeliveryEdit(NtisDeliveryFacilitiesDBService deliveryFacilitiesDbService, NtisWastewaterDeliveriesDBService deliveriesDbService,
            NtisUsedSludgesDBService usedSludgeDbService, BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager,
            NtisWastewaterDeliveriesDBService ntisWastewaterDeliveriesDBService, NtisWastewaterTreatmentOrgDBService ntisWastewaterTreatmentOrgDBService,
            SprOrganizationsDBService organizationsDBService, NtisNotificationsManager ntisNotifications, DeliveryProcessRequest deliveryProcessRequest,
            SprOrgUsersDBServiceImpl sprOrgUsersDBService, NtisSludgeTreatmentDeliveriesList ntisSludgeTreatmentDeliveriesList) {
        super();
        this.deliveryFacilitiesDbService = deliveryFacilitiesDbService;
        this.deliveriesDbService = deliveriesDbService;
        this.usedSludgeDbService = usedSludgeDbService;
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ntisWastewaterDeliveriesDBService = ntisWastewaterDeliveriesDBService;
        this.ntisWastewaterTreatmentOrgDBService = ntisWastewaterTreatmentOrgDBService;
        this.organizationsDBService = organizationsDBService;
        this.ntisNotifications = ntisNotifications;
        this.deliveryProcessRequest = deliveryProcessRequest;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisSludgeTreatmentDeliveriesList = ntisSludgeTreatmentDeliveriesList;
    }

    @Override
    public String getFormName() {
        return "NTIS_SEWAGE_DELIVERY_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų (dumblo) pristatymo valyti redagavimas", "Nuotekų (dumblo) pristatymo valyti redagavimo forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE, FormBase.ACTION_DELETE);
    }

    /**
     * Priklausomai nuo to, ar per parametrus bus perduodamas nuotekų tvarkymo pristatymo įrašo id,
     * metodas arba grąžins jau sukurtą nuotekų pristatymo įrašą, arba grąžins naują pristatymo įrašą
     * @param conn - prisijungimas prie DB
     * @param recordIdentifier - įrašo identifikatorius
     * @param orgId - sesijos organizacijos id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @return NtisWastewaterDeliveryEditModel objektas
     * @throws Exception
     */
    public NtisWastewaterDeliveryEditModel getRecord(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double usrId, String lang)
            throws NumberFormatException, Exception {
        NtisWastewaterDeliveriesDAO deliveryDAO = null;
        NtisWastewaterDeliveryEditModel delivery = new NtisWastewaterDeliveryEditModel();
        if (sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
                this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_CREATE);
                deliveryDAO = deliveriesDbService.newRecord();
                deliveryDAO.setFormActions(getFormActions(conn));
            } else {
                this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
                deliveryDAO = deliveriesDbService.loadRecordByIdAndOrgId(conn, orgId, recordIdentifier.getIdAsDouble());
                deliveryDAO.setFormActions(getFormActions(conn));
                delivery.setOriginFacilities(getSewageOriginFacilities(conn, deliveryDAO.getWd_id(), orgId, usrId, lang));
                delivery.setUsedSewageFacilities(getUsedFacilitiesList(conn, lang, deliveryDAO.getWd_id()));
                if ("copy".equalsIgnoreCase(recordIdentifier.getActionType())) {
                    delivery.setOriginFacilities(getSewageOriginFacilitiesForCopy(conn, deliveryDAO.getWd_id(), orgId, usrId, lang));
                    delivery.setUsedSewageFacilities(getUsedFacilitiesList(conn, lang, deliveryDAO.getWd_id()));
                    for (NtisUsedSewageFacility usFacility : delivery.getUsedSewageFacilities()) {
                        usFacility.setUs_id(null);
                    }
                    deliveryDAO.setWd_id(null);
                    deliveryDAO.setWd_state(null);
                    deliveryDAO.setWd_rejection_reason(null);
                    deliveryDAO.setRec_create_timestamp(null);
                    deliveryDAO.setRec_timestamp(null);
                    deliveryDAO.setRec_userid(null);
                    deliveryDAO.setRec_version(null);
                }
            }
            delivery.setDeliveryInfo(deliveryDAO);
            return delivery;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    /**
     * Pagal nurodytą NtisWastewaterDeliveryEditModel objektą, metodas į duomenų bazę išsaugos NtisWastewaterDeliveriesDAO objektą, 
     * bei ištrins arba išsaugos NtisUsedSludgesDAO ir NtisDeliveryFacilitiesDAO objektus. 
     * Prieš išsaugant metodas patikrins ar naudotojas turi priskirtą teisę sukurti ar redaguoti įrašus.
     * Jeigu pristatymo užsakymas vykdomas automatinio priėmimo galimybę turinčiai valyklai, tuomet atliekami procesai automatiniam patvirtinimui.
     * @param record - NtisWastewaterDeliveryEditModel objektas
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @return NtisWastewaterDeliveryEditModel objektas
     * @throws Exception
     */
    public NtisWastewaterDeliveryEditModel saveDeliveryRecord(Connection conn, NtisWastewaterDeliveryEditModel record, Double orgId, Double usrId)
            throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getDeliveryInfo().getWd_id() == null ? NtisSewageDeliveryEdit.ACTION_CREATE : NtisSewageDeliveryEdit.ACTION_UPDATE);
        NtisWastewaterDeliveriesDAO deliveryDAO = null;
        boolean newRecord = true;
        if (record.getDeliveryInfo() != null && record.getDeliveryInfo().getWd_id() != null) {
            newRecord = false;
            deliveryDAO = deliveriesDbService.loadRecordByIdAndOrgId(conn, orgId, record.getDeliveryInfo().getWd_id());
        }
        if ((deliveryDAO == null && newRecord) || (!newRecord && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId))) {
            record.getDeliveryInfo().setWd_org_id(orgId);
            if (record.getDeliveryInfo().getWd_additional_information_sludge_delivery() != null && record.getDeliveryInfo()
                    .getWd_additional_information_sludge_delivery().equalsIgnoreCase(NtisSludgeDeliveryInfo.SLD_DLV_USED.getCode())) {
                record.getDeliveryInfo().setWd_state(NtisSewageDeliveryStatus.SWG_DLV_STS_USED.getCode());
            } else if (record.getDeliveryInfo().getWd_state() == null) {
                record.getDeliveryInfo().setWd_state(NtisSewageDeliveryStatus.SWG_DLV_STS_SUBMITTED.getCode());
            }
            deliveryDAO = deliveriesDbService.saveRecord(conn, record.getDeliveryInfo());

            for (String deletedId : record.getDeletedUsedFacilities()) {
                usedSludgeDbService.deleteRecord(conn, Utils.getDouble(deletedId));
            }
            for (NtisUsedSewageFacility facility : record.getUsedSewageFacilities()) {
                if (facility.getUs_id() == null) {
                    NtisUsedSludgesDAO obj = usedSludgeDbService.newRecord();
                    obj.setUs_wd_id(record.getDeliveryInfo().getWd_id());
                    obj.setUs_wtf_id(Utils.getDouble(facility.getWtf_id()));
                    usedSludgeDbService.saveRecord(conn, obj);
                }
            }
            for (NtisSewageOriginFacility deletedId : record.getDeletedOriginFacilities()) {
                if (deletedId.getDf_id() != null) {
                    deliveryFacilitiesDbService.deleteRecord(conn, Utils.getDouble(deletedId.getDf_id()));
                }
            }
            for (NtisSewageOriginFacility facility : record.getOriginFacilities()) {
                if (facility.getDf_id() == null) {
                    NtisDeliveryFacilitiesDAO obj = deliveryFacilitiesDbService.newRecord();
                    obj.setDf_wtf_id(facility.getWtf_id());
                    obj.setDf_wd_id(record.getDeliveryInfo().getWd_id());
                    obj.setDf_delivery_sludge_quentity(Utils.getDouble(facility.getOcw_discharged_sludge_amount()));
                    if (record.getOriginFacilities() != null && record.getOriginFacilities().size() == 1 && obj.getDf_delivery_sludge_quentity() == null) {
                        obj.setDf_delivery_sludge_quentity(record.getDeliveryInfo().getWd_delivered_quantity());
                    }
                    obj.setDf_removed_date(record.getDeliveryInfo().getWd_delivery_date());
                    if (facility.getOrd_id() != null) {
                        obj.setDf_ord_id(Utils.getDouble(facility.getOrd_id()));
                    }
                    deliveryFacilitiesDbService.saveRecord(conn, obj);
                }
            }
            if (record.getDeliveryInfo().getWd_wto_id() != null
                    && !NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED.getCode().equalsIgnoreCase(record.getDeliveryInfo().getWd_state())) {
                NtisWastewaterTreatmentOrgDAO wtoDAO = ntisWastewaterTreatmentOrgDBService.loadRecord(conn, record.getDeliveryInfo().getWd_wto_id());
                if (wtoDAO != null && YesNo.Y.getCode().equalsIgnoreCase(wtoDAO.getWto_auto_accept())) {
                    if (deliveryDAO != null) {
                        deliveryDAO.setWd_delivered_wastewater_description(AUTO_ACCEPT);
                        deliveryDAO.setWd_state(NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED.getCode());
                        deliveryDAO.setWd_accepted_sewage_quantity(record.getDeliveryInfo().getWd_delivered_quantity());
                        deliveriesDbService.saveRecord(conn, deliveryDAO);
                        record.setWastewaterTreatmentOrg(wtoDAO);
                    }
                }
            }
            return record;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    /**
     * Metodas grąžins sąrašą paslaugų teikėjo nuotekų išvežimo užsakymų, kurie yra įvykdyti,
     * tačiau dar neturi sukurto nuotekų tvarkymo pristatymo arba jau sukurto nuotekų tvarkymo pristatymo būsenoje nurodyta, kad jis atšauktas
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param usrId - sesijos naudotojo id
     * @return List<NtisWaterManagerSelectionModel>
     * @throws Exception
     */
    public List<NtisWaterManagerSelectionModel> getOriginFacilitiesOrdersList(Connection conn, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select ord.ord_id,
                               ord.ord_id || ' - '|| cr.cr_reg_no || ' - ' || coalesce(wav.full_address_text, '('|| wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')')
                               || ' - ' || coalesce(rfc.rfc_meaning, wtf.wtf_type) as name,
                               ord.ord_removed_sewage_date as date
                               from ntis_orders ord
                               inner join ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id
                               inner join ntis_cars cr on cr.cr_id = ocw.ocw_cr_id
                               inner join ntis_services srv on ord.ord_srv_id = srv.srv_id and srv.srv_type = 'VEZIMAS' and srv.srv_org_id = ?::int
                               inner join ntis_wastewater_treatment_faci wtf on ord.ord_wtf_id = wtf.wtf_id
                               inner join spr_ref_codes_vw rfc on rfc.rfc_code = wtf.wtf_type and rfc.rfc_domain = 'NTIS_WTF_TYPE' and rfc.rft_lang = ?
                               left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                               where ord.ord_state = 'ORD_STS_FINISHED'
                                 and ord.ord_id not in
                                              (select df.df_ord_id from ntis.ntis_delivery_facilities df
                                              inner join ntis_wastewater_deliveries wdr on df.df_wd_id = wdr.wd_id and wdr.wd_state not in
                                              ('SWG_DLV_STS_REJECTED', 'SWG_DLV_STS_CANCELLED') and wdr.wd_org_id = ?::int and df.df_ord_id is not null)
                                              """);

        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(orgId);
        stmt.setStatementOrderPart(" order by ord_removed_sewage_date asc ");
        List<NtisWaterManagerSelectionModel> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWaterManagerSelectionModel.class);
        return data;
    }

    public List<NtisWaterManagerSelectionModel> getPortableFacilities(Connection conn, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select wtf.wtf_id as wtf_id,
                               wtf.c02 || ' - ' || coalesce(wav.full_address_text, '(' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')') || ' - '
                               || coalesce(rfc.rfc_meaning, wtf.wtf_type) as name,
                               wtf.wtf_installation_date as date
                               from ntis_wastewater_treatment_faci wtf
                               inner join ntis_facility_owners fo on fo.fo_wtf_id = wtf.wtf_id and fo.fo_org_id = ?::int
                               left join spr_ref_codes_vw rfc on rfc.rfc_code = wtf.wtf_type and rfc.rfc_domain = 'NTIS_WTF_TYPE' and rfc.rft_lang = ?
                               left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                               where wtf.wtf_state <> 'CLOSED' and wtf.wtf_type = 'PORTABLE_RESERVOIR' and fo.fo_owner_type = 'SERVICE_PROVIDER'
                        """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        stmt.setWhereExists(true);
        stmt.setStatementOrderPart(" order by wtf.wtf_installation_date asc ");
        List<NtisWaterManagerSelectionModel> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWaterManagerSelectionModel.class);
        return data;
    }

    private List<NtisSewageOriginFacility> getSewageOriginFacilities(Connection conn, Double wdId, Double orgId, Double userId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams(
                """
                        select ord.ord_id,
                               df.df_id,
                               coalesce(typ.rfc_meaning, wtf.wtf_type) as wtf_type,
                               wtf.c02 || ' - ' || coalesce(wav.full_address_text, '(' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')')  as name,
                               wtf.wtf_id,
                               df.df_delivery_sludge_quentity as ocw_discharged_sludge_amount,
                               df.df_removed_date as ord_removed_sewage_date,
                               case when ord.ord_id is not null then 'ORDER' when ord.ord_id is null and wtf.wtf_id is not null then 'FACILITY' end as type
                               from ntis_wastewater_deliveries wd
                               inner join ntis_delivery_facilities df on df.df_wd_id = wd.wd_id
                               left join ntis_orders ord on ord.ord_id = df.df_ord_id and ord.ord_wtf_id = df.df_wtf_id
                               inner join ntis_wastewater_treatment_faci wtf on df.df_wtf_id = wtf.wtf_id
                               left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                               left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                               where wd.wd_id = ?::int and wd.wd_org_id = ?::int
                        """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wdId);
        stmt.addSelectParam(orgId);
        stmt.setWhereExists(true);

        stmt.setStatementOrderPart("order by ord_removed_sewage_date asc ");
        List<NtisSewageOriginFacility> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisSewageOriginFacility.class);
        return data;
    }

    private List<NtisSewageOriginFacility> getSewageOriginFacilitiesForCopy(Connection conn, Double wdId, Double orgId, Double usrId, String lang)
            throws Exception {
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                            ord.ord_id,
                            coalesce(typ.rfc_meaning, wtf.wtf_type) as wtf_type,
                            ord.ord_id || ' - ' || cr.cr_reg_no || ' - ' || coalesce(wav.full_address_text, '(' || wtf.wtf_facility_latitude ||
                                ', ' || wtf.wtf_facility_longitude || ')') || ' - ' || coalesce(typ.rfc_meaning, wtf.wtf_type) as name,
                            wtf.wtf_id,
                            ocw.ocw_cr_id,
                            df.df_delivery_sludge_quentity as ocw_discharged_sludge_amount,
                            ord.ord_removed_sewage_date
                         from ntis_delivery_facilities df
                         inner join ntis_wastewater_treatment_faci wtf on wtf.wtf_id = df.df_wtf_id
                         inner join ntis_wastewater_deliveries wd on wd.wd_id = df.df_wd_id
                         inner join ntis_orders ord on ord.ord_id = df.df_ord_id and ord.ord_wtf_id = df.df_wtf_id
                         inner join ntis_services srv on srv.srv_id = ord.ord_srv_id
                         inner join spr_organizations org on org.org_id = srv.srv_org_id and org.org_id = ?
                         inner join spr_org_users ou on ou.ou_org_id = org.org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                         inner join ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id
                         inner join ntis_cars cr on cr.cr_id = ocw.ocw_cr_id
                         left join spr_ref_codes_vw typ on typ.rfc_code =wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                         left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                         where ord.ord_id not in (
                                         select
                                             dfc.df_ord_id
                                         from ntis_delivery_facilities dfc
                                         inner join ntis_wastewater_deliveries wdc on wdc.wd_id = dfc.df_wd_id
                                         and wdc.wd_state <> 'SWG_DLV_STS_REJECTED')
                        """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("wd.wd_id = ? ", wdId);
        stmt.setStatementOrderPart("order by ord_removed_sewage_date asc ");
        List<NtisSewageOriginFacility> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisSewageOriginFacility.class);
        return data;
    }

    /**
     * Metodas grąžins NtisSewageOriginFacility objektą pagal perduodamo užsakymo id
     * @param conn - prisijungimas prie DB
     * @param ordId - užsakymo id
     * @param lang - sesijos kalba
     * @param orgId - sesijos organizacijos id
     * @param usrId - sesijos naudotojo id
     * @return NtisSewageOriginFacility objektas
     * @throws Exception
     */
    public NtisSewageOriginFacility getNewOriginFacilityOrder(Connection conn, String ordId, String lang, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        NtisSewageOriginFacility facility = new NtisSewageOriginFacility();
        stmt.setStatement("SELECT ORD.ORD_ID, " + //
                "WTF.WTF_ID, " + //
                "DF.DF_ID, " + //
                "coalesce(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) AS WTF_TYPE, " + //
                "ORD.ORD_ID || ' - ' || CR.CR_REG_NO || ' - ' || " + //
                "coalesce(WAV.FULL_ADDRESS_TEXT, '(' ||WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ')') " + //
                "|| ' - ' || coalesce(TYP.RFC_MEANING, WTF.WTF_TYPE) AS NAME, " + //
                "OCW.OCW_CR_ID, " + //
                "OCW.OCW_DISCHARGED_SLUDGE_AMOUNT, " + //
                "ORD.ORD_REMOVED_SEWAGE_DATE, " + //
                "'ORDER' AS TYPE " + //
                "FROM NTIS.NTIS_ORDERS ORD " + //
                "INNER JOIN NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID AND SRV.SRV_ORG_ID = ?::int " + //
                "INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = SRV.SRV_ORG_ID AND OU.OU_USR_ID = ?::int and CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) "
                + //
                "LEFT JOIN NTIS.NTIS_DELIVERY_FACILITIES DF ON DF.DF_ORD_ID = ORD.ORD_ID " + //
                "INNER JOIN NTIS.NTIS_WASTEWATER_TREATMENT_FACI WTF ON ORD.ORD_WTF_ID = WTF.WTF_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ? " + //
                "INNER JOIN NTIS.NTIS_ORDER_COMPLETED_WORKS OCW ON OCW.OCW_ORD_ID = ORD.ORD_ID AND ORD.ORD_ID = ?::int " + //
                "INNER JOIN NTIS_CARS CR ON CR.CR_ID = OCW.OCW_CR_ID " + //
                "LEFT JOIN NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID");
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(Utils.getDouble(ordId));
        List<NtisSewageOriginFacility> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisSewageOriginFacility.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
        }
        return facility;
    }

    public NtisSewageOriginFacility getPortableFacilityForDelivery(Connection conn, String wtfId, String lang, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select wtf.wtf_id,
                               'FACILITY' as type,
                               coalesce(typ.rfc_meaning, wtf.wtf_type) as wtf_type,
                               wtf.c02 || ' - ' || coalesce(wav.full_address_text, '(' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')') as name
                               from ntis_wastewater_treatment_faci wtf
                               inner join ntis_facility_owners fo on fo.fo_wtf_id = wtf.wtf_id and fo.fo_org_id = ?::int
                               left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                               left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                               where wtf.wtf_id = ?::int
                        """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(Utils.getDouble(wtfId));
        stmt.setWhereExists(true);
        NtisSewageOriginFacility facility = new NtisSewageOriginFacility();

        List<NtisSewageOriginFacility> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisSewageOriginFacility.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
        }
        return facility;
    }

    private List<NtisUsedSewageFacility> getUsedFacilitiesList(Connection conn, String lang, Double wdId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT " + //
                "WTF.WTF_ID, " + //
                "COALESCE (WAV.FULL_ADDRESS_TEXT, '('|| WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ') ') AS WTF_ADDRESS, " + //
                "WTF.WTF_TECHNICAL_PASSPORT_ID, " + //
                "coalesce(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) as WTF_TYPE, " + //
                "coalesce(MAN.RFT_DISPLAY_CODE, MAN.RFC_MEANING) as WTF_MANUFACTURER, " + //
                "coalesce(MOD.RFT_DISPLAY_CODE, MOD.RFC_MEANING) as WTF_MODEL, " + //
                "coalesce(CAP.RFT_DISPLAY_CODE, CAP.RFC_MEANING) as capacity, " + //
                "wtf.wtf_type as typeClsf, " + //
                "TO_CHAR(WTF.WTF_INSTALLATION_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)
                + "') AS WTF_INSTALLATION_DATE, " + //
                "US.US_ID, " + //
                "US.US_WD_ID, " + //
                "WTF.WTF_DISTANCE " + //
                "FROM NTIS.NTIS_WASTEWATER_TREATMENT_FACI WTF " + //
                "LEFT JOIN NTIS.NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW MAN ON MAN.RFC_CODE = WTF.WTF_MANUFACTURER AND MAN.RFC_DOMAIN = 'NTIS_FACIL_MANUFA' AND MAN.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW MOD ON MOD.RFC_CODE = WTF.WTF_MODEL AND MOD.RFC_DOMAIN = 'NTIS_FACIL_MODEL' AND MOD.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW CAP ON CAP.RFC_CODE = WTF.WTF_CAPACITY AND CAP.RFC_DOMAIN = 'NTIS_FACIL_CAPACITY' AND CAP.RFT_LANG = TYP.RFT_LANG "
                + //
                "INNER JOIN NTIS.NTIS_USED_SLUDGES US ON US.US_WTF_ID = WTF.WTF_ID AND US.US_WD_ID = ?");
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wdId);
        List<NtisUsedSewageFacility> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisUsedSewageFacility.class);
        return data;
    }

    /**
     * Metodas grąžins NtisUsedSewageFacility objektą pagal perduodamą nuotekų tvarkymo įrenginio id
     * @param conn - prisijungimas prie DB
     * @param wtfId - nuotekų tvarkymo įrenginio id
     * @param lang - sesijos kalba
     * @return NtisUsedSewageFacility objektas
     * @throws Exception
     */
    public NtisUsedSewageFacility getNewDeliveryFacility(Connection conn, String wtfId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        NtisUsedSewageFacility facility = new NtisUsedSewageFacility();
        stmt.setStatement("SELECT " + //
                "WTF.WTF_ID, " + //
                "COALESCE (WAV.FULL_ADDRESS_TEXT, '('|| WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ') ') AS WTF_ADDRESS, " + //
                "WTF.WTF_TECHNICAL_PASSPORT_ID, " + //
                "coalesce(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) as WTF_TYPE, " + //
                "coalesce(MAN.RFT_DISPLAY_CODE, MAN.RFC_MEANING) as WTF_MANUFACTURER, " + //
                "coalesce(MOD.RFT_DISPLAY_CODE, MOD.RFC_MEANING) as WTF_MODEL, " + //
                "coalesce(CAP.RFT_DISPLAY_CODE, CAP.RFC_MEANING) as capacity, " + //
                "wtf.wtf_type as typeClsf, " + //
                "TO_CHAR(WTF.WTF_INSTALLATION_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)
                + "') AS WTF_INSTALLATION_DATE, " + //
                "WTF.WTF_DISTANCE " + //
                "FROM NTIS.NTIS_WASTEWATER_TREATMENT_FACI WTF " + //
                "LEFT JOIN NTIS.NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW MAN ON MAN.RFC_CODE = WTF.WTF_MANUFACTURER AND MAN.RFC_DOMAIN = 'NTIS_FACIL_MANUFA' AND MAN.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW MOD ON MOD.RFC_CODE = WTF.WTF_MODEL AND MOD.RFC_DOMAIN = 'NTIS_FACIL_MODEL' AND MOD.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW CAP ON CAP.RFC_CODE = WTF.WTF_CAPACITY AND CAP.RFC_DOMAIN = 'NTIS_FACIL_CAPACITY' AND CAP.RFT_LANG = TYP.RFT_LANG "
                + //
                "WHERE WTF.WTF_ID = ? ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(Utils.getDouble(wtfId));

        List<NtisUsedSewageFacility> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisUsedSewageFacility.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
        }
        return facility;
    }

    /**
     * Metodas grąžins sąrašą sistemoje registruotų nuotekų tvarkymo įrenginių
     * @param conn - prisijungimas prie DB
     * @return List<NtisWaterManagerSelectionModel>
     * @throws Exception
     */
    public List<NtisWaterManagerSelectionModel> getFacilitiesList(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT WTF.WTF_ID AS ID, " + //
                "COALESCE (WAV.FULL_ADDRESS_TEXT, '('|| WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ') ') AS NAME " + //
                "FROM NTIS_WASTEWATER_TREATMENT_FACI WTF " + //
                "LEFT JOIN NTIS.NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID");
        List<NtisWaterManagerSelectionModel> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWaterManagerSelectionModel.class);
        return data;
    }

    /**
     * Metodas grąžins vandentvarkos įmonių ir joms priklausančių naudojamų valyklų sąrašą
     * @param conn - prisijungimas prie DB
     * @param orgId -organizacijos sesijos id
     * @param usrId - sesijos naudotojo id
     * @return json objektas
     * @throws Exception
     */
    public String getWaterManagerFacilitiesList(Connection conn, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                         select
                            CASE WHEN exists
                                    (select 1 from
                                              ntis_favorite_wast_treat_orgs f, ntis_wastewater_treatment_org w, spr_org_users ou
                                              where w.wto_id = f.fwto_wto_id
                                                and ou.ou_org_id = f.fwto_org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                                                and org.org_id = w.wto_org_id
                                                and f.fwto_org_id = ?::int
                                                limit 1) THEN ORG.ORG_NAME || ' (Prior.)'
                                 ELSE ORG.ORG_NAME END AS GROUP_KEY,
                                       (SELECT
                                          JSONB_AGG(
                                                JSON_BUILD_OBJECT('value', wto.wto_id,
                                                                  'label', case when fwo.fwto_wto_id is not null
                                                                   then wto.wto_name || ', ' || wto.wto_address || ' (Prior.)'
                                                                   else wto.wto_name || ', ' || wto.wto_address end )
                                                                   order by fwo.fwto_wto_id) as select_option
                                                            from ntis_wastewater_treatment_org wto
                                                            left join ntis_favorite_wast_treat_orgs fwo on fwo.fwto_wto_id = wto.wto_id and fwo.fwto_org_id = ?::int
                                                            left join spr_org_users ou on ou.ou_org_id = fwo.fwto_org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                                                            where wto.wto_org_id = org.org_id AND WTO.WTO_IS_IT_USED = 'Y'
                                                            GROUP BY wto.wto_org_id) AS VALUES
                                         from spr_organizations org
                                            inner join ntis_wastewater_treatment_org wto1 on wto1.wto_org_id = org.org_id and wto1.wto_is_it_used = 'Y'
                                            where org.c01 in ('VANDEN', 'PASLAUG_VANDEN')
                                              and org.org_date_to is null
                                              and org.n01 = 1
                                            group by org.org_id, wto1.wto_org_id
                        """);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.setStatementOrderPart("""
                (exists (select 1 from
                                      ntis_favorite_wast_treat_orgs f, ntis_wastewater_treatment_org w
                                      where w.wto_id = f.fwto_wto_id
                                        and org.org_id = w.wto_org_id
                                        and f.fwto_org_id = ?::int
                                        limit 1)) desc
                """);
        stmt.addSelectParam(orgId);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins paslaugų teikimo įmonei priklausančių mašinų sąrašą
     * @param conn - prisijungimas prie DB
     * @param orgId - organizacijos sesijos id
     * @param usrId - naudotojo sesijos id
     * @return List<NtisOrderCarSelection>
     * @throws Exception
     */
    public List<NtisOrderCarSelection> getOrgCars(Connection conn, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveryEdit.ACTION_READ);
        
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT CR.CR_ID, " + //
                "CASE WHEN CR.CR_TYPE IS NOT NULL THEN " + //
                " CR.CR_REG_NO || ' (' || RFC_MEANING || ')' ELSE CR.CR_REG_NO END AS CR_NAME " + //
                "FROM NTIS_CARS CR " + //
                "INNER JOIN SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = CR.CR_ORG_ID AND ORG.ORG_ID = ?::int " + //
                "INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = ORG.ORG_ID AND OU.OU_USR_ID = ?::int AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now())" +//
                "LEFT JOIN SPR_REF_CODES_VW ON RFC_DOMAIN = 'NTIS_CAR_TYPE' AND RFC_CODE = CR.CR_TYPE AND RFT_LANG = ?");
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        List<NtisOrderCarSelection> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisOrderCarSelection.class);
        return data;
    }

    /**
     * Metodas išsiųs pranešimus ir email'us vandentvarkos įmonei, pagal pateiktą pristatymo id
     * @param conn - prisijungimas prie DB
     * @param deliveryId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double deliveryId, Double usrId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        // delivery info
        NtisWastewaterDeliveriesDAO deliveryDAO = ntisWastewaterDeliveriesDBService.loadRecordByIdAndOrgId(conn, orgId, deliveryId);
        if (deliveryDAO != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            SprOrganizationsDAO waterManager = organizationsDBService.loadRecord(conn,
                    ntisWastewaterTreatmentOrgDBService.loadRecord(conn, deliveryDAO.getWd_wto_id()).getWto_org_id());
            SprOrganizationsDAO serviceProvider = organizationsDBService.loadRecord(conn, orgId);
            List<String> waterManagerEmails = new ArrayList<>();
            if (waterManager.getC02() != null && waterManager.getC02().equals(DbConstants.BOOLEAN_TRUE) && waterManager.getOrg_email() != null
                    && !waterManager.getOrg_email().isBlank()) {
                waterManagerEmails = Arrays.asList(waterManager.getOrg_email().split("\\s*,\\s*"));
            }
            String roleCodes = """
                    '%s', '%s'
                    """.formatted(NtisRolesConstants.VAND_ADMIN, NtisRolesConstants.PLANT_SPECIALIST);
            List<SprUsersDAO> waterManagerUsers = sprOrgUsersDBService.getOrganizationUsers(conn, waterManager.getOrg_id(), roleCodes);

            // handle context
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("serviceProvider", serviceProvider.getOrg_name());
            context.put("deliveryId", deliveryId.intValue());

            // send notifications and emails
            for (String email : waterManagerEmails) {
                deliveryProcessRequest.createDeliverySubmittedRequest(conn, usrId, deliveryId, email, Languages.getLanguageByCode(lang), context);
            }
            for (SprUsersDAO user : waterManagerUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, deliveryId, "NTIS_DELIVERY_NOTIF", "DELIV_SUBMIT_SUBJECT",
                        "DELIV_SUBMIT_BODY", context, NtisNtfRefType.DELIVERY.getCode(), NtisMessageSubject.MSG_SBJ_DELIVERY_SUBMITTED.getCode(), new Date(),
                        user.getUsr_id(), waterManager.getOrg_id(), null);
            }
        }
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui, pagal pateiktą pristatymo id, 
     * kai bus atliekamas užsakymo pristatymas vandentvarkos įmonei kuri gali priimti nuotekas automatiškai
     * @param conn - prisijungimas prie DB
     * @param deliveryId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @parma userId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotificationAutoAccept(Connection conn, Double deliveryId, Double usrId, Double orgId, String lang) throws Exception {
        // delivery info
        NtisWastewaterDeliveriesDAO deliveryDAO = deliveriesDbService.loadRecordByIdAndOrgId(conn, orgId, deliveryId);
        if (deliveryDAO != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            SprOrganizationsDAO serviceProvider = organizationsDBService.loadRecord(conn, deliveryDAO.getWd_org_id());
            List<String> serviceProviderEmails = new ArrayList<>();
            if (serviceProvider.getC02() != null && serviceProvider.getC02().equals(DbConstants.BOOLEAN_TRUE) && serviceProvider.getOrg_email() != null) {
                serviceProviderEmails = Arrays.asList(serviceProvider.getOrg_email().split("\\s*,\\s*"));
            }
            String roleCodes = """
                    '%s', '%s'
                    """.formatted(NtisRolesConstants.PASL_ADMIN, NtisRolesConstants.CAR_SPECIALIST);
            List<SprUsersDAO> serviceProviderUsers = sprOrgUsersDBService.getOrganizationUsers(conn, serviceProvider.getOrg_id(), roleCodes);

            // handle context
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("deliveryId", deliveryDAO.getWd_id().intValue());

            // send notifications and emails
            String templateSubject = "";
            String templateBody = "";
            String messageSubject = "";

            if (deliveryDAO.getWd_state().equals(NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED.getCode())) {
                templateSubject = "DELIV_CONFIRM_SUBJECT";
                templateBody = "DELIV_CONFIRM_BODY";
                messageSubject = NtisMessageSubject.MSG_SBJ_DELIVERY_CONFIRMED.getCode();
                for (String email : serviceProviderEmails) {
                    deliveryProcessRequest.createDeliveryConfirmedRequest(conn, usrId, deliveryDAO.getWd_id(), email, Languages.getLanguageByCode(lang),
                            context);
                }
            }
            for (SprUsersDAO user : serviceProviderUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, deliveryDAO.getWd_id(), "NTIS_DELIVERY_NOTIF",
                        templateSubject, templateBody, context, NtisNtfRefType.DELIVERY.getCode(), messageSubject, new Date(), user.getUsr_id(),
                        serviceProvider.getOrg_id(), null);
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }
}
