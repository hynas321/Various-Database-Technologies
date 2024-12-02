import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.example.Redis.RedisBoardDecorator;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.EntityRepository;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisBoardDecoratorTest extends BaseRepositoryTest {
    private Jedis jedis;
    private RedisBoardDecorator redisBoardDecorator;
    private static final String BOARD_CACHE_KEY = "board:";

    @BeforeEach
    public void setup() {
        jedis = new Jedis("localhost", 6379);
        jedis.auth("master123");
        EntityRepository<Board> repository = new BoardRepository(database);
        redisBoardDecorator = new RedisBoardDecorator(repository, redisCache);
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
    public void testCreateCachesData() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        redisBoardDecorator.create(board);
        Board cachedBoard = redisCache.getCachedData(BOARD_CACHE_KEY + board.getId().toString(), Board.class);

        assertNotNull(cachedBoard);
        assertEquals(board.getId(), cachedBoard.getId());
    }

    @Test
    public void testGetByIdUsesCache() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        redisBoardDecorator.create(board);
        Board retrievedBoard = redisBoardDecorator.getById(board.getId());

        assertNotNull(retrievedBoard);
        assertEquals(board.getId(), retrievedBoard.getId());
    }

    @Test
    public void testUpdateInvalidatesCacheAndCachesUpdatedData() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        redisBoardDecorator.create(board);
        board.setName("Updated Board");
        redisBoardDecorator.update(board);
        Board cachedBoard = redisCache.getCachedData(BOARD_CACHE_KEY + board.getId().toString(), Board.class);

        assertNotNull(cachedBoard);
        assertEquals("Updated Board", cachedBoard.getName());
    }

    @Test
    public void testDeleteRemovesCache() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        redisBoardDecorator.create(board);
        redisBoardDecorator.delete(board);

        assertNull(redisCache.getCachedData(BOARD_CACHE_KEY + board.getId().toString(), Board.class));
    }

    @Test
    public void testCacheConnectionLost() {
        Board board = new Board("Test Board");
        board.setId(new ObjectId());
        redisBoardDecorator.create(board);
        redisCache.close();
        Board retrievedBoard = redisBoardDecorator.getById(board.getId());

        assertNotNull(retrievedBoard);
        assertEquals(board.getId(), retrievedBoard.getId());
    }
}

