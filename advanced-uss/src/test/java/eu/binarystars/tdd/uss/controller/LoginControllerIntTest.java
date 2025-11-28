package eu.binarystars.tdd.uss.controller;

import eu.binarystars.tdd.uss.Session;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisabledIfSystemProperty(named = "only", matches = "unit")
class LoginControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Disabled
    void testEndpoint() {
        String url = "http://localhost:" + port + "/login";
        var response = restTemplate.postForEntity(
                url,
                Map.of(
                        "email", "andreas.czakaj@binary-stars.eu",
                        "password", "abcdcryptHash123"
                ),
                Session.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}