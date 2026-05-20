package eu.binarystars.tdd.matchers

import spock.lang.Ignore
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.entry

class CollectionMatchersSpec extends Specification {

    Collection<String> list
    Map<String, String> map

    def setup() {
        def first = new First()
        list = first.getList()
        map = first.map
    }

    @Ignore
    def "should contain 3 elements"() {
        expect: true
    }

    @Ignore
    def "should contain 'a'"() {
        expect: true
    }

    @Ignore
    def "should not contain 'd'"() {
        expect: true
    }

    @Ignore
    def "should contain 'c' and 'a'"() {
        expect: true
    }

    @Ignore
    def "should not contain duplicates"() {
        expect: true
    }

    @Ignore
    def "more"() {
        expect: true
    }

    @Ignore
    def "map"() {
        expect: true
    }
}
