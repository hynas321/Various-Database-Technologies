package org.example;

import org.example.Entities.Board;
import org.example.Kafka.KafkaProducerService;

public class Main {

    public void main(String[] args) {
        KafkaProducerService<Board> producerService = new KafkaProducerService<>("topic");

        producerService.sendEvent(new Board());
    }
}