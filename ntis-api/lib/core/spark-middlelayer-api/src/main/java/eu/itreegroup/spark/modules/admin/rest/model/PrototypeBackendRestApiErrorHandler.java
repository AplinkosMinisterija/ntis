package eu.itreegroup.spark.modules.admin.rest.model;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import eu.itreegroup.s2.server.rest.S2RestApiErrorHandler;

@RestControllerAdvice
@EnableWebMvc
public class PrototypeBackendRestApiErrorHandler extends S2RestApiErrorHandler {

}
