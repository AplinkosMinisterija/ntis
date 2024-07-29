package lt.project.ntis.enums;

public enum NtisClassifierDomains {

    NTIS_TYPE_WASTEWATER_TREATMENT("NTIS_TYPE_WASTEWATER_TREATMENT"), //
    NTIS_WTF_TYPE("NTIS_WTF_TYPE"), //
    NTIS_INTS_STATUS("NTIS_INTS_STATUS"), //
    NTIS_FACIL_MANUFA("NTIS_FACIL_MANUFA"), //
    NTIS_FACIL_MODEL("NTIS_FACIL_MODEL"), //
    DISCHARGE_WASTEWATER_TYPE("DISCHARGE_WASTEWATER_TYPE"), //
    NTIS_FACIL_DATA_SOURCE("NTIS_FACIL_DATA_SOURCE");

    String code;

    NtisClassifierDomains(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
