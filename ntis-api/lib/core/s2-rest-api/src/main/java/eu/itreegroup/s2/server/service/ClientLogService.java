package eu.itreegroup.s2.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestApiService;
import eu.itreegroup.s2.server.rest.model.ClientSideError;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Service
@RequestMapping("/client-log")
@Transactional
public class ClientLogService extends S2RestApiService<BackendUserSession> {
    
    private static final Logger log = LoggerFactory.getLogger(ClientLogService.class);
    
    @RequestMapping(value = "/log-error", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> logError(@RequestBody ClientSideError theClientSideError) throws SparkBusinessException {

        theClientSideError.setUser(requestContext.getUserSession().getUsername());
        log.warn(theClientSideError.toString());

        return okResponse(null);
    }

}
