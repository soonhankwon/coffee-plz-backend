package com.soonhankwon.coffeeplzbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class DirectDataTransferService implements DataTransferService {

    public void sendOrderData(OrderDataCollectionDto orderDataCollectionDto) {
        loggingCurrentThread();
        HttpHeaders httpHeaders = setHeader();
        String orderJson = convertOrderObjectToJson(orderDataCollectionDto);
        ResponseEntity<String> responseEntity = requestUrlUseRestTemplate(httpHeaders, orderJson);
        loggingRequestResult(responseEntity);
    }

    private void loggingCurrentThread() {
        log.info(Thread.currentThread().getName());
    }

    private void loggingRequestResult(ResponseEntity<String> responseEntity) {
        log.info(responseEntity.getStatusCode().toString());
        log.info(responseEntity.getBody());
    }

    private ResponseEntity<String> requestUrlUseRestTemplate(HttpHeaders httpHeaders, String orderJson) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange("https://20eb4b5e-0251-47d8-8ec6-3325adb91535.mock.pstmn.io", HttpMethod.POST,
                new HttpEntity<>(orderJson, httpHeaders), String.class);
    }

    private String convertOrderObjectToJson(OrderDataCollectionDto orderDataCollectionDto) {
        String orderObject;
        try {
            orderObject = new ObjectMapper().writeValueAsString(orderDataCollectionDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return orderObject;
    }

    private HttpHeaders setHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return httpHeaders;
    }
}
