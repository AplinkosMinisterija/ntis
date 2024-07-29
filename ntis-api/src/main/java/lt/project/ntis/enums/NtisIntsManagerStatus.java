package lt.project.ntis.enums;

/**
 * Klasė skirta ints valdytojo statuso, naudojamo "Nuotekų tvarkymo įrenginio valdytojai" (formos kodas N4070) formoje, reikšmėms apibrėžti
 */
public enum NtisIntsManagerStatus {

    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE");

    String code;

    NtisIntsManagerStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
