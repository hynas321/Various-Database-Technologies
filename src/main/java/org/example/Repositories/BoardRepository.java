package org.example.Repositories;

import org.example.Entities.Board;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BoardRepository implements EntityRepository<Board> {
    private final Session session;

    public BoardRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Board board) {
        session.save(board);
    }

    @Override
    public Board getById(Long id) {
        return session.get(Board.class, id);
    }

    @Override
    public List<Board> getAll() {
        Query<Board> query = session.createQuery("from Board", Board.class);
        return query.list();
    }

    @Override
    public void update(Board board) {
        session.update(board);
    }

    @Override
    public void delete(Board board) {
        session.delete(board);
    }
}
