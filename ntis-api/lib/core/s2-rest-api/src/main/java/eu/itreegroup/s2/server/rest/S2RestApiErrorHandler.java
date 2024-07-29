package eu.itreegroup.s2.server.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import eu.itreegroup.s2.client.exception.SparkRuntimeException;
import eu.itreegroup.s2.server.rest.model.ServerErrorStackTrace;
import jakarta.validation.ConstraintViolationException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkHasMessages;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkUserMessage;

public class S2RestApiErrorHandler implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(S2RestApiErrorHandler.class);

    @Value("${spring.profiles.active:}")
    String springProfilesActive;

    private boolean isSpringProfileTestDev;

    @ExceptionHandler(value = { SparkBusinessException.class, SparkRuntimeException.class })
    @ResponseBody
    protected ResponseEntity<Collection<SparkUserMessage>> handleConflict(SparkHasMessages ex, WebRequest request) {
        log.error("S2 Error:" + ex.getMessages(), ex);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof SparkBusinessException) {
            responseHeaders.add("X-S2-error-type", "BUS");
        } else if (ex instanceof SparkRuntimeException) {
            responseHeaders.add("X-S2-error-type", "RTM");
        }
        responseHeaders.add("X-S2-status", "ERR");
        return new ResponseEntity<Collection<SparkUserMessage>>(ex.getMessages(), responseHeaders, HttpStatus.OK);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    @ResponseBody
    protected ResponseEntity<String> handleConflict(AccessDeniedException ex, WebRequest request) {
        log.error("Access Denied:", ex);
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, String> error = new HashMap<>();
        error.put(e.getParameterName(), e.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(violation -> {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        });
        return getBadRequestEntityWithErrors(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(BindException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return getBadRequestEntityWithErrors(errors);
    }

    protected ResponseEntity<Map<String, String>> getBadRequestEntityWithErrors(Map<String, String> errors) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errors);
    }

    @ExceptionHandler(value = { CannotCreateTransactionException.class })
    @ResponseBody
    protected ResponseEntity<String> handleConflict(CannotCreateTransactionException ex, WebRequest request) {
        Throwable cause = ex.getCause();
        if (cause != null && cause instanceof AccessDeniedException) {
            return handleConflict((AccessDeniedException) cause, request);
        } else if (cause != null && cause instanceof SparkBusinessException) {
            log.error("Access Denied:", cause);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        } else {
            log.error("Unhandled error creating transaction [{}]", ex);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = { Throwable.class })
    @ResponseBody
    protected ResponseEntity<ServerErrorStackTrace> handleConflict(Throwable ex, WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Unexpected error [{}]:", uuid, ex);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("X-S2-status", "ERR");
        responseHeaders.add("X-S2-Err-UUID", uuid);
        ServerErrorStackTrace httpBody = new ServerErrorStackTrace();
        BodyBuilder response = ResponseEntity.internalServerError().headers(responseHeaders).contentType(MediaType.APPLICATION_JSON);
        if (isSpringProfileTestDev) {
            httpBody.setStackTrace(throwableToString(ex));
            if (ex.getCause() != null) {
                httpBody.setCause(throwableToString(ex.getCause()));
            }
            return response.body(httpBody);
        }
        return response.build();
    }

    protected String throwableToString(Throwable th) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        th.printStackTrace(pw);
        return sw.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.isSpringProfileTestDev = springProfilesActive.contains("test") || springProfilesActive.contains("dev");

    }

}
