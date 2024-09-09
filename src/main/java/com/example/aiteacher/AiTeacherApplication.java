package com.example.aiteacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AiTeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiTeacherApplication.class, args);
    }

}
