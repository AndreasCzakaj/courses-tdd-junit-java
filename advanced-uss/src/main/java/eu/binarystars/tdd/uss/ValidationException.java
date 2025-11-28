package eu.binarystars.tdd.uss;

import java.util.Map;

public class ValidationException extends UserException {
    public ValidationException(Map<String, String> fieldErrors) {
        super("Validation failed");
        this.fieldErrors = fieldErrors;
    }

    public final Map<String, String> fieldErrors;
}
