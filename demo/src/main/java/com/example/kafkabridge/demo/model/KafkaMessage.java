package com.example.kafkabridge.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "kafka_messages")
@Getter
@Setter
public class KafkaMessage {
    @Id
    private String id;
    private String content;
    private long timestamp;

    public KafkaMessage(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
}