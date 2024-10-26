package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.example.Redis.RedisCache;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BoardRepository implements EntityRepository<Board> {
    private final MongoCollection<Board> collection;
    private final RedisCache redisCache;
    private static final int CACHE_EXPIRATION = 1800;
    private static final String BOARD_CACHE_KEY = "boards";

    public BoardRepository(MongoDatabase database, RedisCache redisCache) {
        this.collection = database.getCollection("boards", Board.class);
        this.redisCache = redisCache;
    }

    @Override
    public void create(Board board) {
        collection.insertOne(board);

        String cacheKey = "board:" + board.getId().toString();
        redisCache.cacheData(cacheKey, board, CACHE_EXPIRATION);

        redisCache.deleteCache(BOARD_CACHE_KEY);
    }

    @Override
    public Board getById(ObjectId id) {
        String cacheKey = "board:" + id.toString();
        Board board = null;

        if (redisCache.isConnected()) {
            board = redisCache.getCachedData(cacheKey, Board.class);
        }

        if (board == null) {
            board = collection.find(eq("_id", id)).first();
            if (board != null) {
                redisCache.cacheData(cacheKey, board, CACHE_EXPIRATION);
            }
        }
        return board;
    }

    @Override
    public List<Board> getAll() {
        List<Board> boards = null;

        if (redisCache.isConnected()) {
            boards = redisCache.getCachedDataAsList(BOARD_CACHE_KEY, Board.class);
        }

        if (boards == null) {
            boards = new ArrayList<>();
            collection.find().into(boards);
            if (!boards.isEmpty()) {
                redisCache.cacheData(BOARD_CACHE_KEY, boards, CACHE_EXPIRATION);
            }
        }
        return boards;
    }

    @Override
    public void update(Board board) {
        collection.replaceOne(eq("_id", board.getId()), board);

        String cacheKey = "board:" + board.getId().toString();
        redisCache.cacheData(cacheKey, board, CACHE_EXPIRATION);

        redisCache.deleteCache(BOARD_CACHE_KEY);
    }

    @Override
    public void delete(Board board) {
        collection.deleteOne(eq("_id", board.getId()));

        String cacheKey = "board:" + board.getId().toString();
        redisCache.deleteCache(cacheKey);

        redisCache.deleteCache(BOARD_CACHE_KEY);
    }
}
