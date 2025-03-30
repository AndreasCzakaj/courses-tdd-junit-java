package eu.binarystars.tdd.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;

class FibonacciTest {
    Fibonacci fibonacci;

    @BeforeEach
    void beforeEach() {
        fibonacci = new Fibonacci();
    }

    @Test
    @org.junit.jupiter.api.Disabled
    void itShouldYield0For0() {
        // given
        Integer index = 0;

        // when
        Integer actual = fibonacci.calc(index);

        // then
        Integer expected = 0;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @org.junit.jupiter.api.Disabled
    void itShouldThrowForNegativeIndex() {
        // given
        Integer index = -1;

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fibonacci.calc(index));

        // then
        assertThat(e.getMessage()).contains("index must be >= 0");
    }


    @ParameterizedTest(name = "it should yield {1} for index #{0}")
    @MethodSource("shouldPassParams")
    @org.junit.jupiter.api.Disabled
    void shouldPass(Integer index, Integer expected) {
        Integer actual = fibonacci.calc(index);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> shouldPassParams() {
        return Stream.of(
                arguments(0, 0),
                arguments(1, 1),
                arguments(2, 1),
                arguments(3, 2),
                arguments(4, 3),
                arguments(5, 5),
                arguments(6, 8),
                arguments(19, 4_181),
                arguments(40, 102_334_155),
                arguments(46, 1_836_311_903)
        );
    }


    @ParameterizedTest(name = "it should throw IllegalArgumentException with message `{1}` for index #{0}")
    @MethodSource("shouldFailParams")
    @org.junit.jupiter.api.Disabled
    void shouldFail(Integer index, String expected) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fibonacci.calc(index));
        assertThat(e.getMessage()).contains(expected);
    }

    static Stream<Arguments> shouldFailParams() {
        return Stream.of(
                arguments(null, "index must not be null"),
                arguments(-1, "index must be >= 0"),
                arguments(47, "index must be <= 46")
        );
    }
}
