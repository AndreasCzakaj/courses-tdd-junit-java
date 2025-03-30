package eu.binarystars.tdd.uss;

import eu.binarystars.tdd.uss.legacydb.Db;
import eu.binarystars.tdd.uss.legacydb.FreakyDbException;
import eu.binarystars.tdd.uss.legacydb.TableData;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class UserSelfService {

    UserSelfService() {
    }

    public UserSession login(LoginData loginData) {
        if (loginData == null) {
            throw new IllegalArgumentException("loginData must not be null");
        }
        if (loginData.email() == null) {
            throw new IllegalArgumentException("email must not be null");
        }
        final var regExpEmail = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
        if (!regExpEmail.matcher(loginData.email()).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
        if (loginData.password() == null) {
            throw new IllegalArgumentException("password must not be null");
        }
        if (loginData.password().trim().length() < 8) {
            throw new IllegalArgumentException("invalid password");
        }

        try {
            final TableData userTable = Db.getInstance().executeQuery("users", loginData.email());
            if (userTable.hasNext()) {
                String passwordHash = (String) userTable.getColumnData("passwordHash");
                if (!hashPassword(loginData.password()).equals(passwordHash)) {
                    throw new RuntimeException("User unknown or wrong password");
                }

                Boolean verified = (Boolean) userTable.getColumnData("verified");
                if (verified) {
                    final UserAccount userAccount = UserAccount.builder()
                            .email(loginData.email())
                            .verified(true)
                            .build();
                    return UserSession.builder().userAccount(userAccount).build();
                }
                throw new IllegalStateException("User has not been verified yet");
            } else {
                throw new RuntimeException("User unknown or wrong password");
            }
        } catch (FreakyDbException e) {
            throw new RuntimeException(e);
        }
    }


    public SignUpResult signUp(RegistrationData registrationData) {
        if (registrationData != null) {
            if (registrationData.email() != null) {
                if (registrationData.agreedToTermsOfUse()) {
                    // FIXME:
                    // Db.getInstance().persist()

                    // Set properties
                    Properties properties = System.getProperties();
                    properties.setProperty("mail.smtp.host", "smtp.acme.com");
                    properties.setProperty("mail.smtp.port", "587");
                    properties.setProperty("mail.smtp.auth", "true");
                    properties.setProperty("mail.smtp.starttls.enable", "true");

                    final String from = "noreply@acme.com";
                    final String to = registrationData.email();
                    // Create a session with authentication
                    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new javax.mail.PasswordAuthentication(from, "your-password-here");
                        }
                    });

                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(from));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                        message.setSubject("Please verify your email");
                        message.setText("bla bla here's your code ...");
                        Transport.send(message);

                        return SignUpResult.builder().build();
                    } catch (MessagingException mex) {
                        return SignUpResult.builder().globalErrors(List.of(mex.getMessage())).build();
                    }
                } else {
                    return SignUpResult.builder().fieldErrors(Map.of("agreedToTermsOfUse", "must be set")).build();
                }
            } else {
                return SignUpResult.builder().fieldErrors(Map.of("email", "must be set")).build();
            }
        } else {
            return SignUpResult.builder().globalErrors(List.of("no data passed")).build();
        }
    }

    static String hashPassword(String password) {
        return password;
    }
}
