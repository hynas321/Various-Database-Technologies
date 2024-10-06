package org.example.Services.Interfaces;

import org.example.Entities.Comment;

import java.util.List;

public interface ICommentService {
    Comment addComment(Long postId, Long userId, String content);
    void deleteComment(Long commentId, Long userId);
    Comment getCommentById(Long commentId);
    List<Comment> getAllComments();
    void updateComment(Comment comment, Long userId);
}
