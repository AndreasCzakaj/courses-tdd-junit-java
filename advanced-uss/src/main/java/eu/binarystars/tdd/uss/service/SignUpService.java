package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.*;
import eu.binarystars.tdd.uss.mail.MailSender;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDao;
import eu.binarystars.tdd.utils.UuidGenerator;
import eu.binarystars.tdd.utils.UuidGeneratorNaiveRandomImpl;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class SignUpService {

    public SignUpService() {
    }

    private final UssValidator validator = new UssValidator();

    @Setter
    private UuidGenerator uuidGenerator = new UuidGeneratorNaiveRandomImpl();

    @Setter
    private Function<String, String> passwordHasher = (plainText) -> plainText;


    public User signUp(final SignUpData signUpData) throws UserException, ServerException {
        throw new UnsupportedOperationException("Implement me");
    }
}
