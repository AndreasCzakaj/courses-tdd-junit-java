package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
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
        var first = new First();
        list = first.getList();
        map = first.map;
    }

    @Test
    void shouldContain_3_elements() throws Exception{
        assertThat(list).hasSize(3);
    }

    @Test
    void shouldContain_a() throws Exception{
        assertThat(list).contains("a");
    }

    @Test
    void shouldNotContain_d() throws Exception{
        assertThat(list).doesNotContain("d");
    }

    @Test
    void shouldContain_c_and_a() throws Exception{
        assertThat(list).contains("c", "a");
    }

    @Test
    void shouldNotContainDuplicates() throws Exception{
        assertThat(list).doesNotHaveDuplicates();
        assertThat(list).containsOnlyOnce("a", "b", "c");
    }

    @Test
    void more() throws Exception{
        assertThat(list).containsExactly("a", "b", "c");
        assertThat(list).containsExactlyInAnyOrder("c", "a", "b");
        assertThat(list).containsAnyOf("c", "a", "b", "d");
    }

    @Test
    void map() throws Exception{
        assertThat(map)
                .containsKey("k1")
                .doesNotContainKey("xxx")
                .containsValue("v2")
                .doesNotContainValue("yyy")
                .contains(entry("k2", "v2"))
        ;
    }
}
