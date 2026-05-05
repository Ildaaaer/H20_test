package com.example.h20test;

import org.springframework.boot.SpringApplication;

public class TestH20TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(H20TestApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
