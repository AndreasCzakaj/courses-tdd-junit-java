package eu.binarystars.tdd.uss.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisabledIfSystemProperty(named = "only", matches = "unit")
class MailSenderSmtpImplIntTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());

    private MailSenderSmtpImpl mailSender;

    @BeforeEach
    void setup() {
        mailSender = new MailSenderSmtpImpl();
        // Configure the mail sender to use GreenMail SMTP server
        ReflectionTestUtils.setField(mailSender, "smtpHost", "localhost");
        ReflectionTestUtils.setField(mailSender, "smtpPort", ServerSetupTest.SMTP.getPort());
    }

    @Test
    void testSendSimpleEmail() throws Throwable {
        // Given
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("recipient@example.com");
        message.setSubject("Test Subject");
        message.setText("Test Body");

        // When
        var result = mailSender.send(message);

        // Then
        assertThat(result).isNotNull();
        assertThat(greenMail.getReceivedMessages()).hasSize(1);

        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo("Test Subject");
        assertThat(receivedMessage.getContent()).asString().contains("Test Body");
    }

    @Test
    void testSendEmailToMultipleRecipients() {
        // Given
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("recipient1@example.com", "recipient2@example.com");
        message.setSubject("Multi Recipient Email");
        message.setText("Test Body");

        // When
        var result = mailSender.send(message);

        // Then
        assertThat(result).isNotNull();
        // Note: MailSenderSmtpImpl only sends to the first recipient
        assertThat(greenMail.getReceivedMessages()).hasSize(1);
    }

    @Test
    void testSendEmailPreservesSubjectAndBody() throws Throwable {
        // Given
        String expectedSubject = "Integration Test Subject";
        String expectedBody = "Integration Test Body with special chars: äöü!@#$%";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("test@example.com");
        message.setSubject(expectedSubject);
        message.setText(expectedBody);

        // When
        mailSender.send(message);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(expectedSubject);
        assertThat(receivedMessage.getContent()).asString().contains(expectedBody);
    }

    @Test
    void testSenderAddressIsSet() throws Throwable {
        // Given
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("recipient@example.com");
        message.setSubject("Sender Test");
        message.setText("Test");

        // When
        mailSender.send(message);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getFrom()).isNotNull();
        assertThat(receivedMessage.getFrom()[0].toString()).contains("noreply@example.com");
    }

    @Test
    void testSendMultipleEmails() throws Throwable {
        // Given
        SimpleMailMessage message1 = new SimpleMailMessage();
        message1.setTo("user1@example.com");
        message1.setSubject("Email 1");
        message1.setText("Body 1");

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setTo("user2@example.com");
        message2.setSubject("Email 2");
        message2.setText("Body 2");

        // When
        mailSender.send(message1);
        mailSender.send(message2);

        // Then
        assertThat(greenMail.getReceivedMessages()).hasSize(2);
        assertThat(greenMail.getReceivedMessages()[0].getSubject()).isEqualTo("Email 1");
        assertThat(greenMail.getReceivedMessages()[1].getSubject()).isEqualTo("Email 2");
    }

    @Test
    void testMailPreparationException() {
        // Given
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("");
        message.setSubject("Test");
        message.setText("Test");

        // When / Then
        assertThatThrownBy(() -> mailSender.send(message))
                .isInstanceOf(MailPreparationException.class); // ArrayIndexOutOfBounds or NullPointerException
    }

    @Test
    void testMailSendException() {
        mailSender = new MailSenderSmtpImpl(){
            @Override
            void transportSend(MimeMessage mimeMessage) throws MessagingException {
                throw new jakarta.mail.MessagingException("oops");
            }
        };
        // Configure the mail sender to use GreenMail SMTP server
        ReflectionTestUtils.setField(mailSender, "smtpHost", "localhost");
        ReflectionTestUtils.setField(mailSender, "smtpPort", ServerSetupTest.SMTP.getPort());

        // Given
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("user1@example.com");
        message.setSubject("Test");
        message.setText("Test");

        // When / Then
        assertThatThrownBy(() -> mailSender.send(message))
                .isInstanceOf(MailSendException.class); // ArrayIndexOutOfBounds or NullPointerException
    }
}