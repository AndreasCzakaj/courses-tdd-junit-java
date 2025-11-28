package eu.binarystars.tdd.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GenericDaoMapImpl<I, E> implements GenericDao<I, E> {

    public GenericDaoMapImpl(Function <E, I> keyProvider) {
        this.keyProvider = keyProvider;
    }

    Function <E, I> keyProvider;

    public final Map<I, E> repo = new HashMap<>();

    @Override
    public Optional<E> get(final I key)  throws DaoException {
        return Optional.ofNullable(repo.get(key));
    }

    @Override
    public E save(final E entity) throws DaoException {
        I key = keyProvider.apply(entity);
        repo.put(key, entity);
        return entity;
    }
}