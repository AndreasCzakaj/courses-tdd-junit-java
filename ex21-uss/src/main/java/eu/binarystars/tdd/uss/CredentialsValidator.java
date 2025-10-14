package eu.binarystars.tdd.uss;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CredentialsValidator {
    void validate(Credentials credentials) {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();

        var res = validator.validate(credentials);
        Set<String> fields = new HashSet<>();
        res.stream().forEach(violation -> fields.add(violation.getPropertyPath().toString()));

        if (fields.size() > 0) {
            throw new ValidationError(fields);
        }
    }
}
