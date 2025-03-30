package eu.binarystars.tdd.uss;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DaoListImpl<V> implements Dao<V> {
    private final List<V> db;
    private final Function<V, String> keyProvider;

    public DaoListImpl(Function<V, String> keyProvider) {
        this(keyProvider, null);
    }

    public DaoListImpl(Function<V, String> keyProvider, ArrayList<V> data) {
        this.keyProvider = keyProvider;
        this.db = data == null ? new ArrayList<>() : data;
    }

    @Override
    public V get(String key) {
        return db.stream().filter(v -> keyProvider.apply(v).equals(key)).findFirst().orElse(null);
    }

    @Override
    public V save(V value) {
        if (keyProvider.apply(value) == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        this.db.add(value);
        return value;
    }
}
