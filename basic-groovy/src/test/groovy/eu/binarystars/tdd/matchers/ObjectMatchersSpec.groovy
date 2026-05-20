package eu.binarystars.tdd.matchers

import spock.lang.Ignore
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.assertThatThrownBy

class ObjectMatchersSpec extends Specification {

    First first
    Person[] people
    Person firstPerson

    def setup() {
        first = new First()
        people = first.getPeople()
        firstPerson = people[0]
    }

    @Ignore
    def "people should contain 1000 people"() {
        expect: true
    }

    @Ignore
    def "test first person"() {
        given:
        def expected = new Person(id: 1, firstName: "Skippy", lastName: "Rayne",
                email: "srayne0@dot.gov", ipAddress: "229.183.132.150")
        expect: true
    }

    @Ignore
    def "test first person in one go"() {
        given:
        def expected = new Person(id: 1, firstName: "Skippy", lastName: "Rayne",
                email: "srayne0@dot.gov", ipAddress: "229.183.132.150")
        expect: true
    }

    @Ignore
    def "test first person partially"() {
        given:
        def expected = new Person(id: 1, firstName: "Skippy", lastName: "Rayne")
        expect: true
    }

    @Ignore
    def "getPerson should throw an exception"() {
        expect: true
    }
}
