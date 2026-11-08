package eu.binarystars.tdd.mockito;

/**
 * Port to an external notification service (SMS, mail, push, ...).
 */
public interface AlertSender {
    void send(String message);
}
