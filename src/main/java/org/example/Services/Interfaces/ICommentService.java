package org.example.Services.Interfaces;

import org.example.Entities.Comment;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
    Comment addComment(UUID postId, UUID userId, String content);
    boolean deleteComment(UUID commentId, UUID userId);
    boolean updateComment(Comment comment, UUID userId);
    Comment getCommentById(UUID commentId);
    List<Comment> getAllComments();
}