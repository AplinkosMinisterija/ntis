package lt.project.ntis.logic.forms;

import java.sql.Connection;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprRefDictionariesDBServiceImpl;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisFacilityModelDAO;
import lt.project.ntis.models.NtisFacilityModelEditModel;
import lt.project.ntis.service.NtisFacilityModelDBService;

/**
 * Klasė skirta formos "Nuotekų tvarkymo įrenginio modelio redagavimas" biznio logikai apibrėžti
 */
@Component
public class NtisFacilityModelEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(NtisFacilityModelEdit.class);

    @Autowired
    NtisFacilityModelDBService ntisFacilityModelDBService;

    @Autowired
    SprRefCodesDBServiceImpl sprRefCodesDBService;

    @Autowired
    SprRefDictionariesDBServiceImpl sprRefDictionariesDBServiceImpl;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public String getFormName() {
        return "NTIS_FACILITY_MODEL_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų tvarkymo įrenginio modelio redagavimas", "Nuotekų tvarkymo įrenginio modelio redagavimo forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE);
    }

    /**
     * Function will return NtisFacilityModelEditModel object for provided rfc id
     * @param conn - connection to the DB
     * @param recordIdentifier - facility model identifier, which data should be returned
     * @return NtisFacilityModelEditModel object
     * @throws NumberFormatException
     * @throws Exception
     */
    public NtisFacilityModelEditModel get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisFacilityModelEdit.ACTION_READ);
        NtisFacilityModelEditModel ntisFacilityModelEditModel = new NtisFacilityModelEditModel();
        SprFile passportFile = null;
        SprRefCodesDAO facilityModel = sprRefCodesDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
        NtisFacilityModelDAO facilityModelAdditionalDetails = ntisFacilityModelDBService.loadRecordByParams(conn, "fam_rfc_id = ?::int",
                new SelectParamValue(facilityModel.getRfc_id()));
        if (facilityModelAdditionalDetails != null && facilityModelAdditionalDetails.getFam_fil_id() != null) {
            SprFilesDAO fileDAO = sprFilesDBService.loadRecord(conn, facilityModelAdditionalDetails.getFam_fil_id());
            passportFile = new SprFile();
            passportFile.setFil_content_type(fileDAO.getFil_content_type());
            passportFile.setFil_name(fileDAO.getFil_name());
            passportFile.setFil_size(fileDAO.getFil_size());
            passportFile.setFil_status(fileDAO.getFil_status());
            passportFile.setFil_status_date(fileDAO.getFil_status_date());
            passportFile.setFil_key(fileStorageService.encryptFileKey(fileDAO.getFil_key()));
        }
        ntisFacilityModelEditModel.setFacilityModel(facilityModel);
        ntisFacilityModelEditModel.setFacilityModelAdditionalDetails(facilityModelAdditionalDetails);
        ntisFacilityModelEditModel.setPassport(passportFile);
        return ntisFacilityModelEditModel;

    }

    /**
     * Function will save provided object to the db. Before save will be performed below listed actions:
     *      1. check if user has right perform this action (insert or update)
     *      2. validate provided data
     *      3. save data to the db.
     * @param conn - connection to the db, that will be used for data saving.
     * @param record - object that should be stored in db
     * @return - saved object.
     * @throws Exception
     */
    public NtisFacilityModelEditModel save(Connection conn, NtisFacilityModelEditModel record) throws Exception {
        NtisFacilityModelEditModel facilityModelEditModel = null;
        SprRefCodesDAO facilityModelRecord = record.getFacilityModel();
        SprRefCodesDAO facilityModel = null;
        NtisFacilityModelDAO faciModelAddDetailsRecord = record.getFacilityModelAdditionalDetails();
        NtisFacilityModelDAO faciModelAddDetails = null;
        SprFilesDAO fileDAO = null;
        SprFile passportFile = null;
        if (facilityModelRecord.getRfc_id() == null) {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
            facilityModelEditModel = new NtisFacilityModelEditModel();
            facilityModel = sprRefCodesDBService.newRecord();
            faciModelAddDetails = ntisFacilityModelDBService.newRecord();
        } else {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
            facilityModelEditModel = new NtisFacilityModelEditModel();
            facilityModel = sprRefCodesDBService.loadRecord(conn, facilityModelRecord.getRfc_id());
            if (faciModelAddDetailsRecord != null && faciModelAddDetailsRecord.getFam_id() != null) {
                faciModelAddDetails = ntisFacilityModelDBService.loadRecord(conn, faciModelAddDetailsRecord.getFam_id());
            } else {
                faciModelAddDetails = ntisFacilityModelDBService.newRecord();
            }
        }
        facilityModel.setRfc_meaning(facilityModelRecord.getRfc_meaning());
        facilityModel.setRfc_description(facilityModelRecord.getRfc_description());
        facilityModel.setRfc_date_from(facilityModelRecord.getRfc_date_from());
        facilityModel.setRfc_date_to(facilityModelRecord.getRfc_date_to());
        facilityModel.setRfc_ref_code_1(facilityModelRecord.getRfc_ref_code_1());
        facilityModel.setRfc_code(facilityModelRecord.getRfc_code());
        facilityModel.setRfc_domain("NTIS_FACIL_MODEL");
        Double rfd_id = sprRefDictionariesDBServiceImpl.loadRecordByParams(conn, "rfd_table_name = ?", new SelectParamValue("NTIS_FACIL_MODEL")).getRfd_id();
        facilityModel.setRfc_rfd_id(rfd_id);
        try {
            sprRefCodesDBService.saveRecord(conn, facilityModel);
        } catch (PSQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new SparkBusinessException(new S2Message("institutionsAdmin.pages.facilityModelEdit.facilityModelDuplicateError", SparkMessageType.ERROR,
                        "Facility model code already exists"));
            }
        }

        faciModelAddDetails.setFam_rfc_id(facilityModel.getRfc_id());
        faciModelAddDetails.setFam_pop_equivalent(faciModelAddDetailsRecord.getFam_pop_equivalent());
        faciModelAddDetails.setFam_chds(faciModelAddDetailsRecord.getFam_chds());
        faciModelAddDetails.setFam_bds(faciModelAddDetailsRecord.getFam_bds());
        faciModelAddDetails.setFam_float_material(faciModelAddDetailsRecord.getFam_float_material());
        faciModelAddDetails.setFam_phosphor(faciModelAddDetailsRecord.getFam_phosphor());
        faciModelAddDetails.setFam_nitrogen(faciModelAddDetailsRecord.getFam_nitrogen());
        faciModelAddDetails.setFam_tech_pass(faciModelAddDetailsRecord.getFam_tech_pass());
        if (record.getPassport() != null) {
            fileDAO = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(record.getPassport().getFil_key()));
            faciModelAddDetails.setFam_fil_id(fileDAO.getFil_id());
            passportFile = new SprFile();
            passportFile.setFil_content_type(fileDAO.getFil_content_type());
            passportFile.setFil_name(fileDAO.getFil_name());
            passportFile.setFil_size(fileDAO.getFil_size());
            passportFile.setFil_status(fileDAO.getFil_status());
            passportFile.setFil_status_date(fileDAO.getFil_status_date());
            passportFile.setFil_key(fileStorageService.encryptFileKey(fileDAO.getFil_key()));
        } else if (record.getPassport() == null && faciModelAddDetails.getFam_fil_id() != null) {
            SprFilesDAO sprFileDAO = sprFilesDBService.loadRecord(conn, faciModelAddDetails.getFam_fil_id());
            faciModelAddDetails.setFam_fil_id(null);
            ntisFacilityModelDBService.saveRecord(conn, faciModelAddDetails);
            fileStorageService.deleteFile(sprFileDAO.getFil_key(), sprFileDAO.getFil_path());
            sprFilesDBService.deleteRecord(conn, sprFileDAO.getFil_id());
        }
        ntisFacilityModelDBService.saveRecord(conn, faciModelAddDetails);
        facilityModelEditModel.setFacilityModel(facilityModel);
        facilityModelEditModel.setFacilityModelAdditionalDetails(faciModelAddDetails);
        facilityModelEditModel.setPassport(passportFile);
        return facilityModelEditModel;
    }
}
