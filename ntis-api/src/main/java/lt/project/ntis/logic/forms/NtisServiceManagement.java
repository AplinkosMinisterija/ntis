package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.models.NtisServiceDetails;
import lt.project.ntis.models.NtisServiceManagementItem;
import lt.project.ntis.service.NtisServiceReqItemsDBService;
import lt.project.ntis.service.NtisServiceRequestsDBService;
import lt.project.ntis.service.NtisServicesDBService;

@Component
public class NtisServiceManagement extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final NtisServiceRequestsDBService ntisServiceRequestsDBService;

    private final NtisServiceReqItemsDBService ntisServiceReqItemsDBService;

    private final NtisServicesDBService ntisServicesDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final SprOrganizationsDBService sprOrganizationsDBService;

    public NtisServiceManagement(BaseControllerJDBC baseControllerJDBC, NtisServiceRequestsDBService ntisServiceRequestsDBService,
            NtisServiceReqItemsDBService ntisServiceReqItemsDBService, NtisServicesDBService ntisServicesDBService,
            SprOrgUsersDBServiceImpl sprOrgUsersDBService, SprOrganizationsDBService sprOrganizationsDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.ntisServiceRequestsDBService = ntisServiceRequestsDBService;
        this.ntisServiceReqItemsDBService = ntisServiceReqItemsDBService;
        this.ntisServicesDBService = ntisServicesDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_MANAGEMENT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų valdymas", "Nuotekų tvarkymo paslaugų valdymas");
        addFormActionCRUD();
    }

    public ArrayList<NtisServiceManagementItem> getServicesInfo(Connection conn, Double orgId, Double usrId, String lang) throws Exception {
        if (this.isFormActionAssigned(conn, ACTION_READ)) {

            StatementAndParams statementAndParams = new StatementAndParams(
                    """
                                 SELECT services.*,
                            statusrfc.rfc_meaning AS statusName
                            FROM
                            (
                                SELECT rfc.rfc_code as serviceType,
                                       rfc.rfc_meaning AS serviceName,
                                             CASE
                                                 WHEN s.sr_status IS NULL OR s.sr_status = 'REJECTED' THEN 'SRV_STS_INACTIVE'
                                                 WHEN s.sr_status = 'CONFIRMED' AND rfc.rfc_code = 'TYRIMAI' AND s.sri_removal_date IS NULL THEN 'SRV_STS_ACTIVE'
                                                 WHEN s.sr_status in ('CONFIRMED','SUBMITTED') AND rfc.rfc_code in ('PRIEZIURA','VEZIMAS', 'MONTAVIMAS') AND s.sri_removal_date IS NULL THEN 'SRV_STS_ACTIVE'
                                                 WHEN s.sri_removal_date IS NOT NULL THEN 'SRV_STS_SUSPENDED'
                                                 ELSE 'SRV_STS_INACTIVE'
                                            END AS status,
                                       s.sr_id AS srId,
                                       s.sri_id AS sriId,
                                       s.srv_id AS srvId,
                                       s.sri_removal_date as removalDate,
                                           CASE
                                               WHEN s.sri_removal_date is null AND s.sr_status in ('CONFIRMED', 'SUBMITTED') THEN s.srv_available_in_ntis_portal
                                               ELSE null
                                           END AS srv_available_in_ntis_portal,
                                           CASE
                                               WHEN s.sri_removal_date is null AND s.sr_status in ('CONFIRMED', 'SUBMITTED') THEN s.srv_contract_available
                                               ELSE null
                                           END AS srv_contract_available,
                                           CASE
                                               WHEN s.sri_removal_date is null AND s.sr_status in ('CONFIRMED', 'SUBMITTED') THEN s.srv_lithuanian_level
                                               ELSE null
                                           END AS srv_lithuanian_level,
                                           CASE
                                               WHEN s.sri_removal_date is null AND s.sr_status in ('CONFIRMED', 'SUBMITTED') THEN s.is_confirmed
                                               ELSE null
                                           END AS is_confirmed
                                   FROM spr_ref_codes_vw rfc
                                   LEFT JOIN
                                   (
                                      SELECT sri.sri_service_type,
                                             sr.sr_id,
                                             sr.sr_status,
                                             sri.sri_id,
                                             srv.srv_id,
                                             srv.srv_date_to,
                                             srv.srv_available_in_ntis_portal,
                                             srv.srv_contract_available,
                                             srv.srv_lithuanian_level,
                                             CASE WHEN srv.srv_date_from IS NULL THEN 'N' ELSE 'Y' END AS is_confirmed,
                                             sri.sri_removal_date
                                             FROM ntis_service_req_items sri
                                         INNER JOIN ntis_service_requests sr ON sr.sr_id = sri.sri_sr_id AND sr.sr_org_id = ?::int AND sr.sr_status in ('CONFIRMED','SUBMITTED')
                                         INNER JOIN spr_org_users ou on sr.sr_org_id = ou.ou_org_id and ou.ou_usr_id = ?::int and CURRENT_DATE between ou.ou_date_from and COALESCE(ou.ou_date_to, now())
                                         LEFT JOIN ntis_services srv ON srv.srv_id = sri.sri_srv_id
                                   ) s ON s.sri_service_type = rfc.rfc_code
                                     WHERE rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE'
                                      AND rfc.rfc_code <> 'VALYMAS'
                                      AND rfc.rft_lang = ?
                                ) AS services
                                 LEFT JOIN spr_ref_codes_vw statusrfc ON statusrfc.rfc_code = services.status AND statusrfc.rfc_domain = 'SERVICE_STATUS' AND statusrfc.rft_lang = ?
                                 ORDER BY services.serviceType, services.removalDate desc nulls first
                                """);
            statementAndParams.addSelectParam(orgId);
            statementAndParams.addSelectParam(usrId);
            statementAndParams.addSelectParam(lang);
            statementAndParams.addSelectParam(lang);
            ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
            methods.add(new MethodCaller("setAvailableInNtisPortalString",
                    new StatementDataGetter[] { new StatementStringGetter("srv_available_in_ntis_portal") }));
            methods.add(new MethodCaller("setContractAvailableString", new StatementDataGetter[] { new StatementStringGetter("srv_contract_available") }));
            methods.add(new MethodCaller("setLithuanianLevelString", new StatementDataGetter[] { new StatementStringGetter("srv_lithuanian_level") }));
            methods.add(new MethodCaller("setIsConfirmedString", new StatementDataGetter[] { new StatementStringGetter("is_confirmed") }));
            Data2ObjectProcessor<NtisServiceManagementItem> dataProcessor = new Data2ObjectProcessor<>(NtisServiceManagementItem.class, methods);
            List<NtisServiceManagementItem> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);
            if (queryResult.size() == 3) {
                return new ArrayList<>(queryResult);
            } else {
                ArrayList<NtisServiceManagementItem> filteredServices = new ArrayList<>();
                filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.PRIEZIURA.getCode()))
                        .findFirst().orElse(null));
                filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode()))
                        .findFirst().orElse(null));
                filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.VEZIMAS.getCode()))
                        .findFirst().orElse(null));
                filteredServices.add(queryResult.stream().filter(service -> service.getServiceType().equalsIgnoreCase(NtisServiceItemType.MONTAVIMAS.getCode()))
                        .findFirst().orElse(null));
                return filteredServices;
            }
        } else
            return new ArrayList<>();
    }

    public void confirmService(Connection conn, Double userOrgId, Double usrId, Double srvId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_UPDATE);
        NtisServicesDAO ntisServicesDAO = ntisServicesDBService.loadRecord(conn, srvId);
        if (sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, userOrgId) && ntisServicesDAO.getSrv_org_id().equals(userOrgId)) {
            ntisServicesDAO.setSrv_date_from(Utils.getDate());
            ntisServicesDBService.saveRecord(conn, ntisServicesDAO);
            if (ntisServicesDAO.getSrv_type().equalsIgnoreCase(NtisServiceItemType.MONTAVIMAS.getCode())) {
                SprOrganizationsDAO orgDAO = sprOrganizationsDBService.loadRecord(conn, ntisServicesDAO.getSrv_org_id());
                if (orgDAO != null && orgDAO.getOrg_id() != null && orgDAO.getD03() == null) {
                    orgDAO.setD03(Utils.getDate(new Date()));
                    sprOrganizationsDBService.saveRecord(conn, orgDAO);
                }
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    public void deregisterService(Connection conn, Double userOrgId, Double usrId, Double sriId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_DELETE);
        NtisServiceReqItemsDAO ntisServiceReqItemsDAO = ntisServiceReqItemsDBService.loadRecord(conn, sriId);
        NtisServiceRequestsDAO ntisServiceRequestsDAO = ntisServiceRequestsDBService.loadRecord(conn, ntisServiceReqItemsDAO.getSri_sr_id());
        if (ntisServiceRequestsDAO.getSr_org_id().equals(userOrgId) && sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, userOrgId)) {
            ntisServiceReqItemsDAO.setSri_removal_date(Utils.getDate());
            Double srvId = ntisServiceReqItemsDAO.getSri_srv_id();
            ntisServiceReqItemsDBService.saveRecord(conn, ntisServiceReqItemsDAO);
            if (srvId != null) {
                NtisServicesDAO serviceDAO = ntisServicesDBService.loadRecord(conn, srvId);
                serviceDAO.setSrv_date_to(Utils.getDate(new Date()));
                ntisServicesDBService.saveRecord(conn, serviceDAO);
                if (serviceDAO.getSrv_type().equalsIgnoreCase(NtisServiceItemType.MONTAVIMAS.getCode())) {
                    SprOrganizationsDAO orgDAO = sprOrganizationsDBService.loadRecord(conn, serviceDAO.getSrv_org_id());
                    if (orgDAO != null && orgDAO.getOrg_id() != null) {
                        orgDAO.setD03(null);
                        sprOrganizationsDBService.saveRecord(conn, orgDAO);
                    }
                }
            } else {
                ntisServiceReqItemsDBService.saveRecord(conn, ntisServiceReqItemsDAO);
            }
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    public NtisServiceDetails getServiceDetails(Connection conn, Double userOrgId, Double usrId, Double srvId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "SELECT srv.srv_id AS srvId," //
                + "       srv.srv_contract_available, " //
                + "       srv.srv_available_in_ntis_portal, " //
                + "       srv.srv_lithuanian_level, " //
                + "       srv.srv_type, " //
                + "      CASE  " //
                + "           WHEN srv.srv_lithuanian_level = 'Y' THEN '[]' " //
                + "           ELSE ( " //
                + "                SELECT COALESCE(ARRAY_TO_JSON(ARRAY_REMOVE(ARRAY_AGG(mn_rfc.rfc_meaning order by mn_rfc.rfc_meaning asc), NULL)), '[]') " //
                + "                FROM ntis_service_municipalities mn " //
                + "                INNER JOIN spr_ref_codes mn_rfc ON mn_rfc.rfc_code = mn.smn_municipality AND mn_rfc.rfc_domain = 'NTIS_MUNICIPALITIES' " //
                + "                WHERE mn.smn_srv_id = srv.srv_id " //
                + "           ) " //
                + "       END AS municipalities_json, " //
                + "       rfc.rfc_meaning AS serviceName, " //
                + "       org.org_id AS orgId, " //
                + "       org.org_name AS orgName, " //
                + "       srv.srv_email AS email, " //
                + "       srv.srv_phone_no AS phone, " //
                + "       srv.srv_price_from AS priceFrom, " //
                + "       srv.srv_price_to AS priceTo, " //
                + "       srv.srv_completion_in_days_from AS completionInDays, " //
                + "       srv.srv_completion_in_days_to AS completionInDaysTo, " //
                + "       srv.srv_description AS description, " //
                + "       srv.srv_fil_id AS filId, " //
                + "       srv.srv_lab_instr_fil_id as instrFilId  " //
                + "  FROM ntis_services srv " //
                + " INNER JOIN spr_organizations org ON org.org_id = srv.srv_org_id " //
                + " INNER JOIN spr_org_users ou on ou.ou_org_id = org.org_id and ou.ou_usr_id = ?::int and CURRENT_DATE between ou.ou_date_from and coalesce(ou.ou_date_to, now()) " //
                + " INNER JOIN spr_ref_codes_vw rfc ON rfc.rfc_code = srv.srv_type AND rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE' AND rfc.rft_lang = ? " //
                + " WHERE srv.srv_id = ?::int and srv.srv_org_id = ?::int");
        statementAndParams.addSelectParam(usrId);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(srvId);
        statementAndParams.addSelectParam(userOrgId);
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(
                new MethodCaller("setAvailableInNtisPortalString", new StatementDataGetter[] { new StatementStringGetter("srv_available_in_ntis_portal") }));
        methods.add(new MethodCaller("setContractAvailableString", new StatementDataGetter[] { new StatementStringGetter("srv_contract_available") }));
        methods.add(new MethodCaller("setLithuanianLevelString", new StatementDataGetter[] { new StatementStringGetter("srv_lithuanian_level") }));
        methods.add(new MethodCaller("setMunicipalitiesJson", new StatementDataGetter[] { new StatementStringGetter("municipalities_json") }));
        Data2ObjectProcessor<NtisServiceDetails> dataProcessor = new Data2ObjectProcessor<NtisServiceDetails>(NtisServiceDetails.class, methods);
        List<NtisServiceDetails> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);

        if (queryResult != null && !queryResult.isEmpty()) {
            NtisServiceDetails serviceDetails = queryResult.get(0);

            if (!serviceDetails.getOrgId().equals(userOrgId.intValue())) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }

            if (serviceDetails.getFilId() != null) {
                SprFile file = ntisServicesDBService.getServiceFile(conn, serviceDetails.getFilId().doubleValue());
                serviceDetails.setFile(file);
            }
            if (serviceDetails.getInstrFilId() != null) {
                SprFile file = ntisServicesDBService.getServiceLabInstructions(conn, serviceDetails.getInstrFilId().doubleValue());
                serviceDetails.setLabInstructions(file);
            }
            return serviceDetails;
        } else {
            throw new Exception("No service with id " + srvId + " was found");
        }
    }
}
