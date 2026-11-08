package eu.binarystars.tdd.uss.integration;

import com.sun.net.httpserver.HttpServer;
import eu.binarystars.tdd.uss.AccountDaoJdbcImpl;
import eu.binarystars.tdd.uss.UssServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static eu.binarystars.tdd.uss.UssCreators.createAccount;
import static eu.binarystars.tdd.uss.UssCreators.createVerifiedAccount;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
class UssServerIntTest {
    HttpServer server;

    @BeforeEach
    void setUp() throws Exception {
        var jdbcUrl = "jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1";
        var dao = new AccountDaoJdbcImpl(jdbcUrl);
        dao.createTable();
        dao.save(createVerifiedAccount());
        dao.save(createAccount("id-2", "bob_not_verified", "new"));

        server = UssServer.create(jdbcUrl, 0);   // port 0 => any free port
        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @ParameterizedTest(name = "{0} => {1}")
    @CsvSource(delimiter = '|', value = {
            "{\"username\":\"alice_verified\",\"password\":\"Correct-Horse_42\"}   | 200 | {\"accountId\":\"id-1\",\"username\":\"alice_verified\",\"email\":\"alice@example.com\"}",
            "{\"username\":\"alice_verified\",\"password\":\"Wrong-Horse_42\"}     | 401 | {\"error\":\"unknown username or wrong password\"}",
            "{\"username\":\"nobody_here\",\"password\":\"Correct-Horse_42\"}      | 401 | {\"error\":\"unknown username or wrong password\"}",
            "{\"username\":\"bob_not_verified\",\"password\":\"Correct-Horse_42\"} | 400 | {\"error\":\"account not verified\"}",
            "{\"username\":\"alice\"}                                              | 400 | {\"error\":\"invalid: username\"}",
            "no json                                                               | 400 | {\"error\":\"invalid: username\"}",
    })
    void itShouldLogIn(final String body, final int expectedStatus, final String expectedBody) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + server.getAddress().getPort() + "/uss/login"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(expectedStatus);
        assertThat(response.body()).isEqualTo(expectedBody);
    }
}
