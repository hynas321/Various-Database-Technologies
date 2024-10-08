package org.example.Repositories;

import jakarta.persistence.OptimisticLockException;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository implements EntityRepository<User> {
    private final Session session;

    public UserRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public User getById(Long id) {
        return session.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        Query<User> query = session.createQuery("from User", User.class);
        return query.list();
    }

    @Override
    public void update(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(user);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(user);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
