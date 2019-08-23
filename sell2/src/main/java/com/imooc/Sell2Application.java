package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Sell2Application {

    public static void main(String[] args) {
        SpringApplication.run(Sell2Application.class, args);
    }

}
