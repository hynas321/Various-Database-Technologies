import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.UserRepository;
import org.hibernate.Session;
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

    @Test
    void versionField_ShouldIncrementOnEachUpdate_ForBoard() {
        Session createSession = sessionFactory.openSession();
        createSession.beginTransaction();
        Board board = new Board("Board for Version Test");
        boardRepository.create(board);
        createSession.getTransaction().commit();
        createSession.close();

        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        Board retrievedBoard = session1.get(Board.class, board.getId());
        int initialVersion = retrievedBoard.getVersion();
        session1.getTransaction().commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        retrievedBoard.setName("Updated by first transaction");
        session2.merge(retrievedBoard);
        session2.getTransaction().commit();

        session2.beginTransaction();
        Board updatedBoard = session2.get(Board.class, retrievedBoard.getId());
        int firstUpdatedVersion = updatedBoard.getVersion();
        session2.getTransaction().commit();
        session2.close();

        assertTrue(firstUpdatedVersion > initialVersion, "Version should increment after the first update");

        Session session3 = sessionFactory.openSession();
        session3.beginTransaction();
        updatedBoard.setName("Updated by second transaction");
        session3.merge(updatedBoard);
        session3.getTransaction().commit();

        session3.beginTransaction();
        Board secondUpdatedBoard = session3.get(Board.class, updatedBoard.getId());
        int secondUpdatedVersion = secondUpdatedBoard.getVersion();
        session3.getTransaction().commit();
        session3.close();

        assertTrue(secondUpdatedVersion > firstUpdatedVersion, "Version should increment after the second update");
    }

}
