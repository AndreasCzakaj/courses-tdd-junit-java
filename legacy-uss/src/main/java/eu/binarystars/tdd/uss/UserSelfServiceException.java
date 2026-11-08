package eu.binarystars.tdd.uss;

public class UserSelfServiceException extends RuntimeException {
    public UserSelfServiceException(final String message) {
        super(message);
    }

    public UserSelfServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
