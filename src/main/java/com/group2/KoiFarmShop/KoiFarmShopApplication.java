package com.group2.KoiFarmShop;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class KoiFarmShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoiFarmShopApplication.class, args);
	}

}
