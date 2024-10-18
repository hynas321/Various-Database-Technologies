package org.example.Services.Interfaces;

import org.example.Entities.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Comment addComment(String postId, String userId, String content);
    boolean deleteComment(String commentId, String userId);
    boolean updateComment(Comment comment, String userId);
    Comment getCommentById(String commentId);
    List<Comment> getAllComments();
}
