package org.example.Redis;

import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.example.Repositories.EntityRepository;

import java.util.List;

public class RedisBoardDecorator implements EntityRepository<Board> {
    private final EntityRepository<Board> wrappedRepository;
    private final RedisCache redisCache;
    private static final int CACHE_EXPIRATION = 1800;
    private static final String BOARD_CACHE_KEY = "boards";

    public RedisBoardDecorator(EntityRepository<Board> wrappedRepository, RedisCache redisCache) {
        this.wrappedRepository = wrappedRepository;
        this.redisCache = redisCache;
    }

    @Override
    public void create(Board board) {
        wrappedRepository.create(board);
        cacheBoard(board);
        redisCache.deleteCache(BOARD_CACHE_KEY);
    }

    @Override
    public Board getById(ObjectId id) {
        String cacheKey = "board:" + id.toString();
        if (redisCache.isConnected()) {
            Board cachedBoard = redisCache.getCachedData(cacheKey, Board.class);
            if (cachedBoard != null) {
                return cachedBoard;
            }
        }

        Board board = wrappedRepository.getById(id);
        if (board != null && redisCache.isConnected()) {
            cacheBoard(board);
        }

        return board;
    }

    @Override
    public List<Board> getAll() {
        if (redisCache.isConnected()) {
            List<Board> cachedBoards = redisCache.getCachedDataAsList(BOARD_CACHE_KEY, Board.class);
            if (cachedBoards != null) {
                return cachedBoards;
            }
        }

        List<Board> boards = wrappedRepository.getAll();
        if (!boards.isEmpty() && redisCache.isConnected()) {
            redisCache.cacheData(BOARD_CACHE_KEY, boards, CACHE_EXPIRATION);
        }

        return boards;
    }

    @Override
    public void update(Board board) {
        wrappedRepository.update(board);
        cacheBoard(board);
        redisCache.deleteCache(BOARD_CACHE_KEY);
    }

    @Override
    public void delete(Board board) {
        wrappedRepository.delete(board);
        String cacheKey = "board:" + board.getId().toString();
        redisCache.deleteCache(cacheKey);
        redisCache.deleteCache(BOARD_CACHE_KEY);
    }

    private void cacheBoard(Board board) {
        String cacheKey = "board:" + board.getId().toString();
        redisCache.cacheData(cacheKey, board, CACHE_EXPIRATION);
    }
}
