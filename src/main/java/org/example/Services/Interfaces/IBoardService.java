package org.example.Services.Interfaces;

import org.example.Entities.Board;
import org.example.Entities.User;

import java.util.List;

public interface IBoardService {
    Board createBoard(String name);
    void deleteBoard(Long boardId);

    Board getBoardById(Long boardId);
    List<Board> getAllBoards();
    void updateBoard(Board board);

    void addUserToBoard(Long boardId, Long userId);
    void removeUserFromBoard(Long boardId, Long userId);
}