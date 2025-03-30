package eu.binarystars.tdd.uss;

import lombok.experimental.Accessors;
import lombok.Builder;
import lombok.Getter;

@Accessors(fluent = true, chain = true)
@Builder
@Getter
public class UserAccount {
    private String email;
    private String passwordHash;
    private boolean verified;
}
