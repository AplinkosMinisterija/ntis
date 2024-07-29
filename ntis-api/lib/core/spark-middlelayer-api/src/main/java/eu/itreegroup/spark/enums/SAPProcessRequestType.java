package eu.itreegroup.spark.enums;

public enum SAPProcessRequestType implements ProcessRequestType {

    NEW_USER_REQUEST("NEW_USER_REQUEST"), //
    CHANGE_PASSWORD_REQUEST("CHANGE_PASSWORD_REQUEST"), //
    CHECK_EMAIL_REQUEST("CHECK_EMAIL_REQUEST"), //
    CHANGE_PASSWORD("CHANGE_PASSWORD"), //
    CREATE_PASSWORD_REQUEST("CREATE_PASSWORD_REQUEST");

    String code;

    SAPProcessRequestType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static ProcessRequestType getProcessRequestType(String requestType) throws Exception {
        for (SAPProcessRequestType requestTypeEnum : SAPProcessRequestType.values()) {
            if (requestTypeEnum.getCode().equals(requestType)) {
                return requestTypeEnum;
            }
        }
        throw new Exception("Enumeration with code '" + requestType + "' not found");
    }
}
