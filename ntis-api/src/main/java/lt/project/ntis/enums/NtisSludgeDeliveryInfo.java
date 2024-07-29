package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas SLUDGE_DELIVERY_INFO klasifikatoriaus reikšmes
 */

public enum NtisSludgeDeliveryInfo {

    SLD_DLV_WHOLE("SLD_DLV_WHOLE"),

    SLD_DLV_PARTIAL("SLD_DLV_PARTIAL"),

    SLD_DLV_USED("SLD_DLV_USED");

    String code;

    NtisSludgeDeliveryInfo(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
