package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GenericDaoThrowingImplTest {
    GenericDaoThrowingImpl<String, Book> dao = new GenericDaoThrowingImpl<>();

    @Test
    void itShouldThrowForGet() {
        dao = new GenericDaoThrowingImpl<>();
        assertThatThrownBy(() -> dao.get("123"))
                .isInstanceOf(DaoException.class)
                .hasMessage("get: oops")
                .hasNoCause();
    }

    @Test
    void itShouldThrowForSave() {
        dao = new GenericDaoThrowingImpl<>();
        assertThatThrownBy(() -> dao.save(new Book()))
                .isInstanceOf(DaoException.class)
                .hasMessage("save: oops")
                .hasNoCause();
    }

    static class Book {
    }
}
