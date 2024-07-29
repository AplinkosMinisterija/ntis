package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_ORDER_STATUS klasifikatoriaus reikšmes
 */

public enum NtisOrderStatus {

    ORD_STS_SUBMITTED("ORD_STS_SUBMITTED"),

    ORD_STS_CANCELLED("ORD_STS_CANCELLED"),

    ORD_STS_REJECTED("ORD_STS_REJECTED"),

    ORD_STS_CONFIRMED("ORD_STS_CONFIRMED"),

    ORD_STS_FINISHED("ORD_STS_FINISHED"),

    ORD_STS_CANCELLED_SYSTEM("ORD_STS_CANCELLED_SYSTEM");

    String code;

    NtisOrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
