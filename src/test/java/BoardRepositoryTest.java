import org.example.Entities.Board;
import org.example.Repositories.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardRepositoryTest extends BaseRepositoryTest {
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        boardRepository = new BoardRepository(session);
    }

    @Test
    public void testCreateAndGetById() {
        Board board = new Board("Test Board");
        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        Assertions.assertNotNull(retrievedBoard);
        Assertions.assertEquals("Test Board", retrievedBoard.getName());
    }

    @Test
    public void testGetAll() {
        Board board1 = new Board("Board 1");
        Board board2 = new Board("Board 2");
        boardRepository.create(board1);
        boardRepository.create(board2);

        List<Board> boards = boardRepository.getAll();
        Assertions.assertEquals(2, boards.size());
    }

    @Test
    public void testUpdate() {
        Board board = new Board("Initial Name");
        boardRepository.create(board);

        board.setName("Updated Name");
        boardRepository.update(board);

        Board updatedBoard = boardRepository.getById(board.getId());
        Assertions.assertEquals("Updated Name", updatedBoard.getName());
    }

    @Test
    public void testDelete() {
        Board board = new Board("To Be Deleted");
        boardRepository.create(board);

        boardRepository.delete(board);
        Board deletedBoard = boardRepository.getById(board.getId());

        Assertions.assertNull(deletedBoard);
    }
}