import org.example.Entities.Board;
import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.CommentRepository;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.UserRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<Comment> commentRepository;
    private EntityRepository<User> userRepository;
    private EntityRepository<Post> postRepository;
    private EntityRepository<Board> boardRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        commentRepository = new CommentRepository(session);
        userRepository = new UserRepository(session);
        postRepository = new PostRepository(session);
        boardRepository = new BoardRepository(session);
    }

    @Test
    void create_ShouldSaveComment() {
        User user = new User("comment_user@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Post");
        boardRepository.create(board);

        Post post = new Post("Post for Comment", user, board);
        postRepository.create(post);

        Comment comment = new Comment("Test comment content", post, user);
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals("Test comment content", retrievedComment.getContent());
        assertEquals(post.getId(), retrievedComment.getPost().getId());
        assertEquals(user.getId(), retrievedComment.getCreator().getId());
    }

    @Test
    void getById_ShouldReturnComment() {
        User user = new User("comment_user2@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Another Post");
        boardRepository.create(board);

        Post post = new Post("Another Post for Comment", user, board);
        postRepository.create(post);

        Comment comment = new Comment("Another comment content", post, user);
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals("Another comment content", retrievedComment.getContent());
    }

    @Test
    void getAll_ShouldReturnListOfComments() {
        User user = new User("comment_user3@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Multiple Comments");
        boardRepository.create(board);

        Post post = new Post("Post for Multiple Comments", user, board);
        postRepository.create(post);

        Comment comment1 = new Comment("Comment 1 content", post, user);
        Comment comment2 = new Comment("Comment 2 content", post, user);

        commentRepository.create(comment1);
        commentRepository.create(comment2);

        List<Comment> comments = commentRepository.getAll();
        assertFalse(comments.isEmpty());
    }

    @Test
    void update_ShouldUpdateComment() {
        User user = new User("comment_user4@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Updating Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Updating Comment", user, board);
        postRepository.create(post);

        Comment comment = new Comment("Original comment content", post, user);
        commentRepository.create(comment);

        comment.setContent("Updated comment content");
        commentRepository.update(comment);

        Comment updatedComment = commentRepository.getById(comment.getId());
        assertEquals("Updated comment content", updatedComment.getContent());
    }

    @Test
    void delete_ShouldDeleteComment() {
        User user = new User("comment_user5@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Deleting Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Deleting Comment", user, board);
        postRepository.create(post);

        Comment comment = new Comment("Comment to delete", post, user);
        commentRepository.create(comment);

        commentRepository.delete(comment);

        Comment deletedComment = commentRepository.getById(comment.getId());
        assertNull(deletedComment);
    }

    @Test
    void versionField_ShouldIncrementOnEachUpdate_ForComment() {
        Session createSession = sessionFactory.openSession();
        createSession.beginTransaction();
        User user = new User("version_test_user@example.com", "password123");
        userRepository.create(user);

        Board board = new Board("Board for Version Test");
        boardRepository.create(board);

        Post post = new Post("Post for Versioning", user, board);
        postRepository.create(post);

        Comment comment = new Comment("Initial comment", post, user);
        commentRepository.create(comment);
        createSession.getTransaction().commit();
        createSession.close();

        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        Comment retrievedComment = session1.get(Comment.class, comment.getId());
        int initialVersion = retrievedComment.getVersion();
        session1.getTransaction().commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        retrievedComment.setContent("Updated by first transaction");
        session2.merge(retrievedComment);
        session2.getTransaction().commit();

        session2.beginTransaction();
        Comment updatedComment = session2.get(Comment.class, retrievedComment.getId());
        int firstUpdatedVersion = updatedComment.getVersion();
        session2.getTransaction().commit();
        session2.close();

        assertTrue(firstUpdatedVersion > initialVersion, "Version should increment after the first update");

        Session session3 = sessionFactory.openSession();
        session3.beginTransaction();
        updatedComment.setContent("Updated by second transaction");
        session3.merge(updatedComment);
        session3.getTransaction().commit();

        session3.beginTransaction();
        Comment secondUpdatedComment = session3.get(Comment.class, updatedComment.getId());
        int secondUpdatedVersion = secondUpdatedComment.getVersion();
        session3.getTransaction().commit();
        session3.close();

        assertTrue(secondUpdatedVersion > firstUpdatedVersion, "Version should increment after the second update");
    }
}
