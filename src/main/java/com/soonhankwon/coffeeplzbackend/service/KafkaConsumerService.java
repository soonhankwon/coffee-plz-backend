package com.soonhankwon.coffeeplzbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "orderData", groupId = "dataCollectionServer")
    public void consume(String kafkaMessage) {
        log.info("Consumed Message = " + kafkaMessage);
    }
}
