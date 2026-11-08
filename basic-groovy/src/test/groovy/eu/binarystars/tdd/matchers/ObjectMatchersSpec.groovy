package eu.binarystars.tdd.matchers

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class ObjectMatchersSpec extends Specification {

    First first
    Person[] people
    Person firstPerson

    def setup() {
        first = new First()
        people = first.getPeople()
        firstPerson = people[0]
    }

    def "people should contain 1000 people"() {
        when:
        assertThat(people).hasSize(1000)

        then:
        noExceptionThrown()
    }

    def "test first person"() {
        when:
        assertThat(firstPerson.id).isEqualTo(1)
        assertThat(firstPerson.firstName).isEqualTo("Skippy")
        assertThat(firstPerson.lastName).isEqualTo("Rayne")
        assertThat(firstPerson.email).isEqualTo("srayne0@dot.gov")
        assertThat(firstPerson.ipAddress).isEqualTo("229.183.132.150")

        then:
        noExceptionThrown()
    }

    def "test first person in one go"() {
        given:
        def expected = new Person(id: 1, firstName: "Skippy", lastName: "Rayne",
                email: "srayne0@dot.gov", ipAddress: "229.183.132.150")

        when:
        assertThat(firstPerson)
            .usingRecursiveComparison()
            .ignoringFields("birthday")
            .isEqualTo(expected)

        then:
        noExceptionThrown()
    }

    def "test first person partially"() {
        when:
        assertThat(firstPerson)
            .hasFieldOrPropertyWithValue("id", 1)
            .hasFieldOrPropertyWithValue("firstName", "Skippy")
            .hasFieldOrPropertyWithValue("lastName", "Rayne")

        then:
        noExceptionThrown()
    }

    def "getPerson should throw an exception"() {
        when:
        first.getPerson()

        then:
        def e = thrown(NullPointerException)
        e.message == "oops"
    }
}
