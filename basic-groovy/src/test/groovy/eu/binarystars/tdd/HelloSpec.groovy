package eu.binarystars.tdd

import spock.lang.Specification
import spock.lang.Subject

class HelloSpec extends Specification {

    @Subject
    Hello sut

    def setup() {
        sut = new Hello()
    }

    def "it should yield 42 for the ultimate question"() {
        given:
        def input = "What is the answer to the Ultimate Question of Life, the Universe, and Everything?"

        when:
        def actual = sut.answer(input)

        then:
        actual == 42
    }
}
