package eu.binarystars.tdd.uss;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ValidationError extends RuntimeException {
    public ValidationError(Collection<String> attributes) {
        super("Validation failed");
        this.attributes = attributes;
    }

    public final Collection<String> attributes;
}
