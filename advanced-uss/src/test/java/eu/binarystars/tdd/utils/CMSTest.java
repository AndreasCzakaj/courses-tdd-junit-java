package eu.binarystars.tdd.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CMSTest {
    @Test
    void testStatic() {
        assertThat(CMS.getText("de", "one")).isEqualTo("One");
    }

    @Test
    void testContext() {
        assertThat(CMS.DE("one")).isEqualTo("One");
    }

    @Test
    void testImmutableObject() {
        final var localizedCms = new CMS("de");
        assertThat(localizedCms.getText("one")).isEqualTo("One");
    }
}
