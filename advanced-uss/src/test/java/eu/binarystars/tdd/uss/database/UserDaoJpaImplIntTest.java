package eu.binarystars.tdd.uss.database;

import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.UserRepository;
import eu.binarystars.tdd.utils.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(UserDaoJpaImpl.class)
@DisabledIfSystemProperty(named = "only", matches = "unit")
public class UserDaoJpaImplIntTest {

    @Autowired
    private UserDaoJpaImpl userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryThrowingImpl userRepositoryThrowing;

    private User validUser = User.builder()
            .id("user-123")
            .email("test@example.com")
            .passwordHash("hashed-password")
            .acceptedTerms(true)
            .build();

    @Test
    void itShouldReturnEmptyForNonExistingUser() throws DaoException {
        var result = userDao.get("nonexistent@example.com");
        assertThat(result).isNotPresent();
    }

    @Test
    void itShouldReturnUserWhenExists() throws DaoException {
        // Given - save a user directly via repository
        var user = validUser;
        userRepository.save(user);

        // When - retrieve via DAO
        var result = userDao.get("test@example.com");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void itShouldSaveUser() throws DaoException {
        // Given
        var user = validUser.toBuilder()
                .id("user-456")
                .email("newuser@example.com")
                .build();

        // When
        var savedUser = userDao.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo("user-456");
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");

        // Verify it's persisted in the database
        var retrieved = userRepository.findByEmail("newuser@example.com");
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get()).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    void dbErrorOnSave() throws DaoException {
        // Given
        userDao = new UserDaoJpaImpl(userRepositoryThrowing);
        var user = validUser;

        // Then
        assertThatThrownBy(() -> userDao.save(user))
                .hasMessage("Error on save")
                .isExactlyInstanceOf(DaoException.class)
        ;

    }

    @Test
    void dbErrorOnGet() throws DaoException {
        // Given
        userDao = new UserDaoJpaImpl(userRepositoryThrowing);

        // Then
        assertThatThrownBy(() -> userDao.get(validUser.getEmail()))
                .hasMessage("Error on get")
                .isExactlyInstanceOf(DaoException.class)
        ;

    }

    @Test
    void itShouldUpdateExistingUser() throws DaoException {
        // Given - save initial user
        var user = validUser.toBuilder()
                .id("user-789")
                .email("update@example.com")
                .passwordHash("old-hash")
                .acceptedTerms(false)
                .build();
        userDao.save(user);

        // When - update the user
        var updatedUser = user.toBuilder()
                .passwordHash("new-hash")
                .acceptedTerms(true)
                .build();
        var result = userDao.save(updatedUser);

        // Then
        assertThat(result.getPasswordHash()).isEqualTo("new-hash");
        assertThat(result.isAcceptedTerms()).isTrue();

        // Verify in database
        var retrieved = userDao.get("update@example.com");
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getPasswordHash()).isEqualTo("new-hash");
        assertThat(retrieved.get().isAcceptedTerms()).isTrue();
    }

}
