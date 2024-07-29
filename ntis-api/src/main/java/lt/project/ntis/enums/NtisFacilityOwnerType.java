package lt.project.ntis.enums;

/**
 * Enum'as skirtas apibrėžti galimas NTIS_FACILITY_OWNER_TYPE klasifikatoriaus reikšmes
 */

public enum NtisFacilityOwnerType {

    OWNER("OWNER"),

    SERVICE_PROVIDER("SERVICE_PROVIDER"),

    SELF_ASSIGNED("SELF_ASSIGNED"),

    MANAGER("MANAGER");

    String code;

    NtisFacilityOwnerType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}