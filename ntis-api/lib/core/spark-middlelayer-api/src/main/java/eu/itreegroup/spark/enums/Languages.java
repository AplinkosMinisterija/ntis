package eu.itreegroup.spark.enums;

public enum Languages {

    LT("lt"), EN("en");

    String code;

    Languages(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final Languages getLanguageByCode(String lang) throws Exception {
        for (Languages language : Languages.values()) {
            if (language.getCode().equals(lang)) {
                return language;
            }
        }
        throw new Exception("Enumeration with code '" + lang + "' not found");
    }

}
