package org.example.Services;

import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.GenericRepository;
import org.example.Services.Interfaces.ICommentService;

import java.util.List;

public class CommentService implements ICommentService {
    private final GenericRepository<Comment> commentRepository;
    private final GenericRepository<Post> postRepository;
    private final GenericRepository<User> userRepository;

    public CommentService(GenericRepository<Comment> commentRepository, GenericRepository<Post> postRepository, GenericRepository<User> userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment addComment(Long postId, Long userId, String content) {
        Post post = postRepository.getById(postId);
        User user = userRepository.getById(userId);

        if (post == null) {
            throw new IllegalArgumentException("Post not found.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        Comment comment = new Comment(content, post, user);
        commentRepository.create(comment);
        return comment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.getById(commentId);

        if (comment == null) {
            throw new IllegalArgumentException("Comment not found.");
        }

        if (!comment.getCreator().getId().equals(userId)) {
            throw new IllegalArgumentException("Only the comment owner can delete the comment.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.getById(commentId);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.getAll();
    }

    @Override
    public void updateComment(Comment comment, Long userId) {
        Comment existingComment = commentRepository.getById(comment.getId());

        if (existingComment == null) {
            throw new IllegalArgumentException("Comment not found.");
        }

        if (!existingComment.getCreator().getId().equals(userId)) {
            throw new IllegalArgumentException("Only the comment owner can update the comment.");
        }

        existingComment.setContent(comment.getContent());
        commentRepository.update(existingComment);
    }
}
