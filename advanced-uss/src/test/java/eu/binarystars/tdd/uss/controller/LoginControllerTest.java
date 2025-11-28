package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.Credentials;
import eu.binarystars.tdd.uss.ServerException;
import eu.binarystars.tdd.uss.Session;
import eu.binarystars.tdd.uss.UserException;
import eu.binarystars.tdd.uss.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static eu.binarystars.tdd.uss.UssTestHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    LoginService loginService;
    LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
    }

}