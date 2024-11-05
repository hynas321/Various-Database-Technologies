package org.example.Services;

import org.example.Entities.Post;
import org.example.Repositories.EntityRepository;
import org.example.Services.Interfaces.IPostService;

import java.util.List;
import java.util.UUID;

public class PostService implements IPostService {
    private final EntityRepository<Post> postRepository;

    public PostService(EntityRepository<Post> postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(UUID userId, UUID boardId, String content) {
        try {
            Post post = new Post(content, userId, boardId);
            postRepository.create(post);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deletePost(UUID postId, UUID userId) {
        try {
            Post post = postRepository.getById(postId);
            if (post == null || !post.getCreatorId().equals(userId)) {
                return false;
            }

            postRepository.delete(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePost(Post post, UUID userId) {
        try {
            Post existingPost = postRepository.getById(post.getId());
            if (existingPost == null || !existingPost.getCreatorId().equals(userId)) {
                return false;
            }

            postRepository.update(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Post getPostById(UUID postId) {
        return postRepository.getById(postId);
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            return postRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
