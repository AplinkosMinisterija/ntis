package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti NTIS_SRV_REQ_STATUS klasifikatoriaus reikšmes
 */

public enum NtisServiceReqStatus {

    SUBMITTED("SUBMITTED"),

    REJECTED("REJECTED"),

    CONFIRMED("CONFIRMED");

    String code;

    NtisServiceReqStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
