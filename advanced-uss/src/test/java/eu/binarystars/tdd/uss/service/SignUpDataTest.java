package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.SignUpData;
import eu.binarystars.tdd.uss.UssValidator;
import eu.binarystars.tdd.uss.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SignUpDataTest extends CredentialsTest {

    SignUpData createValidSignUpData() {
        return SignUpData.builder()
                .email("test@acme.com")
                .password("aB345678")
                .passwordRepeat("aB345678")
                .acceptedTerms(true)
                .build();
    }

}
