package eu.binarystars.tdd.uss;

public class AccountNotVerifiedException extends UserSelfServiceException {
    public AccountNotVerifiedException() {
        super("account not verified");
    }
}
