package eu.binarystars.tdd.uss;

import java.util.List;
import java.util.Map;

import lombok.experimental.Accessors;
import lombok.Builder;
import lombok.Getter;

@Accessors(fluent = true, chain = true)
@Builder
@Getter
public class SignUpResult {
    private List<String> globalErrors;
    private Map<String, String> fieldErrors;
}
