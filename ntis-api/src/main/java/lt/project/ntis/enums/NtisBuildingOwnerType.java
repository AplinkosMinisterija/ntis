package lt.project.ntis.enums;

/*
 * Enumas skirtas NTIS_BUILDING_NTR_OWNERS lentelės lauko BNO_TYPE galimoms reikšmėms
 */
public enum NtisBuildingOwnerType {

    F("F"), J("J");

    String code;

    NtisBuildingOwnerType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}