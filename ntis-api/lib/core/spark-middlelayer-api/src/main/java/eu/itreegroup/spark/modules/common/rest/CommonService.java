package eu.itreegroup.spark.modules.common.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.rest.S2RestApiService;
import eu.itreegroup.spark.app.model.AppData;
import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBFormManager;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.logic.forms.model.FormPredefinedData;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.rest.model.Request4FormsDefinedData;
import eu.itreegroup.spark.modules.admin.service.SprPropertiesDBService;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotificationNew;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.rest.model.NotificationRecipient;
import eu.itreegroup.spark.modules.common.rest.model.NotificationRequest;
import eu.itreegroup.spark.modules.common.rest.model.NtfRecipientsSearchReq;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@RestController
@RequestMapping("/common")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommonService extends S2RestApiService<SprBackendUserSession> {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprFilesDBService filesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprPropertiesDBService sprPropertiesDBService;

    @Autowired
    SprRefCodesDBService sprRefCodesDBService;

    @Autowired
    DBFormManager dbFormManager;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    SprNotifications sprNotifications;

    @Autowired
    SprNotificationNew sprNotificationNew;

    @Value("${spring.profiles.active:}")
    String springProfilesActive;

    @RequestMapping(value = "/appData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppData> getAppData() throws Exception {
        return okResponse(this.dbPropertyManager.getAppData(this.getDBConnection()));
    }

    @RequestMapping(value = "/ref-codes/{lang}/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SprListIdKeyValue>> getSparkParamater(@PathVariable(value = "lang") String lang, @PathVariable(value = "code") String code)
            throws Exception {
        return okResponse(sprRefCodesDBService.getRefCodes(this.getDBConnection(), code, lang));
    }

    @RequestMapping(value = "/get-form-predefined-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormPredefinedData> getFormdata(@RequestBody Request4FormsDefinedData formsDefinedData) throws Exception {
        String language = formsDefinedData.getLanguage();
        if (language == null) {
            language = this.requestContext.getUserSession().getSes_language();
        }
        return okResponse(dbFormManager.getPredefinedFormData(this.getDBConnection(), formsDefinedData.getFormCode(),
                this.requestContext.getUserSession().getSes_usr_id(), language));
    }

    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public ResponseEntity<SprFile> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        SprFile tempFile = new SprFile();
        try {
            tempFile = fileStorageService.saveFile(file, this.getDBConnection());
        } catch (Exception e) {
            throw new SparkBusinessException(new S2Message("common.fileUpload.couldNotUploadFile", SparkMessageType.ERROR));
        }
        return okResponse(tempFile);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<SprFile>> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        List<SprFile> result = new ArrayList<SprFile>();
        Map<String, MultipartFile> map = mpr.getFileMap();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            MultipartFile mp = map.get(key);
            result.add(fileStorageService.saveFile(mp, this.getDBConnection()));
        }
        return okResponse(result);
    }

    @RequestMapping(value = "/delete-file", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteFile(@RequestBody SprFile fileToDelete) throws SparkBusinessException {
        try {
            fileStorageService.deleteTempFile(fileToDelete, null, this.getDBConnection());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete files!");
        }
        return okResponse(null);
    }

    @RequestMapping(value = "/get-file/{fileKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttachment(@PathVariable(name = "fileKey") String fileKey) throws SparkBusinessException {
        if (fileKey != null) {
            InputStream fileContent = fileStorageService.getFileByFileEncryptedKey(fileKey, this.getDBConnection());
            HttpHeaders headers = new HttpHeaders();
            headers.add(X_S2_STATUS, OK);
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(fileContent));
        } else {
            return okResponse(null);
        }
    }
    // Notification (start)

    @RequestMapping(value = "/get-user-notifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserNotifications() throws Exception {
        return okResponse(sprNotifications.getUserNotificationList(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_rol_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/send-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void sendNotifications(@RequestBody NotificationRequest record) throws Exception {
        sprNotifications.sendNotifications(this.getDBConnection(), record);
    }

    @RequestMapping(value = "/mark-user-notification-as-read", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> markUserNotificaitonAsRead(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprNotifications.markAsRead(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_rol_id(), recordIdentifier.getIdAsDouble());
        return okResponse(null);
    }

    @RequestMapping(value = "/del-user-notification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delUserNotification(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprNotifications.deleteNotification(this.getDBConnection(), recordIdentifier.getIdAsDouble());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-user-notifications-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllNotifications(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.sprNotifications.getAllNotifications(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_rol_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/find-recipients", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationRecipient>> findRecipients(@RequestBody NtfRecipientsSearchReq searchRequest) throws Exception {
        return okResponse(sprNotificationNew.findRecipients(this.getDBConnection(), searchRequest, this.requestContext.getUserSession().getUsr_id()));
    }

    // Notification (end)

}
