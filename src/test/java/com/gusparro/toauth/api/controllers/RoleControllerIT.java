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
public class RoleControllerIT {

    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        flyway.migrate();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/roles";
        RestAssured.port = port;
    }

    /* GET METHODS */

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointRolesUsingGetMethod() {
        RestAssured.given()
                .accept(JSON)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointRolesUsingGetMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 1)
                .accept(JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointRolesUsingGetMethodWithInvalidPathParamsForID() {
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
    public void itShouldReturnStatusHttp201WhenCallEndpointRolesUsingPostMethodWithValidJson() {
        String body = """
                {
                    "name": "TESTE_ROLE123",
                    "description": "Testando o cadastro de uma nova role."
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
    public void itShouldReturnStatusHttp400WhenCallEndpointRolesUsingPostMethodWithInvalidJson() {
        String body = """
                 {
                    "name": "  ",
                    "description": ""
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

    @Test
    public void itShouldReturnStatusHttp200WhenCallEndpointRoleUsingPutMethodWithValidPathParamForIDAndValidJson() {
        String body = """
                {
                    "name": "TESTE_ROLE123",
                    "description": "Testando o cadastro de uma nova role."
                }
                """;

        RestAssured.given()
                .pathParams("id", 1)
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .put("/{id}")
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointRolesUsingPatchMethodWithInvalidPathParamForIDAndValidJson() {
        String body = """
                {
                    "name": "TESTE_ROLE123",
                    "description": "Testando o cadastro de uma nova role."
                }
                """;

        RestAssured.given()
                .pathParams("id", 100)
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .put("/{id}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    /* DELETE METHODS */

    @Test
    public void itShouldReturnStatusHttp204WhenCallEndpointRolesUsingDeleteMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 4)
                .accept(JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    public void itShouldReturnStatusHttp409WhenCallEndpointRolesUsingDeleteMethodWithValidPathParamsForIDAndRoleIsInUse() {
        RestAssured.given()
                .pathParams("id", 1)
                .accept(JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(CONFLICT.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointRolesUsingDeleteMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("id", 100)
                .accept(JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

}
