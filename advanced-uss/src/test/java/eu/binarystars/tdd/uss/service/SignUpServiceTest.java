package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.*;
import eu.binarystars.tdd.uss.mail.MailSenderListImpl;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDaoMapImpl;
import org.junit.jupiter.api.BeforeEach;
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
        service = new SignUpService(userDao, mailSender);
    }

    @Test
    void validationError() throws Throwable {
        // given
        var data = new SignUpData();

        // when
        ValidationException error = assertThrows(ValidationException.class, () -> service.signUp(data));

        // then
        assertThat(error).isExactlyInstanceOf(ValidationException.class);
        assertThat(error.fieldErrors.keySet()).contains("email", "password", "passwordRepeat", "acceptedTerms");
    }

    @Test
    void validationErrorWhenPasswordRepeatDoesNotMatchPassword() throws Throwable {
        // given
        var validData = createSignUpDataForExistingUser();
        var data = createSignUpDataForExistingUser().toBuilder()
                .email(NON_EXISTING_EMAIL)
                .passwordRepeat(validData.getPasswordRepeat() + "0")
                .build();

        // when
        ValidationException error = assertThrows(ValidationException.class, () -> service.signUp(data));

        // then
        assertThat(error).isExactlyInstanceOf(ValidationException.class);
        assertThat(error.fieldErrors).containsExactly(entry("passwordRepeat", "Not equal to password"));
    }

    @Test
    void itShouldThrowErrorForExistingUser() {
        // given
        var data = createSignUpDataForExistingUser();

        // then
        assertThatThrownBy(() -> service.signUp(data))
                .isExactlyInstanceOf(UserException.class)
                .hasMessage("User already exists");
    }

    @Test
    void itThrowServerErrorOnDbGetError() {
        userDao = new GenericDaoMapImpl<>(User::getEmail) {
            public Optional<User> get(String key) throws DaoException {
                throw new DaoException("db error");
            }
        };
        service = new SignUpService(userDao, mailSender);

        // then
        assertThatThrownBy(() -> service.signUp(createSignUpDataForExistingUser()))
                .isExactlyInstanceOf(ServerException.class)
                .hasMessage("db error");
    }

    @Test
    void itThrowServerErrorOnDbSaveError() {
        userDao = new GenericDaoMapImpl<>(User::getEmail) {
            @Override
            public User save(User user) throws DaoException {
                throw new DaoException("db error");
            }
        };
        service = new SignUpService(userDao, mailSender);

        // then
        assertThatThrownBy(() -> service.signUp(createSignUpDataForExistingUser()))
                .isExactlyInstanceOf(ServerException.class)
                .hasMessage("db error");
    }

    @Test
    void itThrowServerErrorOnMailSendError() {
        mailSender.errorToThrow = new MailSendException("oops");
        service = new SignUpService(userDao, mailSender);

        var data = createSignUpDataForExistingUser().toBuilder().email("new-user@test.com").build();

        // then
        assertThatThrownBy(() -> service.signUp(data))
                .isExactlyInstanceOf(ServerException.class)
                .hasMessage("sendEmail failed");
    }

    @Test
    void signUpOk() {
        // given
        var data = createSignUpDataForExistingUser().toBuilder().email("new-user@test.com").build();

        var actual = service.signUp(data);

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(
                User.builder()
                        .email(data.getEmail())
                        .passwordHash(data.getPassword())
                        .acceptedTerms(true)
                        .build()
        );
        assertThat(actual.getId()).matches("[0-9a-f]+");

        assertThat(mailSender.sentMessages).hasSize(1);
        var sentMessage = mailSender.sentMessages.get(0);
        assertThat(sentMessage).usingRecursiveComparison()
                .comparingOnlyFields("to")
                .isEqualTo(new SimpleMailMessage(){{
                    setTo(new String[]{data.getEmail()});
                }});
    }

    @Test
    void createConfirmationTokenMailMessage() {
        var user = createValidUser("id123");
        var actual = service.createConfirmationTokenMailMessage(user);

        var expected = new SimpleMailMessage();
        expected.setTo(user.getEmail());
        expected.setSubject("Confirmation Token");
        expected.setText("Your Confirmation Token is: 123456");
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
