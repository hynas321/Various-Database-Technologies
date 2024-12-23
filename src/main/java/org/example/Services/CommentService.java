package org.example.Services;

import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.ICommentService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CommentService implements ICommentService {
    private final EntityRepository<Comment> commentRepository;
    private final EntityRepository<Post> postRepository;
    private final EntityRepository<Account> accountRepository;
    private final Session session;

    public CommentService(EntityRepository<Comment> commentRepository, EntityRepository<Post> postRepository, EntityRepository<Account> accountRepository, Session session) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.session = session;
    }

    @Override
    public Optional<Comment> addComment(Long postId, Long accountId, String content) {
        Transaction transaction = session.beginTransaction();

        try {
            Post post = postRepository.getById(postId);
            Account account = accountRepository.getById(accountId);

            if (post == null || account == null) {
                transaction.rollback();
                return Optional.empty();
            }

            Comment comment = new Comment(content, post, account);
            commentRepository.create(comment);
            transaction.commit();
            return Optional.of(comment);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteComment(Long commentId, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Comment comment = commentRepository.getById(commentId);
            Account account = accountRepository.getById(accountId);

            if (comment == null || account == null || (!comment.getCreator().getId().equals(accountId))) {
                transaction.rollback();
                return false;
            }

            commentRepository.delete(comment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateComment(Comment comment, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Comment existingComment = commentRepository.getById(comment.getId());
            Account account = accountRepository.getById(accountId);

            if (existingComment == null || account == null || (!existingComment.getCreator().getId().equals(accountId))) {
                transaction.rollback();
                return false;
            }

            existingComment.setContent(comment.getContent());
            commentRepository.update(existingComment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        return Optional.ofNullable(commentRepository.getById(commentId));
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.getAll();
    }
}
