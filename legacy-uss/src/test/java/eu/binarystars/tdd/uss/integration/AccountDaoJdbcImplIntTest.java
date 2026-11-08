package eu.binarystars.tdd.uss.integration;

import eu.binarystars.tdd.uss.AccountDaoJdbcImpl;
import eu.binarystars.tdd.uss.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static eu.binarystars.tdd.uss.UssCreators.USERNAME;
import static eu.binarystars.tdd.uss.UssCreators.createVerifiedAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("integration")
class AccountDaoJdbcImplIntTest {
    AccountDaoJdbcImpl dao;

    @BeforeEach
    void setUp() {
        // a fresh in-memory database per test
        dao = new AccountDaoJdbcImpl("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
        dao.createTable();
    }

    @Test
    void itShouldYieldEmptyForAnUnknownUsername() {
        assertThat(dao.findByUsername("nobody_here")).isEmpty();
    }

    @Test
    void itShouldFindASavedAccount() {
        dao.save(createVerifiedAccount());

        assertThat(dao.findByUsername(USERNAME)).contains(createVerifiedAccount());
    }

    @Test
    void itShouldThrowADaoExceptionIfTheDatabaseFails() {
        var brokenDao = new AccountDaoJdbcImpl("jdbc:h2:mem:" + UUID.randomUUID());   // no table

        assertThatThrownBy(() -> brokenDao.findByUsername(USERNAME))
                .isExactlyInstanceOf(DaoException.class);
    }

    @Test
    void itShouldThrowADaoExceptionForADuplicateUsername() {
        dao.save(createVerifiedAccount());

        assertThatThrownBy(() -> dao.save(createVerifiedAccount()))
                .isExactlyInstanceOf(DaoException.class);
    }

    @Test
    void itShouldThrowADaoExceptionIfTheTableCannotBeCreated() {
        var brokenDao = new AccountDaoJdbcImpl("jdbc:nosuchdriver:x");

        assertThatThrownBy(brokenDao::createTable)
                .isExactlyInstanceOf(DaoException.class);
    }
}
