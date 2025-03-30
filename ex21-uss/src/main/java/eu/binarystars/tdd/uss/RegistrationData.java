package eu.binarystars.tdd.uss;

import lombok.experimental.Accessors;
import lombok.Builder;
import lombok.Getter;

@Accessors(fluent = true, chain = true)
@Builder
@Getter
public class RegistrationData {
    private String email;
    private boolean agreedToTermsOfUse;
}