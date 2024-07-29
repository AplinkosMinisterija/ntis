package eu.itreegroup.spark.modules.common.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestApiService;
import eu.itreegroup.s2.server.rest.model.ClientSideError;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

@RestController
@RequestMapping("/client-log")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LoggingService extends S2RestApiService<SprBackendUserSession> {

    private static final Logger log = LoggerFactory.getLogger(LoggingService.class);

    @RequestMapping(value = "/log-error", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getSparkParamater(@RequestBody ClientSideError clientSideError) throws Exception {
        log.debug(clientSideError.toString());
        return okResponse(null);
    }

}
