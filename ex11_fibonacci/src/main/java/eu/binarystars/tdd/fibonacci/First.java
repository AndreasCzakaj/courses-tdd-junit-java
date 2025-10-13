package eu.binarystars.tdd.fibonacci;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public class First {

    public String getEmail() {
        return "andreas.czakaj@binary-stars.eu";
    }

    public Collection<String> getList() {
        return List.of("a", "b", "c");
    }

    public Person[] getPeople() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = IOUtils.resourceToURL("/ppl.json");
        File file = new File(url.getFile());
        var parser = objectMapper.createParser(url);
        Person[] people = parser.readValueAs(Person[].class);
        return people;
    }

    public Person getPerson() {
        throw new NullPointerException("oops");
    }
}
