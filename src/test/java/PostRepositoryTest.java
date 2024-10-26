import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.Entities.Admin;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.EntityRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<Post> postRepository;
    private EntityRepository<Account> accountRepository;
    private EntityRepository<Board> boardRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        postRepository = new PostRepository(database);
        accountRepository = new AccountRepository(database);
        boardRepository = new BoardRepository(database, redisCache);
    }

    @Test
    void create_ShouldSavePostByUser() {
        User user = new User("user@example.com", "password123");
        accountRepository.create(user);

        Board board = new Board("Test Board");
        boardRepository.create(board);

        Post post = new Post("Test Post content", user.getId(), board.getId());
        postRepository.create(post);

        Post retrievedPost = postRepository.getById(post.getId());
        assertNotNull(retrievedPost);
        assertEquals("Test Post content", retrievedPost.getContent());
        assertEquals(user.getId(), retrievedPost.getCreatorId());
        assertEquals(board.getId(), retrievedPost.getBoardId());
    }

    @Test
    void create_ShouldSavePostByAdmin() {
        Admin admin = new Admin("admin@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Admin Test Board");
        boardRepository.create(board);

        Post post = new Post("Admin Post content", admin.getId(), board.getId());
        postRepository.create(post);

        Post retrievedPost = postRepository.getById(post.getId());
        assertNotNull(retrievedPost);
        assertEquals("Admin Post content", retrievedPost.getContent());
        assertEquals(admin.getId(), retrievedPost.getCreatorId());
        assertEquals(board.getId(), retrievedPost.getBoardId());
    }

    @Test
    void getById_ShouldReturnPost() {
        User user = new User("user2@example.com", "password123");
        accountRepository.create(user);

        Board board = new Board("Another Board");
        boardRepository.create(board);

        Post post = new Post("Another Post content", user.getId(), board.getId());
        postRepository.create(post);

        Post retrievedPost = postRepository.getById(post.getId());
        assertNotNull(retrievedPost);
        assertEquals("Another Post content", retrievedPost.getContent());
    }

    @Test
    void getAll_ShouldReturnListOfPosts() {
        User user = new User("user3@example.com", "password123");
        accountRepository.create(user);

        Admin admin = new Admin("admin3@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Board for Posts");
        boardRepository.create(board);

        Post post1 = new Post("Post 1 content", user.getId(), board.getId());
        Post post2 = new Post("Post 2 content", admin.getId(), board.getId());

        postRepository.create(post1);
        postRepository.create(post2);

        List<Post> posts = postRepository.getAll();
        assertFalse(posts.isEmpty());
        assertTrue(posts.stream().anyMatch(post -> post.getCreatorId().equals(user.getId())));
        assertTrue(posts.stream().anyMatch(post -> post.getCreatorId().equals(admin.getId())));
    }

    @Test
    void update_ShouldUpdatePostByUser() {
        User user = new User("user4@example.com", "password123");
        accountRepository.create(user);

        Board board = new Board("Board to Update");
        boardRepository.create(board);

        Post post = new Post("Original Post content", user.getId(), board.getId());
        postRepository.create(post);

        post.setContent("Updated Post content");
        postRepository.update(post);

        Post updatedPost = postRepository.getById(post.getId());
        assertEquals("Updated Post content", updatedPost.getContent());
    }

    @Test
    void update_ShouldUpdatePostByAdmin() {
        Admin admin = new Admin("admin4@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Admin Board to Update");
        boardRepository.create(board);

        Post post = new Post("Original Admin Post content", admin.getId(), board.getId());
        postRepository.create(post);

        post.setContent("Updated Admin Post content");
        postRepository.update(post);

        Post updatedPost = postRepository.getById(post.getId());
        assertEquals("Updated Admin Post content", updatedPost.getContent());
    }

    @Test
    void delete_ShouldDeletePostByUser() {
        User user = new User("user5@example.com", "password123");
        accountRepository.create(user);

        Board board = new Board("Board to Delete");
        boardRepository.create(board);

        Post post = new Post("Post to delete", user.getId(), board.getId());
        postRepository.create(post);

        postRepository.delete(post);

        Post deletedPost = postRepository.getById(post.getId());
        assertNull(deletedPost);
    }

    @Test
    void delete_ShouldDeletePostByAdmin() {
        Admin admin = new Admin("admin5@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Admin Board to Delete");
        boardRepository.create(board);

        Post post = new Post("Admin Post to delete", admin.getId(), board.getId());
        postRepository.create(post);

        postRepository.delete(post);

        Post deletedPost = postRepository.getById(post.getId());
        assertNull(deletedPost);
    }
}
