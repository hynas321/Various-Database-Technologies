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
        Transaction transaction = session.beginTransaction();
        try {
            session.save(comment);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
        Transaction transaction = session.beginTransaction();
        try {
            session.update(comment);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Comment comment) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(comment);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
