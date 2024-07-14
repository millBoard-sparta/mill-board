package com.sparta.millboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MillboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MillboardApplication.class, args);
    }

}
