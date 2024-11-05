package org.example.Services.Interfaces;

import org.example.Entities.Board;

import java.util.List;
import java.util.UUID;

public interface IBoardService {
    Board createBoard(String name);
    boolean deleteBoard(UUID boardId, UUID userId);
    boolean updateBoard(Board board);
    Board getBoardById(UUID boardId);
    List<Board> getAllBoards();
    boolean addUserToBoard(UUID boardId, UUID userId);
    boolean removeUserFromBoard(UUID boardId, UUID userId);
}