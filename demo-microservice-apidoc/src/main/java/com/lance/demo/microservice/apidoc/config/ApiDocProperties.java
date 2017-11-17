package com.lance.demo.microservice.apidoc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "framework.api.docs")
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

    private static class ApiInfo {
        private String tittle = "demo-lance";
        private String description ="demo-lance-description";
        private String termsOfServiceUrl = "http://www.immerser.net";
        private String license ="Lance License Version 1.0";
        private String liscenseUrl ="https://github.com/demomine";
        private String version = "1.0";

        public String getTittle() {
            return tittle;
        }

        public void setTittle(String tittle) {
            this.tittle = tittle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLiscenseUrl() {
            return liscenseUrl;
        }

        public void setLiscenseUrl(String liscenseUrl) {
            this.liscenseUrl = liscenseUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public String getPathMapping() {
        return pathMapping;
    }

    public void setPathMapping(String pathMapping) {
        this.pathMapping = pathMapping;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public String getApiSelectType() {
        return apiSelectType;
    }

    public void setApiSelectType(String apiSelectType) {
        this.apiSelectType = apiSelectType;
    }

    public String getApiSelectValue() {
        return apiSelectValue;
    }

    public void setApiSelectValue(String apiSelectValue) {
        this.apiSelectValue = apiSelectValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModelRendering() {
        return modelRendering;
    }

    public void setModelRendering(String modelRendering) {
        this.modelRendering = modelRendering;
    }

    public boolean isJsonEditor() {
        return jsonEditor;
    }

    public void setJsonEditor(boolean jsonEditor) {
        this.jsonEditor = jsonEditor;
    }

    public long getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(long requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }
}
