import org.example.Entities.Board;
import org.example.Kafka.KafkaConsumerService;
import org.example.Kafka.KafkaProducerService;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.EntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

public class KafkaServiceTest extends BaseRepositoryTest {
    private KafkaProducerService<Board> producerService;
    private KafkaConsumerService<Board> consumerService;
    private EntityRepository<Board> boardRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();

        String topicName = "topic";

        boardRepository = new BoardRepository(database);
        producerService = new KafkaProducerService<>(topicName);
        consumerService = new KafkaConsumerService<>(boardRepository, topicName);
    }

    @Test
    public void testConsumeEvent() throws InterruptedException {
        Board testBoard = new Board("Test Board");

        producerService.sendEvent(testBoard);

        Thread consumerThread = new Thread(() -> consumerService.consume(Board.class));
        consumerThread.setDaemon(true);
        consumerThread.start();

        Thread.sleep(1000);

        Board retrievedBoard = boardRepository.getAll().stream()
                .filter(board -> "Test Board".equals(board.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(retrievedBoard);
        assertEquals("Test Board", testBoard.getName());

        consumerThread.interrupt();
        consumerThread.join(1000);
    }

    @AfterEach
    public void tearDown() {
        if (producerService != null) {
            producerService.close();
        }
    }
}
