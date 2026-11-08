package eu.binarystars.tdd.uss;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.binarystars.tdd.uss.UssCreators.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationTest {

    @ParameterizedTest(name = "username \"{0}\" should fail")
    @NullAndEmptySource
    @ValueSource(strings = {"alice", "alice_v", "alice_verified_too_long", "alice verified", "alice@verified", "alice_verified\n"})
    void shouldFailForUsername(final String username) {
        assertThatThrownBy(() -> Validation.validateUsername(username))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("invalid: username");
    }

    @ParameterizedTest(name = "username \"{0}\" should pass")
    @ValueSource(strings = {"alice_ve", "alice-verified", "ALICE_verified_12345", "a1234567890123456789"})
    void shouldPassForUsername(final String username) {
        assertThat(Validation.validateUsername(username)).isEqualTo(username);
    }

    @ParameterizedTest(name = "password \"{0}\" should fail")
    @NullAndEmptySource
    @ValueSource(strings = {"Correct-Hor", "Correct Horse_42", "Correct-Horse_42!", "Correct-Horse_42-Correct-Horse_42-"})
    void shouldFailForPassword(final String password) {
        assertThatThrownBy(() -> Validation.validatePassword(password))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("invalid: password");
    }

    @ParameterizedTest(name = "password \"{0}\" should pass")
    @ValueSource(strings = {"Correct-Hor1", "Correct-Horse_42", "a.b,c+d-e_f1234"})
    void shouldPassForPassword(final String password) {
        assertThat(Validation.validatePassword(password)).isEqualTo(password);
    }

    @Test
    void shouldFailForNonStringUsername() {
        assertThatThrownBy(() -> Validation.validateUsername(12345678))
                .isExactlyInstanceOf(ValidationException.class);
    }

    @Test
    void shouldYieldCredentials() {
        assertThat(Validation.validateCredentials(createValidCredentials()))
                .isEqualTo(new Credentials(USERNAME, PASSWORD));
    }

    @Test
    void shouldFailForCredentialsThatAreNoMap() {
        assertThatThrownBy(() -> Validation.validateCredentials(List.of(USERNAME, PASSWORD)))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("invalid: username");
    }

    @Test
    void shouldFailForCredentialsWithInvalidPassword() {
        assertThatThrownBy(() -> Validation.validateCredentials(createCredentials("password", "short")))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("invalid: password");
    }
}
