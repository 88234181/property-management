

package com.mycompany.propertymanagement.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI appOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Property Management API")
                        .description("Property management application")
                        .version("v0.0.1"));
    }
}
