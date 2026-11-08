package eu.binarystars.tdd.uss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

public class AccountDaoJdbcImpl implements AccountDao {
    static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS accounts (
                id VARCHAR(36) PRIMARY KEY,
                username VARCHAR(20) UNIQUE,
                password_hash VARCHAR(200),
                email VARCHAR(100),
                tc_accepted VARCHAR(40),
                status VARCHAR(20)
            )""";
    static final String INSERT =
            "INSERT INTO accounts (id, username, password_hash, email, tc_accepted, status) VALUES (?, ?, ?, ?, ?, ?)";
    static final String SELECT_BY_USERNAME =
            "SELECT id, username, password_hash, email, tc_accepted, status FROM accounts WHERE username = ?";

    private final String jdbcUrl;

    public AccountDaoJdbcImpl(final String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void createTable() {
        try (Connection connection = connect()) {
            connection.createStatement().execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new DaoException("JDBC Error", e);
        }
    }

    public void save(final Account account) {
        try (Connection connection = connect(); var insert = connection.prepareStatement(INSERT)) {
            insert.setString(1, account.id());
            insert.setString(2, account.username());
            insert.setString(3, account.passwordHash());
            insert.setString(4, account.email());
            insert.setString(5, account.tcAccepted().toString());
            insert.setString(6, account.status());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("JDBC Error", e);
        }
    }

    @Override
    public Optional<Account> findByUsername(final String username) {
        try (Connection connection = connect(); var select = connection.prepareStatement(SELECT_BY_USERNAME)) {
            select.setString(1, username);
            final ResultSet rows = select.executeQuery();
            return rows.next() ? Optional.of(toAccount(rows)) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("JDBC Error", e);
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcUrl);
    }

    static Account toAccount(final ResultSet row) throws SQLException {
        return new Account(
                row.getString("id"),
                row.getString("username"),
                row.getString("password_hash"),
                row.getString("email"),
                Instant.parse(row.getString("tc_accepted")),
                row.getString("status")
        );
    }
}
