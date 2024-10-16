package org.example.Repositories;

import org.example.Entities.Comment;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CommentRepository implements EntityRepository<Comment> {
    private final Session session;

    public CommentRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Comment comment) {
        session.save(comment);
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
        session.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        session.delete(comment);
    }
}
