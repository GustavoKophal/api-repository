package br.edu.utfpr.gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Gateway",
        version = "1.0",
        description = "Documentação do API Gateway para os serviços"
    )
)
public class OpenApiGatewayConfig {
    
}
