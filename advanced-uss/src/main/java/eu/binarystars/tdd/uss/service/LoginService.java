package eu.binarystars.tdd.uss.service;

import eu.binarystars.tdd.uss.*;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDao;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class LoginService {

    public LoginService() {
    }

    private final UssValidator validator = new UssValidator();

    @Setter
    private Function<String, String> passwordHasher = (plainText) -> plainText;

    public Session login(final Credentials credentials) throws UserException, ServerException {
         throw new UnsupportedOperationException("Implement me");
    }
}
