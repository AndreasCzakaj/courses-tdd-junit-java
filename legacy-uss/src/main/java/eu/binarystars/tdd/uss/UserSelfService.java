package eu.binarystars.tdd.uss;

import java.util.Optional;

public class UserSelfService {
    private final AccountDao accountDao;

    public UserSelfService(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public UserSession login(final Object body) {
        final Credentials credentials = Validation.validateCredentials(body);

        final Account account = findAccount(credentials.username())
                .filter(it -> Passwords.verify(credentials.password(), it.passwordHash()))
                .orElseThrow(AuthenticationException::new);
        if (!account.isVerified()) {
            throw new AccountNotVerifiedException();
        }

        return new UserSession(account.id(), account.username(), account.email());
    }

    Optional<Account> findAccount(final String username) {
        try {
            return accountDao.findByUsername(username);
        } catch (DaoException e) {
            throw new ServerException("Database not available. Try later.", e);
        }
    }
}
