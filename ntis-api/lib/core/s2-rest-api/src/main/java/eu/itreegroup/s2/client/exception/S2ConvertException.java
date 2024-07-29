package eu.itreegroup.s2.client.exception;

public class S2ConvertException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    private String code;

    public S2ConvertException(String message) {
        super(message);
    }

    public S2ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public S2ConvertException(String code, String message) {
        super(message);
        this.code = code;
    }

    public S2ConvertException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
