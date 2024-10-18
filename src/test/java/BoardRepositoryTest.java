import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.Entities.Admin;
import org.example.Mappers.AccountMapper;
import org.example.Mappers.BoardMapper;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.EntityRepository;
import org.example.Repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<Board> boardRepository;
    private EntityRepository<Account> accountRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        AccountMapper accountMapper = new AccountMapper();
        BoardMapper boardMapper = new BoardMapper();
        accountRepository = new AccountRepository(database, accountMapper);
        boardRepository = new BoardRepository(database, boardMapper);
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
    void create_ShouldSaveBoardByUser() {
        User user = new User("user1@example.com", "password123");
        accountRepository.create(user);

        Board board = new Board("User's Test Board");
        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals("User's Test Board", retrievedBoard.getName());
    }

    @Test
    void create_ShouldSaveBoardByAdmin() {
        Admin admin = new Admin("admin_unique1@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Admin's Test Board");
        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals("Admin's Test Board", retrievedBoard.getName());
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
        assertFalse(boards.isEmpty());
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
    void create_ShouldSaveBoardWithPostsByUser() {
        User user = new User("user_unique2@example.com", "password123");
        accountRepository.create(user);

        Board board = new Board("Board with Posts");
        Post post = new Post("Post in Board", user.getId(), board.getId());
        board.getPostIds().add(post.getId());

        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals(1, retrievedBoard.getPostIds().size());
    }

    @Test
    void create_ShouldSaveBoardWithPostsByAdmin() {
        Admin admin = new Admin("admin_unique2@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Admin Board with Posts");
        Post post = new Post("Admin Post in Board", admin.getId(), board.getId());
        board.getPostIds().add(post.getId());

        boardRepository.create(board);

        Board retrievedBoard = boardRepository.getById(board.getId());
        assertNotNull(retrievedBoard);
        assertEquals(1, retrievedBoard.getPostIds().size());
    }
}
