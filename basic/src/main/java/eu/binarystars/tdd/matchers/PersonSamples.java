package eu.binarystars.tdd.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class PersonSamples {

    public Person[] getPeople() throws IOException {
        var url = IOUtils.resourceToURL("/ppl.json");
        return new ObjectMapper().readValue(url, Person[].class);
    }

    public Person getPerson() {
        throw new NullPointerException("oops");
    }
}
