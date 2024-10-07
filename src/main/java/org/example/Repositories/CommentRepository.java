package org.example.Repositories;

import jakarta.persistence.OptimisticLockException;
import org.example.Entities.Comment;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CommentRepository implements EntityRepository<Comment> {
    private final Session session;

    public CommentRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Comment comment) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(comment);
            transaction.commit();
        } catch (OptimisticLockException e) {
            e.printStackTrace();
        }
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
        try {
            Transaction transaction = session.beginTransaction();
            session.update(comment);
            transaction.commit();
        } catch (OptimisticLockException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Comment comment) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(comment);
            transaction.commit();
        } catch (OptimisticLockException e) {
            e.printStackTrace();
        }
    }
}
