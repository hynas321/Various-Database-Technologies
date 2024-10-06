package org.example.Repositories;

import org.example.Entities.Post;
import org.example.Repositories.Interfaces.GenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PostRepository implements GenericRepository<Post> {
    private final Session session;

    public PostRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Post post) {
        Transaction transaction = session.beginTransaction();
        session.save(post);
        transaction.commit();
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
        Transaction transaction = session.beginTransaction();
        session.update(post);
        transaction.commit();
    }

    @Override
    public void delete(Post post) {
        Transaction transaction = session.beginTransaction();
        session.delete(post);
        transaction.commit();
    }
}
