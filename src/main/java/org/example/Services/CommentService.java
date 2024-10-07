package org.example.Services;

import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.ICommentService;

import java.util.List;
import java.util.Optional;

public class CommentService implements ICommentService {
    private final EntityRepository<Comment> commentRepository;
    private final EntityRepository<Post> postRepository;
    private final EntityRepository<User> userRepository;

    public CommentService(EntityRepository<Comment> commentRepository, EntityRepository<Post> postRepository, EntityRepository<User> userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Comment> addComment(Long postId, Long userId, String content) {
        if (postId == null || userId == null || content == null || content.isEmpty()) {
            return Optional.empty();
        }

        Post post = postRepository.getById(postId);
        User user = userRepository.getById(userId);

        if (post == null || user == null) {
            return Optional.empty();
        }

        Comment comment = new Comment(content, post, user);
        commentRepository.create(comment);
        return Optional.of(comment);
    }

    @Override
    public boolean deleteComment(Long commentId, Long userId) {
        if (commentId == null || userId == null) {
            return false;
        }

        Comment comment = commentRepository.getById(commentId);

        if (comment == null || !comment.getCreator().getId().equals(userId)) {
            return false;
        }

        commentRepository.delete(comment);
        return true;
    }

    @Override
    public boolean updateComment(Comment comment, Long userId) {
        if (comment == null || comment.getId() == null || userId == null) {
            return false;
        }

        Comment existingComment = commentRepository.getById(comment.getId());

        if (existingComment == null || !existingComment.getCreator().getId().equals(userId)) {
            return false;
        }

        existingComment.setContent(comment.getContent());
        commentRepository.update(existingComment);
        return true;
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        if (commentId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(commentRepository.getById(commentId));
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.getAll();
    }
}
