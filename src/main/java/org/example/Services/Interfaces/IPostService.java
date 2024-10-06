package org.example.Services.Interfaces;

import org.example.Entities.Post;

import java.util.List;

public interface IPostService {
    Post createPost(Long userId, Long boardId, String content);
    void deletePost(Long postId, Long userId);
    Post getPostById(Long postId);
    List<Post> getAllPosts();
    void updatePost(Post post, Long userId);
}
