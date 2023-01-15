package com.soonhankwon.coffeeplzbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CoffeePlzBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeePlzBackendApplication.class, args);
    }

}
