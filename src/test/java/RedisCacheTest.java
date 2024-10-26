import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisCacheTest extends BaseRepositoryTest  {
    private Jedis jedis;

    @BeforeAll
    public void setup() {
        jedis = new Jedis("localhost", 6379);
        jedis.auth("master123");
    }

    @AfterEach
    public void clearCache() {
        jedis.flushDB();
    }

    @AfterAll
    public void tearDown() {
        jedis.close();
    }

    @Test
    public void testCacheDataAndRetrieve() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        String cacheKey = "board:" + board.getId().toString();

        redisCache.cacheData(cacheKey, board, 1800);
        Board cachedBoard = redisCache.getCachedData(cacheKey, Board.class);

        assertNotNull(cachedBoard);
        assertEquals(board.getId(), cachedBoard.getId());
    }

    @Test
    public void testCacheListDataAndRetrieve() {
        List<Board> boards = new ArrayList<>();
        Board board1 = new Board("Test Board 1");
        Board board2 = new Board("Test Board 2");

        board1.setId(new ObjectId());
        board2.setId(new ObjectId());
        boards.add(board1);
        boards.add(board2);

        redisCache.cacheData("boards", boards, 1800);
        List<Board> cachedBoards = redisCache.getCachedDataAsList("boards", Board.class);

        assertNotNull(cachedBoards);
        assertEquals(boards.size(), cachedBoards.size());
        assertEquals(boards.get(0).getId(), cachedBoards.get(0).getId());
    }

    @Test
    public void testCacheInvalidation() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        String cacheKey = "board:" + board.getId().toString();

        redisCache.cacheData(cacheKey, board, 1800);
        redisCache.deleteCache(cacheKey);

        assertNull(redisCache.getCachedData(cacheKey, Board.class));
    }
}
