package eu.binarystars.tdd.uss.controller;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder(toBuilder = true)
public class Errors {
    private String globalError;


    private final Map<String, String> fieldErrors = new HashMap<>();

    public Errors addFieldError(String field, String message) {
        fieldErrors.put(field, message);
        return this;
    }
}
