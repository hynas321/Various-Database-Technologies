package org.example.Services.Interfaces;

import org.example.Entities.Board;

import java.util.List;
import java.util.Optional;

public interface IBoardService {
    Optional<Board> createBoard(String name);
    boolean deleteBoard(Long boardId, Long userId);
    boolean updateBoard(Board board);
    Optional<Board> getBoardById(Long boardId);
    List<Board> getAllBoards();
    boolean addUserToBoard(Long boardId, Long userId);
    boolean removeUserFromBoard(Long boardId, Long userId);
}
