package eu.binarystars.tdd.uss;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GenericDaoThrowingImpl<I, E> implements GenericDao<I, E> {
    @Override
    public Optional<E> get(final I uid)  throws DaoException {
        throw new DaoException("get: oops");
    }

    @Override
    public E save(final E entity) throws DaoException {
        throw new DaoException("save: oops");
    }
}