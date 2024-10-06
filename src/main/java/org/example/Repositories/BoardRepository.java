package org.example.Repositories;

import org.example.Entities.Board;
import org.example.Repositories.Interfaces.GenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BoardRepository implements GenericRepository<Board> {
    private final Session session;

    public BoardRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Board board) {
        Transaction transaction = session.beginTransaction();
        session.save(board);
        transaction.commit();
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
        Transaction transaction = session.beginTransaction();
        session.update(board);
        transaction.commit();
    }

    @Override
    public void delete(Board board) {
        Transaction transaction = session.beginTransaction();
        session.delete(board);
        transaction.commit();
    }
}
