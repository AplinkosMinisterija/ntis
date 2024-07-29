package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_CW_FIL_STATUS klasifikatoriaus reikšmes,
 * su papildoma NOT_SUBMITTED statuso reikšme, kuri naudojama pateiktos bylos filtre Centralizuoto nuotekų tvarkymo suvestinės formoje  
 */

public enum NtisCwFilStatus {

    CW_FIL_FINAL("CW_FIL_FINAL"),

    CW_FIL_PENDING("CW_FIL_PENDING"),

    CW_FIL_PENDING_ERR("CW_FIL_PENDING_ERR"),

    CW_FIL_ERR_PROCESSING("CW_FIL_ERR_PROCESSING"),

    NOT_SUBMITTED("NOT_SUBMITTED");

    String code;

    NtisCwFilStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
