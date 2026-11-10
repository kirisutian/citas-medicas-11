package com.christian.medicos.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("API de Médicos")
                        .version("1.0.0")
                        .description("API para la gestión del personal médico"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api/medicos")
                                .description("Servidor de API Gateway"),
                        new Server()
                                .url("http://localhost:8082")
                                .description("Servidor de localhost")
                ));
    }
}
