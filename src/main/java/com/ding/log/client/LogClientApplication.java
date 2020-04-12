package com.ding.log.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class LogClientApplication {
    /**
     * 程序入口
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LogClientApplication.class, args);
        log.info("LogClientApplication success");
    }
}
