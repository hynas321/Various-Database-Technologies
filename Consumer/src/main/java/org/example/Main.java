package org.example;

import com.mongodb.client.MongoDatabase;
import org.example.Entities.Post;
import org.example.Kafka.KafkaConsumerService;
import org.example.Repositories.EntityRepository;
import org.example.Repositories.MongoDbConnection;
import org.example.Repositories.PostRepository;

public class Main {

    public static void main(String[] args) {
        MongoDbConnection mongoDbConnection = new MongoDbConnection("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single", "testDatabase");
        MongoDatabase database = mongoDbConnection.getDatabase();
        EntityRepository<Post> postRepository = new PostRepository(database);

        KafkaConsumerService<Post> consumerService = new KafkaConsumerService<>(postRepository, "social-site");

        Runtime.getRuntime().addShutdownHook(new Thread(consumerService::close));

        System.out.println("Listening for incoming messages...");

        while (true) {
            consumerService.consume(Post.class);
        }
    }
}