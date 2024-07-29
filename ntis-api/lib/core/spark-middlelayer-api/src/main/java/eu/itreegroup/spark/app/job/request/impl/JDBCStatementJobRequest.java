package eu.itreegroup.spark.app.job.request.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component("JDBC_STATEMENT_EXECUTER")
public class JDBCStatementJobRequest extends JobRequestImpl {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(JDBCStatementJobRequest.class);

    public static final String CODE = "JDBC_STATEMENT_EXECUTER";

    public static final String NAME = "Request for JDBC statement";

    public static final String DESCRIPTION = "Request for JDBC statement";

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public HashMap<String, String> getJobRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        return params;
    }

    @Override
    public String getType() {
        return SprJobDefinitionsDBService.PROGRAM_TYPE; 
    }

    @Override
    public String getExecutorType() {
        return SprJobDefinitionsDBService.EXECUTOR_TYPE_JOB;
    }

}
