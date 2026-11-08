package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static eu.binarystars.tdd.uss.UssCreators.*;
import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController(new UserSelfService(new AccountDaoMapImpl(createVerifiedAccount())));
    }

    @Test
    void itShouldYield200AndTheSession() {
        assertThat(controller.action(createValidCredentials()))
                .isEqualTo(new HttpResult(200, new UserSession("id-1", USERNAME, "alice@example.com")));
    }

    @Test
    void itShouldYield400ForInvalidCredentials() {
        assertThat(controller.action(null))
                .isEqualTo(new HttpResult(400, Map.of("error", "invalid: username")));
    }

    @Test
    void itShouldYield401ForAWrongPassword() {
        assertThat(controller.action(createCredentials("password", "Wrong-Horse_42")).status()).isEqualTo(401);
    }

    @Test
    void itShouldYield500IfTheDatabaseFails() {
        controller = new LoginController(new UserSelfService(new AccountDaoThrowingImpl()));

        assertThat(controller.action(createValidCredentials()))
                .isEqualTo(new HttpResult(500, Map.of("error", "try again later")));
    }
}
