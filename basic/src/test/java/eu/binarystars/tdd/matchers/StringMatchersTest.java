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
    @Disabled("email should not be null")
    void shouldNotBeNull() throws Exception{
    }

    @Test
    @Disabled("email should be andreas.czakaj@binary-stars.eu")
    void shouldBeAndreasCzakaj() throws Exception{
    }

    @Test
    @Disabled("email should start with 'andreas'")
    void shouldStartWith_andreas() throws Exception{
    }

    @Test
    @Disabled("email should end with '.eu'")
    void shouldEndWith_dot_eu() throws Exception{
    }

    @Test
    @Disabled("email should not end with '.com'")
    void shouldNotEndWith_dot_com() throws Exception{
    }

    @Test
    @Disabled("email should contain 'binary'")
    void shouldContain_binary() throws Exception{
    }

    @Test
    @Disabled("email should contain 'andreas' and 'stars'")
    void shouldContain_andreas_and_stars() throws Exception{
    }

    @Test
    @Disabled("email should match regular expression '[a-z.@\\-]+'")
    void shouldMatch_regex() throws Exception{
    }

    @Test
    @Disabled("TODO")
    void shouldMatchAllInOne() throws Exception{
    }
}
