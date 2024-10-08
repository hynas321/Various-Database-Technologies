package org.example.Repositories;

import jakarta.persistence.OptimisticLockException;
import org.example.Entities.Board;
import org.example.Repositories.Interfaces.EntityRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BoardRepository implements EntityRepository<Board> {
    private final Session session;

    public BoardRepository(Session session) {
        this.session = session;
    }

    @Override
    public void create(Board board) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(board);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
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
        try {
            session.update(board);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Board board) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(board);
            transaction.commit();
        } catch (OptimisticLockException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

