package com.lance.demo.microservice.apidoc.config;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;

public class ApiDocClient {

    private final Predicate<String> paths;

    private final String pathMapping;

    private final Predicate<RequestHandler> handlerPredicate;

    private final String modelRendering;

    private final boolean jsonEditor;

    private final long requestTimeout;

    private final String tittle;

    private final String description;

    private final String termsOfServiceUrl;

    private final String license;

    private final String licenseUrl;

    private final String version;


    public ApiDocClient() {
        this(new Builder());
    }

    public ApiDocClient(Builder builder) {
        this.paths = builder.paths;
        this.pathMapping = builder.pathMapping;
        this.handlerPredicate = builder.handlerPredicate;
        this.modelRendering = builder.modelRendering;
        this.jsonEditor = builder.jsonEditor;
        this.requestTimeout = builder.requestTimeout;
        this.tittle = builder.tittle;
        this.description = builder.description;
        this.termsOfServiceUrl = builder.termsOfServiceUrl;
        this.license = builder.license;
        this.licenseUrl = builder.licenseUrl;
        this.version = builder.version;
    }


    public Predicate<String> getPaths() {
        return paths;
    }

    public String getPathMapping() {
        return pathMapping;
    }

    public Predicate<RequestHandler> getHandlerPredicate() {
        return handlerPredicate;
    }

    public String getModelRendering() {
        return modelRendering;
    }

    public boolean isJsonEditor() {
        return jsonEditor;
    }

    public long getRequestTimeout() {
        return requestTimeout;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(tittle)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .license(license)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }

    public Builder newBuilder() {
        return new Builder(this);
    }


    public static final class Builder {
        Predicate<String> paths;

        String pathMapping;

        Predicate<RequestHandler> handlerPredicate;

        String modelRendering;

        boolean jsonEditor;

        long requestTimeout;

        String tittle;

        String description;

        String termsOfServiceUrl;

        String license;

        String licenseUrl;

        String version;

        public Builder() {
            this.paths = PathSelectors.any();
            this.pathMapping = "/";
             this.handlerPredicate = RequestHandlerSelectors.withClassAnnotation(Api.class);
            this.jsonEditor = false;
            this.requestTimeout = 60000L;
            this.tittle = "demo-title";
            this.description = "demo-description";
            this.termsOfServiceUrl = "www.immerser.net";
            this.license = "Apache License 2.0";
            this.licenseUrl = "http://www.apache/license/2.0";
            this.version = "1.0";
        }

        Builder(ApiDocClient apiDocClient) {
            this.paths = apiDocClient.paths;
            this.pathMapping = apiDocClient.pathMapping;
            this.handlerPredicate = apiDocClient.handlerPredicate;
            this.modelRendering = apiDocClient.modelRendering;
            this.jsonEditor = apiDocClient.jsonEditor;
            this.requestTimeout = apiDocClient.requestTimeout;
            this.tittle = apiDocClient.tittle;
            this.description = apiDocClient.description;
            this.termsOfServiceUrl = apiDocClient.termsOfServiceUrl;
            this.license = apiDocClient.license;
            this.licenseUrl = apiDocClient.licenseUrl;
            this.version = apiDocClient.version;
        }

        public Builder paths(String path) {
            if (StringUtils.hasText(path)) {
                if (path.equalsIgnoreCase("none")) {
                    this.paths = PathSelectors.none();
                } else if (path.equalsIgnoreCase("any")) {
                    this.paths = PathSelectors.any();
                } else{
                    this.paths = PathSelectors.regex(path);
                }
            }
            return this;
        }

        public Builder pathMapping(String pathMapping) {
            if (StringUtils.hasText(pathMapping)) {
                this.pathMapping = pathMapping;
            }
            return this;
        }

        public Builder handlerPredicate(String handlerPredicate,String handleValue) {
            if (StringUtils.hasText(handlerPredicate) && StringUtils.hasText(handlerPredicate)) {
                if (handlerPredicate.equalsIgnoreCase("package")) {
                    this.handlerPredicate = RequestHandlerSelectors.basePackage(handleValue);
                } else if (handlerPredicate.equalsIgnoreCase("any")) {
                    this.handlerPredicate = RequestHandlerSelectors.any();
                } else if (handlerPredicate.equalsIgnoreCase("none")) {
                    this.handlerPredicate = RequestHandlerSelectors.none();
                }else if (handlerPredicate.equalsIgnoreCase("annotation")) {
                    Class clazz;
                    try {
                        clazz = Class.forName(handleValue);
                    } catch (ClassNotFoundException e) {
                        clazz = Api.class;
                    }
                    this.handlerPredicate = RequestHandlerSelectors.withClassAnnotation(clazz);
                }
            }
            return this;
        }

        public Builder jsonEditor(boolean jsonEditor) {
            this.jsonEditor = jsonEditor;
            return this;
        }

        public Builder requestTimeout(long requestTimeout) {
            if (requestTimeout > 0) {
                this.requestTimeout = requestTimeout;
            }
            return this;
        }

        public Builder tittle(String tittle) {
            if (StringUtils.hasText(tittle)) {
                this.tittle = tittle;
            }
            return this;
        }

        public Builder description(String description) {
            if (StringUtils.hasText(description)) {
                this.description = description;
            }
            return this;
        }

        public Builder termsOfServiceUrl(String termsOfServiceUrl) {
            if (StringUtils.hasText(termsOfServiceUrl)) {
                this.termsOfServiceUrl = termsOfServiceUrl;
            }
            return this;
        }

        public Builder license(String license) {
            if (StringUtils.hasText(license)) {
                this.license = license;
            }
            return this;
        }

        public Builder licenseUrl(String licenseUrl) {
            if (StringUtils.hasText(licenseUrl)) {
                this.licenseUrl = licenseUrl;
            }
            return this;
        }

        public Builder version(String version) {
            if (StringUtils.hasText(version)) {
                this.version = version;
            }
            return this;
        }

        public ApiDocClient build() {
            return new ApiDocClient(this);
        }

    }
}
