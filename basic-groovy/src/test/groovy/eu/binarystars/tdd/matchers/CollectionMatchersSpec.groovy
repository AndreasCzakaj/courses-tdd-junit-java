package eu.binarystars.tdd.matchers

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

    def "should contain 3 elements"() {
        when:
        assertThat(list).hasSize(3)

        then:
        noExceptionThrown()
    }

    def "should contain 'a'"() {
        when:
        assertThat(list).contains("a")

        then:
        noExceptionThrown()
    }

    def "should not contain 'd'"() {
        when:
        assertThat(list).doesNotContain("d")

        then:
        noExceptionThrown()
    }

    def "should contain 'c' and 'a'"() {
        when:
        assertThat(list).contains("c", "a")

        then:
        noExceptionThrown()
    }

    def "should not contain duplicates"() {
        when:
        assertThat(list).doesNotHaveDuplicates()

        then:
        noExceptionThrown()
    }

    def "more"() {
        when:
        assertThat(list)
            .hasSize(3)
            .containsExactly("a", "b", "c")
            .doesNotContain("d")
            .doesNotHaveDuplicates()

        then:
        noExceptionThrown()
    }

    def "map"() {
        when:
        assertThat(map)
            .hasSize(2)
            .containsKey("k1")
            .containsEntry("k1", "v1")
            .containsEntry("k2", "v2")
            .doesNotContainKey("k3")

        then:
        noExceptionThrown()
    }
}
