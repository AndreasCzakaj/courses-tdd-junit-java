package eu.binarystars.tdd.uss.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

public interface MailSender {
    MailMessage send(SimpleMailMessage message) throws MailException;
}
