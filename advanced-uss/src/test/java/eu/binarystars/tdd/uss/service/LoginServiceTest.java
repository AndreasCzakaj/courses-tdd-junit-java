package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.*;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDaoMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        userDao = new GenericDaoMapImpl<>(User::getEmail);
        userDao.repo.put(EXISTING_EMAIL, User.builder()
                        .id("x")
                        .email(EXISTING_EMAIL)
                        .passwordHash(EXISTING_PASSWORD)
                .build());
    }

    @Test
    @Disabled
    void validationError() throws Throwable {
    }

    @Test
    @Disabled
    void itShouldThrowErrorForNonExistingUser() {
    }


    @Test
    @Disabled
    void itShouldThrowErrorForWrongPassword() {
    }

    @Test
    @Disabled
    void itThrowServerErrorOnDbError() {
    }

    @Test
    @Disabled
    void itShouldYieldSessionForCorrectCredentials() {
    }
}
