package eu.itreegroup.spark.app.job.executor.impl;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestArgsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestArgsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestExecutionsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Service("JDBC_STATEMENT_EXECUTOR")
public class ExecuteJDBCStatementTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExecuteJDBCStatementTask.class);

    @Autowired
    SprJobDefinitionsDBService sprJobDefinitionDBService;

    @Autowired
    SprJobRequestExecutionsDBService sprJobRequestExecutionsDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprJobRequestArgsDBService sprJobRequestArgsDBService;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request)
            throws Exception, InterruptedException, SparkBusinessException {
        SprJobDefinitionsDAO jobDefinition = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());
        String stmt = jobDefinition.getJde_execution_parameter();
        VelocityContext statementContext = new VelocityContext();
        statementContext.put("jrq_id", request.getJrq_id().intValue());
        List<SprJobRequestArgsDAO> argsList = sprJobRequestArgsDBService.loadRecordsByParams(conn, " jra_jrq_id = ? ", request.getJrq_id().longValue());
        for (SprJobRequestArgsDAO arg : argsList) {
            statementContext.put(arg.getJra_name(), arg.getJra_value());
        }
        StringWriter w = new StringWriter();
        Velocity.evaluate(statementContext, w, "jdbcJobStatement", stmt);
        stmt = w.toString();
        if (dbStatementManager.usePostgre() && stmt.toLowerCase().startsWith("begin")) {
            stmt = "DO $$ \r\n" + //
                    "<<first_block>>\r\n" + //
                    "begin\r\n" + //
                    stmt + //
                    "\r\nEND first_block $$;";
        }
        log.debug("Will execute statement: " + stmt);
        try (PreparedStatement pst = conn.prepareStatement(stmt)) {
            pst.execute();
        } catch (Exception e) {
            log.error("Error on execution sql statment: " + stmt, e);
            throw e;
        }

    }

}
