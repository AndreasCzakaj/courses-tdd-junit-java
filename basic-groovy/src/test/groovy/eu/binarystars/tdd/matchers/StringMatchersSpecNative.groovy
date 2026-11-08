package eu.binarystars.tdd.matchers

import spock.lang.Specification

class StringMatchersSpecNative extends Specification {

    First first
    String email

    def setup() {
        first = new First()
        email = first.getEmail()
    }

    def "should not be null"() {
        expect:
        email != null
    }

    def "should be andreas.czakaj@binary-stars.eu"() {
        expect:
        email == "andreas.czakaj@binary-stars.eu"
    }

    def "should start with 'andreas'"() {
        expect:
        email.startsWith("andreas")
    }

    def "should end with '.eu'"() {
        expect:
        email.endsWith(".eu")
    }

    def "should not end with '.com'"() {
        expect:
        !email.endsWith(".com")
    }

    def "should contain 'binary'"() {
        expect:
        email.contains("binary")
    }

    def "should contain 'andreas' and 'stars'"() {
        expect:
        email.contains("andreas")
        email.contains("stars")
    }

    def "should match regex"() {
        expect:
        email ==~ /[a-z.@\-]+/
    }

    def "should match all in one"() {
        expect:
        email != null
        email == "andreas.czakaj@binary-stars.eu"
        email.startsWith("andreas")
        email.endsWith(".eu")
        !email.endsWith(".com")
        email.contains("binary")
        email.contains("andreas") && email.contains("stars")
        email ==~ /[a-z.@\-]+/
    }
}
