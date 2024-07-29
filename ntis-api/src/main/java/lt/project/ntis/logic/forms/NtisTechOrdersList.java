package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.logic.forms.security.NtisTechOrdersListSecurityManager;
import lt.project.ntis.models.NtisOrderCompletedWorksRequest;
import lt.project.ntis.models.NtisOrdersRequest;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formos "Gautų paslaugų užsakymų sąrašas (tech. priež.)" (P2020) biznio logikai apibrėžti
 */
@Component
public class NtisTechOrdersList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService;

    private final NtisOrdersDBService ntisOrdersDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;
    
    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    public NtisTechOrdersList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager,
            NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService, NtisOrdersDBService ntisOrdersDBService,
            SprOrgUsersDBServiceImpl sprOrgUsersDBService, NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ntisOrderCompletedWorksDBService = ntisOrderCompletedWorksDBService;
        this.ntisOrdersDBService = ntisOrdersDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisWastewaterTreatmentFaciDBService = ntisWastewaterTreatmentFaciDBService;

    }

    @Override
    public String getFormName() {
        return "TECH_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "NTIS techninės priežiūros užsakymų sąrašas", "NTIS techninės priežiūros užsakymų sąrašas");
        addFormActionCRUD();
    }

    /**
     * Function will return a list of orders for provided organization id
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT ORD_ID, " //
                + " TO_CHAR(ORD_CREATED, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS ORD_CREATED, " //
                + " ORD_CREATED_IN_NTIS_PORTAL, " //
                + " COALESCE(RCM.RFT_DISPLAY_CODE, RCM.RFC_MEANING) AS ORD_METHOD, " //
                + " COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME) AS ORD_CUSTOMER, " //
                + " ORD_STATE, " //
                + " COALESCE(RCS.RFT_DISPLAY_CODE, RCS.RFC_MEANING) AS ORD_STATUS, " //
                + " TO_CHAR(OCW_COMPLETED_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS OCW_COMPLETED_DATE, " //
                + " AD.AD_ADDRESS AS ADDRESS " //
                + " FROM NTIS_ORDERS AS ORD " //
                + " INNER JOIN NTIS_SERVICES SRV ON SRV_ID = ORD_SRV_ID AND SRV_TYPE = 'PRIEZIURA' " //
                + " LEFT JOIN NTIS_ORDER_COMPLETED_WORKS ON ORD.ORD_ID = OCW_ORD_ID " //
                + " LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG_ID = ORD.ORD_ORG_ID " //
                + " LEFT JOIN SPR_PERSONS PER ON PER_ID = ORD.ORD_PER_ID " //
                + " LEFT JOIN SPR_REF_CODES_VW RCS ON RCS.RFC_CODE = ORD_STATE AND RCS.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RCS.RFT_LANG = ? " //
                + " LEFT JOIN SPR_REF_CODES_VW RCM ON RCM.RFC_CODE = ORD_CREATED_IN_NTIS_PORTAL AND RCM.RFC_DOMAIN = 'NTIS_ORDER_METHOD' AND RCM.RFT_LANG = ? " //
                + " LEFT JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON ORD.ORD_WTF_ID = WTF.WTF_ID " //
                + " LEFT JOIN NTIS_ADR_ADDRESSES AD ON WTF.WTF_AD_ID = AD.AD_ID ");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        // stmt.addSelectParam(orgId);
        stmt.addParam4WherePart(" srv.srv_org_id = ?::int", orgId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("ORD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME)", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_customer"));
        stmt.addParam4WherePart("ORD_CREATED_IN_NTIS_PORTAL", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_method"));
        stmt.addParam4WherePart("ORD_STATE", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_state"));
        stmt.addParam4WherePart("ORD_CREATED", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("OCW_COMPLETED_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("ocw_completed_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("AD_ADDRESS", StatementAndParams.PARAM_STRING, advancedParamList.get("address"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ORD_ID", "COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME)",
                        "COALESCE(RCM.RFT_DISPLAY_CODE, RCM.RFC_MEANING)", "COALESCE(RCS.RFT_DISPLAY_CODE, RCS.RFC_MEANING)",
                        "TO_CHAR(ORD_CREATED,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(OCW_COMPLETED_DATE,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')", "AD_ADDRESS"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord.ord_created desc, ord.ord_id desc");
        NtisTechOrdersListSecurityManager sqm = new NtisTechOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisTechOrdersList.ACTION_READ, NtisTechOrdersList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Function will save new completed order object to the DB. 
     * Before save function will check if user has right to perform this action (insert or update)
     * @param conn - connection to the DB
     * @param record - NtisOrderCompletedWorksRequest object
     * @param userId - session user id
     * @param orgId - session organization id
     * @throws Exception
     */
    public void setOrderCompletionDate(Connection conn, NtisOrderCompletedWorksRequest record, Double userId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_CREATE);
        NtisOrdersDAO orderDAO = ntisOrdersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, record.getOcw_ord_id());
        if (orderDAO != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, orgId)) {
            NtisOrderCompletedWorksDAO order = ntisOrderCompletedWorksDBService.loadRecordByParams(conn, "ocw_ord_id = ?::int",
                    new SelectParamValue(record.getOcw_ord_id()));
            if (order == null || order.getOcw_id() == null) {
                order = ntisOrderCompletedWorksDBService.newRecord();
            }
            order.setOcw_ord_id(record.getOcw_ord_id());
            order.setOcw_completed_date(record.getOcw_completed_date());
            order.setOcw_completed_works_description(record.getOcw_completed_works_description());
            ntisOrderCompletedWorksDBService.saveRecord(conn, order);
            
            NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, orderDAO.getOrd_wtf_id());
            if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode())) {
                facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
                ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Function will update order state in DB for provided order. 
     * Before update function will check if user has right to perform this action (insert or update)
     * @param conn - connection to the DB
     * @param record - NtisOrdersRequest object
     * @param userId - session user id
     * @param orgId - session organization id
     * @throws Exception
     */
    public void setOrderState(Connection conn, NtisOrdersRequest record, Double userId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_UPDATE);
        NtisOrdersDAO order = ntisOrdersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, Utils.getDouble(record.getOrd_id()));
        if (order != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, orgId)) {
            order.setOrd_state(record.getOrd_state());
            ntisOrdersDBService.updateRecord(conn, order);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

}
