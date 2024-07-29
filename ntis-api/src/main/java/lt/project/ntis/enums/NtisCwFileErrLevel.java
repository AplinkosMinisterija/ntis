package lt.project.ntis.enums;

public enum NtisCwFileErrLevel {

    CW_FILE_LEVEL("FILE"),

    CW_COLUMN_LEVEL("COLUMN");

    String code;

    NtisCwFileErrLevel(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
