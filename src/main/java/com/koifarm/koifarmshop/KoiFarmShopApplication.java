package com.koifarm.koifarmshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.koifarm.koifarmshop.entity")
public class KoiFarmShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiFarmShopApplication.class, args);
    }

}
