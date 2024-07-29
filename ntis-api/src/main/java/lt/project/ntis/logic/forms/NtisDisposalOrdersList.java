package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisCarsDAO;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.logic.forms.security.NtisDisposalOrdersListSecurityManager;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formos "Gautų paslaugų užsakymų sąrašas (išvežimas)" (P2010) biznio logikai apibrėžti
 */
@Component
public class NtisDisposalOrdersList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService;

    private final NtisOrdersDBService ntisOrdersDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    public NtisDisposalOrdersList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager,
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
        return "NTIS_DISPOSAL_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų išvežimo paslaugos užsakymai", "Gautų paslaugų užsakymų sąrašas (išvežimas)");
        addFormActions(FormBase.ACTION_READ);
        addFormActions(FormBase.ACTION_UPDATE);
    }

    /**
     * Function will return a list of disposal orders for provided organization.
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        StatementAndParams stmt = new StatementAndParams();
        String coreFromPatrt = " order by ord_created desc, ord_id desc limit "
                + ((params.getPagingParams().getPage_size() + params.getPagingParams().getSkip_rows()) * 2);
        String ordStatusStatement = "";
        if (advancedParamList.get("ord_state") != null && advancedParamList.get("ord_state").getParamValue() != null
                && advancedParamList.get("ord_state").getParamValue().getValues() != null) {
            boolean valueAdded = false;
            ordStatusStatement = " and ord_state in(";
            for (String value : advancedParamList.get("ord_state").getParamValue().getValues()) {
                String valueSt = "";
                if (valueAdded) {
                    valueSt = ",";
                }
                valueSt = valueSt + "'" + value + "'";
                ordStatusStatement = ordStatusStatement + valueSt;
                valueAdded = true;
            }
            ordStatusStatement = ordStatusStatement + ")";
        }

        stmt.setStatement("""
                SELECT ORD_ID,
                        TO_CHAR(ORD_CREATED, '%s') AS ORD_CREATED,
                        ORD_CREATED_IN_NTIS_PORTAL,
                        COALESCE(RCM.RFT_DISPLAY_CODE, RCM.RFC_MEANING) AS ORD_METHOD,
                        COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME) AS ORD_CUSTOMER,
                        ORD_STATE,
                        COALESCE(RCS.RFT_DISPLAY_CODE, RCS.RFC_MEANING) AS ORD_STATUS_MEANING,
                        OCW_DISCHARGED_SLUDGE_AMOUNT,
                        WD_ID,
                        WD_STATE,
                        COALESCE(RCW.RFT_DISPLAY_CODE, RCW.RFC_MEANING) AS SEWAGE_DELIVERY_STATUS,
                        TO_CHAR(ORD_REMOVED_SEWAGE_DATE, '%s') AS ORD_REMOVED_SEWAGE_DATE,
                        AD.AD_ADDRESS AS ADDRESS
                FROM (select ORD_ID,
                            ORD_ORG_ID,
                            SRV_ORG_ID,
                            ORD_PER_ID,
                            ORD_STATE,
                            ORD_CREATED_IN_NTIS_PORTAL,
                            ORD_CREATED,
                            ORD_REMOVED_SEWAGE_DATE,
                            (SELECT FIRST_VALUE(DF_WD_ID) OVER (ORDER BY DF_ID DESC)
                               FROM NTIS_DELIVERY_FACILITIES
                              WHERE DF_ORD_ID = ORD_ID
                              LIMIT 1
                            ) LAST_DF_WD_ID,
                            ORD_WTF_ID
                        FROM NTIS_ORDERS
                        JOIN NTIS_SERVICES SRV ON SRV_ID = ORD_SRV_ID AND SRV_TYPE = 'VEZIMAS'
                        where SRV_ORG_ID = ?::int
                        """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)) + //
                ordStatusStatement + //
                coreFromPatrt + //
                """
                                 ) as ORD
                         LEFT JOIN NTIS_ORDER_COMPLETED_WORKS ON ORD.ORD_ID = OCW_ORD_ID
                         LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG_ID = ORD.ORD_ORG_ID
                         LEFT JOIN SPR_PERSONS PER ON PER_ID = ORD.ORD_PER_ID
                         INNER JOIN SPR_ORG_USERS OU ON SRV_ORG_ID = OU.OU_ORG_ID AND OU.OU_USR_ID = ?::int AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now())
                         LEFT JOIN NTIS_WASTEWATER_DELIVERIES WD ON WD_ID = ORD.LAST_DF_WD_ID
                         JOIN SPR_REF_CODES_VW RCS ON RCS.RFC_CODE = ORD_STATE AND RCS.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RCS.RFT_LANG = ?
                         LEFT JOIN SPR_REF_CODES_VW RCW ON RCW.RFC_CODE = WD_STATE AND RCW.RFC_DOMAIN = 'SEWAGE_DELIV_STATUS' AND RCW.RFT_LANG = RCS.RFT_LANG
                         JOIN SPR_REF_CODES_VW RCM ON RCM.RFC_CODE = ORD_CREATED_IN_NTIS_PORTAL AND RCM.RFC_DOMAIN = 'NTIS_ORDER_METHOD' AND RCM.RFT_LANG = RCS.RFT_LANG
                         LEFT JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON ORD.ORD_WTF_ID = WTF.WTF_ID
                         LEFT JOIN NTIS_ADR_ADDRESSES AD ON WTF.WTF_AD_ID = AD.AD_ID
                        """);
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("ORD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("OCW_DISCHARGED_SLUDGE_AMOUNT", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ocw_discharged_sludge_amount"));
        stmt.addParam4WherePart("COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME)", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_customer"));
        stmt.addParam4WherePart("ORD_CREATED_IN_NTIS_PORTAL", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_method"));
        stmt.addParam4WherePart("ORD_STATE", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_state"));
        stmt.addParam4WherePart("WD_STATE", StatementAndParams.PARAM_STRING, advancedParamList.get("wd_state"));
        stmt.addParam4WherePart("ORD_CREATED", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ORD_REMOVED_SEWAGE_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_removed_sewage_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("AD_ADDRESS", StatementAndParams.PARAM_STRING, advancedParamList.get("address"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ORD_ID", "COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME)", "OCW_DISCHARGED_SLUDGE_AMOUNT",
                        "COALESCE(RCM.RFT_DISPLAY_CODE, RCM.RFC_MEANING)", "COALESCE(RCS.RFT_DISPLAY_CODE, RCS.RFC_MEANING)",
                        "COALESCE(RCW.RFT_DISPLAY_CODE, RCW.RFC_MEANING)",
                        "TO_CHAR(ORD_CREATED,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(ORD_REMOVED_SEWAGE_DATE,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')", "AD_ADDRESS"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord.ord_created desc, ord.ord_id desc");
        NtisDisposalOrdersListSecurityManager sqm = new NtisDisposalOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_READ, FormBase.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Function will return a list of provided organization's cars that are in use.
     * @param conn - connection to the DB
     * @param orgId - provided organization id
     * @param usrId - session user id
     * @return array of NtisCarsDAO objects
     * @throws Exception
     */
    public List<NtisCarsDAO> getOrgCarsList(Connection conn, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisDisposalOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT CR_ID, " //
                + " CR_REG_NO, " //
                + " CR_MODEL " //
                + " FROM NTIS_CARS CR" //
                + " INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = CR.CR_ORG_ID AND OU.OU_USR_ID = ?::int and CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) "
                + " WHERE CR_ORG_ID = ?::int " //
                + " AND " + dbStatementManager.getPeriodValidationForCurrentDateStr("CR.CR_DATE_FROM", "CR.CR_DATE_TO", false));
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisCarsDAO.class);
    }

    /**
     * Function will update Order state to FINISHED and save provided NtisOrderCompletedWorksDAO object to the DB. Before update will be performed below listed actions:
     *      1. check if user has right to perform this action (update)
     *      2. check if record organization id matches provided organization id
     *      3. save data to the DB
     * @param conn - connection to the DB
     * @param record - NtisOrderCompletedWorksDAO object that should be stored in DB
     * @param orgId - provided organization id
     * @param usrId - session user id
     * @throws Exception
     */
    public void setOrderCompletionData(Connection conn, NtisOrderCompletedWorksDAO record, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisDisposalOrdersList.ACTION_UPDATE);
        NtisOrdersDAO order = ntisOrdersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, record.getOcw_ord_id());
        if (order != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            NtisOrderCompletedWorksDAO orderCompleted = ntisOrderCompletedWorksDBService.loadRecordByParams(conn, " ocw_ord_id = ?::int ",
                    new SelectParamValue(order.getOrd_id()));
            if (orderCompleted != null) {
                record.setOcw_id(orderCompleted.getOcw_id());
            }
            ntisOrderCompletedWorksDBService.saveRecord(conn, record);
            this.setOrderState(conn, record, orgId, usrId);
            NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, order.getOrd_wtf_id());
            if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode())) {
                facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
                ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    private void setOrderState(Connection conn, NtisOrderCompletedWorksDAO record, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisDisposalOrdersList.ACTION_UPDATE);
        NtisOrdersDAO order = ntisOrdersDBService.loadRecordByIdAndSrvOrgId(conn, orgId, record.getOcw_ord_id());
        if (order != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            order.setOrd_state(NtisOrderStatus.ORD_STS_FINISHED.getCode());
            order.setOrd_removed_sewage_date(record.getOcw_completed_date());
            ntisOrdersDBService.updateRecord(conn, order);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }
}