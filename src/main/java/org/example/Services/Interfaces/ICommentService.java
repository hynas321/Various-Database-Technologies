package org.example.Services.Interfaces;

import org.example.Entities.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Optional<Comment> addComment(Long postId, Long userId, String content);
    boolean deleteComment(Long commentId, Long userId);
    boolean updateComment(Comment comment, Long userId);
    Optional<Comment> getCommentById(Long commentId);
    List<Comment> getAllComments();
}
