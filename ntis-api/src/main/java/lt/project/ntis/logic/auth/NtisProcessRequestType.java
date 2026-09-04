package lt.project.ntis.logic.auth;

import eu.itreegroup.spark.enums.ProcessRequestType;

public enum NtisProcessRequestType implements ProcessRequestType {

    LOGIN_2FA("LOGIN_2FA");

    private final String code;

    NtisProcessRequestType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
