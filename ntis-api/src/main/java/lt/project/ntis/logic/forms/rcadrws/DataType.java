package lt.project.ntis.logic.forms.rcadrws;

/**
 * Klasė skirta apibrėžti Registrų Centro AdrWS paslaugos teikiamus duomenų rinkinių tipus.
 */
public enum DataType {

    ADM("administraciniai vienetai"), GYV("gyvenamosios vietovės"), GAT("gatvės"), ADRXY("adresai su koordinatėmis"), PAT("patalpos");

    private final String description;

    private DataType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
