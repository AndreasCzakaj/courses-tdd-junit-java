package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ControllerUtilsTest {

    static Stream<Arguments> errors() {
        return Stream.of(
                arguments(new ValidationException("username"), 400, "invalid: username"),
                arguments(new AuthenticationException(), 401, "unknown username or wrong password"),
                arguments(new AccountNotVerifiedException(), 400, "account not verified"),
                arguments(new ServerException("db down", null), 500, "try again later"),
                arguments(new NullPointerException("internal details"), 500, "try again later")
        );
    }

    @ParameterizedTest(name = "{0} => {1} \"{2}\"")
    @MethodSource("errors")
    void itShouldMapErrors(final RuntimeException error, final int expectedStatus, final String expectedMessage) {
        var actual = ControllerUtils.respond(200, () -> {
            throw error;
        });

        assertThat(actual).isEqualTo(new HttpResult(expectedStatus, Map.of("error", expectedMessage)));
    }

    @Test
    void itShouldYieldTheResultWithTheSuccessStatus() {
        assertThat(ControllerUtils.respond(201, () -> "ok")).isEqualTo(new HttpResult(201, "ok"));
    }
}
