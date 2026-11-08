package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static eu.binarystars.tdd.uss.UssCreators.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserSelfServiceTest {
    UserSelfService service;

    @BeforeEach
    void setUp() {
        service = new UserSelfService(new AccountDaoMapImpl(createVerifiedAccount()));
    }

    @Test
    void itShouldFailForInvalidCredentials() {
        assertThatThrownBy(() -> service.login(createCredentials("username", "alice")))
                .isExactlyInstanceOf(ValidationException.class);
    }

    @Test
    void itShouldFailForAnUnknownUsername() {
        assertThatThrownBy(() -> service.login(createCredentials("username", "nobody_here")))
                .isExactlyInstanceOf(AuthenticationException.class)
                .hasMessage("unknown username or wrong password");
    }

    @Test
    void itShouldFailWithTheSameErrorForAWrongPassword() {
        assertThatThrownBy(() -> service.login(createCredentials("password", "Wrong-Horse_42")))
                .isExactlyInstanceOf(AuthenticationException.class)
                .hasMessage("unknown username or wrong password");
    }

    @Test
    void itShouldFailForAnAccountThatIsNotVerified() {
        service = new UserSelfService(new AccountDaoMapImpl(createNewAccount()));

        assertThatThrownBy(() -> service.login(createValidCredentials()))
                .isExactlyInstanceOf(AccountNotVerifiedException.class)
                .hasMessage("account not verified");
    }

    @Test
    void itShouldFailWithAServerErrorIfTheDatabaseFails() {
        service = new UserSelfService(new AccountDaoThrowingImpl());

        assertThatThrownBy(() -> service.login(createValidCredentials()))
                .isExactlyInstanceOf(ServerException.class)
                .hasCauseInstanceOf(DaoException.class);
    }

    @Test
    void itShouldYieldASessionForCorrectCredentials() {
        assertThat(service.login(createValidCredentials()))
                .isEqualTo(new UserSession("id-1", USERNAME, "alice@example.com"));
    }
}
