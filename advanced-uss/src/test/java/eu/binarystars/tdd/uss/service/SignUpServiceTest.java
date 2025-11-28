package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.*;
import eu.binarystars.tdd.uss.mail.MailSenderListImpl;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDaoMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;

import static eu.binarystars.tdd.uss.UssTestHelpers.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignUpServiceTest {
    public static final String EXISTING_EMAIL = "existing@test.com";
    GenericDaoMapImpl<String, User> userDao;
    MailSenderListImpl mailSender;
    SignUpService service;

    @BeforeEach
    void beforeEach() {
        userDao = new GenericDaoMapImpl<>(User::getEmail);
        userDao.repo.put(EXISTING_EMAIL, User.builder()
                        .id("x")
                        .email(EXISTING_EMAIL)
                        .passwordHash("#")
                .build());
        mailSender = new MailSenderListImpl();
    }

    @Test
    @Disabled
    void validationError() throws Throwable {
    }

    @Test
    @Disabled
    void validationErrorWhenPasswordRepeatDoesNotMatchPassword() throws Throwable {
    }

    @Test
    @Disabled
    void itShouldThrowErrorForExistingUser() {
    }

    @Test
    @Disabled
    void itThrowServerErrorOnDbGetError() {
    }

    @Test
    @Disabled
    void itThrowServerErrorOnDbSaveError() {
    }

    @Test
    @Disabled
    void itThrowServerErrorOnMailSendError() {
    }

    @Test
    @Disabled
    void signUpOk() {
    }

    @Test
    @Disabled
    void createConfirmationTokenMailMessage() {
    }
}
