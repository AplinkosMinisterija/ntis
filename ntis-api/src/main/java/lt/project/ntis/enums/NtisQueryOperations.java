package lt.project.ntis.enums;

/**
 * Enum'as skirtas apibrėžti galimas NTIS_FACILITY_OWNER_TYPE klasifikatoriaus reikšmes
 */

public enum NtisQueryOperations {

    UPDATE("UPDATE"),

    INSERT("INSERT");

    String code;

    NtisQueryOperations(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}