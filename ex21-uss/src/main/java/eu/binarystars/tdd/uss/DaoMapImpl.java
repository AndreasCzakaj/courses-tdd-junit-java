package eu.binarystars.tdd.uss;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DaoMapImpl<V> implements Dao<V> {
    private final Map<String, V> db;
    private final Function<V, String> keyProvider;

    public DaoMapImpl(Function<V, String> keyProvider) {
        this(keyProvider, null);
    }

    public DaoMapImpl(Function<V, String> keyProvider, Map<String, V> data) {
        this.keyProvider = keyProvider;
        this.db = data == null ? new HashMap <>() : data;
    }

    @Override
    public V get(String key) {
        return db.get(key);
    }

    @Override
    public V save(V value) {
        final String key = keyProvider.apply(value);
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        this.db.put(key, value);
        return value;
    }
}
