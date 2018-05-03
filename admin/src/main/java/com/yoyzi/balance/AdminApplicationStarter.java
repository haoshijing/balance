package com.yoyzi.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:application-context.xml"})
public class AdminApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationStarter.class, args).start();
    }
}