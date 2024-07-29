package lt.project.ntis.enums;

public enum NtisFacilityRegType {

    MANUALLY("MANUALLY");

    String code;

    NtisFacilityRegType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}