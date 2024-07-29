package eu.itreegroup.spark.modules.common.rest;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestApiService;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.model.MenuStructure;
import eu.itreegroup.spark.app.model.RoleFormsActions;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@RestController
@RequestMapping("/public")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PublicService extends S2RestApiService<SprBackendUserSession> {

    @Autowired
    SprAuthorization<SprBackendUserSession> sprAuthorization;

    @Value("${app.default.language}")
    private String defaultLanguage;

    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping(value = "/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkUserRoles() throws Exception {
        ResponseEntity<String> answer = okResponse(sprAuthorization.getRoleList4User(this.getDBConnection(), this.requestContext.getUserSession()));
        return answer;
    }

    @RequestMapping(value = "/get-role-forms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleFormsActions> getRoleForms() throws SparkBusinessException, Exception {
        SprBackendUserSession session = this.requestContext.getUserSession();
        RoleFormsActions roleFormActiosn = sprAuthorization.getForms4Role(this.getDBConnection(), session.getSes_rol_id(), session.getLanguage());
        return okResponse(roleFormActiosn);
    }

    @RequestMapping(value = "/get-public-menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuStructure>> getPublicRoleMenu(@RequestBody RecordIdentifier recordIdentifier) throws SparkBusinessException, Exception {
        String language = defaultLanguage;
        if (recordIdentifier.getId() != null && !"".equalsIgnoreCase(recordIdentifier.getId())) {
            language = recordIdentifier.getId();
        }
        return okResponse(sprAuthorization.getPublicMenu(this.getDBConnection(), language));
    }

    @RequestMapping(value = "/get-file/{fileKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
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
