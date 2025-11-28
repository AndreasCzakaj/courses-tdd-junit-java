package eu.binarystars.tdd.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class First {

    public String getEmail() {
        return "andreas.czakaj@binary-stars.eu";
    }

    public Collection<String> getList() {
        return List.of("a", "b", "c");
    }
    public Map<String, String> map = new HashMap<>(){{
       put("k1", "v1");
       put("k2", "v2");
    }};

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
