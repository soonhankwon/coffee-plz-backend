package com.soonhankwon.coffeeplzbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class TransactionService {
    @Transactional
     public <T> T executeAsTransactional(Supplier<T> supplier) {
        return supplier.get();
    }
}
