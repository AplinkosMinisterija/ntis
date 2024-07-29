package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.model.NtisNotificationContactsModel;
import lt.project.ntis.logic.forms.model.NtisNotificationViewModel;

/**
 * Klasė skirta formos "Pranešimo peržiūra" (P2110, P2190) biznio logikai apibrėžti
 */
@Component
public class NtisNotificationView extends FormBase {

    public static String MINISTRY_ORG_ID = "MINISTRY_ORG_ID";

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Override
    public String getFormName() {
        return "NTIS_NOTIFICATION_VIEW";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Pranešimo peržiūra", "Informacinio pranešimo peržiūra");
        addFormActions(ACTION_READ);
        addFormAction(NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC,
                NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC);
        addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC);
        addFormAction(NtisCommonActionsConstants.WATER_MANAGER_ACTIONS, NtisCommonActionsConstants.WATER_MANAGER_ACTIONS_DESC,
                NtisCommonActionsConstants.WATER_MANAGER_ACTIONS_DESC);
        addFormAction(NtisCommonActionsConstants.SYSTEM_ADMIN_ACTIONS, NtisCommonActionsConstants.SYSTEM_ADMIN_ACTIONS_DESC,
                NtisCommonActionsConstants.SYSTEM_ADMIN_ACTIONS_DESC);
        addFormAction(NtisCommonActionsConstants.NEW_ORG_ACTIONS, NtisCommonActionsConstants.NEW_ORG_ACTIONS_DESC,
                NtisCommonActionsConstants.NEW_ORG_ACTIONS_DESC);
    }

    /**
     * Metodas grąžina pranešimo informaciją pagal pateiktą ID ir naudotojo sesijos informaciją.
     * @param conn - prisijungimas prie DB
     * @param ntfId - pranešimo ID
     * @param usrId - sesijos naudotojo ID
     * @param orgId - sesijos organizacijos ID
     * @param rolId - sesijos rolės ID
     * @return NtisNotificationViewModel objektas
     * @throws Exception
     */
    public NtisNotificationViewModel loadNotification(Connection conn, Double ntfId, Double usrId, Double orgId, Double rolId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisNotificationsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT ntf_title AS subject,
                        ntf_message AS body,
                        ntf_creation_date AS date,
                        ntf_reference AS refId,
                        c01 AS refType,
                        c02 AS msgSubject
                FROM spr_notifications n
                WHERE ntf_id = ?::int AND (ntf_usr_id = ?::int OR ntf_org_id = ?::int OR ntf_rol_id = ?::int)
                                """);
        stmt.addSelectParam(ntfId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(rolId);
        List<NtisNotificationViewModel> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisNotificationViewModel.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No notification information was found");
        }
        NtisNotificationViewModel notification = queryResult.get(0);
        if (notification.getRefType() != null && !notification.getRefType().equalsIgnoreCase(NtisNtfRefType.SCHEDULER.getCode())
                && !notification.getRefType().equalsIgnoreCase(NtisNtfRefType.CENTRALIZED_WASTEWATER.getCode())) {
            notification.setContactInfo(loadContactInfo(conn, notification.getRefId(), notification.getRefType()));
        }
        return notification;
    }

    private NtisNotificationContactsModel loadContactInfo(Connection conn, Double refId, String refType) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        if (refType.equals(NtisNtfRefType.CONTRACT.getCode())) {
            stmt.setStatement("""
                    SELECT coalesce(cli_org.org_name, cli_per.per_name || ' ' || cli_per.per_surname) as clientName,
                           cot_client_email as clientEmail,
                           cot_client_phone_no as clientPhone,
                           org.org_name as orgName,
                           org.org_email as orgEmail,
                           org.org_phone as orgPhone
                    FROM ntis_contracts cot
                    LEFT JOIN spr_organizations cli_org on cli_org.org_id = cot_org_id
                    LEFT JOIN spr_persons cli_per on per_id = cot_per_id
                    JOIN ntis_contract_services cs on cot_id = cs_cot_id
                    JOIN ntis_services srv on srv_id = cs_srv_id
                    JOIN spr_organizations org ON org.org_id = srv_org_id
                    WHERE cot_id = ?
                                    """);
            stmt.addSelectParam(refId);
        } else if (refType.equals(NtisNtfRefType.ORDER.getCode()) || refType.equals(NtisNtfRefType.RESEARCH.getCode())) {
            stmt.setStatement("""
                    SELECT coalesce(cli_org.org_name, cli_per.per_name || ' ' || cli_per.per_surname) as clientName,
                           coalesce(ord_email, coalesce(cli_org.org_email, cli_per.per_email)) as clientEmail,
                           coalesce(ord_phone_number, coalesce(cli_org.org_phone, cli_per.per_phone_number)) as clientPhone,
                           org.org_name as orgName,
                           org.org_email as orgEmail,
                           org.org_phone as orgPhone
                    FROM ntis_orders ord
                    LEFT JOIN spr_organizations cli_org on cli_org.org_id = ord_org_id
                    LEFT JOIN spr_persons cli_per on per_id = ord_per_id
                    JOIN ntis_services srv on srv_id = ord_srv_id
                    JOIN spr_organizations org ON org.org_id = srv_org_id
                    WHERE ord_id = ?::int
                                        """);
            stmt.addSelectParam(refId);
        } else if (refType.equals(NtisNtfRefType.SRV_REQ.getCode())) {
            stmt.setStatement("""
                     SELECT cli_org.org_name as clientName,
                            sr.sr_email as clientEmail,
                            sr.sr_phone as clientPhone,
                            org.org_name as orgName,
                            org.org_email as orgEmail,
                            org.org_phone as orgPhone
                    FROM ntis_service_requests sr
                    JOIN spr_organizations cli_org on cli_org.org_id = sr_org_id
                    JOIN spr_organizations org ON org.org_id = ?
                    WHERE sr_id = ?
                                                       """);
            stmt.addSelectParam(Utils.getDouble(dbPropertyManager.getPropertyByName(MINISTRY_ORG_ID, null)));
            stmt.addSelectParam(refId);
        } else if (refType.equals(NtisNtfRefType.DELIVERY.getCode())) {
            stmt.setStatement("""
                    SELECT cli_org.org_name as clientName,
                            cli_org.org_email as clientEmail,
                            cli_org.org_phone as clientPhone,
                            org.org_name as orgName,
                            org.org_email as orgEmail,
                            org.org_phone as orgPhone
                    FROM ntis_wastewater_deliveries wd
                    JOIN spr_organizations cli_org on cli_org.org_id = wd_org_id
                    JOIN ntis_wastewater_treatment_org wto on wto_id = wd_wto_id
                    JOIN spr_organizations org ON org.org_id = wto_org_id
                    WHERE wd_id = ?
                                                      """);
            stmt.addSelectParam(refId);
        } else if (refType.equals(NtisNtfRefType.SYSTEM.getCode())) {
            stmt.setStatement("""
                    SELECT cli_org.org_name as clientName,
                            cli_org.org_email as clientEmail,
                            cli_org.org_phone as clientPhone,
                            org.org_name as orgName,
                            org.org_email as orgEmail,
                            org.org_phone as orgPhone
                    FROM spr_organizations cli_org
                    JOIN spr_organizations org ON org.org_id = ?
                    WHERE cli_org.org_id = ?
                                                      """);
            stmt.addSelectParam(Utils.getDouble(dbPropertyManager.getPropertyByName(MINISTRY_ORG_ID, null)));
            stmt.addSelectParam(refId);
        } else if (refType.equals(NtisNtfRefType.FUA_AGREEMENT.getCode())) {
            stmt.setStatement("""
                    SELECT  usr_person_name ||' '|| usr_person_surname as orgName,
                            usr_phone_number as orgPhone
                    FROM ntis_facility_update_agreement fua
                    JOIN spr_users usr ON usr_id = fua_confirmed_usr_id
                    WHERE fua_id = ?::int
                              """);
            stmt.addSelectParam(refId);
        } else if (refType.equals(NtisNtfRefType.AGGLOMERATION.getCode())) {
            stmt.setStatement("""
                    SELECT cli_per.per_name || ' ' || cli_per.per_surname as clientName,
                           cli_per.per_email as clientEmail,
                           cli_per.per_phone_number as clientPhone
                    FROM ntis_agglomeration_versions av
                    LEFT JOIN spr_persons cli_per on cli_per.per_id = av_per_id
                    WHERE av_agg_id = ?
                    """);
            stmt.addSelectParam(refId);
        }
        List<NtisNotificationContactsModel> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisNotificationContactsModel.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No contact information was found");
        }
        NtisNotificationContactsModel contactInfo = queryResult.get(0);
        return contactInfo;
    }
}
