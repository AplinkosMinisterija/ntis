package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti NTIS_SERVICE_TYPE klasifikatoriaus reikšmes
 */

public enum NtisServiceType {

    PASLAUG("PASLAUG"),

    VANDEN("VANDEN");

    String code;

    NtisServiceType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
