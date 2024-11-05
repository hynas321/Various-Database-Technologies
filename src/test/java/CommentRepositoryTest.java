import com.datastax.oss.driver.api.core.CqlIdentifier;
import org.example.Entities.Board;
import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.Account;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.CommentRepository;
import org.example.Repositories.EntityRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<Comment> commentRepository;
    private EntityRepository<Account> accountRepository;
    private EntityRepository<Post> postRepository;
    private EntityRepository<Board> boardRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        commentRepository = new CommentRepository(session, CqlIdentifier.fromCql("site"));
        accountRepository = new AccountRepository(session, CqlIdentifier.fromCql("site"));
        postRepository = new PostRepository(session, CqlIdentifier.fromCql("site"));
        boardRepository = new BoardRepository(session, CqlIdentifier.fromCql("site"));
    }

    @Test
    void create_ShouldSaveCommentByUser() {
        Account user = new Account("comment_user@example.com", "password123", Account.UserType.USER);
        accountRepository.create(user);

        Board board = new Board("Board for Post");
        boardRepository.create(board);

        Post post = new Post("Post for Comment", user.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment = new Comment("Test comment content", post.getId(), user.getUserId());
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals("Test comment content", retrievedComment.getContent());
        assertEquals(post.getId(), retrievedComment.getPostId());
        assertEquals(user.getUserId(), retrievedComment.getCreatorId());
    }

    @Test
    void create_ShouldSaveCommentByAdmin() {
        Account admin = new Account("admin_comment@example.com", "adminpassword123", Account.UserType.ADMIN);
        accountRepository.create(admin);

        Board board = new Board("Board for Admin Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Admin Comment", admin.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment = new Comment("Admin comment content", post.getId(), admin.getUserId());
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals("Admin comment content", retrievedComment.getContent());
        assertEquals(post.getId(), retrievedComment.getPostId());
        assertEquals(admin.getUserId(), retrievedComment.getCreatorId());
    }

    @Test
    void getById_ShouldReturnComment() {
        Account user = new Account("comment_user2@example.com", "password123", Account.UserType.USER);
        accountRepository.create(user);

        Board board = new Board("Board for Another Post");
        boardRepository.create(board);

        Post post = new Post("Another Post for Comment", user.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment = new Comment("Another comment content", post.getId(), user.getUserId());
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals("Another comment content", retrievedComment.getContent());
    }

    @Test
    void getAll_ShouldReturnListOfComments() {
        Account user = new Account("comment_user3@example.com", "password123", Account.UserType.USER);
        accountRepository.create(user);

        Account admin = new Account("admin_user3@example.com", "adminpassword123", Account.UserType.ADMIN);
        accountRepository.create(admin);

        Board board = new Board("Board for Multiple Comments");
        boardRepository.create(board);

        Post post = new Post("Post for Multiple Comments", user.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment1 = new Comment("Comment 1 content", post.getId(), user.getUserId());
        Comment comment2 = new Comment("Comment 2 content", post.getId(), admin.getUserId());

        commentRepository.create(comment1);
        commentRepository.create(comment2);

        List<Comment> comments = commentRepository.getAll();
        assertFalse(comments.isEmpty());
        assertTrue(comments.stream().anyMatch(comment -> comment.getCreatorId().equals(user.getUserId())));
        assertTrue(comments.stream().anyMatch(comment -> comment.getCreatorId().equals(admin.getUserId())));
    }

    @Test
    void update_ShouldUpdateCommentByUser() {
        Account user = new Account("comment_user4@example.com", "password123", Account.UserType.USER);
        accountRepository.create(user);

        Board board = new Board("Board for Updating Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Updating Comment", user.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment = new Comment("Original comment content", post.getId(), user.getUserId());
        commentRepository.create(comment);

        comment.setContent("Updated comment content");
        commentRepository.update(comment);

        Comment updatedComment = commentRepository.getById(comment.getId());
        assertEquals("Updated comment content", updatedComment.getContent());
    }

    @Test
    void delete_ShouldDeleteCommentByUser() {
        Account user = new Account("comment_user5@example.com", "password123", Account.UserType.USER);
        accountRepository.create(user);

        Board board = new Board("Board for Deleting Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Deleting Comment", user.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment = new Comment("Comment to delete", post.getId(), user.getUserId());
        commentRepository.create(comment);

        commentRepository.delete(comment);

        Comment deletedComment = commentRepository.getById(comment.getId());
        assertNull(deletedComment);
    }

    @Test
    void delete_ShouldDeleteCommentByAdmin() {
        Account admin = new Account("admin_user5@example.com", "adminpassword123", Account.UserType.ADMIN);
        accountRepository.create(admin);

        Board board = new Board("Board for Deleting Admin Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Deleting Admin Comment", admin.getUserId(), board.getId());
        postRepository.create(post);

        Comment comment = new Comment("Admin comment to delete", post.getId(), admin.getUserId());
        commentRepository.create(comment);

        commentRepository.delete(comment);

        Comment deletedComment = commentRepository.getById(comment.getId());
        assertNull(deletedComment);
    }
}
