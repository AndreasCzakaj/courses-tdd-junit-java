package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.*;

class DatesTest {
    Dates sut;
    LocalDate birthday;
    LocalDateTime meetingTime;
    ZonedDateTime conferenceStart;
    Instant eventTimestamp;
    LocalTime workStart;
    LocalDate projectDeadline;

    @BeforeEach
    void setUp() {
        sut = new Dates();
        birthday = sut.getBirthday();
        meetingTime = sut.getMeetingTime();
        conferenceStart = sut.getConferenceStart();
        eventTimestamp = sut.getEventTimestamp();
        workStart = sut.getWorkStart();
        projectDeadline = sut.getProjectDeadline();
    }

    // LocalDate assertions
    @Test
    @Disabled("birthday should be 1990-05-15")
    void shouldBe_1990_05_15() {
    }

    @Test
    @Disabled("birthday should be before today")
    void shouldBeBeforeToday() {
    }

    @Test
    @Disabled("birthday should be after 1980-01-01")
    void shouldBeAfter_1980_01_01() {
    }

    @Test
    @Disabled("birthday should be in May")
    void shouldBeInMay() {
    }

    @Test
    @Disabled("birthday should be in year 1990")
    void shouldBeInYear1990() {
    }

    @Test
    @Disabled("birthday should be on day 15")
    void shouldBeOnDay15() {
    }

    @Test
    @Disabled("project deadline should be in the future compared to 2024-01-01")
    void projectDeadlineShouldBeInFuture() {
    }

    @Test
    @Disabled("project deadline should be between 2024-01-01 and 2025-12-31")
    void projectDeadlineShouldBeBetweenDates() {
    }

    // LocalDateTime assertions
    @Test
    @Disabled("meeting time should be 2024-03-20T14:30:00")
    void meetingTimeShouldBe_2024_03_20_at_14_30() {
    }

    @Test
    @Disabled("meeting time should be before now")
    void meetingTimeShouldBeBeforeNow() {
    }

    @Test
    @Disabled("meeting time should have hour 14")
    void meetingTimeShouldHaveHour14() {
    }

    @Test
    @Disabled("meeting time should have minute 30")
    void meetingTimeShouldHaveMinute30() {
    }

    @Test
    @Disabled("meeting time should be in March 2024")
    void meetingTimeShouldBeInMarch2024() {
    }

    // LocalTime assertions
    @Test
    @Disabled("work start should be 09:00")
    void workStartShouldBe_09_00() {
    }

    @Test
    @Disabled("work start should be before noon (12:00)")
    void workStartShouldBeBeforeNoon() {
    }

    @Test
    @Disabled("work start should have hour 9")
    void workStartShouldHaveHour9() {
    }

    @Test
    @Disabled("work start should be between 08:00 and 10:00")
    void workStartShouldBeBetween_08_and_10() {
    }

    // ZonedDateTime assertions
    @Test
    @Disabled("conference start should be in Europe/Berlin timezone")
    void conferenceStartShouldBeInBerlinTimezone() {
    }

    @Test
    @Disabled("conference start should be 2024-06-01T09:00 in Berlin")
    void conferenceStartShouldBeCorrectDateTime() {
    }

    @Test
    @Disabled("conference start should have zone offset +01:00 or +02:00")
    void conferenceStartShouldHaveEuropeanOffset() {
    }

    // Instant assertions
    @Test
    @Disabled("event timestamp should be 2024-01-15T10:30:00Z")
    void eventTimestampShouldBeCorrect() {
    }

    @Test
    @Disabled("event timestamp should be before Instant.now()")
    void eventTimestampShouldBeInPast() {
    }

    @Test
    @Disabled("event timestamp should be close to 2024-01-15T10:30:00Z within 1 second")
    void eventTimestampShouldBeCloseToExpected() {
    }

    // Advanced: Period and Duration
    @Test
    @Disabled("birthday should be more than 30 years before today")
    void birthdayShouldBeMoreThan30YearsAgo() {
    }

    @Test
    @Disabled("meeting time should be at least 2 hours duration from 12:00 same day")
    void meetingShouldBeAtLeast2HoursAfterNoon() {
    }

    // Combined assertions
    @Test
    @Disabled("TODO: combine multiple date assertions in one test")
    void shouldCombineMultipleDateAssertions() {
    }
}
