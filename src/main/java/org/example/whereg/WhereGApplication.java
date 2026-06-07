package org.example.whereg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WhereGApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhereGApplication.class, args);
    }

}
