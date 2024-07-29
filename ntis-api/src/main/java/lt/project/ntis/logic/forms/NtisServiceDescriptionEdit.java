package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisServiceMunicipalitiesDAO;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.model.NtisMunicipalitiesRequest;
import lt.project.ntis.logic.forms.model.NtisServiceDescription;
import lt.project.ntis.logic.forms.model.NtisServiceDescriptionEditModel;
import lt.project.ntis.service.NtisServiceMunicipalitiesDBService;
import lt.project.ntis.service.NtisServiceReqItemsDBService;
import lt.project.ntis.service.NtisServicesDBService;

/**
 * Klasė skirta formos "Paslaugos aprašymas" biznio logikai apibrėžti
 */

@Component
public class NtisServiceDescriptionEdit extends FormBase {

    private final NtisServicesDBService ntisServicesDBService;

    private final BaseControllerJDBC baseControllerJDBC;

    private final SprFilesDBService sprFilesDBService;

    private final FileStorageService fileStorageService;

    private final NtisServiceMunicipalitiesDBService ntisServiceMunicipalitiesDBService;

    private final NtisServiceReqItemsDBService reqItemsDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final SprOrganizationsDBService sprOrganizationsDBService;

    public NtisServiceDescriptionEdit(NtisServicesDBService ntisServicesDBService, BaseControllerJDBC baseControllerJDBC, SprFilesDBService sprFilesDBService,
            FileStorageService fileStorageService, NtisServiceMunicipalitiesDBService ntisServiceMunicipalitiesDBService,
            NtisServiceReqItemsDBService reqItemsDBService, SprOrgUsersDBServiceImpl sprOrgUsersDBService,
            SprOrganizationsDBService sprOrganizationsDBService) {
        super();
        this.ntisServicesDBService = ntisServicesDBService;
        this.baseControllerJDBC = baseControllerJDBC;
        this.sprFilesDBService = sprFilesDBService;
        this.fileStorageService = fileStorageService;
        this.ntisServiceMunicipalitiesDBService = ntisServiceMunicipalitiesDBService;
        this.reqItemsDBService = reqItemsDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_DESCRIPTION_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų redagavimas", "Paslaugų redagavimo forma");
        addFormActions(NtisServiceDescriptionEdit.ACTION_READ, NtisServiceDescriptionEdit.ACTION_UPDATE, NtisServiceDescriptionEdit.ACTION_CREATE);

    }

    /**
     * Metodas grąžins paslaugos aprašymo formai reikalingus duomenis.
     * @param conn - prisijungimas prie DB
     * @param sriId - prašyme teikti paslaugą užregistruotos paslaugos id
     * @param orgId - organizacijos sesijos id
     * @param usrId - naudotojo sesijos id
     * @returns NtisServiceDescriptionEditModel objektas
     * @throws Exception
     */
    public NtisServiceDescriptionEditModel get(Connection conn, String sriId, Double orgId, Double usrId, String lang) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceDescriptionEdit.ACTION_READ);
        NtisServiceDescriptionEditModel answerObj = loadServiceDescriptionData(conn, sriId, orgId, lang);
        if (answerObj.getNtisServiceDescription().getSrv_fil_id() != null) {
            SprFile fileToAttach = ntisServicesDBService.getServiceFile(conn, answerObj.getNtisServiceDescription().getSrv_fil_id());
            answerObj.setContractFile(fileToAttach);
        }
        if (answerObj.getNtisServiceDescription().getSrv_lab_instr_fil_id() != null) {
            SprFile fileToAttach = ntisServicesDBService.getServiceLabInstructions(conn, answerObj.getNtisServiceDescription().getSrv_lab_instr_fil_id());
            answerObj.setLabInstructions(fileToAttach);
        }
        answerObj.setMunicipalities(getMunicipalities(conn, answerObj.getNtisServiceDescription().getSrv_id(), lang));
        if ((answerObj.getNtisServiceDescription().getSrv_org_id() != null && !Objects.equals(answerObj.getNtisServiceDescription().getSrv_org_id(), orgId))
                || !sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        return answerObj;
    }

    private NtisServiceDescriptionEditModel loadServiceDescriptionData(Connection conn, String sriId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceDescriptionEdit.ACTION_READ);
        NtisServiceDescriptionEditModel descriptionModelObj = new NtisServiceDescriptionEditModel();
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT SRV.SRV_ID, " + //
                "SO.ORG_NAME as SRV_ORG_NAME, " + //
                "coalesce(SRV.SRV_TYPE, SRI.SRI_SERVICE_TYPE) as SRV_TYPE, " + //
                "coalesce(RC.RFC_MEANING, SRV_TYPE) as SRV_NAME, " + //
                "SRV.SRV_CONTRACT_AVAILABLE, " + //
                "SRV.SRV_AVAILABLE_IN_NTIS_PORTAL, " + //
                "SRV.SRV_LITHUANIAN_LEVEL, " + //
                "SRV.SRV_EMAIL, " + //
                "SRV.SRV_PHONE_NO, " + //
                "SRV.SRV_PRICE_FROM, " + //
                "SRV.SRV_PRICE_TO, " + //
                "SRV.SRV_DATE_FROM, " + //
                "SRV.SRV_DATE_TO, " + //
                "SRI.SRI_ID, " + //
                "SRV.SRV_COMPLETION_IN_DAYS_FROM, " + //
                "SRV.SRV_COMPLETION_IN_DAYS_TO, " + //
                "SRV.SRV_DESCRIPTION, " + //
                "SRV.SRV_ORG_ID, " + //
                "SRV.SRV_FIL_ID, " + //
                "SRV.SRV_LAB_INSTR_FIL_ID " + //
                "FROM NTIS.NTIS_SERVICE_REQUESTS SR " + //
                "INNER JOIN NTIS.NTIS_SERVICE_REQ_ITEMS SRI ON SR.SR_ID = SRI.SRI_SR_ID  AND SRI_ID = ?::int AND SR_ORG_ID = ?::int " + //
                "INNER JOIN SPARK.SPR_ORGANIZATIONS SO ON SO.ORG_ID = SR.SR_ORG_ID " + //
                "LEFT JOIN NTIS.NTIS_SERVICES SRV ON SRI.SRI_SRV_ID = SRV.SRV_ID " + //
                "LEFT JOIN SPARK.SPR_REF_CODES_VW RC ON RC.RFC_CODE = SRI.SRI_SERVICE_TYPE and RC.RFC_DOMAIN = 'NTIS_SRV_ITEM_TYPE' and RC.RFT_LANG = ? ");
        stmt.addSelectParam(Utils.getDouble(sriId));
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        List<NtisServiceDescription> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisServiceDescription.class);
        if (data != null && !data.isEmpty() && data.get(0).getSri_id() != null) {
            descriptionModelObj.setNtisServiceDescription(data.get(0));
            return descriptionModelObj;
        } else {
            throw new Exception("No service description for service request item with " + sriId + " found");
        }
    }

    private List<NtisMunicipalitiesRequest> getMunicipalities(Connection conn, Double srvId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceDescriptionEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("WITH SERVICE_MUNICIPALITIES AS (SELECT SMN_ID AS MN_ID, "//
                + "                                       SMN_DATE_FROM AS MN_DATE_FROM, "//
                + "                                       SMN_DATE_TO AS MN_DATE_TO, "//
                + "                                       SMN_SRV_ID AS MN_SRV_ID, "//
                + "                                       SMN_MUNICIPALITY AS MN_MUNICIPALITY " //
                + "                                  FROM NTIS.NTIS_SERVICE_MUNICIPALITIES "//
                + "                                 WHERE SMN_SRV_ID = ?::int ) " //
                + "      SELECT MN_ID, " //
                + "             SM.MN_SRV_ID, " //
                + "             SM.MN_DATE_FROM, "//
                + "             SM.MN_DATE_TO, "//
                + "             SM.MN_MUNICIPALITY, " //
                + "             CASE WHEN SM.MN_DATE_FROM IS NULL THEN 'N' ELSE 'Y' END SELECTED, " //
                + "             RFC.RFC_CODE, " //
                + "             RFC.RFC_MEANING " //
                + "        FROM SPARK.SPR_REF_CODES_VW RFC " //
                + "        LEFT JOIN SERVICE_MUNICIPALITIES SM ON RFC.RFC_CODE = SM.MN_MUNICIPALITY " //
                + "       WHERE RFC.RFC_DOMAIN='NTIS_MUNICIPALITIES' AND RFC.RFT_LANG = ? ");

        stmt.addSelectParam(srvId);
        stmt.addSelectParam(lang);
        stmt.setStatementOrderPart("ORDER BY RFC_MEANING");
        List<NtisMunicipalitiesRequest> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisMunicipalitiesRequest.class);

        return data;
    }

    /**
     * Metodas išsaugos perduodamą NtisServiceDescriptionEditModel objektą, prieš tai patikrinęs
     * ar, priklausomai nuo to, ar įrašas su tokiu srv_id jau egzistuoja ntis_services lentelėje,
     * patikrins ar naudotojas turi teisę kurti ar redaguoti įrašus
     * @param conn - prisijungimas prie DB
     * @param record - saugotinas NtisServiceDescriptionEditModel objektas
     * @param orgId - sesijos organizacijos id
     * @param usrId - sesijos naudotojo id
     * @returns NtisServiceDescriptionEditModel objektas
     * @throws Exception
     */
    public NtisServiceDescriptionEditModel save(Connection conn, NtisServiceDescriptionEditModel record, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getNtisServiceDescription().getSrv_id() == null ? NtisServiceDescriptionEdit.ACTION_CREATE : NtisServiceDescriptionEdit.ACTION_UPDATE);
        SprFilesDAO contractFile = null;
        SprFilesDAO labInstructionsFile = null;
        if (!sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        if (record.getContractFile() != null) {
            contractFile = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(record.getContractFile().getFil_key()));
        }
        if (record.getLabInstructions() != null) {
            labInstructionsFile = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(record.getLabInstructions().getFil_key()));
        }
        NtisServicesDAO serviceDAO = new NtisServicesDAO();
        if (record.ntisServiceDescription.getSrv_id() == null || record.ntisServiceDescription.getSrv_id() == 0) {
            serviceDAO = ntisServicesDBService.newRecord();
        } else {
            serviceDAO = ntisServicesDBService.loadRecord(conn, record.ntisServiceDescription.getSrv_id());
            if (!Objects.equals(serviceDAO.getSrv_org_id(), orgId)) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        // setting new values for description
        serviceDAO.setSrv_type(record.ntisServiceDescription.getSrv_type());
        serviceDAO.setSrv_contract_available(record.ntisServiceDescription.getSrv_contract_available());
        serviceDAO.setSrv_date_from(Utils.getDate(record.ntisServiceDescription.getSrv_date_from()));
        serviceDAO.setSrv_available_in_ntis_portal(record.ntisServiceDescription.getSrv_available_in_ntis_portal());
        serviceDAO.setSrv_lithuanian_level(record.ntisServiceDescription.getSrv_lithuanian_level());
        serviceDAO.setSrv_email(record.ntisServiceDescription.getSrv_email());
        serviceDAO.setSrv_phone_no(record.ntisServiceDescription.getSrv_phone_no());
        serviceDAO.setSrv_price_from(record.ntisServiceDescription.getSrv_price_from());
        if (record.ntisServiceDescription.getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
            serviceDAO.setSrv_price_to(null);
        } else {
            serviceDAO.setSrv_price_to(record.ntisServiceDescription.getSrv_price_to());
        }
        serviceDAO.setSrv_completion_in_days_from(record.ntisServiceDescription.getSrv_completion_in_days_from());
        if (!record.ntisServiceDescription.getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
            serviceDAO.setSrv_completion_in_days_to(null);
        } else {
            serviceDAO.setSrv_completion_in_days_to(record.ntisServiceDescription.getSrv_completion_in_days_to());
        }
        serviceDAO.setSrv_description(record.ntisServiceDescription.getSrv_description());
        serviceDAO.setSrv_org_id(orgId);
        if (record.ntisServiceDescription.getSrv_date_to() != null) {
            serviceDAO.setSrv_date_to(record.ntisServiceDescription.getSrv_date_to());
        }
        if (serviceDAO.getSrv_fil_id() != null && contractFile != null && contractFile.getFil_id() != null
                && serviceDAO.getSrv_fil_id().compareTo(contractFile.getFil_id()) != 0) {
            sprFilesDBService.markAsDeleted(conn, serviceDAO.getSrv_fil_id());
            serviceDAO.setSrv_fil_id(contractFile.getFil_id());
            sprFilesDBService.markAsConfirmed(conn, contractFile.getFil_id());
        }
        if (contractFile != null && contractFile.getFil_id() != null && serviceDAO.getSrv_fil_id() == null) {
            serviceDAO.setSrv_fil_id(contractFile.getFil_id());
            sprFilesDBService.markAsConfirmed(conn, contractFile.getFil_id());
        }
        if (contractFile != null) {
            if (!YesNo.getEnumByCode(record.ntisServiceDescription.getSrv_contract_available()).getBoolean()) {
                if (serviceDAO.getSrv_fil_id() != null) {
                    sprFilesDBService.markAsDeleted(conn, serviceDAO.getSrv_fil_id());
                    serviceDAO.setSrv_fil_id(null);
                }
            } else {
                serviceDAO.setSrv_fil_id(contractFile.getFil_id());
                sprFilesDBService.markAsConfirmed(conn, contractFile.getFil_id());
            }
        }

        if (!record.ntisServiceDescription.getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode()) && labInstructionsFile != null) {
            sprFilesDBService.markAsDeleted(conn, labInstructionsFile.getFil_id());
            if (serviceDAO.getSrv_lab_instr_fil_id() != null) {
                sprFilesDBService.markAsDeleted(conn, serviceDAO.getSrv_lab_instr_fil_id());
            }
            serviceDAO.setSrv_lab_instr_fil_id(null);
        }

        if (record.ntisServiceDescription.getSrv_type().equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode()) && labInstructionsFile != null) {
            if (serviceDAO.getSrv_lab_instr_fil_id() != null) {
                sprFilesDBService.markAsDeleted(conn, serviceDAO.getSrv_lab_instr_fil_id());
            }
            serviceDAO.setSrv_lab_instr_fil_id(labInstructionsFile.getFil_id());
            sprFilesDBService.markAsConfirmed(conn, labInstructionsFile.getFil_id());
        }
        ntisServicesDBService.saveRecord(conn, serviceDAO);

        if (record.ntisServiceDescription.getSrv_type().equalsIgnoreCase(NtisServiceItemType.MONTAVIMAS.getCode())
                && record.ntisServiceDescription.getSrv_date_from() != null) {
            SprOrganizationsDAO orgDAO = sprOrganizationsDBService.loadRecord(conn, serviceDAO.getSrv_org_id());
            if (orgDAO != null && orgDAO.getOrg_id() != null && orgDAO.getD03() == null) {
                orgDAO.setD03(Utils.getDate(new Date()));
                sprOrganizationsDBService.saveRecord(conn, orgDAO);
            }
        }

        NtisServiceReqItemsDAO serviceReqDAO = reqItemsDBService.loadRecord(conn, record.getNtisServiceDescription().getSri_id());
        serviceReqDAO.setSri_srv_id(serviceDAO.getSrv_id());
        reqItemsDBService.saveRecord(conn, serviceReqDAO);

        // checking the value of srv_lithuanian_level, if it was set to yes, removing all municipalities that were assigned previously
        if (record.ntisServiceDescription.getSrv_lithuanian_level() != null & record.ntisServiceDescription.getSrv_lithuanian_level().equalsIgnoreCase("Y")) {
            ntisServiceMunicipalitiesDBService.deleteRecordSrv(conn, record.ntisServiceDescription.getSrv_id());
        } else if (record.getMunicipalities() != null) {

            for (NtisMunicipalitiesRequest municipalitiesRow : record.getMunicipalities()) {
                NtisServiceMunicipalitiesDAO daoServiceMunicipality = null;
                if (municipalitiesRow.getMn_id() != null && municipalitiesRow.getMn_id() != 0.0) {
                    daoServiceMunicipality = ntisServiceMunicipalitiesDBService.loadRecord(conn, municipalitiesRow.getMn_id());
                } else {
                    daoServiceMunicipality = ntisServiceMunicipalitiesDBService.newRecord();
                }
                daoServiceMunicipality.setSmn_municipality(municipalitiesRow.getRfc_code());
                daoServiceMunicipality.setSmn_srv_id(serviceDAO.getSrv_id());
                if (municipalitiesRow.getMn_date_from() != null) {
                    daoServiceMunicipality.setSmn_date_from(municipalitiesRow.getMn_date_from());
                } else {
                    daoServiceMunicipality.setSmn_date_from(Utils.getDate(new Date()));
                }
                if (municipalitiesRow.getMn_date_to() != null) {
                    daoServiceMunicipality.setSmn_date_to(municipalitiesRow.getMn_date_to());
                }
                if (municipalitiesRow.isSelected()) {
                    ntisServiceMunicipalitiesDBService.saveRecord(conn, daoServiceMunicipality);
                } else if (!municipalitiesRow.isSelected()) {
                    ntisServiceMunicipalitiesDBService.deleteRecord(conn, municipalitiesRow.getMn_id());
                }
            }
        }
        return record;
    }
}
