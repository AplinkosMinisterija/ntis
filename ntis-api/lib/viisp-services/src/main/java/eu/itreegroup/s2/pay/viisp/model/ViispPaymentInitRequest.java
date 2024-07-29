package eu.itreegroup.s2.pay.viisp.model;

public class ViispPaymentInitRequest {

    private Double correlationId;

    private String returnUrl;

    public ViispPaymentInitRequest() {
        super();
    }

    public ViispPaymentInitRequest(Double correlationId, String returnUrl) {
        super();
        this.correlationId = correlationId;
        this.returnUrl = returnUrl;
    }

    public Double getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Double correlationId) {
        this.correlationId = correlationId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public static ViispPaymentInitRequest fromString(String str) {
        String[] split = str.split(";");
        return new ViispPaymentInitRequest(Double.parseDouble(split[0]), split[1]);
    }

    @Override
    public String toString() {
        return correlationId + ";" + returnUrl;
    }

}
