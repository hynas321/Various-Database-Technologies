package org.example.Repositories.Interfaces;

import java.util.List;

public interface GenericRepository<T> {
    void create(T entity);
    T getById(Long id);
    List<T> getAll();
    void update(T entity);
    void delete(T entity);
}