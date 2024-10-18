package org.example.Services.Interfaces;

import org.example.Entities.Board;

import java.util.List;
import java.util.Optional;

public interface IBoardService {
    Board createBoard(String name);
    boolean deleteBoard(String boardId, String userId);
    boolean updateBoard(Board board);
    Board getBoardById(String boardId);
    List<Board> getAllBoards();
    boolean addUserToBoard(String boardId, String userId);
    boolean removeUserFromBoard(String boardId, String userId);
}
