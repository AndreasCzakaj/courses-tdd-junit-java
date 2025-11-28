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

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForEmailParams")
    public void shouldFailForEmail(String email) {
        shouldFailForField(
                createValidCredentials().toBuilder().email(email).build(),
                "invalid email",
                "email"
        );
    }

    static Stream<Arguments> shouldFailForEmailParams() {
        return of(
                arguments(null, ""),
                arguments("", ""),
                arguments("a", ""),
                arguments("a@", "")
        );
    }

    @ParameterizedTest(name = "email should be OK: '{0}' ({1})")
    @MethodSource("emailOkParams")
    void emailOk(String value, String reason) {
        var data = createValidCredentials().toBuilder().email(value).build();
        shouldPassForField(data, reason);
    }

    static Stream<Arguments> emailOkParams() {
        return of(
                arguments("a@b.c", "it should pass"),
                arguments(createValidCredentials().getEmail(), "it should pass")
        );
    }

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForPasswordParams")
    public void shouldFailForPassword(String pass, String reason) {
        shouldFailForField(
                createValidCredentials().toBuilder().password(pass).build(),
                reason,
                "password"
        );
    }

    static Stream<Arguments> shouldFailForPasswordParams() {
        return of(
                arguments(null, ""),
                arguments("", ""),
                arguments("a", ""),
                arguments("a@", "invalid char"),
                arguments("aB34567", ""),
                arguments("aB345678901234567", "too long"),
                arguments("ab34567890123456", "no upper")
        );
    }

    @ParameterizedTest(name = "password should be OK: '{0}' ({1})")
    @MethodSource("passwordOkParams")
    void passwordOk(String value, String reason) {
        var data = createValidCredentials().toBuilder().password(value).build();
        shouldPassForField(data, reason);
    }

    static Stream<Arguments> passwordOkParams() {
        return of(
                arguments("abC45678", "Min 1"),
                arguments("A2c45678", "Min 2"),
                arguments(createValidCredentials().getPassword(), "it should pass")
        );
    }
}
