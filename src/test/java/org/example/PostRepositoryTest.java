package org.example;

import org.example.Entities.Post;
import org.example.Repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PostRepositoryTest extends BaseRepositoryTest {
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        postRepository = new PostRepository(session);
    }

    @Test
    public void testCreateAndGetById() {
        Post post = new Post("Test Post", null, null);
        postRepository.create(post);

        Post retrievedPost = postRepository.getById(post.getId());
        Assertions.assertNotNull(retrievedPost);
        Assertions.assertEquals("Test Post", retrievedPost.getContent());
    }

    @Test
    public void testGetAll() {
        Post post1 = new Post("Post 1", null, null);
        Post post2 = new Post("Post 2", null, null);

        postRepository.create(post1);
        postRepository.create(post2);

        List<Post> posts = postRepository.getAll();
        Assertions.assertEquals(2, posts.size());
    }

    @Test
    public void testUpdate() {
        Post post = new Post("Initial Post", null, null);
        postRepository.create(post);

        post.setContent("Updated Post");
        postRepository.update(post);

        Post updatedPost = postRepository.getById(post.getId());
        Assertions.assertEquals("Updated Post", updatedPost.getContent());
    }

    @Test
    public void testDelete() {
        Post post = new Post("To Be Deleted", null, null);
        postRepository.create(post);

        postRepository.delete(post);
        Post deletedPost = postRepository.getById(post.getId());

        Assertions.assertNull(deletedPost);
    }
}
