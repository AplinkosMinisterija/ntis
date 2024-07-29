package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_ORD_FIL_STATUS klasifikatoriaus reikšmes
 */

public enum NtisOrdFilStatus {

    ORD_FIL_FINAL("ORD_FIL_FINAL"),

    ORD_FIL_PENDING("ORD_FIL_PENDING"),

    ORD_FIL_PENDING_ERR("ORD_FIL_PENDING_ERR"),

    ORD_FIL_ERR_PROCCESSING("ORD_FIL_ERR_PROCCESSING");

    String code;

    NtisOrdFilStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
