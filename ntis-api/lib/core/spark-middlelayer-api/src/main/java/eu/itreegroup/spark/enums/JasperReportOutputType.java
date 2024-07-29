package eu.itreegroup.spark.enums;

public enum JasperReportOutputType {

    FILE_TYPE_DOCX("DOCX"), FILE_TYPE_PDF("PDF"), FILE_TYPE_XLS("XLS"), FILE_TYPE_CSV("CSV");

    String code;

    JasperReportOutputType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final JasperReportOutputType getFormatByCode(String format) throws Exception {
        if (format == null || format.isEmpty()) {
            return JasperReportOutputType.FILE_TYPE_PDF;
        }
        for (JasperReportOutputType otuptuFormat : JasperReportOutputType.values()) {
            if (otuptuFormat.getCode().equals(format)) {
                return otuptuFormat;
            }
        }
        throw new Exception("Enumeration with code '" + format + "' not found");
    }

    public String getMimeType() {
        switch (this) {
            case FILE_TYPE_PDF:
                return "application/pdf";
            case FILE_TYPE_DOCX:
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case FILE_TYPE_XLS:
                return "application/vnd.ms-excel";
            default:
                return null;
        }
    }

}
