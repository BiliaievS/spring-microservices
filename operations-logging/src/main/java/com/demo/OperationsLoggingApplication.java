package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperationsLoggingApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(OperationsLoggingApplication.class);
        application.run(args);
    }
}
