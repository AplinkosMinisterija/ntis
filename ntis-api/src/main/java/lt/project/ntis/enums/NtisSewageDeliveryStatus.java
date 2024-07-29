package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas klasifikatoriaus SWG_DLV_STS_SUBMITTED reikšmes
 */

public enum NtisSewageDeliveryStatus {

    SWG_DLV_STS_SUBMITTED("SWG_DLV_STS_SUBMITTED"),

    SWG_DLV_STS_REJECTED("SWG_DLV_STS_REJECTED"),

    SWG_DLV_STS_CONFIRMED("SWG_DLV_STS_CONFIRMED"),

    SWG_DLV_STS_USED("SWG_DLV_STS_USED"),
    
    SWG_DLV_STS_CANCELLED("SWG_DLV_STS_CANCELLED");

    String code;

    NtisSewageDeliveryStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
