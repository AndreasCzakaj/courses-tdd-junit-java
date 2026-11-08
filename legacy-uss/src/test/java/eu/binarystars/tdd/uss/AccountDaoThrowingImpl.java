package eu.binarystars.tdd.uss;

import java.util.Optional;

/**
 * Fake: a database that is always down.
 */
public class AccountDaoThrowingImpl implements AccountDao {
    @Override
    public Optional<Account> findByUsername(final String username) {
        throw new DaoException("findByUsername: oops", new RuntimeException("oops"));
    }
}
