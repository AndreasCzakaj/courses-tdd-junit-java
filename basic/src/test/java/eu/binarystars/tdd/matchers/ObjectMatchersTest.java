package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ObjectMatchersTest {
    First first;
    Person[] people;
    Person firstPerson;

    @BeforeEach
    void setUp() throws Exception {
        first = new First();
        people = first.getPeople();
        firstPerson = people[0];
    }

    @Test
    @Disabled
    void peopleShouldContain_1000_people() throws Exception{
    }

    @Test
    @Disabled
    void testFirstPerson() throws Exception{
        final var expected = new Person() {{
            id = 1;
            firstName = "Skippy";
            lastName = "Rayne";
            email = "srayne0@dot.gov";
            ipAddress = "229.183.132.150";
        }};
    }

    @Test
    @Disabled
    void testFirstPersonInOneGo() throws Exception{
        final var expected = new Person() {{
            id = 1;
            firstName = "Skippy";
            lastName = "Rayne";
            email = "srayne0@dot.gov";
            ipAddress = "229.183.132.150";
        }};
    }

    @Test
    @Disabled
    void testFirstPersonPartially() throws Exception{
        final var expected = new Person() {{
            id = 1;
            firstName = "Skippy";
            lastName = "Rayne";
        }};
    }

    @Test
    @Disabled
    void testGetPersonThrowsException() {
    }
}
