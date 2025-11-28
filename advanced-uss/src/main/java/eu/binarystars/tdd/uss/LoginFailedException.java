package eu.binarystars.tdd.uss;

public class LoginFailedException extends UserException {
    public LoginFailedException(final String message) {
        super(message);
    }
}
