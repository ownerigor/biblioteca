package com.biblioteca.biblioteca.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Biblioteca",
                version = "1.0",
                description = "API REST para cadastro de autores e livros e controle de empréstimos de uma biblioteca."
        )
)
public class SwaggerConfig {
}
