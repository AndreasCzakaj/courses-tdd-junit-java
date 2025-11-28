package eu.binarystars.tdd.uss.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public class MailSenderListImpl implements MailSender {
    public List<SimpleMailMessage> sentMessages = new java.util.ArrayList<>();
    public MailException errorToThrow = null;

    @Override
    public MailMessage send(SimpleMailMessage message) {
        if  (errorToThrow != null) {
            throw errorToThrow;
        }
        sentMessages.add(message);
        return message;
    }
}
