package com.soonhankwon.coffeeplzbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soonhankwon.coffeeplzbackend.dto.DataCollectionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaProducerService implements DataTransferService{
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderData(DataCollectionDto dataCollectionDto) {
        String topic = "orderData";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = objectMapper.writeValueAsString(dataCollectionDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the OrderService : " + dataCollectionDto);
    }
}
