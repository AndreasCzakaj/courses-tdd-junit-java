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

    public LoginService(GenericDao<String, User> userDao) {
        this.userDao = userDao;
    }

    private final GenericDao<String, User> userDao;

    private final UssValidator validator = new UssValidator();

    /*private UuidGenerator uuidGenerator = new UuidGeneratorNaiveRandomImpl();

    public LoginService setUuidGenerator(UuidGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }*/

    @Setter
    private Function<String, String> passwordHasher = (plainText) -> plainText;


     public Session login(final Credentials credentials) throws UserException, ServerException {
         validator.validate(credentials);

         var maybeUser = loadUser(credentials);
         var user = maybeUser.orElseThrow(() -> new LoginFailedException("Unknown email"));
         if (!passwordHasher.apply(credentials.getPassword()).equals(user.getPasswordHash())) {
             throw new LoginFailedException("wrong password");
         }

         return Session.builder().userId(user.getId()).build();
    }

    Optional<User> loadUser(final Credentials credentials) {
        try {
            return userDao.get(credentials.getEmail());
        } catch (DaoException e) {
            log.error(e);
            throw new ServerException("db error", e);
        }
    }
}
