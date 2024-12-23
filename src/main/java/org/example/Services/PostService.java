package org.example.Services;

import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.IPostService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PostService implements IPostService {
    private final EntityRepository<Post> postRepository;
    private final EntityRepository<Account> accountRepository;
    private final EntityRepository<Board> boardRepository;
    private final Session session;

    public PostService(EntityRepository<Post> postRepository, EntityRepository<Account> accountRepository, EntityRepository<Board> boardRepository, Session session) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.boardRepository = boardRepository;
        this.session = session;
    }

    @Override
    public Optional<Post> createPost(Long accountId, Long boardId, String content) {
        Transaction transaction = session.beginTransaction();

        try {
            Account account = accountRepository.getById(accountId);
            Board board = boardRepository.getById(boardId);

            if (account == null || board == null || !board.getMembers().contains(account)) {
                transaction.rollback();
                return Optional.empty();
            }

            Post post = new Post(content, account, board);
            postRepository.create(post);

            board.getPosts().add(post);
            boardRepository.update(board);

            transaction.commit();
            return Optional.of(post);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deletePost(Long postId, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Post post = postRepository.getById(postId);
            Account account = accountRepository.getById(accountId);

            if (post == null || account == null || (!post.getCreator().getId().equals(accountId))) {
                transaction.rollback();
                return false;
            }

            postRepository.delete(post);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePost(Post post, Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Post existingPost = postRepository.getById(post.getId());
            Account account = accountRepository.getById(accountId);

            if (existingPost == null || account == null || (!existingPost.getCreator().getId().equals(accountId))) {
                transaction.rollback();
                return false;
            }

            existingPost.setContent(post.getContent());
            postRepository.update(existingPost);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return Optional.ofNullable(postRepository.getById(postId));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }
}
