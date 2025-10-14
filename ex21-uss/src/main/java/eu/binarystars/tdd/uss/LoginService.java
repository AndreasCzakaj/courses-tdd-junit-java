package eu.binarystars.tdd.uss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CredentialsValidator credentialsValidator = new CredentialsValidator();

     Session login(final Credentials credentials) {
         credentialsValidator.validate(credentials);

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
