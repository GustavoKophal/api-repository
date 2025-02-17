package br.edu.utfpr.conteudos.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Conteudos",
        version = "1.0",
        description = "API para gerenciamento de categorias, incluindo operações de criação, leitura, atualização e exclusão."
    )
)
public class OpenAPIConfig {
   
}
