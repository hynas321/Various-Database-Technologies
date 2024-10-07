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

class PostRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<Post> postRepository;
    private EntityRepository<User> userRepository;
    private EntityRepository<Board> boardRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        postRepository = new PostRepository(session);
        userRepository = new UserRepository(session);
        boardRepository = new BoardRepository(session);
    }

    @Test
    void create_ShouldSavePost() {
        User user = new User("user@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Test Board");
        boardRepository.create(board);

        Post post = new Post("Test Post content", user, board);
        postRepository.create(post);

        Post retrievedPost = postRepository.getById(post.getId());
        assertNotNull(retrievedPost);
        assertEquals("Test Post content", retrievedPost.getContent());
        assertEquals(user.getId(), retrievedPost.getCreator().getId());
        assertEquals(board.getId(), retrievedPost.getBoard().getId());
    }

    @Test
    void getById_ShouldReturnPost() {
        User user = new User("user2@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Another Board");
        boardRepository.create(board);

        Post post = new Post("Another Post content", user, board);
        postRepository.create(post);

        Post retrievedPost = postRepository.getById(post.getId());
        assertNotNull(retrievedPost);
        assertEquals("Another Post content", retrievedPost.getContent());
    }

    @Test
    void getAll_ShouldReturnListOfPosts() {
        User user = new User("user3@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Posts");
        boardRepository.create(board);

        Post post1 = new Post("Post 1 content", user, board);
        Post post2 = new Post("Post 2 content", user, board);

        postRepository.create(post1);
        postRepository.create(post2);

        List<Post> posts = postRepository.getAll();
        assertFalse(posts.isEmpty());
    }

    @Test
    void update_ShouldUpdatePost() {
        User user = new User("user4@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board to Update");
        boardRepository.create(board);

        Post post = new Post("Original Post content", user, board);
        postRepository.create(post);

        post.setContent("Updated Post content");
        postRepository.update(post);

        Post updatedPost = postRepository.getById(post.getId());
        assertEquals("Updated Post content", updatedPost.getContent());
    }

    @Test
    void delete_ShouldDeletePost() {
        User user = new User("user5@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board to Delete");
        boardRepository.create(board);

        Post post = new Post("Post to delete", user, board);
        postRepository.create(post);

        postRepository.delete(post);

        Post deletedPost = postRepository.getById(post.getId());
        assertNull(deletedPost);
    }
}
