package org.example;

import com.mongodb.client.MongoDatabase;
import org.example.Entities.Board;
import org.example.Kafka.KafkaConsumerService;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.EntityRepository;
import org.example.Repositories.MongoDbConnection;

public class Main {
    private MongoDbConnection mongoDbConnection;
    private MongoDatabase database;
    private KafkaConsumerService<Board> consumerService;
    private EntityRepository<Board> boardRepository;

    public void main(String[] args) {
        mongoDbConnection = new MongoDbConnection("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single", "testDatabase");
        database = mongoDbConnection.getDatabase();
        boardRepository = new BoardRepository(database);
        consumerService = new KafkaConsumerService<>(boardRepository, "topic");

        consumerService.consume(Board.class);
    }
}