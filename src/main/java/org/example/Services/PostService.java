package org.example.Services;

import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.IPostService;

import java.util.List;
import java.util.Optional;

public class PostService implements IPostService {
    private final EntityRepository<Post> postRepository;
    private final EntityRepository<User> userRepository;
    private final EntityRepository<Board> boardRepository;

    public PostService(EntityRepository<Post> postRepository, EntityRepository<User> userRepository, EntityRepository<Board> boardRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public Optional<Post> createPost(Long userId, Long boardId, String content) {
        if (userId == null || boardId == null || content == null || content.isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.getById(userId);
        Board board = boardRepository.getById(boardId);

        if (user == null || board == null || !board.getMembers().contains(user)) {
            return Optional.empty();
        }

        Post post = new Post(content, user, board);
        postRepository.create(post);

        board.getPosts().add(post);
        boardRepository.update(board);

        return Optional.of(post);
    }

    @Override
    public boolean deletePost(Long postId, Long userId) {
        if (postId == null || userId == null) {
            return false;
        }

        Post post = postRepository.getById(postId);

        if (post == null || !post.getCreator().getId().equals(userId)) {
            return false;
        }

        postRepository.delete(post);
        return true;
    }

    @Override
    public boolean updatePost(Post post, Long userId) {
        if (post == null || post.getId() == null || userId == null) {
            return false;
        }

        Post existingPost = postRepository.getById(post.getId());

        if (existingPost == null || !existingPost.getCreator().getId().equals(userId)) {
            return false;
        }

        existingPost.setContent(post.getContent());
        postRepository.update(existingPost);
        return true;
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        if (postId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(postRepository.getById(postId));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }
}
