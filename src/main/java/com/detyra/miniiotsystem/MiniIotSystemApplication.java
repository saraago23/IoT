package com.detyra.miniiotsystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiniIotSystemApplication  {

    public static void main(String[] args) {
        SpringApplication.run(MiniIotSystemApplication.class, args);
    }

}
