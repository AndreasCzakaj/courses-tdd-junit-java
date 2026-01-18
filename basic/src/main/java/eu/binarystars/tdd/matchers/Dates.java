package eu.binarystars.tdd.matchers;

import java.time.*;

public class Dates {

    public LocalDate getBirthday() {
        return LocalDate.of(1990, 5, 15);
    }

    public LocalDateTime getMeetingTime() {
        return LocalDateTime.of(2024, 3, 20, 14, 30, 0);
    }

    public ZonedDateTime getConferenceStart() {
        return ZonedDateTime.of(2024, 6, 1, 9, 0, 0, 0, ZoneId.of("Europe/Berlin"));
    }

    public Instant getEventTimestamp() {
        return Instant.parse("2024-01-15T10:30:00Z");
    }

    public LocalTime getWorkStart() {
        return LocalTime.of(9, 0);
    }

    public LocalDate getProjectDeadline() {
        return LocalDate.of(2024, 12, 31);
    }
}
