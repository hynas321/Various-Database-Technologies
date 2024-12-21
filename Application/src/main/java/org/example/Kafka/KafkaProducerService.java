package org.example.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerService<T> {
    private final String topicName;
    private final Producer<String, String> producer;

    public KafkaProducerService(String topicName) {
        this.topicName = topicName;

        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        this.producer = new KafkaProducer<>(producerProps);
    }

    public void sendEvent(T entity) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(entity);
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, jsonString);
            producer.send(record);
            System.out.println("Event sent successfully: " + entity);
        } catch (Exception e) {
            System.err.println("Error sending event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        producer.close();
    }
}
