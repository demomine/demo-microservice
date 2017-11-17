package com.lance.demo.microservice.apidoc.config;

import com.lance.demo.FrameworkSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.List;
import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableConfigurationProperties(ApiDocProperties.class)
@ConditionalOnProperty(value = FrameworkSwitcher.API_DOCS, havingValue = "true")
@EnableSwagger2
public class ApiDocAutoConfiguration {
    private final ApiDocProperties apiDocProperties;

    @Autowired
    public ApiDocAutoConfiguration(ApiDocProperties apiDocProperties) {
        this.apiDocProperties = apiDocProperties;
    }

    @Bean
    public ApiDocClient apiDocClient() {
        return new ApiDocClient.Builder()
                .pathMapping(apiDocProperties.getPathMapping())
                .paths(apiDocProperties.getPaths())
                .build();
    }

    @Bean
    public Docket swaggerBean(ApiDocClient apiDocClient) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(apiDocClient.getHandlerPredicate())
                .paths(apiDocClient.getPaths())
                .build()
                .apiInfo(apiDocClient.apiInfo())
                .pathMapping(apiDocClient.getPathMapping())
                .useDefaultResponseMessages(false)
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()));
    }

    @Bean
    UiConfiguration uiConfig(ApiDocClient apiDocClient) {
        return new UiConfiguration(
                "none",
                "list",
                "alpha",
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                apiDocClient.isJsonEditor(),
                true,
                60000L);
    }

    private ApiKey apiKey() {
        return new ApiKey("api_docs", "Authentication", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return newArrayList(new SecurityReference("api_docs", authorizationScopes));
    }
}
