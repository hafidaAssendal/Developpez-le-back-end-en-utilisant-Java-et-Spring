package com.chatop.rental.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;


@Configuration
public class OpenAPIConfig {
  @Bean
  public OpenAPI customOpenAPI() {

    return new OpenAPI()
      .info(new Info()
        .title("ChaTop API")
        .description("Documentation de l’API du projet ChaTop")
        .version("1.0")
        .contact(new Contact()
          .name("ChaTop Support")
          .email("support@chatop.com"))
        .license(new License()
          .name("Apache 2.0")
          .url("https://www.apache.org/licenses/LICENSE-2.0")))
      .addServersItem(new Server()
        .url("http://localhost:8080/api")
        .description("Serveur local"))
      .components(new Components()
        .addSecuritySchemes("BearerAuth",
          new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("JWT Authorization header using the Bearer scheme")))
      .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
  }
}
