package eu.itreegroup.spark.app.job.executor.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

/**
 * Task  will run OS script by print request config
 */
@Service("CMD_SCRIPT_EXECUTOR")
public class ExecuteCmdScriptTask extends BaseRequestLogicExecutor {

    @Value("${scripts.path}")
    private String scriptsPath;

    @Autowired
    SprJobRequestsDBService sprJobRequestsDBService;

    private static final Logger log = LoggerFactory.getLogger(ExecuteCmdScriptTask.class);

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws InterruptedException, SparkBusinessException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        log.debug("CMD: " + scriptsPath + "/" + scriptName + " " + request.getJrq_id().intValue());
        processBuilder.command(scriptsPath + "/" + scriptName, request.getJrq_id().toString());
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            log.error("ExecuteCmdSrciptTask script " + scriptName + "  err:", e);
            throw new SparkBusinessException(new S2Message("common.message.integration", SparkMessageType.ERROR, "Integration script err: p1" + e));
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                output.append(line + " ");
            }
        } catch (IOException e) {
            log.error("ExecuteCmdSrciptTask Script " + scriptName + " err:", e);
            throw new SparkBusinessException(
                    new S2Message("common.message.integration", SparkMessageType.ERROR, "Integration script err" + "Integration script err: " + e));
        }
        int exitVal = process.waitFor();
        request.setJrq_data(Integer.toString(exitVal));
        try {
            this.sprJobRequestsDBService.saveRecord(conn, request);
        } catch (Exception e) {
            log.error("ExecuteCmdSrciptTask script " + scriptName + ", failed to save job request,  err:", e);
        }
        if (exitVal == 0) {
            log.debug("Integration job execute  successfuly");
        } else {
            log.debug(" ExecuteCmdSrciptTask Script " + scriptName + " {} ", exitVal);
            throw new SparkBusinessException(new S2Message(SparkMessageType.ERROR, " ExecuteCmdSrciptTask Script " + scriptName + " {" + exitVal + "} "));
        }
    }

}
