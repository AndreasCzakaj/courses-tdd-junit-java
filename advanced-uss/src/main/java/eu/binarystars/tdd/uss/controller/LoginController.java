package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.Credentials;
import eu.binarystars.tdd.uss.Session;
import eu.binarystars.tdd.uss.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class LoginController {

    public LoginController() {
    }

    private final ErrorHandler errorHandler = new ErrorHandler();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials data) {
        log.info("data: " + data);
        throw new UnsupportedOperationException("Implement me");
    }
}
