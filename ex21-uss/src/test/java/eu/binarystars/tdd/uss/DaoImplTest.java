package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DaoImplTest {


    @Test
    void cycleMap() {
        var dao = new DaoMapImpl<>(UserAccount::email);
        assertThat(dao.get(("e"))).isNull();

        dao.save(new UserAccount("e", "p", false));
        assertThat(dao.get(("e"))).usingRecursiveComparison()
                .isNotNull()
                .isEqualTo(new UserAccount("e", "p", false));

    }

    @Test
    void cycleList() {
        var dao = new DaoListImpl<>(UserAccount::email);
        assertThat(dao.get(("e"))).isNull();

        dao.save(new UserAccount("e", "p", false));
        assertThat(dao.get(("e"))).usingRecursiveComparison()
                .isNotNull()
                .isEqualTo(new UserAccount("e", "p", false));

    }
}
