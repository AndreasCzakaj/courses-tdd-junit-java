package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class StringMatchersTest {

    First first;
    String email;

    @BeforeEach
    void setUp() {
        first = new First();
        email = first.getEmail();
    }

    @Test
    void shouldNotBeNull() throws Exception{
        assertThat(email).isNotNull();
    }

    @Test
    void shouldBeAndreasCzakaj() throws Exception{
        assertThat(email).isEqualToIgnoringCase("ANDREAS.czakaj@binary-stars.eu");
    }

    @Test
    void shouldStartWith_andreas() throws Exception{
        assertThat(email).startsWith("andreas");
    }

    @Test
    void shouldEndWith_dot_eu() throws Exception{
        assertThat(email).endsWith(".eu");
    }

    @Test
    void shouldNotEndWith_dot_com() throws Exception{
        assertThat(email).doesNotEndWith(".com");
    }

    @Test
    void shouldContain_binary() throws Exception{
        assertThat(email).contains("binary");
    }

    @Test
    void shouldMatch_regex() throws Exception{
        assertThat(email)
                .matches("[0-9a-z.@\\-]+")
                .as("it should match super simplistic reg exp");
    }

    @Test
    void shouldMatchAllInOne() throws Exception{
        // matchers can be chained
        // ... however, the test will stop at the first error
        assertThat(email)
                .isNotNull()
                .isEqualTo("andreas.czakaj@binary-stars.eu")
                .startsWith("andreas")
                .endsWith(".eu")
                .doesNotEndWith(".com")
                .contains("binary")
                .matches("[0-9a-z.@\\-]+");

        // to prevent this, you can use SoftAssertions:
        assertSoftly(s -> {
            s.assertThat(email)
                    .isNotNull()
                    .isEqualTo("andreas.czakaj@binary-stars.eu")
                    .startsWith("andreas")
                    .endsWith(".eu")
                    .doesNotEndWith(".com")
                    .contains("binary")
                    .matches("[0-9a-z.@\\-]+");
        });
    }
}
