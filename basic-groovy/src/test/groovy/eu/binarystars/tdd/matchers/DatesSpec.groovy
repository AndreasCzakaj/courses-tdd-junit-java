package eu.binarystars.tdd.matchers

import spock.lang.Ignore
import spock.lang.Specification

import java.time.*

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

    @Ignore("birthday should be 1990-05-15")
    def "should be 1990-05-15"() {
        expect: true
    }

    @Ignore("birthday should be before today")
    def "should be before today"() {
        expect: true
    }

    @Ignore("birthday should be after 1980-01-01")
    def "should be after 1980-01-01"() {
        expect: true
    }

    @Ignore("birthday should be in May")
    def "should be in May"() {
        expect: true
    }

    @Ignore("birthday should be in year 1990")
    def "should be in year 1990"() {
        expect: true
    }

    @Ignore("birthday should be on day 15")
    def "should be on day 15"() {
        expect: true
    }

    @Ignore("project deadline should be in the future compared to 2024-01-01")
    def "project deadline should be in future"() {
        expect: true
    }

    @Ignore("project deadline should be between 2024-01-01 and 2025-12-31")
    def "project deadline should be between dates"() {
        expect: true
    }

    // LocalDateTime assertions

    @Ignore("meeting time should be 2024-03-20T14:30:00")
    def "meeting time should be 2024-03-20 at 14:30"() {
        expect: true
    }

    @Ignore("meeting time should be before now")
    def "meeting time should be before now"() {
        expect: true
    }

    @Ignore("meeting time should have hour 14")
    def "meeting time should have hour 14"() {
        expect: true
    }

    @Ignore("meeting time should have minute 30")
    def "meeting time should have minute 30"() {
        expect: true
    }

    @Ignore("meeting time should be in March 2024")
    def "meeting time should be in March 2024"() {
        expect: true
    }

    // LocalTime assertions

    @Ignore("work start should be 09:00")
    def "work start should be 09:00"() {
        expect: true
    }

    @Ignore("work start should be before noon (12:00)")
    def "work start should be before noon"() {
        expect: true
    }

    @Ignore("work start should have hour 9")
    def "work start should have hour 9"() {
        expect: true
    }

    @Ignore("work start should be between 08:00 and 10:00")
    def "work start should be between 08:00 and 10:00"() {
        expect: true
    }

    // ZonedDateTime assertions

    @Ignore("conference start should be in Europe/Berlin timezone")
    def "conference start should be in Europe/Berlin timezone"() {
        expect: true
    }

    @Ignore("conference start should be 2024-06-01T09:00 in Berlin")
    def "conference start should be correct date-time"() {
        expect: true
    }

    @Ignore("conference start should have zone offset +01:00 or +02:00")
    def "conference start should have European offset"() {
        expect: true
    }

    // Instant assertions

    @Ignore("event timestamp should be 2024-01-15T10:30:00Z")
    def "event timestamp should be correct"() {
        expect: true
    }

    @Ignore("event timestamp should be before Instant.now()")
    def "event timestamp should be in past"() {
        expect: true
    }

    @Ignore("event timestamp should be close to 2024-01-15T10:30:00Z within 1 second")
    def "event timestamp should be close to expected"() {
        expect: true
    }

    // Advanced: Period and Duration

    @Ignore("birthday should be more than 30 years before today")
    def "birthday should be more than 30 years ago"() {
        expect: true
    }

    @Ignore("meeting time should be at least 2 hours duration from 12:00 same day")
    def "meeting should be at least 2 hours after noon"() {
        expect: true
    }

    // Combined assertions

    @Ignore("TODO: combine multiple date assertions in one test")
    def "should combine multiple date assertions"() {
        expect: true
    }
}
