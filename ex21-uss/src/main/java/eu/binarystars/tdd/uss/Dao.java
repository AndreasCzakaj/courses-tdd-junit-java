package eu.binarystars.tdd.uss;

public interface Dao<V> {
    V get(String key);

    V save(V value);
}
