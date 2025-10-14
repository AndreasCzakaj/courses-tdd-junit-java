package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LoginServiceTest {
    LoginService service;

    @BeforeEach
    void beforeEach() {
        service = new LoginService();
    }

    @Test
    void validationError() throws Throwable {
        // given
        Credentials invalidCredentials = new Credentials();

        // when
        ValidationError error = assertThrows(ValidationError.class, () -> service.login(invalidCredentials));

        // then
        assertThat(error).isExactlyInstanceOf(ValidationError.class);
        assertThat(error.attributes).contains("email", "password");
    }
}
