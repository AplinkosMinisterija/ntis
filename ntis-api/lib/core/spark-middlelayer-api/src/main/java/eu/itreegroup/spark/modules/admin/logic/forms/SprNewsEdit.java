package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprNewsCommentsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNewsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNewsFilesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsEditModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprNewsCommentsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsFilesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;

@Component
public class SprNewsEdit extends FormBase {

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprNewsDBService sprNewsDBService;

    @Autowired
    SprNewsFilesDBService sprNewsFilesDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprNewsCommentsDBService sprNewsCommentsDBService;

    @Override
    public String getFormName() {
        return "SPR_NEWS_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark news edit", "Spark news edit");
        addFormActionCRUD();
    }

    /**
     * Returns the news editing form with the required news information details loaded.
     * 
     * @param conn - database connection
     * @param recordId - news record ID whose information needs to be displayed in the editing form
     * @return NewsEditModel object with filled news information details and attachments
     * @throws Exception - if data cannot be obtained from the database or the action form does not allow the action to be performed
    */
    public NewsEditModel get(Connection conn, Double recordId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        SprNewsDAO loadedData = null;
        NewsEditModel returnData = new NewsEditModel();

        if (recordId != null) {

            loadedData = sprNewsDBService.loadRecord(conn, recordId);
            returnData.setNwId(loadedData.getNw_id());
            returnData.setNwTitle(loadedData.getNw_title());
            returnData.setNwText(loadedData.getNw_text());
            returnData.setNwSummary(loadedData.getNw_summary());
            returnData.setNwType(loadedData.getNw_type());
            returnData.setNwLang(loadedData.getNw_lang());
            returnData.setNwDateFrom(loadedData.getNw_date_from());
            returnData.setNwDateTo(loadedData.getNw_date_to());

            List<SprNewsFilesDAO> newsFilesResult = getNewsFiles(conn, returnData.getNwId());
            List<SprFile> attachments = new ArrayList<>();

            if (!newsFilesResult.isEmpty()) {
                for (SprNewsFilesDAO row : newsFilesResult) {
                    SprFile attachment = new SprFile();
                    SprFilesDAO file = sprFilesDBService.loadRecordByParams(conn, " fil_id = ? ", new SelectParamValue(row.getNwf_fil_id()));
                    attachment.setFil_content_type(file.getFil_content_type());
                    attachment.setFil_key(fileStorageService.encryptFileKey(file.getFil_key()));
                    attachment.setFil_name(file.getFil_name());
                    attachments.add(attachment);
                }
            }
            returnData.setAttachment(attachments);
        }
        return returnData;
    }

    /**
     * Saves news information to the database or updates an existing record, and uploads news attachments to the file storage.
     * 
     * @param conn - database connection
     * @param formData - news editing form data model, which contains news information and attachments
     * @return NewsEditModel object with filled news information data and attachments
     * @throws Exception - if data cannot be saved to the database or attachments cannot be uploaded to the file storage
     */
    public NewsEditModel save(Connection conn, NewsEditModel formData) throws Exception {

        this.checkIsFormActionAssigned(conn, formData.getNwId() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);

        SprNewsDAO newsDAO = formData.getNwId() == null ? sprNewsDBService.newRecord() : sprNewsDBService.loadRecord(conn, formData.getNwId());

        newsDAO.setNw_title(formData.getNwTitle());
        newsDAO.setNw_summary(formData.getNwSummary());
        newsDAO.setNw_lang(formData.getNwLang());
        newsDAO.setNw_text(formData.getNwText());
        newsDAO.setNw_lang(formData.getNwLang());
        newsDAO.setNw_type(formData.getNwType());
        newsDAO.setNw_date_from(formData.getNwDateFrom());
        newsDAO.setNw_date_to(formData.getNwDateTo());
        newsDAO.setNw_publication_date(Utils.getDate());
        newsDAO = sprNewsDBService.saveRecord(conn, newsDAO);
        saveFile(conn, formData.getAttachment(), newsDAO.getNw_id());
        return formData;
    }

    /**
     * Method for getting news files by specified id.
     * @param conn - database connection
     * @param nwId - news id
     * @return - list of news files
     * @throws Exception - if an error occurs while getting news files
     */
    protected List<SprNewsFilesDAO> getNewsFiles(Connection conn, Double nwId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT * FROM spr_news_files WHERE nwf_nw_id = ?");
        stmt.addSelectParam(nwId);
        return queryController.selectQueryAsObjectArrayList(conn, stmt, SprNewsFilesDAO.class);
    }

    /**
     * Inserts attachments and updates the list with the latest news entries.
     *
     * @param conn - created instance of database connection object
     * @param attachments - list of news entries attachments
     * @param nwId - news ID
     * @throws Exception - if an error occurs during the function execution
     */
    protected void saveFile(Connection conn, List<SprFile> attachments, Double nwId) throws Exception {

        List<SprNewsFilesDAO> questionFiles = getNewsFiles(conn, nwId);
        List<SprNewsFilesDAO> fileToDelete = new ArrayList<>();
        List<SprFilesDAO> fileToInsert = new ArrayList<>();

        for (SprNewsFilesDAO fileRecord : questionFiles) {
            boolean recordMatches = false;
            int i = 0;
            while (i < attachments.size() && !recordMatches) {
                SprFile fileAttachments = attachments.get(i++);
                SprFilesDAO fileData = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileAttachments.getFil_key()));
                recordMatches = (fileData != null && Double.compare(fileRecord.getNwf_fil_id(), fileData.getFil_id()) == 0);

            }

            if (!recordMatches) {
                fileToDelete.add(fileRecord);
            }
        }

        for (SprFile fileAttachments : attachments) {
            boolean recordMatches = false;
            int i = 0;
            while (i < questionFiles.size() && !recordMatches) {
                SprNewsFilesDAO fileRecord = questionFiles.get(i++);
                SprFilesDAO fileData = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileAttachments.getFil_key()));
                recordMatches = (fileData != null && Double.compare(fileRecord.getNwf_fil_id(), fileData.getFil_id()) == 0);

            }

            if (!recordMatches) {
                fileToInsert.add(sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileAttachments.getFil_key())));
            }
        }

        for (SprFilesDAO file : fileToInsert) {
            SprNewsFilesDAO newRecord = new SprNewsFilesDAO();
            newRecord.setNwf_fil_id(file.getFil_id());
            newRecord.setNwf_nw_id(nwId);
            sprNewsFilesDBService.insertRecord(conn, newRecord);
            sprFilesDBService.markAsConfirmed(conn, file.getFil_id());
        }

        for (SprNewsFilesDAO file : fileToDelete) {
            sprFilesDBService.markAsDeleted(conn, file.getNwf_fil_id());
            sprNewsFilesDBService.deleteRecord(conn, file.getNwf_id());
        }
    }

    /**
     * Deletes news entry, its comments and attachments from the database.
     *
     * @param conn - created instance of database connection object
     * @param recordId - ID of the entry to be deleted
     * @throws NumberFormatException - if an incorrect format ID is passed
     * @throws Exception - if an error occurs during the function execution
     */
    public void delete(Connection conn, Double recordId) throws NumberFormatException, Exception {

        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);

        List<SprNewsCommentsDAO> childComments = new ArrayList<>();

        List<SprNewsCommentsDAO> comments = sprNewsCommentsDBService.loadRecordsByParams(conn, " nwc_nw_id = ? ", new SelectParamValue(recordId));

        for (SprNewsCommentsDAO comment : comments) {
            childComments.add(comment);
        }

        for (SprNewsCommentsDAO childComment : childComments) {
            if (childComment.getNwc_nwc_id() != null) {
                sprNewsCommentsDBService.deleteRecord(conn, childComment.getNwc_id());
            }
        }

        for (SprNewsCommentsDAO comment : comments) {
            sprNewsCommentsDBService.deleteRecord(conn, comment.getNwc_id());
        }

        List<SprNewsFilesDAO> questionFilesResult = getNewsFiles(conn, recordId);

        for (SprNewsFilesDAO row : questionFilesResult) {
            SprFilesDAO file = sprFilesDBService.loadRecordByParams(conn, " fil_id = ? ", new SelectParamValue(row.getNwf_fil_id()));
            sprFilesDBService.markAsDeleted(conn, file.getFil_id());
            sprNewsFilesDBService.deleteRecord(conn, row.getNwf_id());
        }

        sprNewsDBService.deleteRecord(conn, recordId);
    }

}
