package com.soonhankwon.coffeeplzbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class DataCollectionService {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOrderData(OrderDataCollectionDto orderDataCollectionDto) {
        log.info(Thread.currentThread().getName());
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        // Convert the order object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson;
        try {
            orderJson = objectMapper.writeValueAsString(orderDataCollectionDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // HttpEntity 에 param 및 header 설정
        HttpEntity entity = new HttpEntity(orderJson, httpHeaders);

        // RestTemplate exchange 메소드를 통해 URL 에 HttpEntity 와 함께 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://20eb4b5e-0251-47d8-8ec6-3325adb91535.mock.pstmn.io", HttpMethod.POST,
                entity, String.class);

        // 요청 후 응답 확인
        log.info(responseEntity.getStatusCode().toString());
        log.info(responseEntity.getBody());

    }
}
