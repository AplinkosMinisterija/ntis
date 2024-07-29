package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrganizationsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrganizationsEdit;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.service.NtisServiceReqItemsDBService;
import lt.project.ntis.service.NtisServiceRequestsDBService;
import lt.project.ntis.service.NtisServicesDBService;

@Component
public class NtisSprOrganizationsEdit extends SprOrganizationsEdit {

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    NtisServiceRequestsDBService ntisServiceRequestsDBService;

    @Autowired
    NtisServiceReqItemsDBService ntisServiceReqItemsDBService;
    
    @Autowired
    NtisServicesDBService ntisServicesDBService;

    public SprOrganizationsDAO saveRecord(Connection conn, SprOrganizationsDAO record, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getRecordIdentifier() == null ? SprOrganizationsBrowse.ACTION_CREATE : SprOrganizationsBrowse.ACTION_UPDATE);
        if (record.getOrg_id() != null) {
            SprOrganizationsDAO orgDAO = sprOrganizationsDBService.loadRecord(conn, record.getOrg_id());
            if (orgDAO.getD03() == null && record.getD03() != null) {
                createServiceRequestForInstallation(conn, record, usrId);
            } else if (record.getD03() == null && orgDAO.getD03() != null) {
                this.removeServiceRequestForInstallation(conn, record);
            }
            sprOrganizationsDBService.saveRecord(conn, record);
        } else if (record.getOrg_id() == null && record.getD03() != null) {
            sprOrganizationsDBService.saveRecord(conn, record);
            createServiceRequestForInstallation(conn, record, usrId);
        } else {
            sprOrganizationsDBService.saveRecord(conn, record);
        }
        return record;
    }

    private void createServiceRequestForInstallation(Connection conn, SprOrganizationsDAO record, Double usrId) throws Exception {
        NtisServiceRequestsDAO requestDAO = new NtisServiceRequestsDAO();
        SprUsersDAO userDAO = this.sprUsersDBService.loadRecord(conn, usrId);
        requestDAO.setSr_type("PASLAUG");
        requestDAO.setSr_resp_person_description(userDAO.getUsr_person_name() + " " + userDAO.getUsr_person_surname() + "(sistemos administratorius)");
        if (record.getOrg_email() != null && !record.getOrg_email().isEmpty()) {
            requestDAO.setSr_email(record.getOrg_email());
        } else {
            requestDAO.setSr_email("Nepateikta registracijos metu (sistemos administratorius)");
        }
        if (record.getOrg_phone() != null && !record.getOrg_phone().isEmpty()) {
            requestDAO.setSr_phone(record.getOrg_phone());
        } else {
            requestDAO.setSr_phone("Nepateikta registracijos metu (sistemos administratorius)");
        }

        requestDAO.setSr_data_is_correct("Y");
        requestDAO.setSr_rules_accepted("Y");
        requestDAO.setSr_email_verified("N");
        requestDAO.setSr_status("CONFIRMED");
        requestDAO.setSr_status_date(new Date());
        requestDAO.setSr_registration_date(new Date());
        requestDAO.setSr_usr_id(usrId);
        requestDAO.setSr_org_id(record.getOrg_id());
        requestDAO = ntisServiceRequestsDBService.insertRecord(conn, requestDAO);

        NtisServiceReqItemsDAO service = ntisServiceReqItemsDBService.newRecord();
        service.setSri_service_type("MONTAVIMAS");
        service.setSri_status("NETEIKIAMA");
        service.setSri_sr_id(requestDAO.getSr_id());
        service.setSri_status_date(new Date());
        service.setSri_registration_date(new Date());
        ntisServiceReqItemsDBService.insertRecord(conn, service);
    }

    private void removeServiceRequestForInstallation(Connection conn, SprOrganizationsDAO record) throws Exception {
        NtisServiceRequestsDAO changedRequest = null;
        List<NtisServiceRequestsDAO> requestsDAO = ntisServiceRequestsDBService.loadRecordsByParams(conn,
                " sr_status = 'CONFIRMED' and sr_org_id = ?::int and sr_removal_date is null ", new SelectParamValue(record.getOrg_id()));
        if (requestsDAO != null && !requestsDAO.isEmpty()) {
            for (NtisServiceRequestsDAO request : requestsDAO) {
                NtisServiceReqItemsDAO service = ntisServiceReqItemsDBService.loadRecordByParams(conn,
                        "sri_service_type = 'MONTAVIMAS' and sri_sr_id = ?::int and sri_removal_date is null ", new SelectParamValue(request.getSr_id()));
                if (service != null && service.getSri_id() != null) {
                    service.setSri_removal_date(Utils.getDate(new Date()));
                    ntisServiceReqItemsDBService.saveRecord(conn, service);
                    if(service.getSri_srv_id() != null) {
                        NtisServicesDAO serviceDesc = ntisServicesDBService.loadRecord(conn, service.getSri_srv_id());
                        if(serviceDesc.getSrv_type().equalsIgnoreCase(NtisServiceItemType.MONTAVIMAS.getCode())) {
                            serviceDesc.setSrv_date_to(Utils.getDate(new Date()));
                            ntisServicesDBService.saveRecord(conn, serviceDesc);
                        }
                    }
                    changedRequest = request;
                }
            }
        }
        if (changedRequest != null) {
            changedRequest.setSr_removal_date(Utils.getDate(new Date()));
            ntisServiceRequestsDBService.saveRecord(conn, changedRequest);
        }
    }
}
