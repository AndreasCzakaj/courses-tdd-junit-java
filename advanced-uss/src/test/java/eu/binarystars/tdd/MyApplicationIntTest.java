package eu.binarystars.tdd;

import eu.binarystars.tdd.uss.User;
import eu.binarystars.tdd.uss.UserRepository;
import eu.binarystars.tdd.uss.jpa.DataInitializer;
import eu.binarystars.tdd.utils.UuidGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisabledIfSystemProperty(named = "only", matches = "unit")
@IfProfileValue(name = "integration")
class MyApplicationIntTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private UuidGenerator uuidGenerator;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testApplicationContextLoads() {
        // Verify that the application context loads successfully
        // and all required beans are created
        assertThat(dataInitializer).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(uuidGenerator).isNotNull();
    }

    @Test
    void testUuidGeneratorBeanIsInjected() {
        // Verify that the UuidGenerator bean from utils package is properly injected
        String uuid = uuidGenerator.create();
        assertThat(uuid).isNotBlank();
        assertThat(uuid).hasSize(32);
    }

    @Test
    void testDataInitializerCanRunManually() throws Exception {
        // Given - empty database
        assertThat(userRepository.count()).isZero();

        // When - DataInitializer runs
        dataInitializer.run();

        // Then - sample user should be initialized
        assertThat(userRepository.count()).isEqualTo(1);
        var user = userRepository.findByEmail("andreas.czakaj@binary-stars.eu");
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("andreas.czakaj@binary-stars.eu");
        assertThat(user.get().getId()).isNotBlank();
    }

    @Test
    void testDataInitializerSkipsIfDataExists() throws Exception {
        // Given - database already has a user
        var existingUser = User.builder()
                .id(uuidGenerator.create())
                .email("existing@example.com")
                .passwordHash("hash")
                .build();
        userRepository.save(existingUser);
        long initialCount = userRepository.count();

        // When - DataInitializer runs
        dataInitializer.run();

        // Then - no additional users should be added
        assertThat(userRepository.count()).isEqualTo(initialCount);
    }
}