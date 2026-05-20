package eu.binarystars.tdd.matchers

import java.time.*

class Dates {

    LocalDate getBirthday() {
        LocalDate.of(1990, 5, 15)
    }

    LocalDateTime getMeetingTime() {
        LocalDateTime.of(2024, 3, 20, 14, 30, 0)
    }

    ZonedDateTime getConferenceStart() {
        ZonedDateTime.of(2024, 6, 1, 9, 0, 0, 0, ZoneId.of("Europe/Berlin"))
    }

    Instant getEventTimestamp() {
        Instant.parse("2024-01-15T10:30:00Z")
    }

    LocalTime getWorkStart() {
        LocalTime.of(9, 0)
    }

    LocalDate getProjectDeadline() {
        LocalDate.of(2024, 12, 31)
    }
}
