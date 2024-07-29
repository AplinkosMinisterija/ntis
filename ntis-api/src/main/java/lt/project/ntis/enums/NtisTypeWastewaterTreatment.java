package lt.project.ntis.enums;

public enum NtisTypeWastewaterTreatment {

    CENTRALIZED("CENTRALIZED"), LOCAL("LOCAL");

    String code;

    NtisTypeWastewaterTreatment(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}