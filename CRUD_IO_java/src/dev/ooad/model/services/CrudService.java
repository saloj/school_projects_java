package dev.ooad.model.services;

import java.util.*;

/**
 * Generic interface representing a basic CRUD service that handles saving, retrieving, updating and deleting of objects.
 */
public interface CrudService<ID, T> {

    Set<T> findAll();
    T findById(ID id);
    T save(T object);
    T update(T object);
    void deleteById(ID id);
    void delete(T object);
}
