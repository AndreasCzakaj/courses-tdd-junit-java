package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.SignUpData;
import eu.binarystars.tdd.uss.UssValidator;
import eu.binarystars.tdd.uss.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SignUpDataTest extends CredentialsTest {

    SignUpData createValidSignUpData() {
        return SignUpData.builder()
                .email("test@acme.com")
                .password("aB345678")
                .passwordRepeat("aB345678")
                .acceptedTerms(true)
                .build();
    }

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForEmailParams")
    @Override
    public void shouldFailForEmail(String email) {
        shouldFailForField(
                createValidSignUpData().toBuilder().email(email).build(),
                "invalid email",
                "email"
        );
    }

    @ParameterizedTest(name = "email should be OK: '{0}' ({1})")
    @MethodSource("emailOkParams")
    @Override
    void emailOk(String value, String reason) {
        var data = createValidSignUpData().toBuilder().email(value).build();
        shouldPassForField(data, reason);
    }

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForPasswordParams")
    @Override
    public void shouldFailForPassword(String pass, String reason) {
        shouldFailForField(
                createValidSignUpData().toBuilder().password(pass).build(),
                reason,
                "password"
        );
    }

    @ParameterizedTest(name = "password should be OK: '{0}' ({1})")
    @MethodSource("passwordOkParams")
    @Override
    void passwordOk(String value, String reason) {
        var data = createValidSignUpData().toBuilder().password(value).build();
        shouldPassForField(data, reason);
    }

    @ParameterizedTest(name = "it should fail for input {0}")
    @MethodSource("shouldFailForPasswordParams")
    public void shouldFailForPasswordRepeat(String pass, String reason) {
        shouldFailForField(
                createValidSignUpData().toBuilder().passwordRepeat(pass).build(),
                reason,
                "passwordRepeat"
        );
    }

    @ParameterizedTest(name = "password should be OK: '{0}' ({1})")
    @MethodSource("passwordOkParams")
    void passwordRepeatOk(String value, String reason) {
        var data = createValidSignUpData().toBuilder().passwordRepeat(value).build();
        shouldPassForField(data, reason);
    }

    @Test
    void shouldFailForAcceptedTermsNull() throws Throwable {
        var given = createValidSignUpData().toBuilder().acceptedTerms(null).build();
        ValidationException error = assertThrows(ValidationException.class, () -> validator.validate(given));

        // then
        assertThat(error)
                .hasNoCause()
                .isExactlyInstanceOf(ValidationException.class);
        assertThat(error.fieldErrors.keySet()).containsExactly("acceptedTerms");
    }

    @Test
    void shouldFailForAcceptedTermsFalse() throws Throwable {
        var given = createValidSignUpData().toBuilder().acceptedTerms(false).build();
        ValidationException error = assertThrows(ValidationException.class, () -> validator.validate(given));

        // then
        assertThat(error)
                .hasNoCause()
                .isExactlyInstanceOf(ValidationException.class);
        assertThat(error.fieldErrors.keySet()).containsExactly("acceptedTerms");
    }

    @ParameterizedTest(name = "acceptedTerms should Fail for '{0}'")
    @MethodSource("shouldFailForAcceptedTermsFailParams")
    void shouldFailForAcceptedTermsFail(Boolean value, String reason) {
        var data = createValidSignUpData().toBuilder().acceptedTerms(value).build();
        shouldFailForField(data, reason, "acceptedTerms");
    }

    static Stream<Arguments> shouldFailForAcceptedTermsFailParams() {
        return of(
                arguments(null, "must not be null"),
                arguments(false, "must not be false"),
                arguments(Boolean.FALSE, "must not be FALSE")
        );
    }

    @Test
    void acceptedTermsOk() {
        UssValidator validator = new UssValidator();
        var given = createValidSignUpData();
        validator.validate(given);
        assertThat(true).as("it should pass").isTrue();
    }
}
