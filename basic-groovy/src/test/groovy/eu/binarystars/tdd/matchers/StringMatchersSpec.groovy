package eu.binarystars.tdd.matchers

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class StringMatchersSpec extends Specification {

    First first
    String email

    def setup() {
        first = new First()
        email = first.getEmail()
    }

    def "should not be null"() {
        when:
        assertThat(email).isNotNull()

        then:
        noExceptionThrown()
    }

    def "should be andreas.czakaj@binary-stars.eu"() {
        when:
        assertThat(email).isEqualTo("andreas.czakaj@binary-stars.eu")

        then:
        noExceptionThrown()
    }

    def "should start with 'andreas'"() {
        when:
        assertThat(email).startsWith("andreas")

        then:
        noExceptionThrown()
    }

    def "should end with '.eu'"() {
        when:
        assertThat(email).endsWith(".eu")

        then:
        noExceptionThrown()
    }

    def "should not end with '.com'"() {
        when:
        assertThat(email).doesNotEndWith(".com")

        then:
        noExceptionThrown()
    }

    def "should contain 'binary'"() {
        when:
        assertThat(email).contains("binary")

        then:
        noExceptionThrown()
    }

    def "should contain 'andreas' and 'stars'"() {
        when:
        assertThat(email).contains("andreas").contains("stars")

        then:
        noExceptionThrown()
    }

    def "should match regex"() {
        when:
        assertThat(email).matches("[a-z.@\\-]+")

        then:
        noExceptionThrown()
    }

    def "should match all in one"() {
        when:
        assertThat(email)
            .isNotNull()
            .isEqualTo("andreas.czakaj@binary-stars.eu")
            .startsWith("andreas")
            .endsWith(".eu")
            .doesNotEndWith(".com")
            .contains("binary", "andreas", "stars")
            .matches("[a-z.@\\-]+")

        then:
        noExceptionThrown()
    }
}
