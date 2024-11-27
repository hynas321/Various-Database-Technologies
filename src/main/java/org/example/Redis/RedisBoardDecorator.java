package org.example.Redis;

import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.example.Repositories.EntityRepository;

import java.util.List;

public class RedisBoardDecorator implements EntityRepository<Board> {
    private final EntityRepository<Board> wrappedRepository;
    private final RedisCache redisCache;
    private static final int CACHE_EXPIRATION = 1800;
    private static final String BOARD_CACHE_KEY = "board:";

    public RedisBoardDecorator(EntityRepository<Board> wrappedRepository, RedisCache redisCache) {
        this.wrappedRepository = wrappedRepository;
        this.redisCache = redisCache;
    }

    @Override
    public void create(Board board) {
        wrappedRepository.create(board);
        redisCache.cacheData(BOARD_CACHE_KEY + board.getId().toString(), board, CACHE_EXPIRATION);
    }

    @Override
    public Board getById(ObjectId id) {
        if (redisCache.isConnected()) {
            Board cachedBoard = redisCache.getCachedData(BOARD_CACHE_KEY + id.toString(), Board.class);
            if (cachedBoard != null) {
                return cachedBoard;
            }
        }

        Board board = wrappedRepository.getById(id);
        if (board != null && redisCache.isConnected()) {
            redisCache.cacheData(BOARD_CACHE_KEY + board.getId().toString(), board, CACHE_EXPIRATION);
        }

        return board;
    }

    @Override
    public void update(Board board) {
        wrappedRepository.update(board);
        redisCache.deleteCache(BOARD_CACHE_KEY + board.getId());
        redisCache.cacheData(BOARD_CACHE_KEY + board.getId().toString(), board, CACHE_EXPIRATION);
    }

    @Override
    public void delete(Board board) {
        wrappedRepository.delete(board);
        redisCache.deleteCache(BOARD_CACHE_KEY + board.getId().toString());
    }

    @Override
    public List<Board> getAll() {
        //Brak implementacji
        return null;
    }
}
