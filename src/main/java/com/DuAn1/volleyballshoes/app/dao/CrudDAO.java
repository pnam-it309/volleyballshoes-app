package com.DuAn1.volleyballshoes.app.dao;

import java.util.List;

/**
 * Generic interface for CRUD operations on a repository for a specific type.
 *
 * @param <T> the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 */
public interface CrudDAO<T, ID> {

    /**
     * Saves a given entity. Use the returned instance for further operations.
     *
     * @param entity must not be null
     * @return the saved entity; will never be null
     */
    T create(T entity);

    /**
     * Updates a given entity. Use the returned instance for further operations.
     *
     * @param entity must not be null
     * @return the updated entity; will never be null
     */
    T update(T entity);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be null
     * @return true if the entity was deleted, false otherwise
     */
    boolean deleteById(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be null
     * @return the entity with the given id or null if none found
     */
    T findById(ID id);
}
