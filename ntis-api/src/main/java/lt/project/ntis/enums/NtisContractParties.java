package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimus dalyvavimo sutartyje būdus.
 */
public enum NtisContractParties {

    SERVICE_PROVIDER("SERVICE_PROVIDER"),

    CLIENT("CLIENT"),

    NONE("NONE");

    String code;

    NtisContractParties(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
