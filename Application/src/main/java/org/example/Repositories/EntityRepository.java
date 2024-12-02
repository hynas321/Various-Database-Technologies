package org.example.Repositories;

import org.bson.types.ObjectId;

import java.util.List;

public interface EntityRepository<T> {
    void create(T entity);
    T getById(ObjectId id);
    List<T> getAll();
    void update(T entity);
    void delete(T entity);
}