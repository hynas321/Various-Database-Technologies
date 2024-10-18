package org.example.Repositories;

import java.util.List;

public interface EntityRepository<T> {
    void create(T entity);
    T getById(String id);
    List<T> getAll();
    void update(T entity);
    void delete(T entity);
}