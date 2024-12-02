package org.example.Repositories.Interfaces;

import java.util.List;

public interface EntityRepository<T> {
    void create(T entity);
    T getById(Long id);
    List<T> getAll();
    void update(T entity);
    void delete(T entity);
}