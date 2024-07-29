package eu.itreegroup.spark.app.job.request;

import java.sql.Connection;
import java.util.Map;

import eu.itreegroup.spark.enums.JasperReportOutputType;

public interface JobRequest {

    String getCode();

    /*
     * At this moment there is to types of request REPORT or MAIL
     */
    String getType();

    String getName();

    String getDescription();

    /*
     * MANUAL or JOB
     * If will be set to manual, the job will not execute this request
     */
    String getExecutorType();

    /*
     * PDF, DOCX..
     * Depend on request type
     */
    default JasperReportOutputType getResultType() {
        return null;
    };

    Map<String, String> getJobRequestParams();

    Map<String, String> loadJobRequestParams(Connection conn, Double requestId);

}
