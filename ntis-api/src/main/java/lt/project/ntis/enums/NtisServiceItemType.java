package lt.project.ntis.enums;

/**
 * Klasė skirta apibrėžti galimas NTIS_SRV_ITEM_TYPE klasifikatoriaus reikšmes
 */

public enum NtisServiceItemType {

    TYRIMAI("TYRIMAI"),

    VEZIMAS("VEZIMAS"),

    PRIEZIURA("PRIEZIURA"),
    
    MONTAVIMAS("MONTAVIMAS");

    String code;

    NtisServiceItemType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
