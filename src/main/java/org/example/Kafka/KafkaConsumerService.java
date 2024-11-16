package org.example.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.Repositories.EntityRepository;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerService<T> {
    private final Consumer<String, String> consumer;
    private final EntityRepository<T> repository;

    public KafkaConsumerService(EntityRepository<T> repository, String topicName) {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "entityConsumerProps");
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(consumerProps);
        this.consumer.subscribe(Collections.singletonList(topicName));
        this.repository = repository;
    }

    public void consume(Class<T> entityType) {
        ObjectMapper mapper = new ObjectMapper();

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            try {
                T entity = mapper.readValue(record.value(), entityType);
                repository.create(entity);
                consumer.commitSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}