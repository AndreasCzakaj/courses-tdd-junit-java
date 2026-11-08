package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.Test;

import static eu.binarystars.tdd.uss.UssCreators.PASSWORD;
import static eu.binarystars.tdd.uss.UssCreators.PASSWORD_HASH;
import static org.assertj.core.api.Assertions.assertThat;

class PasswordsTest {

    @Test
    void itShouldYieldSaltAndHash() {
        assertThat(PASSWORD_HASH).matches("[0-9a-f]{32}:[0-9a-f]{128}");
    }

    @Test
    void itShouldUseANewSaltEveryTime() {
        assertThat(Passwords.hash(PASSWORD)).isNotEqualTo(PASSWORD_HASH);
    }

    @Test
    void itShouldBeDeterministicForTheSameSalt() {
        assertThat(Passwords.hash(PASSWORD, "salt")).isEqualTo(Passwords.hash(PASSWORD, "salt"));
    }

    @Test
    void itShouldVerifyTheCorrectPassword() {
        assertThat(Passwords.verify(PASSWORD, PASSWORD_HASH)).isTrue();
    }

    @Test
    void itShouldRejectAWrongPassword() {
        assertThat(Passwords.verify("Wrong-Horse_42", PASSWORD_HASH)).isFalse();
    }
}
