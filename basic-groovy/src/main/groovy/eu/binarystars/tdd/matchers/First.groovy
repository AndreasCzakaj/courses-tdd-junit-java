package eu.binarystars.tdd.matchers

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils

class First {

    String getEmail() {
        "andreas.czakaj@binary-stars.eu"
    }

    Collection<String> getList() {
        ["a", "b", "c"]
    }

    Map<String, String> map = ["k1": "v1", "k2": "v2"]

    Person[] getPeople() throws Exception {
        def objectMapper = new ObjectMapper()
        def url = IOUtils.resourceToURL("/ppl.json")
        def parser = objectMapper.createParser(url)
        parser.readValueAs(Person[].class)
    }

    Person getPerson() {
        throw new NullPointerException("oops")
    }
}
