package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.LoginFailedException;
import eu.binarystars.tdd.uss.ServerException;
import eu.binarystars.tdd.uss.UserException;
import eu.binarystars.tdd.uss.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@Log4j2
public class ErrorHandler {

    private final Map<Class<? extends Throwable>, Function<Throwable, ResponseEntity<Errors>>> errorMapping = new HashMap<>(){{
        put(ValidationException.class, exc -> {
            ValidationException e = (ValidationException) exc;
            final Errors errors = Errors.builder().globalError("ValidationError").build();
            e.fieldErrors.keySet().forEach(fieldName -> {
                errors.addFieldError(fieldName, e.fieldErrors.get(fieldName));
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        });
        put(LoginFailedException.class, e -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Errors.builder().globalError("LoginFailed").build()
        ));
        put(UserException.class, e -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Errors.builder().globalError("UserError").build()
        ));
        put(ServerException.class, e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Errors.builder().globalError("ServerError").build()
        ));
    }};

    ResponseEntity<Errors> get(Throwable exc) {
        return errorMapping.getOrDefault(
                exc.getClass(),
                errorMapping.get(ServerException.class)
        ).apply(exc);
    }
}
