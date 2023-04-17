package com.sparta.spring1week;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Spring1weekApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring1weekApplication.class, args);
    }

}
