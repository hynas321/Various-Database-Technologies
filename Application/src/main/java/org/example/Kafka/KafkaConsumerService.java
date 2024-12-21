package org.example.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bson.types.ObjectId;
import org.example.Redis.ObjectIdDeserializer;
import org.example.Redis.ObjectIdSerializer;
import org.example.Repositories.EntityRepository;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerService<T> {
    private final Consumer<String, String> consumer;
    private final EntityRepository<T> repository;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(EntityRepository<T> repository, String topicName) {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "site");
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(consumerProps);
        this.consumer.subscribe(Collections.singletonList(topicName));
        this.repository = repository;

        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        objectMapper.registerModule(module);
    }

    public void consume(Class<T> entityType) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
        for (ConsumerRecord<String, String> record : records) {
            try {
                System.out.println("Received message" + " (topic: " + record.key() + "): " + record.value());
                T entity = objectMapper.readValue(record.value(), entityType);
                System.out.println("Deserialized object: " + entity);

                repository.create(entity);
                System.out.println("Successfully saved object to repository");

                consumer.commitSync();
            } catch (Exception e) {
                System.err.println("Error processing message: " + record.value());
                e.printStackTrace();
            }
        }
    }

    public void close() {
        consumer.close();
    }
}
