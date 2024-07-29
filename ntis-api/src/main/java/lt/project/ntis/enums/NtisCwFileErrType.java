package lt.project.ntis.enums;

public enum NtisCwFileErrType {

    CW_ERR("ERR"),

    CW_WARN("WARN");

    String code;

    NtisCwFileErrType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
