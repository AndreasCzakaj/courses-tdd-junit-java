package eu.binarystars.tdd.uss.database;

import eu.binarystars.tdd.utils.DaoException;
import eu.binarystars.tdd.utils.GenericDao;

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