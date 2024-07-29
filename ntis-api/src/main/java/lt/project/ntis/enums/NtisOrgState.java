package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_ORG_TYPE klasifikatoriaus reikšmes
 */

public enum NtisOrgState {

    REGISTERED(1),

    DEREGISTERED(2);

    int code;

    NtisOrgState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
