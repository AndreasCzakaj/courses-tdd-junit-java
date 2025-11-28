package eu.binarystars.tdd.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

abstract class FibonacciTestBase {
    Fibonacci fibonacci;

    abstract Fibonacci factory();

    @BeforeEach
    void beforeEach() {
        fibonacci = factory();
    }

    // see https://www.wackerart.de/mathematik/big_numbers/fibonacci_numbers.html

    @ParameterizedTest(name = "it should yield {1} for index #{0}")
    @MethodSource("shouldPassForSmallNumbersParams")
    void shouldPassForSmallNumbers(Integer index, Integer expected) {
        shouldPass(index, expected);
    }

    void shouldPass(Integer index, Integer expected) {
        Integer actual = fibonacci.calculate(index);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> shouldPassForSmallNumbersParams() {
        return Stream.of(
                arguments(0, 0),
                arguments(1, 1),
                arguments(2, 1),
                arguments(3, 2),
                arguments(4, 3),
                arguments(5, 5),
                arguments(6, 8),
                arguments(10, 55),
                arguments(20, 6_765),
                arguments(19, 4181),
                arguments(30, 832_040)
                //arguments(40, 102_334_155)
                //arguments(46, 1_836_311_903)
        );
    }

    @ParameterizedTest(name = "it should yield {1} for index #{0}")
    @MethodSource("shouldFailParams")
    void shouldFail(Integer index, String expected) {
        var error =  assertThrows(IllegalArgumentException.class, () -> fibonacci.calculate(index));
        assertThat(error).hasMessage(expected);
    }

    static Stream<Arguments> shouldFailParams() {
        return Stream.of(
                arguments(null, "index must not be null"),
                arguments(-1, "index must not be negative"),
                arguments(47, "index must not be > 46")
        );
    }
}
