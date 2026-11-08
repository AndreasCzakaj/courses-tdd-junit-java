package eu.binarystars.tdd.uss;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Fake: real code, in-memory.
 */
public class AccountDaoMapImpl implements AccountDao {
    private final Map<String, Account> repo;

    public AccountDaoMapImpl(final Account... accounts) {
        repo = Arrays.stream(accounts).collect(Collectors.toMap(Account::username, Function.identity()));
    }

    @Override
    public Optional<Account> findByUsername(final String username) {
        return Optional.ofNullable(repo.get(username));
    }
}
