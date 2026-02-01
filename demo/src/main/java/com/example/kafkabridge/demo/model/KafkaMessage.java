package com.example.kafkabridge.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "kafka_messages")
@Getter
@Setter
@NoArgsConstructor
public class KafkaMessage {
    @Id
    private String id;
    private UserDetails userDetails;
    private long timestamp;

    public KafkaMessage(UserDetails userDetails) {
        this.userDetails = userDetails;
        this.timestamp = System.currentTimeMillis();
    }
}