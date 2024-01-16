package com.cinemaUnderTheJava.configuration;

import com.cinemaUnderTheJava.configuration.support.ControllerTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;

public abstract class RestAssureConfigurationTestBase extends AbstractIT implements ControllerTestSupport {

    @LocalServerPort
    private int serverPort;

    @Value("${server.servlet.context-path}")
    private String basePath;

    @Autowired
    protected ObjectMapper objectMapper;


    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public RequestSpecification requestSpecification() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(serverPort)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
