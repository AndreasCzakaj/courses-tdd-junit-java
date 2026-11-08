package eu.binarystars.tdd.uss;

import java.util.Optional;

/**
 * An interface on purpose: the service is isolated from the database,
 * and the implementations are interchangeable.
 */
public interface AccountDao {
    Optional<Account> findByUsername(String username) throws DaoException;
}
