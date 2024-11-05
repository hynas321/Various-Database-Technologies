package org.example.Services.Interfaces;

import org.example.Entities.Post;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post createPost(UUID userId, UUID boardId, String content);
    boolean deletePost(UUID postId, UUID userId);
    boolean updatePost(Post post, UUID userId);
    Post getPostById(UUID postId);
    List<Post> getAllPosts();
}
