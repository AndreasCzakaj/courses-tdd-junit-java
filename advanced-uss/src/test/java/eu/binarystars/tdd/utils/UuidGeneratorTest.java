package eu.binarystars.tdd.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UuidGeneratorTest {

    @ParameterizedTest(name = "it should match pattern {1} for case: {2}")
    @MethodSource("shouldCreateAUuidInTheMatchingFormatParams")
    public void shouldCreateAUuidInTheMatchingFormat(UuidGenerator uuidGenerator, String expectedRegex, String info) {
        var actual = uuidGenerator.create();
        assertThat(actual)
                .matches(Pattern.compile(expectedRegex))
                .as(info);
    }

    static Stream<Arguments> shouldCreateAUuidInTheMatchingFormatParams() {
        final var baseImpl = new UuidGeneratorNaiveRandomImpl();
        return Stream.of(
                Arguments.arguments(baseImpl, "[a-f0-9]{32}", "lower case, no dashes")
        );
    }

    @Test
    void shouldUseAllChars() {
        var hexChars = new ArrayList<>(){{
            IntStream.range(0, 15).forEach(i ->add(Integer.toHexString(i)));
        }}.toArray(new String[0]);
        var foundChars = new HashMap<String, Integer>();

        UuidGenerator uuidGenerator = new UuidGeneratorNaiveRandomImpl();

        // yes, I'm looping. I need this because the process is random.
        IntStream.range(0, 10).forEach(r -> {
            uuidGenerator.create().chars().forEach(i -> {
                String s = String.valueOf((char) i);
                foundChars.put(s, foundChars.getOrDefault(s, 0) + 1);
            });
        });

        assertThat(foundChars)
                .containsKeys(hexChars)
                .allSatisfy((k, v) -> assertThat(v).isGreaterThan(0));
        assertThat(foundChars.values()).allMatch(value -> value > 0);

    }
}
