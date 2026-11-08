package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ObjectMatchersTest {
    PersonSamples samples;
    Person[] people;
    Person firstPerson;

    @BeforeEach
    void setUp() throws Exception {
        samples = new PersonSamples();
        people = samples.getPeople();
        firstPerson = people[0];
    }

    @Test
    void peopleShouldContain_1000_people() throws Exception{
        assertThat(people).hasSize(1000);
    }

    @Test
    void testFirstPerson() throws Exception{
        final var expected = new Person() {{
            id = 1;
            firstName = "Skippy";
            lastName = "Rayne";
            email = "srayne0@dot.gov";
            ipAddress = "229.183.132.150";
        }};

        assertThat(firstPerson.id).isEqualTo(expected.id);
        assertThat(firstPerson.firstName).isEqualTo(expected.firstName);
        assertThat(firstPerson.lastName).isEqualTo(expected.lastName);
        assertThat(firstPerson.email).isEqualTo(expected.email);
        assertThat(firstPerson.ipAddress).isEqualTo(expected.ipAddress);

        // alternative
        assertSoftly(s -> {
            s.assertThat(firstPerson.id).isEqualTo(expected.id);
            s.assertThat(firstPerson.firstName).isEqualTo(expected.firstName);
            s.assertThat(firstPerson.lastName).isEqualTo(expected.lastName);
            s.assertThat(firstPerson.email).isEqualTo(expected.email);
            s.assertThat(firstPerson.ipAddress).isEqualTo(expected.ipAddress);
        });

        // batch!
        assertThat(firstPerson).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testFirstPersonInOneGo() throws Exception{
        final var expected = new Person() {{
            id = 1;
            firstName = "Skippy";
            lastName = "Rayne";
            email = "srayne0@dot.gov";
            ipAddress = "229.183.132.150";
        }};

        assertThat(firstPerson).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testFirstPersonPartially() throws Exception{
        final var expected = new Person() {{
            id = 1;
            firstName = "Skippy";
            lastName = "Rayne";
        }};

        // variant 1: selected fields
        assertThat(firstPerson).usingRecursiveComparison()
            .comparingOnlyFields("id", "firstName", "lastName")
            .isEqualTo(expected);

        // variant 2: subtracted fields
        assertThat(firstPerson).usingRecursiveComparison()
            .ignoringFields("email", "ipAddress")
            .isEqualTo(expected);

        // variant 3: compact
        assertThat(firstPerson)
            .extracting("id", "firstName", "lastName")
            .containsExactly(1, "Skippy", "Rayne");
    }

    @Test
    void testGetPersonThrowsException() {
        // variant 1
        var error = assertThrowsExactly(NullPointerException.class, () -> samples.getPerson());
        assertThat(error.getMessage()).isEqualTo("oops");

        // variant 2
        assertThatThrownBy(() -> samples.getPerson())
                .isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("oops");
    }
}
