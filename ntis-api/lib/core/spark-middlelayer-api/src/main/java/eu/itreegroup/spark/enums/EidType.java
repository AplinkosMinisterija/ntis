package eu.itreegroup.spark.enums;

public enum EidType {

    FACEBOOK("FACEBOOK"), GOOGLE("GOOGLE"), VIISP("VIISP"), OTHER_EXTERNAL("OTHER_EXTERNAL");

    private String eidType;

    public String getValue() {
        return eidType;
    }

    EidType(String eidType) {
        this.eidType = eidType;
    }

}
