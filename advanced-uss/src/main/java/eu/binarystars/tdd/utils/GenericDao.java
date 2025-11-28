package eu.binarystars.tdd.utils;

import java.util.Optional;

public interface GenericDao<I, E> {

    Optional<E> get(I key) throws DaoException;



    E save(E entity) throws DaoException;
}
