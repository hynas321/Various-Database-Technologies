package org.example.Services;

import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Repositories.Interfaces.GenericRepository;
import org.example.Services.Interfaces.ICommentService;

import java.util.List;

public class CommentService implements ICommentService {
    private final GenericRepository<Comment> commentRepository;
    private final GenericRepository<Post> postRepository;

    public CommentService(GenericRepository<Comment> commentRepository, GenericRepository<Post> postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment addComment(Long postId, String content) {
        Post post = postRepository.getById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post not found.");
        }

        Comment comment = new Comment(content, post);
        commentRepository.create(comment);
        return comment;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.getById(commentId);
        if (comment != null) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("Comment not found.");
        }
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
    public void updateComment(Comment comment) {
        commentRepository.update(comment);
    }
}
