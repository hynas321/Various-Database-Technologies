package org.example.Services;

import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Repositories.EntityRepository;
import org.example.Services.Interfaces.IBoardService;

import java.util.List;

public class BoardService implements IBoardService {
    private final EntityRepository<Board> boardRepository;
    private final EntityRepository<Account> accountRepository;

    public BoardService(EntityRepository<Board> boardRepository, EntityRepository<Account> accountRepository) {
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Board createBoard(String name) {
        try {
            Board board = new Board(name);
            boardRepository.create(board);
            return board;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBoard(ObjectId boardId, ObjectId userId) {
        try {
            Board board = boardRepository.getById(boardId);
            Account account = accountRepository.getById(userId);

            if (board == null || account == null || (!(account instanceof Admin) && !board.getMemberIds().contains(userId))) {
                return false;
            }

            boardRepository.delete(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBoard(Board board) {
        try {
            Board existingBoard = boardRepository.getById(board.getId());

            if (existingBoard == null) {
                return false;
            }

            boardRepository.update(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Board getBoardById(ObjectId boardId) {
        try {
            return boardRepository.getById(boardId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Board> getAllBoards() {
        try {
            return boardRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addUserToBoard(ObjectId boardId, ObjectId userId) {
        try {
            Board board = boardRepository.getById(boardId);
            Account account = accountRepository.getById(userId);

            if (board == null || account == null) {
                return false;
            }

            board.getMemberIds().add(userId);
            boardRepository.update(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUserFromBoard(ObjectId boardId, ObjectId userId) {
        try {
            Board board = boardRepository.getById(boardId);
            Account account = accountRepository.getById(userId);

            if (board == null || account == null || !board.getMemberIds().contains(userId)) {
                return false;
            }

            board.getMemberIds().remove(userId);
            boardRepository.update(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
