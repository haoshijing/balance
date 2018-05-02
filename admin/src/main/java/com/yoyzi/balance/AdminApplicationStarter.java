package com.yoyzi.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:application-context.xml"})
public class AdminApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationStarter.class, args).start();
    }
}