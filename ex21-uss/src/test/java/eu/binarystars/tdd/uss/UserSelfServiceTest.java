package eu.binarystars.tdd.uss;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserSelfServiceTest {
    UserSelfService service;

    static String EMAIL_UNKNOWN = "i_do_not_exist@gmail.com";
    static String EMAIL_UNVERIFIED = "unverified@gmail.com";
    static String EMAIL_VERIFIED = "verified@gmail.com";
    static String EMAIL_INVALID = "invalid@";
    static String EMAIL_VALID = "valid@example.com";
    static String PASSWORD_INVALID = "invalid";
    static String PASSWORD_OK = "correctPassword";
    static String PASSWORD_NOK = "wrongPassword";

    @BeforeEach
    void beforeEach() {
        service = new UserSelfService();
    }


    @Test
    void loginShouldFailForNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.login(null));
        assertThat(e.getMessage()).contains("loginData must not be null");
    }

    @ParameterizedTest(name = "it should throw IllegalArgumentException with message `{1}` for index #{0}")
    @MethodSource("loginShouldFailWithInvalidArgExcParams")
    void loginShouldFailWithInvalidArgExc(String email, String password, String expected) {
        final LoginData loginData = LoginData.builder().email(email).password(password).build();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.login(loginData));
        assertThat(e.getMessage()).contains(expected);
    }

    static Stream<Arguments> loginShouldFailWithInvalidArgExcParams() {
        return Stream.of(
                arguments(null, PASSWORD_OK, "email must not be null"),
                arguments(EMAIL_INVALID, PASSWORD_OK, "invalid email")
        );
    }

    @Test
    void loginShouldFailForUnknownEmail() {
        final LoginData loginData = LoginData.builder().email(EMAIL_UNKNOWN).password(PASSWORD_OK).build();
        RuntimeException e = assertThrows(RuntimeException.class, () -> service.login(loginData));
        assertThat(e.getMessage()).contains("User unknown");
    }
    @Test
    void loginShouldFailForWrongPassword() {
        final LoginData loginData = LoginData.builder().email(EMAIL_VERIFIED).password(PASSWORD_NOK).build();
        RuntimeException e = assertThrows(RuntimeException.class, () -> service.login(loginData));
        assertThat(e.getMessage()).contains("User unknown");
    }

    @Test
    void loginShouldFailForUnverifiedEmail() {
        final LoginData loginData = LoginData.builder().email(EMAIL_UNVERIFIED).password(PASSWORD_OK).build();
        RuntimeException e = assertThrows(RuntimeException.class, () -> service.login(loginData));
        assertThat(e.getMessage()).contains("User has not been verified yet");
    }

    @Test
    @Disabled
    void whatAboutDBErrors() {
        // ???
    }

    @Test
    void loginShouldPassForVerifiedEmail() {
        final LoginData loginData = LoginData.builder()
                .email(EMAIL_VERIFIED)
                .password(PASSWORD_OK)
                .build();
        var actual = service.login(loginData);
        assertThat(actual).usingRecursiveComparison().isEqualTo(
                UserSession.builder()
                        .userAccount(
                        UserAccount.builder()
                                .email(EMAIL_VERIFIED)
                                .verified(true)
                                .build()
                        )
                        .build()
        );
    }
}
