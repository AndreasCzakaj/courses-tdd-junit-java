package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.SignUpData;
import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.service.SignUpService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SignUpController {

    public SignUpController() {

    }

    private final ErrorHandler errorHandler = new ErrorHandler();

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpData data) {
        log.info("data: " + data);
        throw new UnsupportedOperationException("Implement me");
    }
}
