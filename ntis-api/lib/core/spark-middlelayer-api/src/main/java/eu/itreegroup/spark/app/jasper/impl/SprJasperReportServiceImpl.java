package eu.itreegroup.spark.app.jasper.impl;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.spark.app.jasper.SprJasperReportService;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.enums.JasperReportOutputType;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRParagraph;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class SprJasperReportServiceImpl implements SprJasperReportService, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(SprJasperReportServiceImpl.class);

    private static final String JASPER_EXTENTION = ".jasper";

    private static final String JRXML_EXTENTION = ".jrxml";

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    DBStatementManager dbStatementManager;

    private String jasperPath;

    private String systemFileSeparator;

    @Override
    public SprFilesDAO generateReport(Connection conn, String reportPath, String reportName, JasperReportOutputType outputFormat, Map<String, Object> params)
            throws Exception {
        try {
            JasperPrint jasperPrint = fillReport(conn, reportPath, reportName, params);
            SprFilesDAO fileInfo = fileStorageService.createFileIndexInformation();
            File destFile = new File(fileInfo.getFil_path() + "/" + fileInfo.getFil_key());
            var exporter = ExporterFactory.createExporter(outputFormat, jasperPrint, destFile);
            exporter.exportReport();
            return fileStorageService.saveExistingFile(fileInfo, reportName + "." + outputFormat.getCode().toLowerCase(), outputFormat.getMimeType(), conn);
        } catch (Exception e) {
            log.error(String.format("Failed to generate report [{}]. Error: {}", reportName, e.toString()));
            throw new Exception("Failed to generate report: " + e.getMessage(), e);
        }
    }

    private File initReportFile(String fullPathJasper, String reportName) {
        log.debug("Try find report by name: " + fullPathJasper + systemFileSeparator + reportName);
        File file = new File(fullPathJasper + systemFileSeparator + reportName);
        if (!file.exists()) {
            String filePath = systemFileSeparator + "jasper" + systemFileSeparator + reportName;
            log.debug("Try find report by name: " + filePath);
            URL url = this.getClass().getClassLoader().getResource(filePath);
            if (url != null) {
                file = new File(url.getFile());
            }
        }
        return file;

    }

    private Map<String, Object> addParamIfNotExists(Map<String, Object> params, String key, Object value) {
        if (!params.containsKey(key)) {
            params.put(key, value);
        }
        return params;
    }

    private JasperPrint fillReport(Connection conn, String reportPath, String reportName, Map<String, Object> params) throws Exception {
        String jasperDirectory = jasperPath + (reportPath == null ? "" : systemFileSeparator + reportPath + systemFileSeparator);
        String reportNameWithDir = (reportPath == null ? "" : reportPath + systemFileSeparator) + reportName;
        log.debug("Try find compiled report by name: " + reportNameWithDir + JASPER_EXTENTION);
        File file = initReportFile(jasperPath, reportNameWithDir + JASPER_EXTENTION);
        JasperReport jasperReport = null;
        if (file.exists() && !file.isDirectory()) {
            jasperReport = (JasperReport) JRLoader.loadObject(file);
        } else {
            log.debug("Try find report source by name: " + reportNameWithDir + JRXML_EXTENTION);
            file = initReportFile(jasperPath, reportNameWithDir + JRXML_EXTENTION);
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            File reportDirectory = new File(jasperDirectory);
            if (!reportDirectory.exists()) {
                Files.createDirectories(Paths.get(jasperDirectory));
            }
            JRSaver.saveObject(jasperReport, jasperDirectory + systemFileSeparator + reportName + JASPER_EXTENTION);
        }
        return JasperFillManager.getInstance(DefaultJasperReportsContext.getInstance()).fill(jasperReport, addDefaultParams(params, jasperDirectory), conn);
    }

    protected Map<String, Object> addDefaultParams(Map<String, Object> params, String jasperDirectory) throws Exception {
        params = addParamIfNotExists(params, JRParameter.REPORT_LOCALE, new Locale("lt", "LT"));
        params = addParamIfNotExists(params, "DATE_PATTERN", dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        params = addParamIfNotExists(params, "DATE_TIME_PATTERN", dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB));
        params = addParamIfNotExists(params, "SUBREPORT_DIR", jasperDirectory);
        params = addParamIfNotExists(params, JRXPathQueryExecuterFactory.XML_LOCALE, new Locale("lt", "LT"));
        return params;
    }

    public void setJasperPath(String jasperPath) {
        this.jasperPath = jasperPath;
    }

    static class ExporterFactory {

        public static JRAbstractExporter<?, ?, ?, ?> createExporter(JasperReportOutputType outputFormat, JasperPrint jasperPrint, File destFile) {
            switch (outputFormat) {
                case FILE_TYPE_PDF: {
                    return createPdfExporter(jasperPrint, destFile);
                }
                case FILE_TYPE_XLS: {
                    JRXlsExporter xlsExporter = new JRXlsExporter();
                    xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
                    SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
                    xlsReportConfiguration.setOnePagePerSheet(true);
                    xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(false);
                    xlsReportConfiguration.setDetectCellType(true);
                    xlsExporter.setConfiguration(xlsReportConfiguration);
                    return xlsExporter;
                }
                case FILE_TYPE_DOCX: {
                    JRDocxExporter docExporter = new JRDocxExporter();
                    docExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    docExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
                    return docExporter;
                }
                case FILE_TYPE_CSV: {
                    JRCsvExporter csvExporter = new JRCsvExporter();
                    csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    csvExporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
                    SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
                    csvExporter.setConfiguration(configuration);
                    return csvExporter;
                }
                default: {
                    return createPdfExporter(jasperPrint, destFile);
                }
            }

        }

        private static JRPdfExporter createPdfExporter(JasperPrint jasperPrint, File destFile) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            return exporter;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
        JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
        JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.font.name", "Times New Roman");
        JRPropertiesUtil.getInstance(context).setProperty(JRParagraph.DEFAULT_TAB_STOP_WIDTH, "10");
        this.systemFileSeparator = System.getProperty("file.separator");
    }

}
