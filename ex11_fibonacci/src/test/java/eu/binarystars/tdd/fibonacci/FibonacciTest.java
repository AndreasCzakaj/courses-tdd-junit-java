package eu.binarystars.tdd.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FibonacciTest {

    @Test
    @Disabled
    void itShouldThrowForNullIndex() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldThrowForNegativeIndex() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldThrowForIndexAbove46() {
        fail("impl me");
    }

    // see https://www.wackerart.de/mathematik/big_numbers/fibonacci_numbers.html

    @Test
    @DisplayName("it should yield 0 for index 0")
    void itShouldYield0ForIndex0() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield1ForIndex1() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield1ForIndex2() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield2ForIndex3() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield3ForIndex4() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield5ForIndex5() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield8ForIndex6() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield55ForIndex10() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield6_765ForIndex20() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield832_040ForIndex30() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield102_334_155ForIndex40() {
        fail("impl me");
    }

    @Test
    @Disabled
    void itShouldYield1_836_311_903For46() {
        fail("impl me");
    }

}
