package eu.itreegroup.spark.app.job.request.impl;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.enums.JasperReportOutputType;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

@Component("JASPER_REPORT_REQUEST")
public class JasperReportRequest extends JobRequestImpl {

    private static final Logger log = LoggerFactory.getLogger(JasperReportRequest.class);

    private String code;

    private String description;

    private JasperReportOutputType resultType = JasperReportOutputType.FILE_TYPE_PDF;

    private String executorType = SprJobDefinitionsDBService.EXECUTOR_TYPE_MANUAL;

    private HashMap<String, String> params = new HashMap<String, String>();

    /**
     * Reference to job executer.
     */
    @Autowired
    ExecutorJob executerJob;

    /**
     * Function will generate and return report
     * @param conn - DB connection used for data deletion
     * @param ses_usr_id - User ID
     * @param reportCode - Jasper report code defined in spr_jon_definiotion table
     * @param resultType - Jasper report outputtype (PDF,CSV, DOCX, XLS). Default value PDF
     * @param ses_language - Language
     * @throws NumberFormatException
     * @throws Exception
     */
    public SprFilesDAO executeJasper(Connection dbConnection, Double ses_usr_id, String ses_language, String reportCode,
            JasperReportOutputType outputResultType, HashMap<String, String> params) throws Exception {
        code = reportCode;
        resultType = outputResultType;
        Double reqId = createJobRequest(dbConnection, ses_usr_id, Languages.getLanguageByCode(ses_language), params);
        dbConnection.commit();
        // Jasper has own sesion
        SprFilesDAO file = executerJob.generateReportByRequest(dbConnection, reqId);
        log.debug("After jasper report execution: " + file);
        return file;
    }

    @Override
    public HashMap<String, String> getJobRequestParams() {
        return params;
    }

    public void setParameter(HashMap<String, String> jasperArgs) {
        params = jasperArgs;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getType() {
        return SprJobDefinitionsDBService.REPORT_TYPE;
    }

    @Override
    public String getExecutorType() {
        return executorType;
    }

    @Override
    public JasperReportOutputType getResultType() {
        return resultType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResultType(JasperReportOutputType resultType) {
        this.resultType = resultType;
    }

    public void setExecutorType(String executorType) {
        this.executorType = executorType;
    }

}
