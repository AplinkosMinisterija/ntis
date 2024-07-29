package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprQuestionAnswersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprQuestionFilesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.FaqModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprQuestionAnswersDBService;
import eu.itreegroup.spark.modules.admin.service.SprQuestionFilesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;

@Component
public class SprFaqEdit extends FormBase {

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    SprQuestionAnswersDBService sprQuestionAnswersDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprQuestionFilesDBService sprQuestionFilesDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Override
    public String getFormName() {
        return "SPR_FAQ_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark FAQ edit", "Spark FAQ edit");
        addFormActionCRUD();
    }

    public void saveQuestionAnswer(Connection conn, FaqModel formData, String language) throws Exception {
        this.checkIsFormActionAssigned(conn, formData.getFacId() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);

        SprQuestionAnswersDAO sprQuestionAnswersDAO = null;

        if (formData.getFacId() == null) {
            sprQuestionAnswersDAO = sprQuestionAnswersDBService.newRecord();
        } else {
            sprQuestionAnswersDAO = sprQuestionAnswersDBService.loadRecord(conn, formData.getFacId());
        }
        sprQuestionAnswersDAO.setFac_group(formData.getFacGroup());
        sprQuestionAnswersDAO.setFac_type(formData.getFacType());
        sprQuestionAnswersDAO.setFac_lang(language);
        sprQuestionAnswersDAO.setFac_code(formData.getFacCode());
        sprQuestionAnswersDAO.setFac_question(formData.getFacQuestion());
        sprQuestionAnswersDAO.setFac_answer(formData.getFacAnswer());
        sprQuestionAnswersDAO.setFac_date_from(Utils.getDate());

        Double facId = sprQuestionAnswersDBService.saveRecord(conn, sprQuestionAnswersDAO).getFac_id();

        saveFile(conn, formData.getAttachment(), facId);

    }

    private ArrayList<SprQuestionFilesDAO> getQuestionFiles(Connection conn, Double fecId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT * FROM spr_question_files WHERE fcf_fac_id = ?");
        stmt.addSelectParam(fecId);
        return new ArrayList<>(queryController.selectQueryAsObjectArrayList(conn, stmt, SprQuestionFilesDAO.class));
    }

    public void saveFile(Connection conn, List<SprFile> attachments, Double fecId) throws Exception {

        ArrayList<SprQuestionFilesDAO> questionFiles = getQuestionFiles(conn, fecId);
        ArrayList<SprQuestionFilesDAO> fileToDelete = new ArrayList<>();
        ArrayList<SprFilesDAO> fileToInsert = new ArrayList<>();

        for (SprQuestionFilesDAO fileRecord : questionFiles) {
            boolean recordMatches = false;
            for (SprFile fileAttachments : attachments) {
                SprFilesDAO fileData = sprFilesDBService.loadRecordByKey(conn, fileAttachments.getFil_key());
                if (fileData != null && Double.compare(fileRecord.getFcf_fil_id(), fileData.getFil_id()) == 0) {
                    recordMatches = true;
                    break;
                }
            }
            if (!recordMatches) {
                fileToDelete.add(fileRecord);

            }
        }

        for (SprFile fileAttachments : attachments) {
            boolean recordMatches = false;
            for (SprQuestionFilesDAO fileRecord : questionFiles) {
                SprFilesDAO fileData = sprFilesDBService.loadRecordByKey(conn, fileAttachments.getFil_key());
                if (fileData != null && Double.compare(fileRecord.getFcf_fil_id(), fileData.getFil_id()) == 0) {
                    recordMatches = true;
                    break;
                }
            }

            if (!recordMatches) {
                fileToInsert.add(sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileAttachments.getFil_key())));
            }
        }

        for (SprFilesDAO file : fileToInsert) {
            SprQuestionFilesDAO newRecord = new SprQuestionFilesDAO();
            newRecord.setFcf_fil_id(file.getFil_id());
            newRecord.setFcf_fac_id(fecId);
            sprQuestionFilesDBService.insertRecord(conn, newRecord);
            sprFilesDBService.markAsConfirmed(conn, file.getFil_id());
        }

        for (SprQuestionFilesDAO file : fileToDelete) {
            sprFilesDBService.markAsDeleted(conn, file.getFcf_fil_id());
            sprQuestionFilesDBService.deleteRecord(conn, file.getFcf_id());
        }
    }

    public FaqModel getQuestionAnswer(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        SprQuestionAnswersDAO loadedData = null;
        FaqModel returnData = new FaqModel();
        if (recordIdentifier.getIdAsDouble() != null) {
            loadedData = sprQuestionAnswersDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
            returnData.setFacAnswer(loadedData.getFac_answer());
            returnData.setFacCode(loadedData.getFac_code());
            returnData.setFacQuestion(loadedData.getFac_question());
            returnData.setFacId(loadedData.getFac_id());
        }

        ArrayList<SprQuestionFilesDAO> questionFilesResult = getQuestionFiles(conn, returnData.getFacId());
        ArrayList<SprFile> attachments = new ArrayList<>();

        if (!questionFilesResult.isEmpty()) {
            for (SprQuestionFilesDAO row : questionFilesResult) {

                SprFile attachment = new SprFile();
                SprFilesDAO file = sprFilesDBService.loadRecordByParams(conn, " fil_id = ? ", new SelectParamValue(row.getFcf_fil_id()));
                attachment.setFil_content_type(file.getFil_content_type());
                attachment.setFil_key(file.getFil_key());
                attachment.setFil_name(file.getFil_name());
                attachments.add(attachment);
            }
        }

        returnData.setAttachment(attachments);

        return returnData;
    }

    public void deleteQuestionAnswer(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);

        ArrayList<SprQuestionFilesDAO> questionFilesResult = getQuestionFiles(conn, recordIdentifier.getIdAsDouble());
        if (!questionFilesResult.isEmpty()) {
            for (SprQuestionFilesDAO row : questionFilesResult) {
                SprFilesDAO file = sprFilesDBService.loadRecordByParams(conn, " fil_id = ? ", new SelectParamValue(row.getFcf_fil_id()));
                sprFilesDBService.markAsDeleted(conn, file.getFil_id());
                sprQuestionFilesDBService.deleteRecord(conn, row.getFcf_id());
            }
        }

        sprQuestionAnswersDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
    }

}
