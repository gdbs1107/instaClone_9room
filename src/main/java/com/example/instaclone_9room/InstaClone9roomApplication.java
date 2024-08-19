package com.example.instaclone_9room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InstaClone9roomApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstaClone9roomApplication.class, args);
    }

}
