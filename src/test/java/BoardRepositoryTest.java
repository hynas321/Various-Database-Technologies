import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<Board> boardRepository;
    private EntityRepository<User> userRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        boardRepository = new BoardRepository(session);
        userRepository = new UserRepository(session);
    }

    @Test
    void create_ShouldSaveBoard() {
        Board board = new Board("Test Board");
        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals("Test Board", retrievedBoard.getName());
    }

    @Test
    void getById_ShouldReturnBoard() {
        Board board = new Board("Another Board");
        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals("Another Board", retrievedBoard.getName());
    }

    @Test
    void getAll_ShouldReturnListOfBoards() {
        Board board1 = new Board("Board 1");
        Board board2 = new Board("Board 2");

        boardRepository.create(board1);
        boardRepository.create(board2);

        List<Board> boards = boardRepository.getAll();
        assertEquals(2, boards.size());
    }

    @Test
    void update_ShouldUpdateBoard() {
        Board board = new Board("Original Board Name");
        boardRepository.create(board);

        board.setName("Updated Board Name");
        boardRepository.update(board);

        Board updatedBoard = boardRepository.getById(board.getId());
        assertEquals("Updated Board Name", updatedBoard.getName());
    }

    @Test
    void delete_ShouldDeleteBoard() {
        Board board = new Board("Board to delete");
        boardRepository.create(board);

        boardRepository.delete(board);

        Board deletedBoard = boardRepository.getById(board.getId());
        assertNull(deletedBoard);
    }

    @Test
    void create_ShouldSaveBoardWithPosts() {
        User user = new User("user6@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board with Posts");
        Post post = new Post("Post in Board", user, board);
        board.getPosts().add(post);

        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals(1, retrievedBoard.getPosts().size());
    }
}
