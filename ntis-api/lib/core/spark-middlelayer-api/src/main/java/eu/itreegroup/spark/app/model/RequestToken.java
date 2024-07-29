package eu.itreegroup.spark.app.model;

import eu.itreegroup.spark.enums.SAPProcessRequestType;

public class RequestToken {

    String token;

    String requestType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public SAPProcessRequestType getProcessRequestType() {
        return SAPProcessRequestType.valueOf(getRequestType());
    }

}
