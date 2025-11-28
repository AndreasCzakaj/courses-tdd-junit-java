package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.ServerException;
import eu.binarystars.tdd.uss.SignUpData;
import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.service.SignUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static eu.binarystars.tdd.uss.UssTestHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

class SignUpControllerTest {

    SignUpService service;
    SignUpController controller;

    @BeforeEach
    void setUp() {
        service = createSignUpService(Optional.empty());
        controller = new SignUpController(service);
    }

    @Test
    void signUpOk() {
        var data = createSignUpDataForExistingUser().toBuilder().email(NON_EXISTING_EMAIL).build();
        var actual = controller.signUp(data);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("body.id")
                .isEqualTo(
                    ResponseEntity.status(HttpStatus.CREATED).body(
                       User.builder()
                               .email(data.getEmail())
                               .acceptedTerms(data.getAcceptedTerms())
                               .build()
                    )
                );
    }

    @Test
    void loginFailedWithServerError() {
        SignUpService service = new SignUpService(null, null) {
            @Override
            public User signUp(final SignUpData signUpData) {
                throw new ServerException("oops", new RuntimeException("oops"));
            }
        };

        SignUpController controller = new SignUpController(service);

        var actual = controller.signUp(createSignUpDataForExistingUser());
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        Errors.builder()
                                .globalError("ServerError")
                                .build()
                )
        );
    }
}