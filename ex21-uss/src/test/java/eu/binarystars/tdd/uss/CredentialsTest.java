package eu.binarystars.tdd.uss;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CredentialsTest {
    Credentials createValidCredentials() {
        return Credentials.builder()
                .email("test@acme.com")
                .password("aB345678")
                .build();
    }

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForEmailParams")
    public void shouldFailForEmail(String email) {
        // given
        CredentialsValidator validator = new CredentialsValidator();
        Credentials credentials = createValidCredentials().toBuilder()
                .email(email)
                .build();

        ValidationError error = assertThrows(ValidationError.class, () -> validator.validate(credentials));

        // then
        assertThat(error)
                .hasNoCause()
                .isExactlyInstanceOf(ValidationError.class);
        assertThat(error.attributes).containsExactly("email");
    }

    static Stream<Arguments> shouldFailForEmailParams() {
        return of(
                arguments(null, ""),
                arguments("", ""),
                arguments("a", ""),
                arguments("a@", "")
        );
    }

    @Test
    void emailOk() throws Throwable {
        // given
        CredentialsValidator validator = new CredentialsValidator();

        validator.validate(createValidCredentials());

        // then
        assertThat(true).as("it should pass").isTrue();
    }

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForPasswordParams")
    public void shouldFailForPassword(String pass, String reason) {
        // given
        CredentialsValidator validator = new CredentialsValidator();
        Credentials credentials = createValidCredentials().toBuilder()
                .password(pass)
                .build();

        ValidationError error = assertThrows(ValidationError.class, () -> validator.validate(credentials));

        // then
        assertThat(error)
                .as(reason)
                .hasNoCause()
                .isExactlyInstanceOf(ValidationError.class);
        assertThat(error.attributes).containsExactly("password");
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

    @Test
    void passwordOk() throws Throwable {
        // given
        CredentialsValidator validator = new CredentialsValidator();

        validator.validate(createValidCredentials());

        // then
        assertThat(true).as("it should pass").isTrue();
    }
}
