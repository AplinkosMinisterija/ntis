package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_ORDER_METHOD klasifikatoriaus reikšmes
 */
public enum NtisOrderMethod {

    IN_NTIS_PORTAL("Y"),

    NOT_IN_NTIS_PORTAL("N"),

    IMPORT("I"),

    API("A");

    String code;

    NtisOrderMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
