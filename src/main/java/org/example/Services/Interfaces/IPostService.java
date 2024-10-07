package org.example.Services.Interfaces;

import org.example.Entities.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    Optional<Post> createPost(Long userId, Long boardId, String content);
    boolean deletePost(Long postId, Long userId);
    boolean updatePost(Post post, Long userId);
    Optional<Post> getPostById(Long postId);
    List<Post> getAllPosts();
}