package eu.binarystars.tdd.uss;

/**
 * The SAME error for an unknown username and for a wrong password:
 * the response must not reveal which usernames exist.
 */
public class AuthenticationException extends UserSelfServiceException {
    public AuthenticationException() {
        super("unknown username or wrong password");
    }
}
