package org.example.Repositories;

import jakarta.persistence.OptimisticLockException;
import org.example.Entities.Post;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PostRepository implements EntityRepository<Post> {
    private final Session session;

    public PostRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Post post) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(post);
            transaction.commit();
        } catch (OptimisticLockException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getById(Long id) {
        return session.get(Post.class, id);
    }

    @Override
    public List<Post> getAll() {
        Query<Post> query = session.createQuery("from Post", Post.class);
        return query.list();
    }

    @Override
    public void update(Post post) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(post);
            transaction.commit();
        } catch (OptimisticLockException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Post post) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(post);
            transaction.commit();
        } catch (OptimisticLockException e) {
            e.printStackTrace();
        }
    }
}
