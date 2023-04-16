package com.gusparro.toauth.api.controllers;

import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.http.ContentType.JSON;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AppUserControllerTest {

    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        flyway.migrate();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/users";
        RestAssured.port = port;
    }

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointUsers() {
        RestAssured.given()
                .accept(JSON)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointUsersWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 1)
                .accept(JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointUsersWithInvalidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 100)
                .accept(JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

}
