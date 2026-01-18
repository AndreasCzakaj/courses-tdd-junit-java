package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

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
    void shouldBe_1990_05_15() {
        assertThat(birthday).isEqualTo(LocalDate.of(1990, 5, 15));
    }

    @Test
    void shouldBeBeforeToday() {
        assertThat(birthday).isBefore(LocalDate.now());
    }

    @Test
    void shouldBeAfter_1980_01_01() {
        assertThat(birthday).isAfter(LocalDate.of(1980, 1, 1));
    }

    @Test
    void shouldBeInMay() {
        assertThat(birthday).hasMonth(MAY);
    }

    @Test
    void shouldBeInYear1990() {
        assertThat(birthday).hasYear(1990);
    }

    @Test
    void shouldBeOnDay15() {
        assertThat(birthday).hasDayOfMonth(15);
    }

    @Test
    void projectDeadlineShouldBeInFuture() {
        assertThat(projectDeadline).isAfter(LocalDate.of(2024, 1, 1));
    }

    @Test
    void projectDeadlineShouldBeBetweenDates() {
        assertThat(projectDeadline)
                .isBetween(LocalDate.of(2024, 1, 1), LocalDate.of(2025, 12, 31));
    }

    // LocalDateTime assertions
    @Test
    void meetingTimeShouldBe_2024_03_20_at_14_30() {
        assertThat(meetingTime).isEqualTo(LocalDateTime.of(2024, 3, 20, 14, 30, 0));
    }

    @Test
    void meetingTimeShouldBeBeforeNow() {
        assertThat(meetingTime).isBefore(LocalDateTime.now());
    }

    @Test
    void meetingTimeShouldHaveHour14() {
        assertThat(meetingTime).hasHour(14);
    }

    @Test
    void meetingTimeShouldHaveMinute30() {
        assertThat(meetingTime).hasMinute(30);
    }

    @Test
    void meetingTimeShouldBeInMarch2024() {
        assertThat(meetingTime)
                .hasMonth(MARCH)
                .hasYear(2024);
    }

    // LocalTime assertions
    @Test
    void workStartShouldBe_09_00() {
        assertThat(workStart).isEqualTo(LocalTime.of(9, 0));
    }

    @Test
    void workStartShouldBeBeforeNoon() {
        assertThat(workStart).isBefore(LocalTime.NOON);
    }

    @Test
    void workStartShouldHaveHour9() {
        assertThat(workStart).hasHour(9);
    }

    @Test
    void workStartShouldBeBetween_08_and_10() {
        assertThat(workStart)
                .isBetween(LocalTime.of(8, 0), LocalTime.of(10, 0));
    }

    // ZonedDateTime assertions
    @Test
    void conferenceStartShouldBeInBerlinTimezone() {
        assertThat(conferenceStart.getZone()).isEqualTo(ZoneId.of("Europe/Berlin"));
    }

    @Test
    void conferenceStartShouldBeCorrectDateTime() {
        assertThat(conferenceStart)
                .isEqualTo(ZonedDateTime.of(2024, 6, 1, 9, 0, 0, 0, ZoneId.of("Europe/Berlin")));
    }

    @Test
    void conferenceStartShouldHaveEuropeanOffset() {
        assertThat(conferenceStart.getOffset())
                .isIn(ZoneOffset.of("+01:00"), ZoneOffset.of("+02:00"));
    }

    // Instant assertions
    @Test
    void eventTimestampShouldBeCorrect() {
        assertThat(eventTimestamp).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    }

    @Test
    void eventTimestampShouldBeInPast() {
        assertThat(eventTimestamp).isBefore(Instant.now());
    }

    @Test
    void eventTimestampShouldBeCloseToExpected() {
        assertThat(eventTimestamp)
                .isCloseTo(Instant.parse("2024-01-15T10:30:00Z"), within(1, ChronoUnit.SECONDS));
    }

    // Advanced: Period and Duration
    @Test
    void birthdayShouldBeMoreThan30YearsAgo() {
        assertThat(birthday)
                .isBefore(LocalDate.now().minusYears(30));
    }

    @Test
    void meetingShouldBeAtLeast2HoursAfterNoon() {
        LocalDateTime noon = meetingTime.withHour(12).withMinute(0).withSecond(0);
        Duration duration = Duration.between(noon, meetingTime);
        assertThat(duration).isGreaterThanOrEqualTo(Duration.ofHours(2));
    }

    // Combined assertions
    @Test
    void shouldCombineMultipleDateAssertions() {
        assertThat(birthday)
                .isEqualTo(LocalDate.of(1990, 5, 15))
                .isBefore(LocalDate.now())
                .isAfter(LocalDate.of(1980, 1, 1))
                .hasYear(1990)
                .hasMonth(MAY)
                .hasDayOfMonth(15);
    }
}
