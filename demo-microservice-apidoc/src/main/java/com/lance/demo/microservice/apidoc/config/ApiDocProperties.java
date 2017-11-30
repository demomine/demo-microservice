package com.lance.demo.microservice.apidoc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "framework.api.docs")
@Data
public class ApiDocProperties {
    private String pathMapping = "/";

    private String paths = "any";

    private String apiSelectType = "CLASS_ANNOTATION";

    private String apiSelectValue = "io.swagger.annotations.Api";

    private String title = "demo-lance-apidos";

    private String modelRendering;

    private boolean jsonEditor = false;

    private long requestTimeout = 600000;

    private ApiInfo apiInfo;

    @Data
    static class ApiInfo {
        private String tittle = "demo-lance";
        private String description ="demo-lance-description";
        private String termsOfServiceUrl = "http://www.immerser.net";
        private String license ="Lance License Version 1.0";
        private String liscenseUrl ="https://github.com/demomine";
        private String version = "1.0";

    }
}
