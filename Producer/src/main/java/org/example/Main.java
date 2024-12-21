package org.example;

import org.bson.types.ObjectId;
import org.example.Entities.Post;
import org.example.Kafka.KafkaProducerService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        KafkaProducerService<Post> producerService = new KafkaProducerService<>("social-site");
        Scanner scanner = new Scanner(System.in);

        Runtime.getRuntime().addShutdownHook(new Thread(producerService::close));

        while (true) {
            System.out.println("Enter post content: ");
            String content = scanner.nextLine();

            Post post = new Post();
            post.setId(new ObjectId());
            post.setContent(content);

            producerService.sendEvent(post);
        }
    }
}