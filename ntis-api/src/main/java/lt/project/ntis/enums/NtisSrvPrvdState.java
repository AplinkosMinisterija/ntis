package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_SRV_PRVD_STATE klasifikatoriaus reikšmes
 */
public enum NtisSrvPrvdState {

    SRV_PRVD_REGISTERED("SRV_PRVD_REGISTERED"),

    SRV_PRVD_DEREGISTERED("SRV_PRVD_DEREGISTERED"),

    SRV_PRVD_INACTIVE("SRV_PRVD_INACTIVE");

    String code;

    NtisSrvPrvdState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
