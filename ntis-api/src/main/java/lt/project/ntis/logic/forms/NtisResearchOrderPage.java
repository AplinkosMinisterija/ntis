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
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.JavaObjectGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisResearchesDAO;
import lt.project.ntis.dao.NtisReviewsDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.processRequests.OrderProcessRequest;
import lt.project.ntis.models.NtisResearchOrderModel;
import lt.project.ntis.models.ResearchCriteriaResultsModel;
import lt.project.ntis.models.ResearchRequestedCriteriaModel;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisResearchesDBService;
import lt.project.ntis.service.NtisReviewsDBService;
import lt.project.ntis.service.NtisServicesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formų 
 * T3050 - Neatlikto tyrimo peržiūra užsakovui
 * T3060 - Neatlikto tyrimo peržiūra laboratorijai
 * T3090 - Tyrimo peržiūra redagavimui laboratorijai
 * T3100 - Atlikto tyrimo peržiūra laboratorijai
 * T3110 - Atlikto tyrimo peržiūra užsakovui
 *  biznio logikai apibrėžti
 */
@Component
public class NtisResearchOrderPage extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisOrdersDBService ordersDbService;

    private final NtisResearchesDBService researchesDbService;

    private final NtisOrderCompletedWorksDBService orderCompletedWorksDbService;

    private final SprFilesDBService filesDbService;

    private final FileStorageService fileStorageService;

    private final NtisNotificationsManager ntisNotifications;

    private final OrderProcessRequest orderProcessRequest;

    private final SprOrganizationsDBService organizationsDBService;

    private final NtisServicesDBService servicesDBService;

    private final SprPersonsDBService sprPersonsDBService;

    private final NtisCommonMethods ntisCommonMethods;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    private final NtisReviewsDBService ntisReviewsDBService;

    public NtisResearchOrderPage(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, NtisOrdersDBService ordersDbService,
            NtisResearchesDBService researchesDbService, NtisOrderCompletedWorksDBService orderCompletedWorksDbService, SprFilesDBService filesDbService,
            FileStorageService fileStorageService, NtisNotificationsManager ntisNotifications, OrderProcessRequest orderProcessRequest,
            SprOrganizationsDBService organizationsDBService, NtisServicesDBService servicesDBService, SprPersonsDBService sprPersonsDBService,
            NtisCommonMethods ntisCommonMethods, SprOrgUsersDBServiceImpl sprOrgUsersDBService,
            NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService, NtisReviewsDBService ntisReviewsDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ordersDbService = ordersDbService;
        this.researchesDbService = researchesDbService;
        this.orderCompletedWorksDbService = orderCompletedWorksDbService;
        this.filesDbService = filesDbService;
        this.fileStorageService = fileStorageService;
        this.ntisNotifications = ntisNotifications;
        this.orderProcessRequest = orderProcessRequest;
        this.organizationsDBService = organizationsDBService;
        this.servicesDBService = servicesDBService;
        this.sprPersonsDBService = sprPersonsDBService;
        this.ntisCommonMethods = ntisCommonMethods;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisWastewaterTreatmentFaciDBService = ntisWastewaterTreatmentFaciDBService;
        this.ntisReviewsDBService = ntisReviewsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_RESEARCH_ORDER_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų užsakymo peržiūra", "Tyrimų užsakymo peržiūros forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE);
        this.addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS);
        this.addFormAction(NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC,
                NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS);
    }

    public NtisResearchOrderModel getOrder(Connection conn, Double ordId, Double perId, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceOrderPage.ACTION_READ);
        NtisResearchOrderModel orderRequest = getOrderInfo(conn, ordId, lang, orgId, perId, usrId);
        if (orderRequest.getOrdId() == null) {
            throw new SparkBusinessException(
                    new S2Message("common.error.noOrderFound", SparkMessageType.ERROR, "User does not have an order with this order ID"));
        } else {
            orderRequest.setFacility(getOrderFacility(conn, orderRequest.getOrdId(), orderRequest.getWtfId(), perId, orgId, lang));
            orderRequest.setSelectedCriteria(getResearchRequestedCriteria(conn, orderRequest.getOrdId(), lang));
            if (orderRequest.getStatusClsf().equals(NtisOrderStatus.ORD_STS_CONFIRMED.getCode())) {
                orderRequest.setResults(getResearchResults(conn, orderRequest.getOrdId(), NtisOrderStatus.ORD_STS_CONFIRMED, lang));
            } else if (orderRequest.getStatusClsf().equals(NtisOrderStatus.ORD_STS_FINISHED.getCode())) {
                orderRequest.setResults(getResearchResults(conn, orderRequest.getOrdId(), NtisOrderStatus.ORD_STS_FINISHED, lang));
            }
            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                NtisReviewsDAO reviewDAO = this.getReviewInfo(conn, orderRequest.getOrdId(), usrId);
                if (reviewDAO != null) {
                    orderRequest.setRevId(reviewDAO.getRev_id());
                    orderRequest.setRevScore(reviewDAO.getRev_score());
                    orderRequest.setRevComment(reviewDAO.getRev_comment());
                }
            }
            orderRequest.setResultsFile(getResultsFile(conn, orderRequest.getOrdId()));
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

    private NtisWtfInfo getOrderFacility(Connection conn, Double ordId, Double wtfId, Double perId, Double orgId, String lang) throws Exception {
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
                from ntis.ntis_wastewater_treatment_faci wtf
                inner join ntis.ntis_orders ord on ord.ord_wtf_id = wtf.wtf_id and ord.ord_id = ?::int and wtf.wtf_id = ?::int
                left join ntis.ntis_facility_owners fo on fo.fo_wtf_id = wtf.wtf_id
                left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                left join spr_ref_codes_vw mnf on mnf.rfc_code = wtf.wtf_manufacturer and mnf.rfc_domain = 'NTIS_FACIL_MANUFA' and mnf.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw mdl on mdl.rfc_code = wtf.wtf_model and mdl.rfc_domain = 'NTIS_FACIL_MODEL' and mdl.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw cap on cap.rfc_code = wtf.wtf_capacity and cap.rfc_domain = 'NTIS_FACIL_CAPACITY' and cap.rft_lang = typ.rft_lang
                            """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(ordId);
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(lang);
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            if (orgId != null) {
                stmt.addParam4WherePart("fo.fo_org_id = ?::int ", orgId);
            } else {
                stmt.addParam4WherePart("fo.fo_per_id = ?::int ", perId);
            }
        }
        ArrayList<NtisWtfInfo> data = (ArrayList<NtisWtfInfo>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWtfInfo.class);
        if (data != null && !data.isEmpty()) {
            facility = data.get(0);
            return facility;
        } else {
            throw new Exception("No wastewater facility found");
        }
    }

    private NtisResearchOrderModel getOrderInfo(Connection conn, Double ordId, String lang, Double orgId, Double perId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchOrderPage.ACTION_READ);
        NtisResearchOrderModel order = new NtisResearchOrderModel();
        StatementAndParams stmt = new StatementAndParams("""
                select
                    to_char(ord.ord_created, '%s') as ordCreated,
                    ord.ord_id as ordId,
                    ord.ord_wtf_id as wtfId,
                    coalesce(r.rfc_meaning, ord.ord_state) as status,
                    ord.ord_state as statusClsf,
                    og.org_name as orgName,
                    og.org_email as orgEmail,
                    og.org_phone as orgPhone,
                    to_char(ord.ord_prefered_date_from, '%s') as requestedDateFrom,
                    to_char(ord.ord_prefered_date_to, '%s') as requestedDateTo,
                    ord.ord_completion_estimate_date as completionEstimate,
                    ord.ord_additional_description as comment,
                    ord.ord_rejection_reason as rejectionReason,
                    oc.ocw_res_person as responsiblePerson,
                    ord.ord_created_in_ntis_portal as createdInNtis,
                    ord.ord_wtf_id as wtfId,
                    oc.ocw_id as ocwId,
                    oc.ocw_completed_works_description as researchComments,
                    coalesce(ord.c01, coalesce(oo.org_name, p.per_name || ' ' || p.per_surname)) as ordererName,
                    coalesce(ord.ord_phone_number, coalesce(oo.org_phone, p.per_phone_number)) as ordererPhone,
                    coalesce(ord.ord_email, coalesce(oo.org_email, p.per_email)) as ordererEmail,
                    rs.res_research_date as researchDate,
                    rs.res_sample_date as sampleDate,
                    rs.res_created as resultsDate,
                    oc.ocw_smp_person as samplePerson,
                    oc.ocw_rsr_person as researchPerson,
                    coalesce(oc.ocw_res_person, rp.per_name || ' ' || rp.per_surname) as resultsPerson
                from ntis_orders ord
                inner join ntis_services s on ord.ord_srv_id = s.srv_id and s.srv_type = 'TYRIMAI' and ord.ord_id = ?::int
                inner join spr_organizations og on s.srv_org_id = og.org_id
                inner join spr_org_users ous on ous.ou_org_id = s.srv_org_id and current_date between ous.ou_date_from and coalesce(ous.ou_date_to, now())
                left join ntis_order_completed_works oc on oc.ocw_ord_id = ord.ord_id
                left join ntis_researches rs on rs.res_ord_id = ord.ord_id
                left join spr_organizations oo on oo.org_id = ord.ord_org_id
                left join spr_persons p on p.per_id = ord.ord_per_id
                left join spr_users us on us.usr_id = oc.ocw_usr_id
                left join spr_persons rp on rp.per_id = us.usr_per_id
                left join spr_ref_codes_vw r on r.rfc_code = ord.ord_state and r.rfc_domain = 'NTIS_ORDER_STATUS' and r.rft_lang = ?
                                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(ordId);
        stmt.addSelectParam(lang);
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            stmt.addParam4WherePart("s.srv_org_id = ?::int", orgId);
            stmt.addParam4WherePart("ous.ou_usr_id = ?::int ", usrId);
        } else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        }
        ArrayList<NtisResearchOrderModel> data = (ArrayList<NtisResearchOrderModel>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                NtisResearchOrderModel.class);
        if (data != null && !data.isEmpty()) {
            order = data.get(0);
        }
        return order;
    }

    private ArrayList<ResearchRequestedCriteriaModel> getResearchRequestedCriteria(Connection conn, Double ordId, String lang) throws Exception {
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
        stmt.setStatementOrderPart(" order by res_id ");
        ArrayList<ResearchRequestedCriteriaModel> data = (ArrayList<ResearchRequestedCriteriaModel>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                ResearchRequestedCriteriaModel.class);
        return data;
    }

    /**
     * Metodas atitinkamai pagal užsakymo statusą, grąžina esamąją normą arba buvusią tuo metu kai buvo užsakytas tyrimas.
     * @param conn - prisijungimas prie DB
     * @param ordId - užsakymo id
     * @param status - užsakymo statusas
     * @param lang - sesijos kalba
     * @throws Exception
     */
    private ArrayList<ResearchCriteriaResultsModel> getResearchResults(Connection conn, Double ordId, NtisOrderStatus status, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchOrderPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();

        if (status.equals(NtisOrderStatus.ORD_STS_CONFIRMED)) {
            stmt.setStatement("""
                    select
                    res_id as resId,
                        rfc_code as code,
                        coalesce(rfc_meaning, rn_research_type) as display,
                        rn_research_norm as norm,
                        rn_id
                    from ntis.ntis_research_norms
                    left join ntis.ntis_researches on res_reserch_type = rn_research_type and res_ord_id = ?::int
                    left join ntis.ntis_orders on ord_id = res_ord_id
                    left join ntis.ntis_wastewater_treatment_faci on wtf_id = ord_wtf_id
                    left join spark.spr_ref_codes_vw on rfc_code = rn_research_type and rfc_domain = 'NTIS_RESEARCH_TYPE' and rft_lang = ?
                    where current_date between rn_date_from and coalesce (rn_date_to, now())
                    and case
                        when rn_facility_installation_date is not null
                             and wtf_installation_date::date >= '2018-09-26'::date
                        then rn_facility_installation_date = '>20180926'
                         when (rn_facility_installation_date is not null
                             and wtf_installation_date::date < '2018-09-26'::date)
                         or (rn_facility_installation_date is not null
                             and wtf_installation_date is null)
                             then rn_facility_installation_date = '<20180926'
                        else rn_facility_installation_date is null end
                                    """);
        } else if (status.equals(NtisOrderStatus.ORD_STS_FINISHED)) {
            stmt.setStatement("""
                    select
                    res_id as resId,
                    rfc_code as code,
                         coalesce(rfc_meaning, rn_research_type) as display,
                         rn_research_norm as norm,
                         res_value as result,
                         case
                             when res_value <= rn_research_norm then 'RESEARCH_NORM_COMPLIES'
                             when res_value > rn_research_norm then 'RESEARCH_NORM_NOT_COMPLIES'
                             else null
                           end normCompliance
                     from ntis.ntis_research_norms
                     left join ntis.ntis_researches on res_reserch_type = rn_research_type and res_ord_id = ?::int
                     left join ntis.ntis_orders on ord_id = res_ord_id
                     left join spark.spr_ref_codes_vw on rfc_code = rn_research_type and rfc_domain = 'NTIS_RESEARCH_TYPE' and rft_lang = ?
                     where rn_id = res_rn_id
                               """);
        }

        stmt.addSelectParam(ordId);
        stmt.addSelectParam(lang);
        stmt.setWhereExists(true);
        stmt.setStatementOrderPart(" order by res_id ");
        ArrayList<ResearchCriteriaResultsModel> data = (ArrayList<ResearchCriteriaResultsModel>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                ResearchCriteriaResultsModel.class);
        return data;
    }

    private SprFile getResultsFile(Connection conn, Double ordId) throws Exception {
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setFilKey4Encript", new StatementDataGetter[] {
                new JavaObjectGetter("fileStorageService", fileStorageService, FileStorageService.class), new StatementStringGetter("fil_key") }));
        StatementAndParams stmt = new StatementAndParams("""
                select fil_content_type,
                       fil_key,
                       fil_name,
                       fil_size,
                       fil_status,
                       fil_status_date
                       from spr_files
                       inner join ntis_order_completed_works on fil_id = ocw_fil_id
                       where ocw_ord_id = ?::int
                """);
        stmt.addSelectParam(ordId);
        List<SprFile> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, new Data2ObjectProcessor<SprFile>(SprFile.class, methods));
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        } else {
            return null;
        }
    }

    public void updateOrder(Connection conn, NtisResearchOrderModel researchOrder, Double userId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchOrderPage.ACTION_UPDATE);
        NtisOrdersDAO orderDAO = null;
        if (researchOrder != null && researchOrder.getOrdId() != null) {
            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
                orderDAO = ordersDbService.loadRecordByIdAndSrvOrgId(conn, orgId, researchOrder.getOrdId());
            } else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                if (orgId != null) {
                    orderDAO = ordersDbService.loadRecordByIdAndOrgId(conn, orgId, researchOrder.getOrdId());
                } else if (perId != null) {
                    orderDAO = ordersDbService.loadRecordByIdAndPerId(conn, perId, researchOrder.getOrdId());
                }
            }
        }
        if (orderDAO != null) {
            orderDAO.setOrd_state(researchOrder.getStatusClsf());
            orderDAO.setOrd_completion_estimate_date(researchOrder.getCompletionEstimate());
            orderDAO.setOrd_rejection_reason(researchOrder.getRejectionReason());
            orderDAO.setOrd_rejection_date(Utils.getDate(researchOrder.getRejectionDate()));
            ordersDbService.saveRecord(conn, orderDAO);
            if (researchOrder.getStatusClsf().equalsIgnoreCase(NtisOrderStatus.ORD_STS_CONFIRMED.getCode()) && researchOrder.getOcwId() == null) {
                NtisOrderCompletedWorksDAO completedWorksDAO = orderCompletedWorksDbService.newRecord();
                completedWorksDAO.setOcw_ord_id(researchOrder.getOrdId());
                completedWorksDAO.setOcw_res_person(researchOrder.getResponsiblePerson());
                completedWorksDAO.setOcw_completed_date(Utils.getDate(new Date()));
                orderCompletedWorksDbService.saveRecord(conn, completedWorksDAO);
            }
            if (researchOrder.getStatusClsf().equalsIgnoreCase(NtisOrderStatus.ORD_STS_FINISHED.getCode()) && researchOrder.getOcwId() != null) {
                NtisOrderCompletedWorksDAO completedWorksDAO = orderCompletedWorksDbService.loadRecord(conn, researchOrder.getOcwId());
                if (researchOrder.getResultsFile() != null && researchOrder.getResultsFile().getFil_key() != null) {
                    SprFilesDAO sprFile = filesDbService.loadRecordByKey(conn, fileStorageService.decryptFileKey(researchOrder.getResultsFile().getFil_key()));
                    filesDbService.markAsConfirmed(conn, sprFile);
                    completedWorksDAO.setOcw_fil_id(sprFile.getFil_id());
                } else {
                    completedWorksDAO.setOcw_fil_id(null);
                }
                completedWorksDAO.setOcw_smp_person(researchOrder.getSamplePerson());
                completedWorksDAO.setOcw_rsr_person(researchOrder.getResearchPerson());
                completedWorksDAO.setOcw_completed_date(Utils.getDate(new Date()));
                completedWorksDAO.setOcw_completed_works_description(researchOrder.getResearchComments());
                completedWorksDAO.setOcw_usr_id(userId);
                orderCompletedWorksDbService.saveRecord(conn, completedWorksDAO);
                NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, researchOrder.getWtfId());
                if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode())) {
                    facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
                    ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
                }
                boolean complies = true;
                for (ResearchCriteriaResultsModel result : researchOrder.getResults()) {
                    if (result.getResult() != null) {
                        NtisResearchesDAO research = new NtisResearchesDAO();
                        if (result.getResId() != null) {
                            research = researchesDbService.loadRecord(conn, result.getResId());
                        } else {
                            research = researchesDbService.newRecord();
                            research.setRes_ord_id(orderDAO.getOrd_id());
                            research.setRes_reserch_type(result.getCode());
                        }
                        research.setRes_ord_id(orderDAO.getOrd_id());
                        research.setRes_rn_id(result.getRn_id());
                        research.setRes_value(result.getResult());
                        research.setRes_research_date(researchOrder.getResearchDate());
                        research.setRes_sample_date(researchOrder.getSampleDate());
                        research.setRes_created(Utils.getDate(new Date()));
                        researchesDbService.saveRecord(conn, research);
                        if (result.getResult() != null && result.getResult().compareTo(result.getNorm()) > 0) {
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
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui arba INTS savininkui, pagal pateiktą užsakymo id
     * @param conn - prisijungimas prie DB
     * @param orderId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double orderId, Double usrId, String lang, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        Locale localeLT = new Locale("lt", "LT");
        SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy 'm.' MMMMM dd 'd.'", localeLT);

        // order info
        NtisOrdersDAO orderDAO = ordersDbService.loadRecord(conn, orderId);
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
            } else if (clientOrg.getC02() != null && clientOrg.getC02().equals(DbConstants.BOOLEAN_TRUE) && clientOrg.getOrg_email() != null) {
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
                """.formatted(NtisRolesConstants.PASL_ADMIN, NtisRolesConstants.LAB_SPECIALIST);
        List<SprUsersDAO> orgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, serviceProvider.getOrg_id(), roleCodes);
        List<String> orgEmails = new ArrayList<>();
        if (serviceProvider.getC02() != null && serviceProvider.getC02().equals(DbConstants.BOOLEAN_TRUE) && serviceProvider.getOrg_email() != null
                && !serviceProvider.getOrg_email().isBlank()) {
            orgEmails = Arrays.asList(serviceProvider.getOrg_email().split("\\s*,\\s*"));
        }

        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("serviceType", orderService.get("rfc_meaning"));
        context.put("orderId", Double.valueOf(orderDAO.getOrd_id()).intValue());
        if (orderDAO.getOrd_rejection_date() != null) {
            context.put("orderCancelDate", dateFormatLT.format(orderDAO.getOrd_rejection_date()));
        }
        context.put("clientName", clientName);
        if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CANCELLED.getCode())) {
            context.put("orderUrl", "research/sp-research-list/research-order/");
        } else {
            context.put("orderUrl", "research/owner-research-list/research-order/");
        }

        // send notifications and emails
        if (orderDAO.getOrd_state().equals(NtisOrderStatus.ORD_STS_CANCELLED.getCode())) {
            for (SprUsersDAO orgUser : orgUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), "NTIS_ORD_STS_NOTIF",
                        "ORD_STS_CANCEL_SUBJECT", "ORD_STS_CANCEL_BODY", context, NtisNtfRefType.RESEARCH.getCode(),
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
                templateSubject = "ORD_STS_CONFIRM_SUBJECT";
                templateBody = "ORD_STS_CONFIRM_BODY";
                messageSubject = NtisMessageSubject.MSG_SBJ_ORDER_CONFIRMED.getCode();
                if (!clientEmails.isEmpty()) {
                    for (String email : clientEmails) {
                        orderProcessRequest.createOrderConfirmedRequest(conn, usrId, orderDAO.getOrd_id(), email, Languages.getLanguageByCode(lang), context);
                    }
                }
            }
            if (orderDAO.getOrd_usr_id() != null) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, orderDAO.getOrd_id(), "NTIS_ORD_STS_NOTIF",
                        templateSubject, templateBody, context, NtisNtfRefType.RESEARCH.getCode(), messageSubject, new Date(), orderDAO.getOrd_usr_id(),
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
