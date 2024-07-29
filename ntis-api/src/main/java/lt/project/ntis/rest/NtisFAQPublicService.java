package lt.project.ntis.rest;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqThemesList;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.logic.forms.NtisSprFaqBrowse;

/**
 * Klasė skirta peržiūrėti FAQ (neprisijungusiam vartotojui)
 * 
 */

@RestController
@RequestMapping("/ntis-faq-pub")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisFAQPublicService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    SprFaqThemesList sprFaqThemesList;

    @Autowired
    NtisSprFaqBrowse ntisSprFaqBrowse;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // Frequently asked questions:
    @PostMapping(value = "/get-faq-themes-list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFaqThemesList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprFaqThemesList.getThemesList(this.getDBConnection(), params));
    }

    @PostMapping(value = "/get-faq-list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFaqListForAdmin(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSprFaqBrowse.getFaqList(this.getDBConnection(), params));
    }

    @PostMapping(value = "/get-faq-name", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<IdKeyValuePair> getGroupName(@RequestBody String code) throws Exception {
        return okResponse(ntisSprFaqBrowse.getGroupName(this.getDBConnection(), code));
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
}
