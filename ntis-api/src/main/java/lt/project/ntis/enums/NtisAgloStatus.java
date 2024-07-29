package lt.project.ntis.enums;

/**
 * Enum'as skirtas apibrėžti galimas NTIS_AGLO_STATUS klasifikatoriaus reikšmes
 */

public enum NtisAgloStatus {

    AGLO_STATUS_CHECKING("AGLO_STATUS_CHECKING"),

    AGLO_STATUS_APPROVED("AGLO_STATUS_APPROVED"),

    AGLO_STATUS_REJECTED("AGLO_STATUS_REJECTED"),

    AGLO_STATUS_DEPRECATED("AGLO_STATUS_DEPRECATED");

    String code;

    NtisAgloStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}