package com.soonhankwon.coffeeplzbackend.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DataCollectionServiceTest {
    @Test
    void sendOrderData() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });

        future.get();
        System.out.println("Thread: " + Thread.currentThread().getName());
    }
}