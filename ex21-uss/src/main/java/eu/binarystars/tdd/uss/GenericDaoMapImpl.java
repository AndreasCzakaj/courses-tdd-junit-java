package eu.binarystars.tdd.uss;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GenericDaoMapImpl<I, E> implements GenericDao<I, E> {
    public final Map<String, E> repo = new HashMap<>();

    @Override
    public Optional<E> get(final I uid)  throws DaoException {
        return Optional.ofNullable(repo.get(uid.toString()));
    }

    @Override
    public E save(final E entity, final I key) throws DaoException {
        repo.put(key.toString(), entity);
        return entity;
    }
}