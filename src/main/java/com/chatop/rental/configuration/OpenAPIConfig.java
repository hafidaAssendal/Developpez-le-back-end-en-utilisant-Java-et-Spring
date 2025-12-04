package com.chatop.rental.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    // Créer le SecurityScheme pour JWT
    SecurityScheme securityScheme = new SecurityScheme()
      .name("BearerAuth")
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT")
      .in(SecurityScheme.In.HEADER)
      .description("JWT Authorization header using the Bearer scheme. Example: 'Bearer {token}'");

    // Créer le SecurityRequirement
    SecurityRequirement securityRequirement = new SecurityRequirement()
      .addList("BearerAuth");

    return new OpenAPI()
      .info(new Info()
        .title("ChaTop API")
        .description("Documentation of the ChaTop project API")
        .version("1.0")
        .contact(new Contact()
          .name("ChaTop Support")
          .email("hafida.assendal@gmail.com")
        )
      )
      .addServersItem(new Server()
        .url("http://localhost:8080/api")
        .description("Local server")
      )
      .components(new Components()
        .addSecuritySchemes("BearerAuth", securityScheme)
      )
      .addSecurityItem(securityRequirement);
  }
}
