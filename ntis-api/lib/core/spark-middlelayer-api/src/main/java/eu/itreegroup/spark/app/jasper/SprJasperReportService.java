package eu.itreegroup.spark.app.jasper;

import java.sql.Connection;
import java.util.Map;

import eu.itreegroup.spark.enums.JasperReportOutputType;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

public interface SprJasperReportService {

    /**
     * Function will generate file in OS storage and save file metadata in spr_files table.
     * @param conn - connection to the db
     * @param reportPath - report directory, where are placed all report files
     * @param reportName - report name
     * @param outputFormat - generated report format
     * @param params - report parameters
     * @return - SprFilesDAO object, that holds information about generated file
     * @throws Exception
     */
    SprFilesDAO generateReport(Connection conn, String reportPath, String reportName, JasperReportOutputType outputFormat, Map<String, Object> params) throws Exception;

}
