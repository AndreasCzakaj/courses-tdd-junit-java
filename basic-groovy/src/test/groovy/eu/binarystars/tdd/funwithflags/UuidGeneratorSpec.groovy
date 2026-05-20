package eu.binarystars.tdd.funwithflags

import spock.lang.Specification

class UuidGeneratorSpec extends Specification {

    def "should create a UUID matching pattern #expectedRegex for case: #info"() {
        when:
        def actual = uuidGenerator.create()

        then:
        actual ==~ expectedRegex

        where:
        uuidGenerator                      | expectedRegex  | info
        new UuidGeneratorNaiveRandomImpl() | '[a-f0-9]{32}' | 'lower case, no dashes'
        // new ???                         | '[A-F0-9]{32}' | 'upper case, no dashes'
        // new ???                         | '[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}' | 'lower case, with dashes'
        // new ???                         | '[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}' | 'upper case, with dashes'
    }

    def "should use all hex chars"() {
        given:
        def hexChars = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']
        def foundChars = [:]
        def uuidGenerator = new UuidGeneratorNaiveRandomImpl()

        when:
        10.times {
            uuidGenerator.create().each { ch ->
                def s = ch as String
                foundChars[s] = (foundChars[s] ?: 0) + 1
            }
        }

        then:
        hexChars.every { foundChars.containsKey(it) }
        foundChars.values().every { it > 0 }
    }
}
