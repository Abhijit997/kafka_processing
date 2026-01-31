package com.example.kafkabridge.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.kafkabridge.demo.model.KafkaMessage;
import com.example.kafkabridge.demo.repository.MessageRepository;

@Service
public class KafkaConsumerService {

    private final MessageRepository repository;

    public KafkaConsumerService(MessageRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "demo_topic", groupId = "springboot_kafkabridge")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        
        // Save to MongoDB
        KafkaMessage kafkaMessage = new KafkaMessage(message);
        repository.save(kafkaMessage);
        
        System.out.println("Saved to MongoDB with ID: " + kafkaMessage.getId());
    }
}