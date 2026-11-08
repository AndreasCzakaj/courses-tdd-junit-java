package eu.binarystars.tdd.uss;

public class ValidationException extends UserSelfServiceException {
    public final String field;

    public ValidationException(final String field) {
        super("invalid: " + field);
        this.field = field;
    }
}
