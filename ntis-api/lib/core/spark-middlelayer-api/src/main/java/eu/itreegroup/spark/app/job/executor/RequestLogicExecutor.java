package eu.itreegroup.spark.app.job.executor;

import java.sql.Connection;

import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

public interface RequestLogicExecutor {

    void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request)
            throws Exception, InterruptedException, SparkBusinessException;

    String execute(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception;

}
