package org.example.Repositories;

import org.example.Entities.Post;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PostRepository implements EntityRepository<Post> {
    private final Session session;

    public PostRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Post post) {
        session.save(post);
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
        session.update(post);
    }

    @Override
    public void delete(Post post) {
        session.delete(post);
    }
}
