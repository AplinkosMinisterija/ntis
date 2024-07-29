package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementIntegerGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.enums.NtisServiceReqStatus;
import lt.project.ntis.models.NtisServiceProviderContacts;
import lt.project.ntis.models.NtisServiceProviderSettingsInfo;

@Component
public class NtisWaterManagerSettings extends FormBase {

    private final SprOrganizationsDBService sprOrganizationsDBService;

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisWaterManagerSettings(SprOrganizationsDBService sprOrganizationsDBService, BaseControllerJDBC baseControllerJDBC,
            DBStatementManager dbStatementManager, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.sprOrganizationsDBService = sprOrganizationsDBService;
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_WATER_MANAGER_SETTINGS";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Vandentvarkos įmonės profilio tvarkymas", "Vandentvarkos įmonės profilio tvarkymas");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE);
    }

    public NtisServiceProviderSettingsInfo getInfo(Connection conn, Double orgId) throws SparkBusinessException, SQLException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "SELECT org.org_id, " //
                + "       org.org_name AS name, " //
                + "       org.org_code AS code, " //
                + "       CONCAT_WS(', ', NULLIF(TRIM(org.org_address), ''), NULLIF(TRIM(org.org_house_number), ''), NULLIF(TRIM(org.org_region), '')) AS address, " //
                + "       org.org_delegation_person AS manager, " //
                + "       org.org_email AS email, " //
                + "       org.c01 as orgType, " //
                + "       org.c02 AS email_notifications, " //
                + "       org.org_phone AS phone_number, " //
                + "       org.c03 as website, " //
                + "       TO_CHAR(org.d01, '" //
                + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS registeredFrom, "//
                + "       TO_CHAR(org.d02, '"//
                + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS deregisteredFrom, " //
                + "       org.c04 AS deregisteredReason, "//
                + "       (SELECT count(ou.ou_id) FROM spark.spr_org_users ou " //
                + "               WHERE ou.ou_org_id = org.org_id " //
                + "                 and now() between ou.ou_date_from and coalesce(ou.ou_date_to, now()) " //
                + "       ) AS employeesCount, " //
                + "       (SELECT count(wto.wto_id) FROM ntis.ntis_wastewater_treatment_org wto WHERE wto.wto_org_id = org.org_id and wto.wto_is_it_used = '"
                + YesNo.Y + "' ) AS wtoCount" //
                + "  FROM spark.spr_organizations org " //
                + "  LEFT JOIN ntis.ntis_service_requests sr ON sr.sr_org_id = org.org_id AND sr.sr_type = '" + NtisOrgType.VANDEN + "' and sr.sr_status in ('"
                + NtisServiceReqStatus.CONFIRMED + "')" //
                + " WHERE org.org_id = ? ");
        statementAndParams.addSelectParam(orgId);

        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
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
            throw new Exception("No water manager information for organization with id " + orgId + " was found");
        }
    }

    public NtisServiceProviderContacts updateContacts(Connection conn, NtisServiceProviderContacts contacts, Double userId)
            throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        NtisServiceProviderContacts result = null;
        if (sprOrgUsersDBService.checkIsUserInOrganization(conn, userId, contacts.getOrgId().doubleValue())) {
            SprOrganizationsNtisDAO organizationsDAO = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, contacts.getOrgId().doubleValue());
            organizationsDAO.setOrg_email(contacts.getEmail());
            organizationsDAO.setEmailNotifications(contacts.getEmailNotifications());
            organizationsDAO.setOrg_phone(contacts.getPhoneNumber());
            organizationsDAO.setWebsite(contacts.getWebsite());
            SprOrganizationsNtisDAO savedRecord = (SprOrganizationsNtisDAO) sprOrganizationsDBService.saveRecord(conn, organizationsDAO);
            result = new NtisServiceProviderContacts(savedRecord.getOrg_id().intValue(), savedRecord.getOrg_email(), savedRecord.getEmailNotifications(),
                    savedRecord.getOrg_phone(), savedRecord.getWebsite());
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        return result;
    }

}
