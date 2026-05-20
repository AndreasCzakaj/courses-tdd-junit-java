package eu.binarystars.tdd.matchers

import spock.lang.Specification

import java.time.*

class DatesSpecNative extends Specification {

    Dates sut
    LocalDate birthday
    LocalDateTime meetingTime
    ZonedDateTime conferenceStart
    Instant eventTimestamp
    LocalTime workStart
    LocalDate projectDeadline

    def setup() {
        sut = new Dates()
        birthday = sut.getBirthday()
        meetingTime = sut.getMeetingTime()
        conferenceStart = sut.getConferenceStart()
        eventTimestamp = sut.getEventTimestamp()
        workStart = sut.getWorkStart()
        projectDeadline = sut.getProjectDeadline()
    }

    // LocalDate assertions

    def "birthday should be 1990-05-15"() {
        expect:
        birthday == LocalDate.of(1990, 5, 15)
    }

    def "birthday should be before today"() {
        expect:
        birthday < LocalDate.now()
    }

    def "birthday should be after 1980-01-01"() {
        expect:
        birthday > LocalDate.of(1980, 1, 1)
    }

    def "birthday should be in May"() {
        expect:
        birthday.month == Month.MAY
    }

    def "birthday should be in year 1990"() {
        expect:
        birthday.year == 1990
    }

    def "birthday should be on day 15"() {
        expect:
        birthday.dayOfMonth == 15
    }

    def "project deadline should be in future compared to 2024-01-01"() {
        expect:
        projectDeadline > LocalDate.of(2024, 1, 1)
    }

    def "project deadline should be between 2024-01-01 and 2025-12-31"() {
        expect:
        projectDeadline >= LocalDate.of(2024, 1, 1)
        projectDeadline <= LocalDate.of(2025, 12, 31)
    }

    // LocalDateTime assertions

    def "meeting time should be 2024-03-20 at 14:30"() {
        expect:
        meetingTime == LocalDateTime.of(2024, 3, 20, 14, 30, 0)
    }

    def "meeting time should be before now"() {
        expect:
        meetingTime < LocalDateTime.now()
    }

    def "meeting time should have hour 14"() {
        expect:
        meetingTime.hour == 14
    }

    def "meeting time should have minute 30"() {
        expect:
        meetingTime.minute == 30
    }

    def "meeting time should be in March 2024"() {
        expect:
        meetingTime.month == Month.MARCH
        meetingTime.year == 2024
    }

    // LocalTime assertions

    def "work start should be 09:00"() {
        expect:
        workStart == LocalTime.of(9, 0)
    }

    def "work start should be before noon"() {
        expect:
        workStart < LocalTime.NOON
    }

    def "work start should have hour 9"() {
        expect:
        workStart.hour == 9
    }

    def "work start should be between 08:00 and 10:00"() {
        expect:
        workStart >= LocalTime.of(8, 0)
        workStart <= LocalTime.of(10, 0)
    }

    // ZonedDateTime assertions

    def "conference start should be in Europe/Berlin timezone"() {
        expect:
        conferenceStart.zone == ZoneId.of("Europe/Berlin")
    }

    def "conference start should be correct date-time"() {
        expect:
        conferenceStart.year == 2024
        conferenceStart.monthValue == 6
        conferenceStart.dayOfMonth == 1
        conferenceStart.hour == 9
        conferenceStart.minute == 0
    }

    def "conference start should have European offset"() {
        expect:
        conferenceStart.offset.id in ["+01:00", "+02:00"]
    }

    // Instant assertions

    def "event timestamp should be correct"() {
        expect:
        eventTimestamp == Instant.parse("2024-01-15T10:30:00Z")
    }

    def "event timestamp should be in past"() {
        expect:
        eventTimestamp < Instant.now()
    }

    def "event timestamp should be close to expected within 1 second"() {
        given:
        def expected = Instant.parse("2024-01-15T10:30:00Z")

        expect:
        Math.abs(eventTimestamp.toEpochMilli() - expected.toEpochMilli()) <= 1000
    }

    // Advanced: Period and Duration

    def "birthday should be more than 30 years ago"() {
        expect:
        Period.between(birthday, LocalDate.now()).years > 30
    }

    def "meeting should be at least 2 hours after noon"() {
        given:
        def noon = meetingTime.toLocalDate().atTime(LocalTime.NOON)

        expect:
        Duration.between(noon, meetingTime).toHours() >= 2
    }

    // Combined assertions

    def "should combine multiple date assertions"() {
        expect:
        birthday.year == 1990
        birthday.month == Month.MAY
        birthday.dayOfMonth == 15
        birthday < LocalDate.now()
        birthday > LocalDate.of(1980, 1, 1)
    }
}
