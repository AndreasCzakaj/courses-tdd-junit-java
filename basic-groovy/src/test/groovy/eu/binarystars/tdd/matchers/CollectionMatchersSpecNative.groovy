package eu.binarystars.tdd.matchers

import spock.lang.Specification

class CollectionMatchersSpecNative extends Specification {

    Collection<String> list
    Map<String, String> map

    def setup() {
        def first = new First()
        list = first.getList()
        map = first.map
    }

    def "should contain 3 elements"() {
        expect:
        list.size() == 3
    }

    def "should contain 'a'"() {
        expect:
        list.contains("a")
    }

    def "should not contain 'd'"() {
        expect:
        !list.contains("d")
    }

    def "should contain 'c' and 'a'"() {
        expect:
        list.containsAll(["c", "a"])
    }

    def "should not contain duplicates"() {
        expect:
        list.toSet().size() == list.size()
    }

    def "more"() {
        expect:
        with(list) {
            size() == 3
            containsAll(["a", "b", "c"])
            !contains("d")
            toSet().size() == size()
        }
    }

    def "map"() {
        expect:
        map.size() == 2
        map.containsKey("k1")
        map["k1"] == "v1"
        map["k2"] == "v2"
        !map.containsKey("k3")
    }
}
