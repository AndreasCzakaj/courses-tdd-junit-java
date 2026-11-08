package eu.binarystars.tdd.uss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UssServerTest {

    @Test
    void theSeedAccountsShouldMatchTheReadme() {
        var service = new UserSelfService(new AccountDaoMapImpl(UssServer.seedAccounts().toArray(Account[]::new)));

        assertThat(service.login(UssCreators.createValidCredentials()).username()).isEqualTo("alice_verified");
    }

    @Test
    void invalidJsonShouldBeParsedAsNull() {
        assertThat(UssServer.parse(new java.io.ByteArrayInputStream("no json".getBytes()))).isNull();
    }
}
