package org.example.Services;

import org.example.Entities.Board;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.GenericRepository;
import org.example.Services.Interfaces.IBoardService;

import java.util.List;

public class BoardService implements IBoardService {
    private final GenericRepository<Board> boardRepository;
    private final GenericRepository<User> userRepository;

    public BoardService(GenericRepository<Board> boardRepository, GenericRepository<User> userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Board createBoard(String name) {
        Board board = new Board(name);
        boardRepository.create(board);
        return board;
    }

    @Override
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.getById(boardId);
        if (board != null) {
            boardRepository.delete(board);
        } else {
            throw new IllegalArgumentException("Board not found.");
        }
    }

    @Override
    public Board getBoardById(Long boardId) {
        return boardRepository.getById(boardId);
    }

    @Override
    public List<Board> getAllBoards() {
        return boardRepository.getAll();
    }

    @Override
    public void updateBoard(Board board) {
        boardRepository.update(board);
    }

    @Override
    public void addUserToBoard(Long boardId, Long userId) {
        Board board = boardRepository.getById(boardId);
        User user = userRepository.getById(userId);

        if (board == null || user == null) {
            throw new IllegalArgumentException("Board or User not found.");
        }

        board.getUsers().add(user);
        boardRepository.update(board);
    }

    @Override
    public void removeUserFromBoard(Long boardId, Long userId) {
        Board board = boardRepository.getById(boardId);
        User user = userRepository.getById(userId);

        if (board == null || user == null) {
            throw new IllegalArgumentException("Board or User not found.");
        }

        board.getUsers().remove(user);
        boardRepository.update(board);
    }
}
