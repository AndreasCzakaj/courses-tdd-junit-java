package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.ServerException;
import eu.binarystars.tdd.uss.SignUpData;
import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.service.SignUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static eu.binarystars.tdd.uss.UssTestHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

class SignUpControllerTest {

    SignUpService service;
    SignUpController controller;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Disabled
    void signUpOk() {
    }

    @Test
    @Disabled
    void loginFailedWithServerError() {
    }
}