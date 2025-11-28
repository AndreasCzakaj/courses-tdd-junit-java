package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.Credentials;
import eu.binarystars.tdd.uss.ServerException;
import eu.binarystars.tdd.uss.Session;
import eu.binarystars.tdd.uss.UserException;
import eu.binarystars.tdd.uss.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static eu.binarystars.tdd.uss.UssTestHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    LoginService loginService;
    LoginController controller;

    @BeforeEach
    void setUp() {
        loginService = createLoginService(Optional.empty());
        controller = new LoginController(loginService);
    }

    @Test
    void loginOk() {
        var actual = controller.login(createCredentialsForExistingUser());
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                ResponseEntity.status(HttpStatus.CREATED).body(
                   Session.builder().userId(EXISTING_USER_ID).build()
                )
        );
    }

    @Test
    void loginFailedWithServerError() {
        LoginService loginService = new LoginService(null) {
            @Override
            public Session login(final Credentials credentials) {
                throw new ServerException("oops", new RuntimeException("oops"));
            }
        };

        LoginController controller = new LoginController(loginService);

        var actual = controller.login(createCredentialsForExistingUser());
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        Errors.builder()
                                .globalError("ServerError")
                                .build()
                )
        );
    }

    @Test
    void loginFailedWithValidationError() {
        var actual = controller.login(Credentials.builder().email("").password("aB34567").build());
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Errors.builder()
                            .globalError("ValidationError")
                            .build()
                            .addFieldError("email", "must not be empty")
                            .addFieldError("password", "size must be between 8 and 16")
                )
        );
    }

    @Test
    void loginFailedWithLoginError() {
        var actual = controller.login(
                Credentials.builder().email(NON_EXISTING_EMAIL).password(WRONG_PASSWORD).build()
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        Errors.builder()
                                .globalError("LoginFailed")
                                .build()
                )
        );
    }

    @Test
    void loginFailedWithGenericUserException() {
        LoginService loginService = new LoginService(null) {
            @Override
            public Session login(final Credentials credentials) {
                throw new UserException("oops");
            }
        };

        LoginController controller = new LoginController(loginService);

        var actual = controller.login(createCredentialsForExistingUser());
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Errors.builder()
                                .globalError("UserError")
                                .build()
                )
        );
    }
}