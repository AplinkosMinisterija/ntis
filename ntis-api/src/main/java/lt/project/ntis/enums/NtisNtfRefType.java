package lt.project.ntis.enums;

public enum NtisNtfRefType {

    SRV_REQ("SRV_REQ"),

    ORDER("ORDER"),

    DELIVERY("DELIVERY"),

    SYSTEM("SYSTEM"),

    RESEARCH("RESEARCH"),

    CONTRACT("CONTRACT"),

    AGGLOMERATION("AGGLOMERATION"),
    
    CENTRALIZED_WASTEWATER("CENTRALIZED_WASTEWATER"),
    
    SCHEDULER("SCHEDULER"),

    FUA_AGREEMENT("FUA_AGREEMENT");

    String code;

    NtisNtfRefType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
