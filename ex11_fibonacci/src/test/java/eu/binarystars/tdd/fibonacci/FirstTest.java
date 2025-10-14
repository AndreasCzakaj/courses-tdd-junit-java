package eu.binarystars.tdd.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class FirstTest {

    @Test
    void getEmail() throws Exception{
        // given
        First first = new First();

        // when
        String actual = first.getEmail();

        // then
        assertThat(actual).isNull();
    }
}
