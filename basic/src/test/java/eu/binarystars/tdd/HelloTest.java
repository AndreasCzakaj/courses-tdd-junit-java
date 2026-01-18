package eu.binarystars.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HelloTest {
    Hello sut;

    @BeforeEach
    void beforeEach() {
        sut = new Hello();
    }

    @Test
    @DisplayName("It should yield 42 for the ultimate question")
    void init() {
        // given
        var input = "What is the answer to the Ultimate Question of Life, the Universe, and Everything?";

        // when
        var actual = sut.answer(input);

        // then        
        assertThat(actual).as("it should be as Douglas Adams said").isEqualTo(42);
    }
}
