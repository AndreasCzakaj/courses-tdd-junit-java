package eu.binarystars.tdd.mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Fake with a collector: real code, no framework needed.
 */
public class AlertSenderListImpl implements AlertSender {
    public final List<String> sent = new ArrayList<>();

    @Override
    public void send(final String message) {
        sent.add(message);
    }
}
