package lt.project.ntis.enums;

/**
 * Enum'as skirtas apibrėžti galimas NTIS_BUILDING_TYPE klasifikatoriaus reikšmes
 */

public enum NtisBuildingType {

    WASTEWATER("WASTEWATER"),

    BUILDING("BUILDING");

    String code;

    NtisBuildingType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
