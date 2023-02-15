package com.soonhankwon.coffeeplzbackend.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaProducerService implements DataTransferService{
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderData(OrderDataCollectionDto orderDataCollectionDto) {
        String topic = "orderData";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String jsonInString;
        try {
            jsonInString = objectMapper.writeValueAsString(orderDataCollectionDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the OrderService : " + orderDataCollectionDto);
    }
}
