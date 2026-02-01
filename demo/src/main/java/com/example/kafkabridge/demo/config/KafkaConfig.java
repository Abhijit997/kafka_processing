package com.example.kafkabridge.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.example.kafkabridge.demo.model.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, UserDetails> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
            props, 
            new StringDeserializer(), 
            new JacksonDeserializer<>(new ObjectMapper(), UserDetails.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDetails> kafkaListenerContainerFactory(
            ConsumerFactory<String, UserDetails> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserDetails> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(new org.springframework.kafka.listener.DefaultErrorHandler());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
    
    // Custom Deserializer
    private static class JacksonDeserializer<T> implements Deserializer<T> {
        private final ObjectMapper objectMapper;
        private final Class<T> targetType;

        public JacksonDeserializer(ObjectMapper objectMapper, Class<T> targetType) {
            this.objectMapper = objectMapper;
            this.targetType = targetType;
        }

        @Override
        public T deserialize(String topic, byte[] data) {
            if (data == null) return null;
            try {
                T obj = objectMapper.readValue(data, targetType);

                // Logic to anonymize PII if flag is true
                if (obj instanceof UserDetails user) {
                    user.anonymize();
                }

                return obj;
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize JSON", e);
            }
        }
    }
}