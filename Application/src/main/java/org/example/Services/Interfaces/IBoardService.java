package org.example.Services.Interfaces;

import org.bson.types.ObjectId;
import org.example.Entities.Board;

import java.util.List;

public interface IBoardService {
    Board createBoard(String name);
    boolean deleteBoard(ObjectId boardId, ObjectId userId);
    boolean updateBoard(Board board);
    Board getBoardById(ObjectId boardId);
    List<Board> getAllBoards();
    boolean addUserToBoard(ObjectId boardId, ObjectId userId);
    boolean removeUserFromBoard(ObjectId boardId, ObjectId userId);
}
