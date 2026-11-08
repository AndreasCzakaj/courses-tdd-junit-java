package eu.binarystars.tdd.uss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Integration code only: wires the parts and maps HTTP to the controller.
 * No logic here => nothing to unit test, the integration test covers it.
 * <p>
 * Local use: {@code mvn compile exec:java}
 */
public final class UssServer {
    static final String DEFAULT_DB = "jdbc:h2:mem:uss;DB_CLOSE_DELAY=-1";
    static final ObjectMapper JSON = new ObjectMapper();

    private UssServer() {
    }

    public static HttpServer create(final String jdbcUrl, final int port) throws IOException {
        final var loginController = new LoginController(new UserSelfService(new AccountDaoJdbcImpl(jdbcUrl)));

        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/uss/login", exchange -> send(exchange, loginController.action(parse(exchange.getRequestBody()))));
        return server;
    }

    static Object parse(final InputStream body) {
        try {
            return JSON.readValue(body, Object.class);
        } catch (IOException e) {
            // invalid JSON => invalid credentials
            return null;
        }
    }

    static void send(final HttpExchange exchange, final HttpResult result) throws IOException {
        final byte[] bytes = JSON.writeValueAsBytes(result.body());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(result.status(), bytes.length);
        try (var out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    static List<Account> seedAccounts() {
        return List.of(
                new Account(UUID.randomUUID().toString(), "alice_verified", Passwords.hash("Correct-Horse_42"),
                        "alice_verified@example.com", Instant.now(), Account.STATUS_VERIFIED),
                new Account(UUID.randomUUID().toString(), "bob_not_verified", Passwords.hash("Battery.Staple+7"),
                        "bob_not_verified@example.com", Instant.now(), "new")
        );
    }

    public static void main(final String[] args) throws IOException {
        final String jdbcUrl = System.getProperty("uss.db", DEFAULT_DB);
        final var dao = new AccountDaoJdbcImpl(jdbcUrl);
        dao.createTable();
        seedAccounts().forEach(dao::save);

        final int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        create(jdbcUrl, port).start();
        System.out.println("POST http://localhost:" + port + "/uss/login");
    }
}
