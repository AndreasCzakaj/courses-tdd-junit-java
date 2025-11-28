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

    public SignUpService(
            @Autowired GenericDao<String, User> userDao,
            @Autowired MailSender mailSender) {
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    private final GenericDao<String, User> userDao;
    private final MailSender mailSender;

    private final UssValidator validator = new UssValidator();

    @Setter
    private UuidGenerator uuidGenerator = new UuidGeneratorNaiveRandomImpl();

    @Setter
    private Function<String, String> passwordHasher = (plainText) -> plainText;


     public User signUp(final SignUpData signUpData) throws UserException, ServerException {
         validate(signUpData);

         var maybeUser = loadUser(signUpData);
         maybeUser.ifPresent(user -> {
             throw new UserException("User already exists");
         });

         var user = User.builder()
                 .id(uuidGenerator.create())
                 .email(signUpData.getEmail())
                 .passwordHash(passwordHasher.apply(signUpData.getPassword()))
                 .acceptedTerms(signUpData.getAcceptedTerms())
                 .build();

         saveUser(user);
         sendConfirmationToken(createConfirmationTokenMailMessage(user));

         return user;
    }

    SimpleMailMessage createConfirmationTokenMailMessage(User user) {
         var out = new SimpleMailMessage();

         out.setTo(user.getEmail());
         out.setSubject("Confirmation Token");
         out.setText("Your Confirmation Token is: 123456");

         return out;
    }

    void sendConfirmationToken(final SimpleMailMessage message) {
         try {
            mailSender.send(message);
         } catch (MailException e) {
             log.error(e);
             throw new ServerException("sendEmail failed", e);
         }
    }

    void validate(final SignUpData signUpData) {
        validator.validate(signUpData);
        if (! signUpData.getPassword().equals(signUpData.getPasswordRepeat())) {
            throw new ValidationException(new HashMap<>(){{
                put("passwordRepeat", "Not equal to password");
            }});
        }
    }


    Optional<User> loadUser(final SignUpData signUpData) throws UserException {
        try {
            return userDao.get(signUpData.getEmail());
        } catch (DaoException e) {
            log.error(e);
            throw new ServerException("db error", e);
        }
    }

    User saveUser(final User user) throws UserException {
        try {
            return userDao.save(user);
        } catch (DaoException e) {
            log.error(e);
            throw new ServerException("db error", e);
        }
    }
}
