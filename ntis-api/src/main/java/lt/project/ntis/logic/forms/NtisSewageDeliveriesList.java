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
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
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
import lt.project.ntis.logic.forms.security.NtisSewageDeliveriesListSecurityManager;
import lt.project.ntis.service.NtisDeliveryFacilitiesDBService;
import lt.project.ntis.service.NtisUsedSludgesDBService;
import lt.project.ntis.service.NtisWastewaterDeliveriesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentOrgDBService;

/**
 * Klasė skirta formos "Nuotekų tvarkymo užsakymų sąrašas (pasl. teikejas)"  biznio logikai apibrėžti
 */

@Component
public class NtisSewageDeliveriesList extends FormBase {

    public static final String ACTION_CANCEL = "CANCEL";

    public static String ACTION_CANCEL_DESC = "Action cancel right";

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbstatementManager;

    private final NtisWastewaterDeliveriesDBService wastewaterDeliveriesDbService;

    private final NtisDeliveryFacilitiesDBService deliveryFacilitiesDbService;

    private final NtisUsedSludgesDBService usedSludgesDbService;

    private final SprOrganizationsDBService organizationsDBService;

    private final NtisWastewaterTreatmentOrgDBService ntisWastewaterTreatmentOrgDBService;

    private final DeliveryProcessRequest deliveryProcessRequest;

    private final NtisNotificationsManager ntisNotifications;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisSewageDeliveriesList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbstatementManager,
            NtisWastewaterDeliveriesDBService wastewaterDeliveriesDbService, NtisDeliveryFacilitiesDBService deliveryFacilitiesDbService,
            NtisUsedSludgesDBService usedSludgesDbService, SprOrganizationsDBService organizationsDBService,
            NtisWastewaterTreatmentOrgDBService ntisWastewaterTreatmentOrgDBService, DeliveryProcessRequest deliveryProcessRequest,
            NtisNotificationsManager ntisNotifications, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbstatementManager = dbstatementManager;
        this.wastewaterDeliveriesDbService = wastewaterDeliveriesDbService;
        this.deliveryFacilitiesDbService = deliveryFacilitiesDbService;
        this.usedSludgesDbService = usedSludgesDbService;
        this.organizationsDBService = organizationsDBService;
        this.ntisWastewaterTreatmentOrgDBService = ntisWastewaterTreatmentOrgDBService;
        this.deliveryProcessRequest = deliveryProcessRequest;
        this.ntisNotifications = ntisNotifications;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_SEWAGE_DELIVERIES_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų (dumblo) pristatymų valyti sąrašas", "Nuotekų (dumblo) pristatymų valyti sąrašo forma");
        this.addFormActionCRUD();
        addFormAction(ACTION_CANCEL, ACTION_CANCEL_DESC, ACTION_CANCEL);
    }

    /**
     * Metodas grąžins paslaugų teikėjo pateiktų nuotekų tvarkymo pristatymų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param lang - sesijos kalba
     * @return json objektas
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, Double usrId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveriesList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT WD.WD_ID, " + //
                "TO_CHAR(WD.WD_DELIVERY_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS WD_DELIVERY_DATE, " + //
                "WD.WD_DELIVERED_QUANTITY, " + //
                "CR.CR_REG_NO, " + //
                "WD.WD_STATE as WD_STATE_CLSF, " + //
                "WTO.WTO_NAME, " + //
                "coalesce(RFC.RFC_MEANING, WD.WD_STATE) as WD_STATE, " + //
                "CASE WHEN WD2.WD_ID IS NULL THEN 'N' ELSE 'Y' END as HAS_CHILD, " + //
                "WTO.WTO_ADDRESS as ADDRESS " + //
                "FROM NTIS_WASTEWATER_DELIVERIES WD " + //
                "LEFT JOIN NTIS_WASTEWATER_TREATMENT_ORG WTO ON WTO.WTO_ID = WD.WD_WTO_ID " + //
                "INNER JOIN SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = WD.WD_ORG_ID AND ORG.ORG_ID = ? " + //
                "INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = ORG.ORG_ID AND OU.OU_USR_ID = ?::int and CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) "
                + //
                "LEFT JOIN NTIS_CARS CR ON CR.CR_ID = WD.WD_CR_ID AND CR.CR_ORG_ID = ORG.ORG_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = WD.WD_STATE AND RFC.RFC_DOMAIN = 'SEWAGE_DELIV_STATUS' AND RFC.RFT_LANG = ? " + //
                "LEFT JOIN NTIS_WASTEWATER_DELIVERIES WD2 ON WD.WD_ID = WD2.WD_WD_ID");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("WD.WD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wd_id"));
        stmt.addParam4WherePart("WD.WD_DELIVERY_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("wd_delivery_date"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("WD.WD_DELIVERED_QUANTITY", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wd_delivered_quantity"));
        stmt.addParam4WherePart("CR_REG_NO", StatementAndParams.PARAM_STRING, advancedParamList.get("cr_reg_no"));
        stmt.addParam4WherePart("WTO_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_name"));
        stmt.addParam4WherePart("WD.WD_STATE", StatementAndParams.PARAM_STRING, advancedParamList.get("wd_state"));
        stmt.addParam4WherePart("WTO_ADDRESS", StatementAndParams.PARAM_STRING, advancedParamList.get("address"));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("WD.WD_ID", "WD.WD_DELIVERED_QUANTITY", "CR_REG_NO", "WTO_NAME", "RFC.RFC_MEANING",
                        "TO_CHAR(WD.WD_DELIVERY_DATE,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')", "WTO_ADDRESS"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by wd_delivery_date desc, wd_id desc");
        NtisSewageDeliveriesListSecurityManager sqm = new NtisSewageDeliveriesListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(NtisSewageDeliveriesList.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Pagal perduodamą nuotekų tvarkymo pristatymo id, metodas ištrins nuotekų tvarkymo įrašą
     * @param conn - prisijungimas prie DB
     * @param recordIdentifier - įrašo identifikatorius
     * @param orgId - sesijos organizacijos id
     * @param userId - sesijos naudotojo id
     * @throws NumberFormatException, Exception
     */
    public void delete(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double userId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveriesList.ACTION_DELETE);
        NtisWastewaterDeliveriesDAO deliveryDAO = wastewaterDeliveriesDbService.loadRecordByParams(conn, " wd_id = ?::int and wd_org_id = ?::int ",
                new SelectParamValue(recordIdentifier.getIdAsDouble()), new SelectParamValue(orgId));
        if (deliveryDAO != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, orgId)) {
            deliveryFacilitiesDbService.deleteRecordByWdId(conn, recordIdentifier.getIdAsDouble());
            usedSludgesDbService.deleteRecordByWdId(conn, recordIdentifier.getIdAsDouble());
            wastewaterDeliveriesDbService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Pagal perduodamą nuotekų tvarkymo pristatymo id, metodas atšauks nuotekų tvarkymo įrašą
     * @param conn - prisijungimas prie DB
     * @param recordIdentifier - įrašo identifikatorius
     * @param orgId - sesijos organizacijos id
     * @param userId - sesijos naudotojo id
     * * @param lang - sesijos kalba
     * @throws NumberFormatException, Exception
     */
    public void cancelSewageDelivery(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double userId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSewageDeliveriesList.ACTION_DELETE);
        NtisWastewaterDeliveriesDAO deliveryDAO = wastewaterDeliveriesDbService.loadRecordByParams(conn, " wd_id = ?::int and wd_org_id = ?::int ",
                new SelectParamValue(recordIdentifier.getIdAsDouble()), new SelectParamValue(orgId));
        if (deliveryDAO != null && sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, orgId)) {
            deliveryDAO.setWd_state(NtisSewageDeliveryStatus.SWG_DLV_STS_CANCELLED.getCode());
            wastewaterDeliveriesDbService.saveRecord(conn, deliveryDAO);
            sendNotification(conn, deliveryDAO, userId, orgId, lang);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    private void sendNotification(Connection conn, NtisWastewaterDeliveriesDAO deliveryDAO, Double usrId, Double orgId, String lang) throws Exception {
        // delivery info
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
            Map<String, Object> context = new HashMap<>();
            context.put("serviceProvider", serviceProvider.getOrg_name());
            context.put("deliveryId", deliveryDAO.getWd_id().intValue());

            // send notifications and emails
            for (String email : waterManagerEmails) {
                deliveryProcessRequest.createDeliveryCancelledRequest(conn, usrId, deliveryDAO.getWd_id(), email, Languages.getLanguageByCode(lang), context);
            }

            for (SprUsersDAO user : waterManagerUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, deliveryDAO.getWd_id(), "NTIS_DELIVERY_NOTIF",
                        "DELIV_CANCEL_SUBJECT", "DELIV_CANCEL_BODY", context, NtisNtfRefType.DELIVERY.getCode(),
                        NtisMessageSubject.MSG_SBJ_DELIVERY_CANCELLED.getCode(), new Date(), user.getUsr_id(), waterManager.getOrg_id(), null);
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }
}
