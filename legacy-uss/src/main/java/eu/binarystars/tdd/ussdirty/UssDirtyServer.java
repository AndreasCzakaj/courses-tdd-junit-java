package eu.binarystars.tdd.ussdirty;

import com.sun.net.httpserver.HttpServer;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Starts the user self service for local use: {@code mvn compile exec:java}
 * <p>
 * The database is an in-memory H2 database, filled with the accounts below.
 */
public class UssDirtyServer {

    static final List<Map<String, String>> ACCOUNTS = List.of(
            Map.of("username", "alice_verified", "password", "Correct-Horse_42", "status", "verified"),
            Map.of("username", "bob_not_verified", "password", "Battery.Staple+7", "status", "new")
    );

    /**
     * format: "&lt;salt&gt;:&lt;hash&gt;", both hex encoded
     */
    static String hashPassword(String password) throws Exception {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);
        String salt = HexFormat.of().formatHex(saltBytes);
        byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 100000, 512))
                .getEncoded();
        return salt + ":" + HexFormat.of().formatHex(hash);
    }

    static void createDatabase(String jdbcUrl) throws Exception {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            connection.createStatement().execute(
                    "CREATE TABLE accounts ("
                            + "id VARCHAR(36) PRIMARY KEY, username VARCHAR(20) UNIQUE, password_hash VARCHAR(200), "
                            + "email VARCHAR(100), tc_accepted VARCHAR(40), status VARCHAR(20))");
            PreparedStatement insert = connection.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?, ?, ?)");
            for (Map<String, String> account : ACCOUNTS) {
                insert.setString(1, UUID.randomUUID().toString());
                insert.setString(2, account.get("username"));
                insert.setString(3, hashPassword(account.get("password")));
                insert.setString(4, account.get("username") + "@example.com");
                insert.setString(5, Instant.now().toString());
                insert.setString(6, account.get("status"));
                insert.executeUpdate();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String jdbcUrl = System.getProperty("uss.db", "jdbc:h2:mem:uss;DB_CLOSE_DELAY=-1");
        createDatabase(jdbcUrl);

        System.out.println("H2 database: " + jdbcUrl);
        for (Map<String, String> account : ACCOUNTS) {
            System.out.println("  " + account);
        }

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/uss/login", new LoginHandler());
        server.start();
        System.out.println("POST http://localhost:" + port + "/uss/login");
    }
}
