package org.example.Services;

import org.example.Entities.Board;
import org.example.Entities.Post;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.GenericRepository;
import org.example.Services.Interfaces.IPostService;

import java.util.List;

public class PostService implements IPostService {
    private final GenericRepository<Post> postRepository;
    private final GenericRepository<User> userRepository;
    private final GenericRepository<Board> boardRepository;

    public PostService(GenericRepository<Post> postRepository, GenericRepository<User> userRepository, GenericRepository<Board> boardRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public Post createPost(Long userId, Long boardId, String content) {
        User user = userRepository.getById(userId);
        Board board = boardRepository.getById(boardId);

        if (user == null || board == null) {
            throw new IllegalArgumentException("User or Board not found.");
        }

        if (!board.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is not a member of this board.");
        }

        Post post = new Post(content, user, board);
        postRepository.create(post);

        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.getById(postId);

        if (post != null) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("Post not found.");
        }
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.getById(postId);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }

    @Override
    public void updatePost(Post post) {
        postRepository.update(post);
    }
}
