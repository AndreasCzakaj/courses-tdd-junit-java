package eu.binarystars.tdd.uss.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Log4j2
public class MailSenderSmtpImpl implements MailSender {

    @Value("${mail.smtp.host:localhost}")
    private String smtpHost;

    @Value("${mail.smtp.port:3025}")
    private int smtpPort;

    public MailMessage send(SimpleMailMessage message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props);
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(new InternetAddress("noreply@example.com"));
            mimeMessage.addRecipient(
                        Message.RecipientType.TO,
                        new InternetAddress(message.getTo()[0]));
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setText(message.getText());
        } catch (MessagingException e) {
            log.error("Email preparation failed", e);
            throw new MailPreparationException(e) {
            };
        }

        try {
            transportSend(mimeMessage);
            log.info(
                    "Email sent to: {}",
                    Arrays.stream(mimeMessage.getAllRecipients())
                            .map(Address::toString)
                            .collect(Collectors.joining(", "))
            );
        } catch (MessagingException e) {
            log.error("Email send failed", e);
            throw new MailSendException("send failed", e);
        }

        return message;
    }

    void transportSend(MimeMessage mimeMessage) throws MessagingException {
        Transport.send(mimeMessage);
    }


}
