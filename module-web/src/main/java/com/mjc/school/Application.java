package com.mjc.school;

import com.mjc.school.controller.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class Application {
    public static void main(String[] args) {
        //run
        SpringApplication.run(Application.class, args);
    }
}