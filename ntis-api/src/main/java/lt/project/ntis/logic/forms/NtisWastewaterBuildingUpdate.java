package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.FacilityUpdateAgreementConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisAdrAddressesDAO;
import lt.project.ntis.dao.NtisBuildingAgreementsDAO;
import lt.project.ntis.dao.NtisFacilityUpdateAgreementDAO;
import lt.project.ntis.dao.NtisServedObjectsDAO;
import lt.project.ntis.enums.NtisAggreementSource;
import lt.project.ntis.enums.NtisBuildingAgreementStatus;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.enums.NtisTypeWastewaterTreatment;
import lt.project.ntis.models.NtisServedBuildingUpdateModel;
import lt.project.ntis.service.NtisAdrAddressesDBService;
import lt.project.ntis.service.NtisBuildingAgreementsDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisFacilityUpdateAgreementDBService;
import lt.project.ntis.service.NtisServedObjectsDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

@Component
public class NtisWastewaterBuildingUpdate extends FormBase {

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Autowired
    NtisServedObjectsDBService ntisServedObjectsDBService;

    @Autowired
    NtisAdrAddressesDBService ntisAdrAddressesDBService;

    @Autowired
    NtisBuildingAgreementsDBService ntisBuildingAgreementsDBService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    NtisFacilityUpdateAgreementDBService ntisFacilityUpdateAgreementDBService;

    @Autowired
    NtisWfAgreementsLists ntisWfAgreementsLists;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Override
    public String getFormName() {
        return "WASTEWATER_FACILITY_BUILDING_UPDATE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Statinio informacijos apie nuotekų tvarkymą atnaujinimas", "Statinio informacijos apie nuotekų tvarkymą atnaujinimas");
        addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
        addFormAction(FormBase.ACTION_CREATE, FormBase.ACTION_CREATE_DESC, FormBase.ACTION_CREATE_DESC);
        addFormAction(FormBase.ACTION_UPDATE, FormBase.ACTION_UPDATE_DESC, FormBase.ACTION_UPDATE_DESC);
    }

    public List<SprOrganizationsDAO> getWaterManagementCompany(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT org_id, org_name FROM spark.spr_organizations WHERE c01 in (?,?) ");
        stmt.addSelectParam(NtisOrgType.VANDEN.getCode());
        stmt.addSelectParam(NtisOrgType.PASLAUG_VANDEN.getCode());
        return queryController.selectQueryAsObjectArrayList(conn, stmt, SprOrganizationsDAO.class);
    }

    public Boolean updateBuildingAgreement(Connection conn, NtisServedBuildingUpdateModel formData, Double perId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        Boolean needApprove = false;

        NtisBuildingAgreementsDAO buildingAgreements = ntisBuildingAgreementsDBService.newRecord();
        Double soBnId = ntisServedObjectsDBService.loadRecord(conn, formData.getBa_so_id()).getSo_bn_id();

        if (this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) || this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                || this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)) {
            buildingAgreements.setBa_state(NtisBuildingAgreementStatus.CONFIRMED.getCode());

            buildingAgreements.setBa_bn_id(soBnId);
            buildingAgreements.setBa_network_connection_date(formData.getBa_network_connection_date());
            buildingAgreements.setBa_org_id(formData.getBa_org_id());
            buildingAgreements.setBa_manual_network_con_date(formData.getBa_network_connection_date());
            buildingAgreements.setBa_manual_org_id(formData.getBa_org_id());
            buildingAgreements.setBa_wastewater_treatment(NtisTypeWastewaterTreatment.CENTRALIZED.getCode());
            buildingAgreements.setBa_created(Utils.getDate());
            buildingAgreements.setBa_source(NtisAggreementSource.MANUAL.getCode());
            if (formData.getAttachments() != null) {
                Double fileId = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(formData.getAttachments().getFil_key())).getFil_id();
                sprFilesDBService.markAsConfirmed(conn, fileId);
                buildingAgreements.setBa_fil_id(fileId);
            }
            this.ntisBuildingAgreementsDBService.saveRecord(conn, buildingAgreements);
            NtisServedObjectsDAO servedObjectDAO = ntisServedObjectsDBService.loadRecord(conn, formData.getBa_so_id());
            servedObjectDAO.setSo_date_to(new Date());
            ntisServedObjectsDBService.saveRecord(conn, servedObjectDAO);
            if (formData.getFua_id() != null) {
                ntisFacilityUpdateAgreementDBService.deleteRecord(conn, formData.getFua_id());
            }
            ntisWfAgreementsLists.closeWtf(conn, formData.getBa_so_id(), formData.getBa_network_connection_date());

            // Reikia kurti prašymą
        } else if (this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN) || this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN) || this.hasUserRole(conn, NtisRolesConstants.INSTALLATION_ADMIN) ||this.hasUserRole(conn, NtisRolesConstants.INSTALLATION_SPECIALIST)
                || this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER)) {
            checkIsUserWtfOwner(conn, formData.getBa_so_id(), orgId, perId);
            NtisFacilityUpdateAgreementDAO updateAgreement;
            if (formData.getFua_id() != null) {
                updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecord(conn, formData.getFua_id());
            } else {
                updateAgreement = ntisFacilityUpdateAgreementDBService.newRecord();
                updateAgreement.setFua_created(new Date());
                updateAgreement.setFua_so_id(formData.getBa_so_id());
            }
            updateAgreement.setFua_per_id(perId);
            updateAgreement.setFua_req_org_id(orgId);
            updateAgreement.setFua_wtf_id(null);
            updateAgreement.setFua_state(FacilityUpdateAgreementConstants.SUBMITTED);
            updateAgreement.setFua_type("BUILDING");
            updateAgreement.setFua_bn_id(soBnId);
            updateAgreement.setFua_network_connection_date(formData.getBa_network_connection_date());
            updateAgreement.setFua_org_id(formData.getBa_org_id());
            if (formData.getAttachments() != null) {
                Double fileId = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(formData.getAttachments().getFil_key())).getFil_id();
                sprFilesDBService.markAsConfirmed(conn, fileId);
                updateAgreement.setFua_fil_id(fileId);
            }

            ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            needApprove = true;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

        return needApprove;
    }

    public NtisServedBuildingUpdateModel getBuildingAgreement(Connection conn, Double soId, Double perId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        if (!hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN) && !hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                && !hasUserRole(conn, NtisRolesConstants.INST_WORK) && !hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)) {
            checkIsUserWtfOwner(conn, soId, orgId, perId);
        }
        String personInfo = null;

        NtisServedBuildingUpdateModel formData = new NtisServedBuildingUpdateModel();

        NtisAdrAddressesDAO objectAddress = new NtisAdrAddressesDAO();
        StatementAndParams stmt = new StatementAndParams("""
                     select
                          ad.full_address_text as ad_address
                      from ntis_served_objects so
                inner join ntis_ntr_building_vw bn
                        on bn.bn_id = so.so_bn_id
                inner join ntis_address_vw ad
                        on ad.address_id = bn.bn_ad_id
                     where so_id = ?::int
                     """);
        stmt.addSelectParam(soId);
        List<NtisAdrAddressesDAO> address = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisAdrAddressesDAO.class);
        if (!address.isEmpty()) {
            objectAddress = address.get(0);
        }
        SprPersonsDAO personData = sprPersonsDBService.loadRecord(conn, perId);
        NtisFacilityUpdateAgreementDAO agreement = new NtisFacilityUpdateAgreementDAO();

        stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT  * from ntis_facility_update_agreement fua where fua_state = 'SUBMITTED' and fua_so_id = ?::int order by 1 desc
                 """);
        stmt.addSelectParam(soId);
        List<NtisFacilityUpdateAgreementDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisFacilityUpdateAgreementDAO.class);

        if (!queryResult.isEmpty()) {
            agreement = queryResult.get(0);
        }

        if (agreement != null & agreement.getFua_fil_id() != null) {
            SprFilesDAO sprFileDAO = sprFilesDBService.loadRecord(conn, agreement.getFua_fil_id());
            sprFileDAO.setFil_key(fileStorageService.encryptFileKey(sprFileDAO.getFil_key()));
            SprFile attachment = new SprFile();
            attachment.setFil_content_type(sprFileDAO.getFil_content_type());
            attachment.setFil_key(sprFileDAO.getFil_key());
            attachment.setFil_name(sprFileDAO.getFil_name());
            attachment.setFil_size(sprFileDAO.getFil_size());
            attachment.setFil_status(sprFileDAO.getFil_status());
            attachment.setFil_status_date(sprFileDAO.getFil_status_date());
            formData.setAttachments(attachment);
        }

        StringBuilder sb = new StringBuilder();

        sb.append(personData.getPer_name());
        sb.append(" ");
        sb.append(personData.getPer_surname());

        if (this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN) || this.hasUserRole(conn, NtisRolesConstants.INSTALLATION_ADMIN)|| this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN)) {

            String orgName = sprOrganizationsDBService.loadRecord(conn, orgId).getOrg_name();

            if (orgName != null) {
                sb.append(", ");
                sb.append(orgName);
            }
        }

        if (personData.getPer_email() != null) {
            sb.append(", ");
            sb.append(personData.getPer_email());
        }

        if (personData.getPer_phone_number() != null) {
            sb.append(", ");
            sb.append(personData.getPer_phone_number());
        }
        personInfo = sb.toString();

        formData.setBa_so_address(objectAddress.getAd_address());
        formData.setBa_update_by(personInfo);
        formData.setBa_so_id(soId);
        formData.setBa_org_id(agreement.getFua_org_id());
        formData.setBa_network_connection_date(agreement.getFua_network_connection_date());
        formData.setFua_id(agreement.getFua_id());
        return formData;
    }

    public void deleteFacilityUpdateAgreementFile(Connection conn, SprFile file) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        String decryptedKey = fileStorageService.decryptFileKey(file.getFil_key());
        SprFilesDAO fileToDelete = sprFilesDBService.loadRecordByKey(conn, decryptedKey);
        if (fileToDelete != null) {
            NtisFacilityUpdateAgreementDAO fuaDAO = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE FUA_FIL_ID = ?::int ",
                    new SelectParamValue(Utils.getDouble(fileToDelete.getFil_id())));
            if (fuaDAO != null) {
                fuaDAO.setFua_fil_id(null);
                ntisFacilityUpdateAgreementDBService.saveRecord(conn, fuaDAO);
                sprFilesDBService.deleteRecord(conn, fileToDelete.getFil_id());
                fileStorageService.deleteFile(decryptedKey, fileToDelete.getFil_path());
            }
        }

    }

    private void checkIsUserWtfOwner(Connection conn, Double soId, Double orgId, Double perId) throws Exception {
        NtisServedObjectsDAO servedObjectDAO = ntisServedObjectsDBService.loadRecord(conn, soId);
        boolean isOwner;
        if (orgId != null) {
            isOwner = ntisFacilityOwnersDBService.isOrganizationAnOwnerOfFacility(conn, orgId, servedObjectDAO.getSo_wtf_id());
        } else {
            isOwner = ntisFacilityOwnersDBService.isPersonAnOwnerOfFacility(conn, perId, servedObjectDAO.getSo_wtf_id());
        }
        if (!isOwner) {
            throw new SparkBusinessException(new S2Message("common.error.notAnOwner", SparkMessageType.ERROR, "User is not the owner of selected facility"));
        }
    }

}