package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.*;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDaoMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginServiceTest {
    public static final String EXISTING_EMAIL = "existing@test.com";
    public static final String EXISTING_PASSWORD = "aBC321654";
    GenericDaoMapImpl<String, User> userDao;
    LoginService service;

    @BeforeEach
    void beforeEach() {
        userDao = new GenericDaoMapImpl<>(user -> user.getEmail());
        userDao.repo.put(EXISTING_EMAIL, User.builder()
                        .id("x")
                        .email(EXISTING_EMAIL)
                        .passwordHash(EXISTING_PASSWORD)
                .build());
        service = new LoginService(userDao);
    }

    @Test
    void validationError() throws Throwable {
        // given
        Credentials invalidCredentials = new Credentials();

        // when
        ValidationException error = assertThrows(ValidationException.class, () -> service.login(invalidCredentials));

        // then
        assertThat(error).isExactlyInstanceOf(ValidationException.class);
        assertThat(error.fieldErrors.keySet()).contains("email", "password");
    }

    @Test
    void itShouldThrowErrorForNonExistingUser() {
        // given
        Credentials credentials = Credentials.builder()
                .email("i_do_not_exist@test.com")
                .password(EXISTING_PASSWORD)
                .build();

        // then
        assertThatThrownBy(() -> service.login(credentials))
                .isExactlyInstanceOf(LoginFailedException.class)
                .hasMessage("Unknown email");
    }


    @Test
    void itShouldThrowErrorForWrongPassword() {
        // given
        Credentials credentials = Credentials.builder()
                .email(EXISTING_EMAIL)
                .password("aB345678")
                .build();

        // then
        assertThatThrownBy(() -> service.login(credentials))
                .isExactlyInstanceOf(LoginFailedException.class)
                .hasMessage("wrong password");
    }

    @Test
    void itThrowServerErrorOnDbError() {
        userDao = new GenericDaoMapImpl<>(User::getEmail) {
            public Optional<User> get(String key) throws DaoException {
                throw new DaoException("db error");
            }
        };
        service = new LoginService(userDao);

        Credentials credentials = Credentials.builder()
                .email(EXISTING_EMAIL)
                .password(EXISTING_PASSWORD)
                .build();


        // then
        assertThatThrownBy(() -> service.login(credentials))
                .isExactlyInstanceOf(ServerException.class)
                .hasMessage("db error");
    }

    @Test
    void itShouldYieldSessionForCorrectCredentials() {
        // given
        Credentials credentials = Credentials.builder()
                .email(EXISTING_EMAIL)
                .password(EXISTING_PASSWORD)
                .build();

        var actual = service.login(credentials);

        assertThat(actual).usingRecursiveComparison().isEqualTo(
                Session.builder()
                        .userId("x")
                        .build()
        );
    }
}
