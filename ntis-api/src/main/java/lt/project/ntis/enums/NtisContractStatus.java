package lt.project.ntis.enums;

/**
 * Klasė skirta sutarties būsenos klasifikatoriaus NTIS_COT_STATE reikšmėms apibrėžti
 */

public enum NtisContractStatus {

    SUBMITTED("SUBMITTED"),

    CANCELLED("CANCELLED"),

    REJECTED("REJECTED"),

    TERMINATED("TERMINATED"),

    SIGNED_BY_SRV_PROV("SIGNED_BY_SRV_PROV"),

    SIGNED_BY_CUSTOMER("SIGNED_BY_CUSTOMER"),

    SIGNED_BY_BOTH("SIGNED_BY_BOTH"),

    CONFIRMED("CONFIRMED"),

    VALID("VALID"),

    EXPIRED("EXPIRED");

    String code;

    NtisContractStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
