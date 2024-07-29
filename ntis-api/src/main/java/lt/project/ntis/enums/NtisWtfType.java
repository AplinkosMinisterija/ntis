package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_WTF_TYPE klasifikatoriaus reikšmes
 */

public enum NtisWtfType {

    RESERVOIR("RESERVOIR"),

    SEPTIC("SEPTIC"),

    BIO("BIO"),

    PORTABLE_RESERVOIR("PORTABLE_RESERVOIR");

    String code;

    NtisWtfType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
