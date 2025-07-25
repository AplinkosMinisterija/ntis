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
        StatementAndParams stmt = new StatementAndParams("""
                WITH ord as (
                SELECT STRING_AGG(ord.ord_id::text, ', ') as orders,
                                df.df_wd_id as wd
                                FROM ntis_orders ord
                          INNER JOIN ntis_delivery_facilities df ON ord.ord_id = df.df_ord_id
                                                AND ord.ord_wtf_id = df.df_wtf_id
                            GROUP BY df.df_wd_id
                )
                    SELECT wd.wd_id,
                           TO_CHAR(wd.wd_delivery_date, '%s') as wd_delivery_date,
                           wd.wd_delivered_quantity,
                           CASE WHEN cr.cr_type is not null
                                  THEN cr.cr_reg_no || ' (' || crc.rfc_meaning || ')'
                                ELSE cr.cr_reg_no
                           END as cr_reg_no,
                           wd.wd_state as wd_state_clsf,
                           wto.wto_name,
                           COALESCE(rfc.rfc_meaning, wd.wd_state) as wd_state,
                           CASE WHEN wd2.wd_id IS NULL
                                  THEN 'N'
                                ELSE 'Y'
                           END as has_child,
                           wto.wto_address as address,
                           ord.orders as orders
                      FROM ntis_wastewater_deliveries WD
                 LEFT JOIN ntis_wastewater_treatment_org wto ON wto.wto_id = wd.wd_wto_id
                 LEFT JOIN ord ord ON ord.wd = wd.wd_id
                 INNER JOIN spr_organizations org ON org.org_id = wd.wd_org_id
                                     AND org.org_id = ?::int
                 INNER JOIN spr_org_users ou ON ou.ou_org_id = org.org_id
                                     AND ou.ou_usr_id = ?::int
                                     AND CURRENT_DATE BETWEEN ou.ou_date_from AND COALESCE(ou.ou_date_to, now())
                 LEFT JOIN ntis_cars cr ON cr.cr_id = wd.wd_cr_id AND cr.cr_org_id = org.org_id
                 LEFT JOIN spr_ref_codes_vw rfc ON rfc.rfc_code = wd.wd_state
                                     AND rfc.rfc_domain = 'SEWAGE_DELIV_STATUS'
                                     AND rfc.rft_lang = ?
                 LEFT JOIN ntis_wastewater_deliveries wd2 ON wd.wd_id = wd2.wd_wd_id
                 LEFT JOIN spr_ref_codes_vw crc ON crc.rfc_domain = 'NTIS_CAR_TYPE'
                                     AND crc.rfc_code = cr.cr_type
                                     AND crc.rft_lang = ?
                """.formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("WD.WD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wd_id"));
        stmt.addParam4WherePart("WD.WD_DELIVERY_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("wd_delivery_date"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("WD.WD_DELIVERED_QUANTITY", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wd_delivered_quantity"));
        stmt.addParam4WherePart("CR_REG_NO", StatementAndParams.PARAM_STRING, advancedParamList.get("cr_reg_no"));
        stmt.addParam4WherePart("WTO_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_name"));
        stmt.addParam4WherePart("WD.WD_STATE", StatementAndParams.PARAM_STRING, advancedParamList.get("wd_state"));
        stmt.addParam4WherePart("ord.orders", StatementAndParams.PARAM_STRING, advancedParamList.get("orders"));
        stmt.addParam4WherePart("WTO_ADDRESS", StatementAndParams.PARAM_STRING, advancedParamList.get("address"));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("WD.WD_ID", "ord.orders", "WD.WD_DELIVERED_QUANTITY", "CR_REG_NO", "CRC.RFC_MEANING", "WTO_NAME", "RFC.RFC_MEANING",
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
