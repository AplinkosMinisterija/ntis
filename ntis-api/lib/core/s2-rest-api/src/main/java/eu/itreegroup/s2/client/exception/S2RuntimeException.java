package eu.itreegroup.s2.client.exception;

public class S2RuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public S2RuntimeException() {
        super();
    }

    public S2RuntimeException(String message) {
        super(message);
    }

    public S2RuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public S2RuntimeException(Throwable cause) {
        super(cause);
    }
}
