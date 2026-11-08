package eu.binarystars.tdd.matchers;

import java.util.List;
import java.util.Map;

public class CollectionSamples {

    public List<String> getList() {
        return List.of("a", "b", "c");
    }

    public Map<String, String> getMap() {
        return Map.of(
                "k1", "v1",
                "k2", "v2"
        );
    }
}
