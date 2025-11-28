package eu.binarystars.tdd.uss.database;

import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.UserRepository;
import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserDaoJpaImpl implements GenericDao<String, User> {

    public UserDaoJpaImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @Override
    public Optional<User> get(final String key) throws DaoException {
        try {
            return userRepository.findByEmail(key);
        } catch (Exception e) {
            log.error("UserDaoJpaImpl.get: " + e.getMessage(), e);
            throw new DaoException("Error on get");
        }
    }

    @Override
    public User save(final User entity) throws DaoException {
        try {
            return userRepository.save(entity);
        } catch (Exception e) {
            log.error("UserDaoJpaImpl.save: " + e.getMessage(), e);
            throw new DaoException("Error on save");
        }
    }
}
