package lt.project.ntis.enums;

public enum NtisFacilityStatus {

    REGISTERED("REGISTERED"),

    CONFIRMED("CONFIRMED"),

    CLOSED("CLOSED");

    String code;

    NtisFacilityStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}