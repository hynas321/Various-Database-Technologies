package org.example;

import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.CommentRepository;
import org.example.Repositories.PostRepository;
import org.example.Repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CommentRepositoryTest extends BaseRepositoryTest {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        commentRepository = new CommentRepository(session);
        postRepository = new PostRepository(session);
        userRepository = new UserRepository(session);
    }

    @Test
    public void testCreateAndGetById() {
        User user = new User("testuser@example.com", "password123");
        userRepository.create(user);

        Post post = new Post("Test Post", user, null);
        postRepository.create(post);

        Comment comment = new Comment("Test Comment", post, user);
        commentRepository.create(comment);

        Comment retrievedComment = commentRepository.getById(comment.getId());
        Assertions.assertNotNull(retrievedComment);
        Assertions.assertEquals("Test Comment", retrievedComment.getContent());
        Assertions.assertEquals(user.getId(), retrievedComment.getCreator().getId());
    }

    @Test
    public void testGetAll() {
        User user = new User("testuser@example.com", "password123");
        userRepository.create(user);

        Post post = new Post("Test Post", user, null);
        postRepository.create(post);

        Comment comment1 = new Comment("Comment 1", post, user);
        Comment comment2 = new Comment("Comment 2", post, user);
        commentRepository.create(comment1);
        commentRepository.create(comment2);

        List<Comment> comments = commentRepository.getAll();
        Assertions.assertEquals(2, comments.size());
    }

    @Test
    public void testUpdate() {
        User user = new User("testuser@example.com", "password123");
        userRepository.create(user);

        Post post = new Post("Test Post", user, null);
        postRepository.create(post);

        Comment comment = new Comment("Initial Comment", post, user);
        commentRepository.create(comment);

        comment.setContent("Updated Comment");
        commentRepository.update(comment);

        Comment updatedComment = commentRepository.getById(comment.getId());
        Assertions.assertEquals("Updated Comment", updatedComment.getContent());
    }

    @Test
    public void testDelete() {
        User user = new User("testuser@example.com", "password123");
        userRepository.create(user);

        Post post = new Post("Test Post", user, null);
        postRepository.create(post);

        Comment comment = new Comment("To Be Deleted", post, user);
        commentRepository.create(comment);

        commentRepository.delete(comment);

        Comment deletedComment = commentRepository.getById(comment.getId());
        Assertions.assertNull(deletedComment);
    }
}
