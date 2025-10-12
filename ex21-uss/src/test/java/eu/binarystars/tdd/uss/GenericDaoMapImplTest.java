package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GenericDaoMapImplTest {
    GenericDaoMapImpl<String, Book> dao = new GenericDaoMapImpl<>();

    @Test
    void itShouldYieldNullForNotExisting() {
        dao = new GenericDaoMapImpl<>();
        var actual = dao.get("123");
        assertThat(actual).isNotPresent();
    }

    @Test
    void itShouldYieldBookForExisting() {
        dao = new GenericDaoMapImpl<>();
        dao.repo.put("123", new Book());
        var actual = dao.get("123");
        assertThat(actual).isPresent();
        var expected = new Book();
        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void itShouldSave() {
        dao = new GenericDaoMapImpl<>();
        var actual = dao.get("123");
        assertThat(actual).isNotPresent();

        var bookToSave = new Book();
        bookToSave.id = "123";
        bookToSave.title = "Necronomicon";
        var savedBook = dao.save(bookToSave, bookToSave.id);

        assertThat(savedBook).usingRecursiveComparison().isEqualTo(bookToSave);

        actual = dao.get("123");
        assertThat(actual).isPresent();

        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(bookToSave);
    }

    static class Book {
        String id;
        String title;
    }
}
