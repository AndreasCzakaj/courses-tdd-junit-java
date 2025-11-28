package eu.binarystars.tdd.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    @Disabled("TODO")
    void shouldNotBeNull() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldBeAndreasCzakaj() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldStartWith_andreas() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldEndWith_dot_eu() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldNotEndWith_dot_com() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldContain_binary() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldMatch_regex() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldMatchAllInOne() throws Exception{
    }
}
