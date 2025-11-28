package eu.binarystars.tdd.uss;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;

public class UssValidator {
    public void validate(Object object) {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();

        var res = validator.validate(object);
        Map<String, String> fields = new HashMap<>();
        res.stream().forEach(violation ->
                fields.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        if (!fields.isEmpty()) {
            throw new ValidationException(fields);
        }
    }
}
