package com.example.kafkabridge.demo.repository;

import com.example.kafkabridge.demo.model.KafkaMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<KafkaMessage, String> {
}