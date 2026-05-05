package com.example.h20test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI productStoreOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("H20 Store API")
                        .description("REST API for a computer hardware store")
                        .version("v1"));
    }
}
