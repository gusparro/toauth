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
import static org.springframework.http.HttpStatus.*;

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

    /* GET METHODS */

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointUsersUsingGetMethod() {
        RestAssured.given()
                .accept(JSON)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointUsersUsingGetMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 1)
                .accept(JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointUsersUsingGetMethodWithInvalidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 100)
                .accept(JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    /* POST METHODS */

    @Test
    public void itShouldReturnStatusHttp201WhenCallEndpointUsersUsingPostMethodWithValidJson() {
        String body = """
                {
                    "fullName": "Daniel Silva",
                    "username": "dasilva",
                    "email": "dasilva@gmail.com",
                    "password": "123456"
                }
                """;

        RestAssured.given()
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value());
    }

    @Test
    public void itShouldReturnStatusHttp400WhenCallEndpointUsersUsingPostMethodWithInvalidJson() {
        String body = """
                {
                    "fullName": "Daniel Silva",
                    "username": "",
                    "email": "dasilvagmail.com",
                    "password": "    "
                }
                """;

        RestAssured.given()
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    /* PUT METHODS */

    /* PATCH METHODS */

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointUsersUsingPatchMethodWithValidPathParamForIDAndValidJson() {
        String body = """
                {
                    "fullName": "Gustavo F. Parro",
                    "username": "gustavoparro"
                }
                """;

        RestAssured.given()
                .pathParams("id", 1)
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .patch("/{id}")
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointUsersUsingPatchMethodWithInvalidPathParamForIDAndValidJson() {
        String body = """
                {
                    "fullName": "Gustavo F. Parro",
                    "username": "gustavoparro"
                }
                """;

        RestAssured.given()
                .pathParams("id", 100)
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .patch("/{id}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    /* DELETE METHODS */

    @Test
    public void itShouldReturnStatusHttp204WhenCallEndpointUsersUsingDeleteMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 1)
                .accept(JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointUsersUsingDeleteMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 100)
                .accept(JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

}
