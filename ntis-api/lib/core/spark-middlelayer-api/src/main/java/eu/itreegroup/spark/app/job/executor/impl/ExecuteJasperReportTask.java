package eu.itreegroup.spark.app.job.executor.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.jasper.SprJasperReportService;
import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.enums.JasperReportOutputType;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestArgsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

/**
 * Task  will run OS script by print request config
 */
@Service("JASPER_REPORT_EXECUTOR")
public class ExecuteJasperReportTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExecuteJasperReportTask.class);

    @Autowired
    SprJobDefinitionsDBService sprJobDefinitionsDBService;

    @Autowired
    SprJobRequestsDBService sprJobRequestsDBService;

    @Autowired
    SprJobRequestArgsDBService sprJobRequestArgsDBService;

    public ExecuteJasperReportTask(SprJasperReportService sprJasperReportService) {
        super();
        this.sprJasperReportService = sprJasperReportService;
    }

    private final SprJasperReportService sprJasperReportService;

    @Override
    public void executeSpecificLogic(Connection conn, String jasperRepCode, SprJobRequestsDAO request) throws Exception {
        SprJobDefinitionsDAO reqDefinition = sprJobDefinitionsDBService.loadRecord(conn, request.getJrq_jde_id());
        HashMap<String, Object> params = loadJobRequestParams(conn, request.getJrq_id());
        params.put("JRQ_ID", request.getJrq_id().intValue());
        params.putAll(this.sprJobRequestArgsDBService.getArgsMap(conn, request.getJrq_id()));
        SprFilesDAO fileDAO = sprJasperReportService.generateReport(conn, reqDefinition.getJde_path(), reqDefinition.getJde_execution_parameter(),
                JasperReportOutputType.getFormatByCode(request.getJrq_result_type()), params);
        request.setJrq_fil_id(fileDAO.getFil_id());
        request.setJrq_result_time(new Date());
        request.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_COMPLETED);
        request = sprJobRequestsDBService.saveRecord(conn, request);
        log.info("generateReport END");
    }

}
