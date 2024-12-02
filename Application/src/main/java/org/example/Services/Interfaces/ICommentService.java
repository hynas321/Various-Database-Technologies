package org.example.Services.Interfaces;

import org.bson.types.ObjectId;
import org.example.Entities.Comment;

import java.util.List;

public interface ICommentService {
    Comment addComment(ObjectId postId, ObjectId userId, String content);
    boolean deleteComment(ObjectId commentId, ObjectId userId);
    boolean updateComment(Comment comment, ObjectId userId);
    Comment getCommentById(ObjectId commentId);
    List<Comment> getAllComments();
}
