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
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisWastewaterDeliveriesDAO;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisSewageDeliveryStatus;
import lt.project.ntis.logic.forms.processRequests.DeliveryProcessRequest;
import lt.project.ntis.logic.forms.security.NtisSludgeTreatmentDeliveriesListSecurityManager;
import lt.project.ntis.service.NtisWastewaterDeliveriesDBService;

/**
 * Klasė skirta formos "Nuotekų tvarkymo pristatymų sąrašas (vandentvarka)" biznio logikai apibrėžti
 */
@Component
public class NtisSludgeTreatmentDeliveriesList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisWastewaterDeliveriesDBService ntisWastewaterDeliveriesDBService;

    private final SprOrganizationsDBService organizationsDBService;

    private final NtisNotificationsManager ntisNotifications;

    private final DeliveryProcessRequest deliveryProcessRequest;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisSludgeTreatmentDeliveriesList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager,
            NtisWastewaterDeliveriesDBService ntisWastewaterDeliveriesDBService, SprOrganizationsDBService organizationsDBService,
            NtisNotificationsManager ntisNotifications, DeliveryProcessRequest deliveryProcessRequest, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ntisWastewaterDeliveriesDBService = ntisWastewaterDeliveriesDBService;
        this.organizationsDBService = organizationsDBService;
        this.ntisNotifications = ntisNotifications;
        this.deliveryProcessRequest = deliveryProcessRequest;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų tvarkymo pristatymai", "Nuotekų tvarkymo pristatymų sąrašas (vandentvarka)");
        addFormActions(FormBase.ACTION_CREATE);
        addFormActions(FormBase.ACTION_READ);
        addFormActions(FormBase.ACTION_UPDATE);
    }

    /**
     * Function will return a list of sludge deliveries for provided organization.
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSludgeTreatmentDeliveriesList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        SELECT WD_ID,
                               TO_CHAR(WD_DELIVERY_DATE, '%s') AS WD_DELIVERY_DATE,
                               WD_DELIVERED_QUANTITY,
                               CR_REG_NO,
                               WD_STATE,
                               COALESCE(WST.RFT_DISPLAY_CODE, WST.RFC_MEANING) AS WD_STATE_MEANING,
                               ORG_NAME
                        FROM NTIS_WASTEWATER_DELIVERIES AS WD
                        INNER JOIN NTIS_WASTEWATER_TREATMENT_ORG WTO ON WTO.WTO_ID = WD.WD_WTO_ID
                        INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = WTO.WTO_ORG_ID AND OU.OU_USR_ID = ?::int and CURRENT_DATE BETWEEN OU.OU_DATE_FROM and COALESCE(OU.OU_DATE_TO, now())
                        LEFT JOIN NTIS_CARS ON CR_ID = WD_CR_ID
                        LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = CR_ORG_ID
                        JOIN SPR_REF_CODES_VW WST ON WST.RFC_CODE = WD_STATE AND WST.RFC_DOMAIN = 'SEWAGE_DELIV_STATUS' AND WST.RFT_LANG = ?
                        WHERE WD_STATE != '%s'
                        AND WTO.WTO_ORG_ID = ?
                        """
                        .formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                                NtisSewageDeliveryStatus.SWG_DLV_STS_USED.getCode()));
        stmt.setWhereExists(true);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(orgId);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addParam4WherePart("WD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wd_id"));
        stmt.addParam4WherePart("WD_DELIVERED_QUANTITY", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wd_delivered_quantity"));
        stmt.addParam4WherePart("CR_REG_NO", StatementAndParams.PARAM_STRING, advancedParamList.get("cr_reg_no"));
        stmt.addParam4WherePart("WD_STATE = ? ", paramList.get("p_wd_state"));
        stmt.addParam4WherePart("ORG_ID = ? ", Utils.getDouble(paramList.get("p_org_id")));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("WD_DELIVERY_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("wd_delivery_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("WD_ID", "WD_DELIVERED_QUANTITY", "CR_REG_NO", "COALESCE(WST.RFT_DISPLAY_CODE, WST.RFC_MEANING)",
                        "ORG_NAME", "TO_CHAR(WD_DELIVERY_DATE,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by wd_delivery_date desc, wd_id desc");
        NtisSludgeTreatmentDeliveriesListSecurityManager sqm = new NtisSludgeTreatmentDeliveriesListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisSludgeTreatmentDeliveriesList.ACTION_READ, NtisSludgeTreatmentDeliveriesList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Function will update wastewater delivery data. Before update will be performed below listed actions:
     *      1. check if user has right to perform this action (update)
     *      2. check if record organization id matches provided organization id
     *      3. validate provided data
     *      4. save data to the DB
     * @param conn - connection to the DB
     * @param record - NtisWastewaterDeliveriesDAO object that should be stored in DB
     * @param userId - session user id
     * @param orgId - organization id
     * @throws Exception
     */
    public void setDeliveryData(Connection conn, NtisWastewaterDeliveriesDAO record, Double userId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSludgeTreatmentDeliveriesList.ACTION_UPDATE);
        NtisWastewaterDeliveriesDAO delivery = ntisWastewaterDeliveriesDBService.loadRecordByIdAndWtoOrgId(conn, orgId, record.getWd_id());
        if (delivery != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, orgId)) {
            delivery.setWd_description(record.getWd_description());
            delivery.setWd_rejection_reason(record.getWd_rejection_reason());
            delivery.setWd_delivered_wastewater_description(record.getWd_delivered_wastewater_description());
            delivery.setWd_state(record.getWd_state());
            delivery.setWd_accepted_sewage_quantity(record.getWd_accepted_sewage_quantity());
            ntisWastewaterDeliveriesDBService.saveRecord(conn, delivery);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui, pagal pateiktą pristatymo id
     * @param conn - prisijungimas prie DB
     * @param deliveryId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @parma userId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double deliveryId, Double usrId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        // delivery info
        NtisWastewaterDeliveriesDAO deliveryDAO = ntisWastewaterDeliveriesDBService.loadRecordByIdAndWtoOrgId(conn, orgId, deliveryId);
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
            context.put("deliveryId", deliveryId.intValue());

            // send notifications and emails
            String templateSubject = "";
            String templateBody = "";
            String messageSubject = "";

            if (deliveryDAO.getWd_state().equals(NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED.getCode())) {
                templateSubject = "DELIV_CONFIRM_SUBJECT";
                templateBody = "DELIV_CONFIRM_BODY";
                messageSubject = NtisMessageSubject.MSG_SBJ_DELIVERY_CONFIRMED.getCode();
                for (String email : serviceProviderEmails) {
                    deliveryProcessRequest.createDeliveryConfirmedRequest(conn, usrId, deliveryId, email, Languages.getLanguageByCode(lang), context);
                }
            } else if (deliveryDAO.getWd_state().equals(NtisSewageDeliveryStatus.SWG_DLV_STS_REJECTED.getCode())) {
                templateSubject = "DELIV_REJECT_SUBJECT";
                templateBody = "DELIV_REJECT_BODY";
                messageSubject = NtisMessageSubject.MSG_SBJ_DELIVERY_REJECTED.getCode();
                for (String email : serviceProviderEmails) {
                    deliveryProcessRequest.createDeliveryRejectedRequest(conn, usrId, deliveryId, email, Languages.getLanguageByCode(lang), context);
                }
            }

            for (SprUsersDAO user : serviceProviderUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, deliveryId, "NTIS_DELIVERY_NOTIF", templateSubject,
                        templateBody, context, NtisNtfRefType.DELIVERY.getCode(), messageSubject, new Date(), user.getUsr_id(), serviceProvider.getOrg_id(),
                        null);
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }
}
