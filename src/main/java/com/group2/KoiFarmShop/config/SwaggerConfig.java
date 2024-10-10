package com.group2.KoiFarmShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Documentation")
                        .description("Swagger API documentation example")
                        .version("1.0"))
                .addServersItem(new Server().url("http://localhost:8080")); // Thêm server URL của bạn
    }
}

