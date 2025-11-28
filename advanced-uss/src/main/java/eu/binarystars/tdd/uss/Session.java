package eu.binarystars.tdd.uss;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder=true)
public class Session {
    private final String userId;
}
