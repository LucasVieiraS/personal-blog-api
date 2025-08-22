package com.github.lucasvieiras.springboot_template.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Spring Boot Template API")
                .description("Boilerplate API setup with Spring Boot")
                .version("0.0.1")
                .contact(new Contact()
                    .name("Lucas Vieira")
                    .url("https://github.com/lucasvieiras"))
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
