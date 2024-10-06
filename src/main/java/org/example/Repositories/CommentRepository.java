package org.example.Repositories;

import org.example.Entities.Comment;
import org.example.Repositories.Interfaces.GenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CommentRepository implements GenericRepository<Comment> {
    private final Session session;

    public CommentRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Comment comment) {
        Transaction transaction = session.beginTransaction();
        session.save(comment);
        transaction.commit();
    }

    @Override
    public Comment getById(Long id) {
        return session.get(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        Query<Comment> query = session.createQuery("from Comment", Comment.class);
        return query.list();
    }

    @Override
    public void update(Comment comment) {
        Transaction transaction = session.beginTransaction();
        session.update(comment);
        transaction.commit();
    }

    @Override
    public void delete(Comment comment) {
        Transaction transaction = session.beginTransaction();
        session.delete(comment);
        transaction.commit();
    }
}
