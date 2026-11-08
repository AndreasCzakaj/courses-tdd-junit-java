package eu.binarystars.tdd.matchers

import spock.lang.Specification

class ObjectMatchersSpecNative extends Specification {

    First first
    Person[] people
    Person firstPerson

    def setup() {
        first = new First()
        people = first.getPeople()
        firstPerson = people[0]
    }

    def "people should contain 1000 people"() {
        expect:
        people.length == 1000
    }

    def "test first person"() {
        expect:
        firstPerson.id == 1
        firstPerson.firstName == "Skippy"
        firstPerson.lastName == "Rayne"
        firstPerson.email == "srayne0@dot.gov"
        firstPerson.ipAddress == "229.183.132.150"
    }

    def "test first person in one go"() {
        expect:
        with(firstPerson) {
            id == 1
            firstName == "Skippy"
            lastName == "Rayne"
            email == "srayne0@dot.gov"
            ipAddress == "229.183.132.150"
        }
    }

    def "test first person partially"() {
        expect:
        with(firstPerson) {
            id == 1
            firstName == "Skippy"
            lastName == "Rayne"
        }
    }

    def "getPerson should throw an exception"() {
        when:
        first.getPerson()

        then:
        def e = thrown(NullPointerException)
        e.message == "oops"
    }
}
