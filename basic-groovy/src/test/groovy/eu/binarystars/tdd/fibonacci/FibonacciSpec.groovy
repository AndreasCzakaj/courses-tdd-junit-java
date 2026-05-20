package eu.binarystars.tdd.fibonacci

import spock.lang.Specification

class FibonacciSpec extends Specification {

    // see https://www.wackerart.de/mathematik/big_numbers/fibonacci_numbers.html

    def "it should calculate fibonacci correctly for index #index"() {
        given:
        def sut = new Fibonacci()

        expect:
        sut.calc(index) == expected

        where:
        index | expected
        0     | 0
        1     | 1
        2     | 1
        3     | 2
        4     | 3
        5     | 5
        6     | 8
        10    | 55
        20    | 6_765
        30    | 832_040
        40    | 102_334_155
        46    | 1_836_311_903
    }

    def "it should throw IllegalArgumentException for invalid index #index"() {
        given:
        def sut = new Fibonacci()

        when:
        sut.calc(index)

        then:
        thrown(IllegalArgumentException)

        where:
        index << [-1, 47, null]
    }
}
