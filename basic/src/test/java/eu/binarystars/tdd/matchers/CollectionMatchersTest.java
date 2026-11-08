package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class CollectionMatchersTest {

    Collection<String> list;
    Map<String, String> map;

    @BeforeEach
    void setUp() {
        var samples = new CollectionSamples();
        list = samples.getList();
        map = samples.getMap();
    }

    @Test
    @Disabled
    void shouldContain_3_elements() {
    }

    @Test
    @Disabled
    void shouldContain_a() {
    }

    @Test
    @Disabled
    void shouldNotContain_d() {
    }

    @Test
    @Disabled
    void shouldContain_c_and_a() {
    }

    @Test
    @Disabled
    void shouldNotContainDuplicates() {
    }

    @Test
    @Disabled
    void more() {
    }

    @Test
    @Disabled
    void map() {
    }
}
