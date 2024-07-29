package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_ORG_TYPE klasifikatoriaus reikšmes
 */

public enum NtisOrgType {

    PASLAUG("PASLAUG"),

    VANDEN("VANDEN"),

    PASLAUG_VANDEN("PASLAUG_VANDEN"),

    INST("INST"),

    INST_LT("INST_LT");

    String code;

    NtisOrgType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
