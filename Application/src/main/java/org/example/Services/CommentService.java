package org.example.Services;

import org.bson.types.ObjectId;
import org.example.Entities.Comment;
import org.example.Repositories.EntityRepository;
import org.example.Services.Interfaces.ICommentService;

import java.util.List;

public class CommentService implements ICommentService {
    private final EntityRepository<Comment> commentRepository;

    public CommentService(EntityRepository<Comment> commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(ObjectId postId, ObjectId userId, String content) {
        try {
            Comment comment = new Comment(content, postId, userId);
            commentRepository.create(comment);
            return comment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteComment(ObjectId commentId, ObjectId userId) {
        try {
            Comment comment = commentRepository.getById(commentId);
            if (comment == null || !comment.getCreatorId().equals(userId)) {
                return false;
            }

            commentRepository.delete(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateComment(Comment comment, ObjectId userId) {
        try {
            Comment existingComment = commentRepository.getById(comment.getId());
            if (existingComment == null || !existingComment.getCreatorId().equals(userId)) {
                return false;
            }

            commentRepository.update(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Comment getCommentById(ObjectId commentId) {
        return commentRepository.getById(commentId);
    }

    @Override
    public List<Comment> getAllComments() {
        try {
            return commentRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
