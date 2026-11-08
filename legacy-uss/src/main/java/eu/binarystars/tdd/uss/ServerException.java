package eu.binarystars.tdd.uss;

public class ServerException extends UserSelfServiceException {
    public ServerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
