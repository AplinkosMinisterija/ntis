package eu.itreegroup.spark.enums;

public enum YesNo {

    Y("Y"), N("N");

    String code;

    YesNo(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final YesNo getEnumByCode(String status) throws Exception {
        for (YesNo yn : YesNo.values()) {
            if (yn.getCode().equals(status)) {
                return yn;
            }
        }
        throw new EnumConstantNotPresentException(YesNo.class, status);
    }

    public static final YesNo getEnumFromBoolean(boolean value) throws Exception {
        return value ? YesNo.Y : YesNo.N;
    }

    public boolean getBoolean() {
        return YesNo.Y.getCode().equals(code);
    }

}
