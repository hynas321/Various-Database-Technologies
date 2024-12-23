import org.example.Entities.Board;
import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.Entities.Admin;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.CommentRepository;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        commentRepository = new CommentRepository(session);
        accountRepository = new AccountRepository(session);
        postRepository = new PostRepository(session);
        boardRepository = new BoardRepository(session);
    }

    @Test
    void create_ShouldSaveCommentByUser() {
        User user = new User("comment_user@example.com", "password123");
        accountRepository.create(user);

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
    void create_ShouldSaveCommentByAdmin() {
        Admin admin = new Admin("admin_comment@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Board for Admin Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Admin Comment", admin, board);
        postRepository.create(post);

        Comment comment = new Comment("Admin comment content", post, admin);
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals("Admin comment content", retrievedComment.getContent());
        assertEquals(post.getId(), retrievedComment.getPost().getId());
        assertEquals(admin.getId(), retrievedComment.getCreator().getId());
    }

    @Test
    void getById_ShouldReturnComment() {
        User user = new User("comment_user2@example.com", "password123");
        accountRepository.create(user);

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
        accountRepository.create(user);

        Admin admin = new Admin("admin_user3@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Board for Multiple Comments");
        boardRepository.create(board);

        Post post = new Post("Post for Multiple Comments", user, board);
        postRepository.create(post);

        Comment comment1 = new Comment("Comment 1 content", post, user);
        Comment comment2 = new Comment("Comment 2 content", post, admin);

        commentRepository.create(comment1);
        commentRepository.create(comment2);

        List<Comment> comments = commentRepository.getAll();
        assertFalse(comments.isEmpty());
        assertTrue(comments.stream().anyMatch(comment -> comment.getCreator() instanceof User));
        assertTrue(comments.stream().anyMatch(comment -> comment.getCreator() instanceof Admin));
    }

    @Test
    void update_ShouldUpdateCommentByUser() {
        User user = new User("comment_user4@example.com", "password123");
        accountRepository.create(user);

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
    void update_ShouldUpdateCommentByAdmin() {
        Admin admin = new Admin("admin_user4@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Board for Updating Admin Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Updating Admin Comment", admin, board);
        postRepository.create(post);

        Comment comment = new Comment("Original admin comment content", post, admin);
        commentRepository.create(comment);

        comment.setContent("Updated admin comment content");
        commentRepository.update(comment);

        Comment updatedComment = commentRepository.getById(comment.getId());
        assertEquals("Updated admin comment content", updatedComment.getContent());
    }

    @Test
    void delete_ShouldDeleteCommentByUser() {
        User user = new User("comment_user5@example.com", "password123");
        accountRepository.create(user);

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
    void delete_ShouldDeleteCommentByAdmin() {
        Admin admin = new Admin("admin_user5@example.com", "adminpassword123");
        accountRepository.create(admin);

        Board board = new Board("Board for Deleting Admin Comment");
        boardRepository.create(board);

        Post post = new Post("Post for Deleting Admin Comment", admin, board);
        postRepository.create(post);

        Comment comment = new Comment("Admin comment to delete", post, admin);
        commentRepository.create(comment);

        commentRepository.delete(comment);

        Comment deletedComment = commentRepository.getById(comment.getId());
        assertNull(deletedComment);
    }
}
