package org.example.Mappers;

import org.bson.Document;

public interface EntityMapper<T> {
    Document toDocument(T entity);
    T fromDocument(Document document);
}
