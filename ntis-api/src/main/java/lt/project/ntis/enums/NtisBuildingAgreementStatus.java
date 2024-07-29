package lt.project.ntis.enums;

/**
 * Enum'as skirtas pasakyti naudotojui ar po duomenų atnaujinimo bus pateiktas prašymas ar bus iškart atnaujinta
 */

public enum NtisBuildingAgreementStatus {

    WAITING("WAITING"),

    CONFIRMED("CONFIRMED"),

    REJECT("REJECT");

    String code;

    NtisBuildingAgreementStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
