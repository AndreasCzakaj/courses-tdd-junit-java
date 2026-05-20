package eu.binarystars.tdd.matchers

import spock.lang.Ignore
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class StringMatchersSpec extends Specification {

    First first
    String email

    def setup() {
        first = new First()
        email = first.getEmail()
    }

    @Ignore("email should not be null")
    def "should not be null"() {
        expect: true
    }

    @Ignore("email should be andreas.czakaj@binary-stars.eu")
    def "should be andreas.czakaj@binary-stars.eu"() {
        expect: true
    }

    @Ignore("email should start with 'andreas'")
    def "should start with 'andreas'"() {
        expect: true
    }

    @Ignore("email should end with '.eu'")
    def "should end with '.eu'"() {
        expect: true
    }

    @Ignore("email should not end with '.com'")
    def "should not end with '.com'"() {
        expect: true
    }

    @Ignore("email should contain 'binary'")
    def "should contain 'binary'"() {
        expect: true
    }

    @Ignore("email should contain 'andreas' and 'stars'")
    def "should contain 'andreas' and 'stars'"() {
        expect: true
    }

    @Ignore("email should match regular expression '[a-z.@\\-]+'")
    def "should match regex"() {
        expect: true
    }

    @Ignore("TODO")
    def "should match all in one"() {
        expect: true
    }
}
