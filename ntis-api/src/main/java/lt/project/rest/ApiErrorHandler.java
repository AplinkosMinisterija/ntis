package lt.project.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import eu.itreegroup.s2.client.exception.SparkRuntimeException;
import eu.itreegroup.s2.server.rest.S2RestApiErrorHandler;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkHasMessages;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkUserMessage;
import lt.project.ntis.rest.controller.NtisApiController;
import lt.project.ntis.rest.controller.models.ApiErrorMessage;

@RestControllerAdvice
@EnableWebMvc
@ControllerAdvice(assignableTypes = { NtisApiController.class })
public class ApiErrorHandler extends S2RestApiErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiErrorHandler.class);

    @Override
    @ExceptionHandler(value = { SparkBusinessException.class, SparkRuntimeException.class })
    @ResponseBody
    protected ResponseEntity<Collection<SparkUserMessage>> handleConflict(SparkHasMessages ex, WebRequest request) {
        log.error("ApiErrorHandler ControllerAdvice S2 Error:" + ex.getMessages(), ex);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (ex instanceof SparkBusinessException) {
            responseHeaders.add("X-S2-error-type", "BUS");

        } else if (ex instanceof SparkRuntimeException) {
            responseHeaders.add("X-S2-error-type", "RTM");
        }

        List<ApiErrorMessage> errorMessages = new ArrayList<ApiErrorMessage>();
        responseHeaders.add("X-S2-status", "ERR");
        for (SparkUserMessage message : ex.getMessages()) {
            ApiErrorMessage errorMessage = new ApiErrorMessage();
            errorMessage.setTitle(message.getCode());
            // errorMessage.setSource(message.get);
            errorMessage.setDetail(message.getParam1());
            // errorMessage.setStatus(message.get);
            errorMessages.add(errorMessage);
        }

        return new ResponseEntity<>(ex.getMessages(), responseHeaders, HttpStatus.BAD_REQUEST);
    }
}
