package eu.itreegroup.s2.server.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.service.S2AbstractService;

public class S2RestApiService<S extends BackendUserSession> extends S2AbstractService<S> {

    protected static final Double MAX_ROWS = 10000D;

    protected static final String X_S2_STATUS = "X-S2-status";

    protected static final String OK = "OK";

    protected <T> ResponseEntity<T> okResponse(T data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(X_S2_STATUS, OK);
        HttpStatus status = data != null ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<T>(data, responseHeaders, status);
    }
}
