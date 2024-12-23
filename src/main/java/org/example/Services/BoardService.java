package org.example.Services;

import org.example.Entities.Board;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.IBoardService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class BoardService implements IBoardService {
    private final EntityRepository<Board> boardRepository;
    private final EntityRepository<Account> accountRepository;
    private final Session session;

    public BoardService(EntityRepository<Board> boardRepository, EntityRepository<Account> accountRepository, Session session) {
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
        this.session = session;
    }

    @Override
    public Optional<Board> createBoard(String name) {
        Transaction transaction = session.beginTransaction();

        try {
            Board board = new Board(name);
            boardRepository.create(board);
            transaction.commit();
            return Optional.of(board);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteBoard(Long boardId, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Board board = boardRepository.getById(boardId);
            Account account = accountRepository.getById(accountId);

            if (board == null || account == null || (!(account instanceof Admin) && !board.getMembers().contains(account))) {
                transaction.rollback();
                return false;
            }

            boardRepository.delete(board);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBoard(Board board) {
        Transaction transaction = session.beginTransaction();

        try {
            Board existingBoard = boardRepository.getById(board.getId());
            if (existingBoard == null) {
                transaction.rollback();
                return false;
            }

            boardRepository.update(board);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Board> getBoardById(Long boardId) {
        return Optional.ofNullable(boardRepository.getById(boardId));
    }

    @Override
    public List<Board> getAllBoards() {
        return boardRepository.getAll();
    }

    @Override
    public boolean addUserToBoard(Long boardId, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Board board = boardRepository.getById(boardId);
            Account account = accountRepository.getById(accountId);

            if (board == null || account == null) {
                transaction.rollback();
                return false;
            }

            board.getMembers().add(account);
            boardRepository.update(board);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUserFromBoard(Long boardId, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Board board = boardRepository.getById(boardId);
            Account account = accountRepository.getById(accountId);

            if (board == null || account == null || !board.getMembers().contains(account)) {
                transaction.rollback();
                return false;
            }

            board.getMembers().remove(account);
            boardRepository.update(board);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
