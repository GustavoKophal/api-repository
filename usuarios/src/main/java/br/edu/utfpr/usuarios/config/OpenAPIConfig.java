package br.edu.utfpr.usuarios.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Categorias",
        version = "1.0",
        description = "API para gerenciamento de categorias, incluindo operações de criação, leitura, atualização e exclusão."
    )
)
public class OpenAPIConfig {
    // Configuração adicional, se necessário
    //@Bean
    //public OpenAPI customOpenAPI() {
    //    return new OpenAPI()
    //            .info(apiInfo())  // Informações sobre a API
    //            .servers(apiServers())  // Definição dos servidores da API
    //            .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Requisito de segurança
    //            .schemaRequirement("bearerAuth", securityScheme()); // Esquema de segurança
    //}
}
