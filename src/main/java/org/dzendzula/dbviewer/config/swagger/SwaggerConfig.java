package org.dzendzula.dbviewer.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${dbviewer.api.version}")
    private String apiVersion;

    @Bean
    public ApiInfo apiInfo() {
        Contact contact = new Contact(
                "dzendzula",
                "https://github.com/dzendzula/",
                "dzendzula@gmail.com"
        );

        return new ApiInfo(
                "Database viewer API",
                "REST API for simple PostgreSQL database viewer application. Allows creating and modifying database connections settings. Connecto to database, browse schema and data.",
                apiVersion,
                null,
                contact,
                null,
                null,
                Collections.<VendorExtension>emptyList()
        );
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.dzendzula.dbviewer"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

}