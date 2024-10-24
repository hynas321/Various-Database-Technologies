package org.example.Services.Interfaces;

import org.bson.types.ObjectId;
import org.example.Entities.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    Post createPost(ObjectId userId, ObjectId boardId, String content);
    boolean deletePost(ObjectId postId, ObjectId userId);
    boolean updatePost(Post post, ObjectId userId);
    Post getPostById(ObjectId postId);
    List<Post> getAllPosts();
}