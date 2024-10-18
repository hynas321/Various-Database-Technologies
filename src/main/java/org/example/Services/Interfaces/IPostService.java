package org.example.Services.Interfaces;

import org.example.Entities.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    Post createPost(String userId, String boardId, String content);
    boolean deletePost(String postId, String userId);
    boolean updatePost(Post post, String userId);
    Post getPostById(String postId);
    List<Post> getAllPosts();
}