package eu.binarystars.tdd.uss;

public class DaoException extends RuntimeException {
    public DaoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
