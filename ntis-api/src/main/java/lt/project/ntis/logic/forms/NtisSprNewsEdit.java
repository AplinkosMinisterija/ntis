package lt.project.ntis.logic.forms;

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
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprNewsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNewsFilesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprNewsEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprNewsCommentsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsFilesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.project.ntis.logic.forms.model.NtisNewsEditModel;

@Component
public class NtisSprNewsEdit extends SprNewsEdit {

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

    /**
     * Returns the news editing form with the required news information details loaded.
     * 
     * @param conn - database connection
     * @param recordId - news record ID whose information needs to be displayed in the editing form
     * @return NtisNewsEditModel object with filled news information details and attachments
     * @throws Exception - if data cannot be obtained from the database or the action form does not allow the action to be performed
    */
    public NtisNewsEditModel get(Connection conn, Double recordId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        SprNewsDAO loadedData = null;
        NtisNewsEditModel returnData = null;
        if (recordId != null) {
            loadedData = sprNewsDBService.loadRecord(conn, recordId);
            if (!lang.equals(loadedData.getNw_lang())) {
                if (loadedData.getC03() != null) {
                    loadedData = sprNewsDBService.loadRecordByParams(conn, " where nw_lang = ? and c03 = ? ", new SelectParamValue(lang),
                            new SelectParamValue(loadedData.getC03()));
                } else {
                    loadedData = sprNewsDBService.loadRecordByParams(conn, " where nw_lang = ? and nw_id = ?::int", new SelectParamValue(lang),
                            new SelectParamValue(recordId));
                }
            }
            if (loadedData != null) {
                returnData = new NtisNewsEditModel();
                returnData.setNwId(loadedData.getNw_id());
                returnData.setNwTitle(loadedData.getNw_title());
                returnData.setNwText(loadedData.getNw_text());
                returnData.setNwSummary(loadedData.getNw_summary());
                returnData.setNwType(loadedData.getNw_type());
                returnData.setNwLang(loadedData.getNw_lang());
                returnData.setNwDateFrom(loadedData.getNw_date_from());
                returnData.setNwDateTo(loadedData.getNw_date_to());
                returnData.setNwPublished(loadedData.getNw_publication_date());
                returnData.setIsPublic(loadedData.getC01());
                returnData.setIsTemplate(loadedData.getC02());
                returnData.setPageTemplateType(loadedData.getC03());

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
        }
        return returnData;
    }

    /**
     * Saves news information to the database or updates an existing record, and uploads news attachments to the file storage.
     * 
     * @param conn - database connection
     * @param formData - news editing form data model, which contains news information and attachments
     * @return NtisNewsEditModel object with filled news information data and attachments
     * @throws Exception - if data cannot be saved to the database or attachments cannot be uploaded to the file storage
     */
    public NtisNewsEditModel save(Connection conn, NtisNewsEditModel formData, String lang) throws Exception {

        this.checkIsFormActionAssigned(conn, formData.getNwId() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);

        SprNewsDAO newsDAO = formData.getNwId() == null || formData.isSaveAsNewTemplate() ? sprNewsDBService.newRecord()
                : sprNewsDBService.loadRecord(conn, formData.getNwId());

        newsDAO.setNw_title(formData.getNwTitle());
        newsDAO.setNw_summary(formData.getNwSummary());
        newsDAO.setNw_lang(formData.getNwLang());
        newsDAO.setNw_text(formData.getNwText());
        newsDAO.setNw_lang(lang);
        newsDAO.setNw_type(formData.getNwType());
        newsDAO.setNw_date_from(formData.getNwDateFrom());
        newsDAO.setNw_date_to(formData.getNwDateTo());
        newsDAO.setNw_publication_date(formData.getNwPublished());
        newsDAO.setC01(formData.getIsPublic());
        newsDAO.setC02(formData.getIsTemplate());
        newsDAO.setC03(formData.getPageTemplateType());
        newsDAO = sprNewsDBService.saveRecord(conn, newsDAO);
        saveFile(conn, formData.getAttachment(), newsDAO.getNw_id());
        return formData;
    }
}
