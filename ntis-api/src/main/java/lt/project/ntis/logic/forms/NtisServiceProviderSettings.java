package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementIntegerGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.models.NtisServiceProviderContacts;
import lt.project.ntis.models.NtisServiceProviderSettingsInfo;

@Component
public class NtisServiceProviderSettings extends FormBase {

    private final SprOrganizationsDBService sprOrganizationsDBService;

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisServiceProviderSettings(SprOrganizationsDBService sprOrganizationsDBService, BaseControllerJDBC baseControllerJDBC,
            DBStatementManager dbStatementManager, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
        this.dbStatementManager = dbStatementManager;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_PROVIDER_SETTINGS";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų teikėjo profilio tvarkymas", "Paslaugų teikėjo profilio tvarkymas");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE);
    }

    public NtisServiceProviderSettingsInfo getInfo(Connection conn, Double orgId, Double usrId) throws SparkBusinessException, SQLException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "SELECT org.org_id, " //
                + "       org.org_name AS name, " //
                + "       org.org_code AS code, " //
                + "       CONCAT_WS(', ', NULLIF(TRIM(org.org_address), ''), NULLIF(TRIM(org.org_house_number), ''), NULLIF(TRIM(org.org_region), '')) AS address, " //
                + "       org.org_delegation_person AS manager, " //
                + "       org.org_email AS email, " //
                + "       org.c01 as orgType," + "       org.c02 AS email_notifications, " //
                + "       org.org_phone AS phone_number, " //
                + "       org.c03 as website, " //
                + "       TO_CHAR(org.d01, '" //
                + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS registeredFrom, "//
                + "       TO_CHAR(org.d02, '"//
                + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS deregisteredFrom, "//
                + "       TO_CHAR(org.d03, '" //
                + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS facilityInstallerFrom, "//
                + "       org.c04 AS deregisteredReason, "//
                + "       ( " //
                + "            SELECT COALESCE(ARRAY_TO_JSON(ARRAY_REMOVE(ARRAY_AGG( "//
                + "                 JSONB_BUILD_OBJECT( "//
                + "                     'service_type', sri.sri_service_type, " //
                + "                     'is_active', case when now() between srv.srv_date_from and coalesce(srv.srv_date_to, now()) then 'Y' else 'N' end " //
                + "                   ) " //
                + "                 ), NULL)), '[]') " //
                + "              FROM ntis_service_req_items sri " //
                + "             INNER JOIN ntis_service_requests sr on sr_id = sri.sri_sr_id AND now() between sri.sri_registration_date and coalesce(sri.sri_removal_date, now()) and sr.sr_status in ('CONFIRMED','SUBMITTED')" //
                + "              LEFT JOIN ntis_services srv on sri.sri_srv_id = srv.srv_id " //
                + "             WHERE sr.sr_org_id = org.org_id " //
                + "            ) AS servicesJson, " //
                + "       (SELECT count(ou.ou_id) FROM spark.spr_org_users ou " //
                + "                          INNER JOIN spark.spr_users u on u.usr_id = ou.ou_usr_id and u.usr_type = 'ORGANIZATION' "//
                + "                               WHERE ou.ou_org_id = org.org_id " //
                + "                               AND now() between ou.ou_date_from and coalesce(ou.ou_date_to, now())) " //
                + "       AS employeesCount, " //
                + "       (SELECT count(cr.cr_id) FROM ntis.ntis_cars cr " //
                + "                               WHERE cr.cr_org_id = org.org_id " //
                + "                               AND now() between cr.cr_date_from and coalesce(cr.cr_date_to, now())) " //
                + "       AS vehiclesCount, " //
                + "       ( " //
                + "            SELECT COALESCE(ARRAY_TO_JSON(ARRAY_REMOVE(ARRAY_AGG(distinct favorg.org_name || ' (' || wtos.wtos || ')'), NULL)), '[]') " //
                + "              FROM ntis.ntis_favorite_wast_treat_orgs fwto " //
                + "             INNER JOIN ntis.ntis_wastewater_treatment_org wto on fwto.fwto_wto_id = wto.wto_id and wto.wto_is_it_used = 'Y' " //
                + "             INNER JOIN spark.spr_organizations favorg ON favorg.org_id = wto.wto_org_id and favorg.c01 in ('VANDEN', 'PASLAUG_VANDEN') " //
                + "             INNER JOIN (SELECT wto2.wto_org_id, STRING_AGG(wto2.wto_name, ', ') as wtos " //
                + "                              FROM ntis.ntis_wastewater_treatment_org wto2 " //
                + "                             INNER JOIN ntis.ntis_favorite_wast_treat_orgs fwto2 on fwto2.fwto_wto_id = wto2.wto_id and fwto2.fwto_org_id = ?::int "//
                + "                             GROUP BY wto2.wto_org_id) as wtos on favorg.org_id = wtos.wto_org_id " //
                + "             WHERE fwto.fwto_org_id = ?::int" //
                + "            ) AS favWaterManagersJson " //
                + "  FROM spark.spr_organizations org " //
                + " WHERE org.org_id = ?::int ");
        statementAndParams.addSelectParam(orgId);
        statementAndParams.addSelectParam(orgId);
        statementAndParams.addSelectParam(orgId);
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setServicesJson", new StatementDataGetter[] { new StatementStringGetter("servicesJson") }));
        methods.add(new MethodCaller("setFavWaterManagersJson", new StatementDataGetter[] { new StatementStringGetter("favWaterManagersJson") }));
        methods.add(new MethodCaller("createContacts", new StatementDataGetter[] { new StatementIntegerGetter("org_id"), //
                new StatementStringGetter("email"), //
                new StatementStringGetter("email_notifications"), //
                new StatementStringGetter("phone_number"), //
                new StatementStringGetter("website") }));

        Data2ObjectProcessor<NtisServiceProviderSettingsInfo> dataProcessor = new Data2ObjectProcessor<NtisServiceProviderSettingsInfo>(
                NtisServiceProviderSettingsInfo.class, methods);
        List<NtisServiceProviderSettingsInfo> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);
        if (queryResult != null && !queryResult.isEmpty()) {
            return queryResult.get(0);
        } else {
            throw new Exception("No service provider information for organization with id " + orgId + " was found");
        }
    }

    public NtisServiceProviderContacts updateContacts(Connection conn, NtisServiceProviderContacts contacts, Double userId, Double orgId)
            throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        NtisServiceProviderContacts result = null;
        if (Utils.getDouble(contacts.getOrgId()).compareTo(orgId) == 0) {
            if (sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, contacts.getOrgId().doubleValue())) {
                SprOrganizationsNtisDAO organizationsDAO = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn,
                        contacts.getOrgId().doubleValue());
                organizationsDAO.setOrg_email(contacts.getEmail());
                organizationsDAO.setEmailNotifications(contacts.getEmailNotifications());
                organizationsDAO.setOrg_phone(contacts.getPhoneNumber());
                organizationsDAO.setWebsite(contacts.getWebsite());
                SprOrganizationsNtisDAO savedRecord = (SprOrganizationsNtisDAO) sprOrganizationsDBService.saveRecord(conn, organizationsDAO);
                result = new NtisServiceProviderContacts(savedRecord.getOrg_id().intValue(), savedRecord.getOrg_email(), savedRecord.getEmailNotifications(),
                        savedRecord.getOrg_phone(), savedRecord.getWebsite());
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        return result;
    }

    public void deregisterInstallation(Connection conn, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        SprOrganizationsNtisDAO organizationsDAO = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, orgId);
        organizationsDAO.setFacilityInstallerDateFrom(null);
        organizationsDAO.setManageOrgRoles(1.0);
        sprOrganizationsDBService.saveRecord(conn, organizationsDAO);
        StatementAndParams stmt = new StatementAndParams("""
                                update ntis_service_req_items set sri_removal_date = current_date
                where sri_sr_id in (select sr_id from ntis_service_requests
                                   inner join ntis_service_req_items on sri_sr_id = sr_id
                                   and sr_org_id = ?::int
                                   and sri_service_type = 'MONTAVIMAS'
                                   and sri_removal_date is null)
                                   and sri_service_type = 'MONTAVIMAS';
                                """);
        stmt.addSelectParam(orgId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}
