package eu.binarystars.tdd.matchers

import spock.lang.Specification

import java.time.*
import java.time.temporal.ChronoUnit

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.within

class DatesSpec extends Specification {

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
        when:
        assertThat(birthday).isEqualTo(LocalDate.of(1990, 5, 15))

        then:
        noExceptionThrown()
    }

    def "birthday should be before today"() {
        when:
        assertThat(birthday).isBefore(LocalDate.now())

        then:
        noExceptionThrown()
    }

    def "birthday should be after 1980-01-01"() {
        when:
        assertThat(birthday).isAfter(LocalDate.of(1980, 1, 1))

        then:
        noExceptionThrown()
    }

    def "birthday should be in May"() {
        when:
        assertThat(birthday).hasMonth(Month.MAY)

        then:
        noExceptionThrown()
    }

    def "birthday should be in year 1990"() {
        when:
        assertThat(birthday).hasYear(1990)

        then:
        noExceptionThrown()
    }

    def "birthday should be on day 15"() {
        when:
        assertThat(birthday).hasDayOfMonth(15)

        then:
        noExceptionThrown()
    }

    def "project deadline should be in future compared to 2024-01-01"() {
        when:
        assertThat(projectDeadline).isAfter(LocalDate.of(2024, 1, 1))

        then:
        noExceptionThrown()
    }

    def "project deadline should be between 2024-01-01 and 2025-12-31"() {
        when:
        assertThat(projectDeadline)
            .isAfterOrEqualTo(LocalDate.of(2024, 1, 1))
            .isBeforeOrEqualTo(LocalDate.of(2025, 12, 31))

        then:
        noExceptionThrown()
    }

    // LocalDateTime assertions

    def "meeting time should be 2024-03-20 at 14:30"() {
        when:
        assertThat(meetingTime).isEqualTo(LocalDateTime.of(2024, 3, 20, 14, 30, 0))

        then:
        noExceptionThrown()
    }

    def "meeting time should be before now"() {
        when:
        assertThat(meetingTime).isBefore(LocalDateTime.now())

        then:
        noExceptionThrown()
    }

    def "meeting time should have hour 14"() {
        when:
        assertThat(meetingTime).hasHour(14)

        then:
        noExceptionThrown()
    }

    def "meeting time should have minute 30"() {
        when:
        assertThat(meetingTime).hasMinute(30)

        then:
        noExceptionThrown()
    }

    def "meeting time should be in March 2024"() {
        when:
        assertThat(meetingTime)
            .hasMonth(Month.MARCH)
            .hasYear(2024)

        then:
        noExceptionThrown()
    }

    // LocalTime assertions

    def "work start should be 09:00"() {
        when:
        assertThat(workStart).isEqualTo(LocalTime.of(9, 0))

        then:
        noExceptionThrown()
    }

    def "work start should be before noon"() {
        when:
        assertThat(workStart).isBefore(LocalTime.NOON)

        then:
        noExceptionThrown()
    }

    def "work start should have hour 9"() {
        when:
        assertThat(workStart).hasHour(9)

        then:
        noExceptionThrown()
    }

    def "work start should be between 08:00 and 10:00"() {
        when:
        assertThat(workStart)
            .isAfterOrEqualTo(LocalTime.of(8, 0))
            .isBeforeOrEqualTo(LocalTime.of(10, 0))

        then:
        noExceptionThrown()
    }

    // ZonedDateTime assertions

    def "conference start should be in Europe/Berlin timezone"() {
        when:
        assertThat(conferenceStart.zone).isEqualTo(ZoneId.of("Europe/Berlin"))

        then:
        noExceptionThrown()
    }

    def "conference start should be correct date-time"() {
        when:
        assertThat(conferenceStart.toLocalDateTime())
            .hasYear(2024)
            .hasMonth(Month.JUNE)
            .hasDayOfMonth(1)
            .hasHour(9)
            .hasMinute(0)

        then:
        noExceptionThrown()
    }

    def "conference start should have European offset"() {
        when:
        assertThat(conferenceStart.offset.id).isIn("+01:00", "+02:00")

        then:
        noExceptionThrown()
    }

    // Instant assertions

    def "event timestamp should be correct"() {
        when:
        assertThat(eventTimestamp).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"))

        then:
        noExceptionThrown()
    }

    def "event timestamp should be in past"() {
        when:
        assertThat(eventTimestamp).isBefore(Instant.now())

        then:
        noExceptionThrown()
    }

    def "event timestamp should be close to expected within 1 second"() {
        when:
        assertThat(eventTimestamp)
            .isCloseTo(Instant.parse("2024-01-15T10:30:00Z"), within(1, ChronoUnit.SECONDS))

        then:
        noExceptionThrown()
    }

    // Advanced: Period and Duration

    def "birthday should be more than 30 years ago"() {
        given:
        def period = Period.between(birthday, LocalDate.now())

        when:
        assertThat(period.years).isGreaterThan(30)

        then:
        noExceptionThrown()
    }

    def "meeting should be at least 2 hours after noon"() {
        given:
        def noon = meetingTime.toLocalDate().atTime(LocalTime.NOON)

        expect:
        Duration.between(noon, meetingTime).toHours() >= 2
    }

    // Combined assertions

    def "should combine multiple date assertions"() {
        when:
        assertThat(birthday)
            .hasYear(1990)
            .hasMonth(Month.MAY)
            .hasDayOfMonth(15)
            .isBefore(LocalDate.now())
            .isAfter(LocalDate.of(1980, 1, 1))

        then:
        noExceptionThrown()
    }
}
