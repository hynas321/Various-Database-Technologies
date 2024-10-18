package org.example.Services;

import org.example.Entities.Post;
import org.example.Repositories.EntityRepository;
import org.example.Services.Interfaces.IPostService;

import java.util.List;

public class PostService implements IPostService {
    private final EntityRepository<Post> postRepository;

    public PostService(EntityRepository<Post> postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(String userId, String boardId, String content) {
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
    public boolean deletePost(String postId, String userId) {
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
    public boolean updatePost(Post post, String userId) {
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
    public Post getPostById(String postId) {
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
