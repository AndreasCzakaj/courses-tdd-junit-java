package eu.binarystars.tdd.fibonacci;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FibonacciLoopImplTest extends FibonacciTestBase {
    Fibonacci factory() {
        return new FibonacciLoopImpl();
    }

    @ParameterizedTest(name = "it should yield {1} for index #{0}")
    @MethodSource("shouldPassForLargeNumbersParams")
    void shouldPassForLargeNumbers(Integer index, Integer expected) {
        shouldPass(index, expected);
    }

    static Stream<Arguments> shouldPassForLargeNumbersParams() {
        return Stream.of(
                arguments(40, 102_334_155),
                arguments(46, 1_836_311_903)
        );
    }
}
