package org.example.cloud.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi PayApi() {
        return GroupedOpenApi.builder()
                .group("pay module")
                .packagesToExclude("/pay/**")
                .build();
    }

    @Bean
    public GroupedOpenApi CustomerApi() {
        return GroupedOpenApi.builder()
                .group("customer module")
                .packagesToExclude("/customer/**")
                .build();
    }


    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My Swagger")
                        .description("这是一个springboot测试")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("test")))
                .externalDocs(new ExternalDocumentation()
                        .description("test swagger")
                        .url("test"));
    }
}
