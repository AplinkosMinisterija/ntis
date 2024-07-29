package lt.project.ntis.enums;

public enum NtisAggreementSource {

    MANUAL("MANUAL"),

    AUTO_LOAD("AUTO_LOAD");

    String code;

    NtisAggreementSource(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
