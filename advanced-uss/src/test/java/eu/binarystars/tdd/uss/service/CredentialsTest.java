package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.Credentials;
import eu.binarystars.tdd.uss.UssValidator;
import eu.binarystars.tdd.uss.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CredentialsTest {

    UssValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UssValidator();
    }

    static Credentials createValidCredentials() {
        return Credentials.builder()
                .email("test@acme.com")
                .password("aB345678")
                .build();
    }

    void shouldFailForField(Object object, String reason, String fieldName) {
        ValidationException error = assertThrows(ValidationException.class, () -> validator.validate(object));

        // then
        assertThat(error)
                .as(reason)
                .hasNoCause()
                .isExactlyInstanceOf(ValidationException.class);
        assertThat(error.fieldErrors.keySet()).containsExactly(fieldName);
    }

    void shouldPassForField(Object object, String reason) {
        validator.validate(object);
        assertThat(true).as(reason).isTrue();
    }

}
