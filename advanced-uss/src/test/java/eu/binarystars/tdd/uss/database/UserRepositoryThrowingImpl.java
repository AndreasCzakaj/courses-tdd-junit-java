package eu.binarystars.tdd.uss.database;

import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepositoryThrowingImpl extends UserRepository {
    default Optional<User> findByEmail(final String email) {
        throw new RuntimeException("oops get");
    }

    default User save(User entity) {
        throw new RuntimeException("oops save");
    }
}
