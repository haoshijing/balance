package com.yoyzi.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ImportResource({"classpath:application-context.xml"})
public class ApplicationStartLauncher extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationStartLauncher.class);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationStartLauncher.class).web(true).run(args);
    }
}