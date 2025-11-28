package eu.binarystars.tdd.uss.jpa;

import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.UserRepository;
import eu.binarystars.tdd.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UuidGenerator uuidGenerator;

    @Override
    public void run(String... args) throws Exception {
        // Initialize some sample users for demo purposes
        if (userRepository.count() == 0) {
            User user;

            user = User.builder()
                    .id(uuidGenerator.create())
                    .email("andreas.czakaj@binary-stars.eu")
                    .passwordHash("abcdcryptHash123")
                    .build();
            userRepository.save(user);

            System.out.println("Sample users initialized in database");
        }
    }
}