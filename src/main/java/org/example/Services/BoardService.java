package org.example.Services;

import org.example.Entities.Board;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.IBoardService;

import java.util.List;
import java.util.Optional;

public class BoardService implements IBoardService {
    private final EntityRepository<Board> boardRepository;
    private final EntityRepository<User> userRepository;

    public BoardService(EntityRepository<Board> boardRepository, EntityRepository<User> userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Board> createBoard(String name) {
        if (name == null || name.isEmpty()) {
            return Optional.empty();
        }

        Board board = new Board(name);
        boardRepository.create(board);
        return Optional.of(board);
    }

    @Override
    public boolean deleteBoard(Long boardId, Long userId) {
        if (boardId == null || userId == null) {
            return false;
        }

        Board board = boardRepository.getById(boardId);
        User user = userRepository.getById(userId);

        if (board == null || user == null || !board.getMembers().contains(user)) {
            return false;
        }

        boardRepository.delete(board);
        return true;
    }

    @Override
    public boolean updateBoard(Board board) {
        if (board == null || board.getId() == null) {
            return false;
        }

        Board existingBoard = boardRepository.getById(board.getId());
        if (existingBoard == null) {
            return false;
        }

        boardRepository.update(board);
        return true;
    }

    @Override
    public Optional<Board> getBoardById(Long boardId) {
        if (boardId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(boardRepository.getById(boardId));
    }

    @Override
    public List<Board> getAllBoards() {
        return boardRepository.getAll();
    }

    @Override
    public boolean addUserToBoard(Long boardId, Long userId) {
        if (boardId == null || userId == null) {
            return false;
        }

        Board board = boardRepository.getById(boardId);
        User user = userRepository.getById(userId);

        if (board == null || user == null) {
            return false;
        }

        board.getMembers().add(user);
        boardRepository.update(board);
        return true;
    }

    @Override
    public boolean removeUserFromBoard(Long boardId, Long userId) {
        if (boardId == null || userId == null) {
            return false;
        }

        Board board = boardRepository.getById(boardId);
        User user = userRepository.getById(userId);

        if (board == null || user == null || !board.getMembers().contains(user)) {
            return false;
        }

        board.getMembers().remove(user);
        boardRepository.update(board);
        return true;
    }
}
