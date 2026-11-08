package eu.binarystars.tdd.ussdirty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HexFormat;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange x) throws IOException {
        Connection c = null;
        int status;
        String json;
        try {
            Object o = null;
            try {
                o = new ObjectMapper().readValue(x.getRequestBody(), Object.class);
            } catch (Exception e) {
                // ignore
            }
            if (o != null && o instanceof Map) {
                Map d = (Map) o;
                if (d.containsKey("username") && d.get("username") instanceof String) {
                    if (((String) d.get("username")).length() >= 8) {
                        if (((String) d.get("username")).length() <= 20) {
                            if (((String) d.get("username")).matches("[a-zA-Z0-9\\-_]+")) {
                                if (d.containsKey("password") && d.get("password") instanceof String) {
                                    if (((String) d.get("password")).length() >= 12 && ((String) d.get("password")).length() <= 32) {
                                        if (((String) d.get("password")).matches("[a-zA-Z0-9\\-_.,+]+")) {
                                            // check db
                                            c = DriverManager.getConnection(System.getProperty("uss.db", "jdbc:h2:mem:uss;DB_CLOSE_DELAY=-1"));
                                            PreparedStatement s = c.prepareStatement("SELECT * FROM accounts WHERE username = ?");
                                            s.setString(1, (String) d.get("username"));
                                            ResultSet r = s.executeQuery();
                                            if (r.next()) {
                                                String[] tmp = r.getString(3).split(":");
                                                byte[] h = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                                                        .generateSecret(new PBEKeySpec(((String) d.get("password")).toCharArray(), tmp[0].getBytes(StandardCharsets.UTF_8), 100000, 512))
                                                        .getEncoded();
                                                if (HexFormat.of().formatHex(h).equals(tmp[1])) {
                                                    if (r.getString(6).equals("verified")) {
                                                        status = 200;
                                                        json = "{\"accountId\":\"" + r.getString(1) + "\",\"username\":\"" + r.getString(2) + "\",\"email\":\"" + r.getString(4) + "\"}";
                                                    } else {
                                                        status = 400;
                                                        json = "{\"error\":\"account not verified\"}";
                                                    }
                                                } else {
                                                    status = 401;
                                                    json = "{\"error\":\"unknown username or wrong password\"}";
                                                }
                                            } else {
                                                status = 401;
                                                json = "{\"error\":\"unknown username or wrong password\"}";
                                            }
                                        } else {
                                            status = 400;
                                            json = "{\"error\":\"invalid: password\"}";
                                        }
                                    } else {
                                        status = 400;
                                        json = "{\"error\":\"invalid: password\"}";
                                    }
                                } else {
                                    status = 400;
                                    json = "{\"error\":\"invalid: password\"}";
                                }
                            } else {
                                status = 400;
                                json = "{\"error\":\"invalid: username\"}";
                            }
                        } else {
                            status = 400;
                            json = "{\"error\":\"invalid: username\"}";
                        }
                    } else {
                        status = 400;
                        json = "{\"error\":\"invalid: username\"}";
                    }
                } else {
                    status = 400;
                    json = "{\"error\":\"invalid: username\"}";
                }
            } else {
                status = 400;
                json = "{\"error\":\"invalid: username\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = 500;
            json = "{\"error\":\"try again later\"}";
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                // ignore
            }
        }
        byte[] b = json.getBytes(StandardCharsets.UTF_8);
        x.getResponseHeaders().set("Content-Type", "application/json");
        x.sendResponseHeaders(status, b.length);
        OutputStream os = x.getResponseBody();
        os.write(b);
        os.close();
    }
}
