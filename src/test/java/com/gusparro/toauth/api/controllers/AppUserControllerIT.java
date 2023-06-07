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
public class AppUserControllerIT {

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
                .pathParams("codeUUID", "ab3b7982-5f0c-4935-b5bd-109f6dfaf59a")
                .accept(JSON)
                .when()
                .get("/{codeUUID}")
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointUsersUsingGetMethodWithInvalidPathParamsForID() {
        RestAssured.given()
                .pathParams("codeUUID", "ab3b7982-5f0c-4935-b5bd-109f6dfaf59d")
                .accept(JSON)
                .when()
                .get("/{codeUUID}")
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
                    "password": "12345678"
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

    @Test
    public void itShouldReturnStatusHttp204WhenCallEndpointUsersRolesUsingPostMethodWithValidPathParamsForIdAppUserAndIdRole() {
        RestAssured.given()
                .pathParams("codeUUIDAppUser", "1a9995ed-e3cb-490f-8684-7f24c6c59e7c")
                .pathParams("idRole", 2)
                .accept(JSON)
                .when()
                .post("/{codeUUIDAppUser}/roles/{idRole}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

    /* TODO
    *   Ajustar Exception
    * */
    @Test
    public void itShouldReturnStatusHttp400WhenCallEndpointUsersRolesUsingPostMethodWithInvalidPathParamsForIdAppUserOrIdRole() {
        RestAssured.given()
                .pathParams("codeUUIDAppUser", "1a9995ed-e3cb-490f-8684-7f24c6c59e7c")
                .pathParams("idRole", 12)
                .accept(JSON)
                .when()
                .post("/{codeUUIDAppUser}/roles/{idRole}")
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
                .pathParams("codeUIID", "1a9995ed-e3cb-490f-8684-7f24c6c59e7c")
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .patch("/{codeUIID}")
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
                .pathParams("codeUIID", "1a9995ed-e3cb-490f-8684-7f24c6c59e7d")
                .body(body)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .patch("/{codeUIID}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    /* DELETE METHODS */

    @Test
    public void itShouldReturnStatusHttp204WhenCallEndpointUsersUsingDeleteMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("codeUIID", "1a9995ed-e3cb-490f-8684-7f24c6c59e7c")
                .accept(JSON)
                .when()
                .delete("/{codeUIID}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    public void itShouldReturnStatusHttp404WhenCallEndpointUsersUsingDeleteMethodWithValidPathParamsForID() {
        RestAssured.given()
                .pathParams("codeUUID", "1a9995ed-e3cb-490f-8684-7f24c6c59e7d")
                .accept(JSON)
                .when()
                .delete("/{codeUUID}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    public void itShouldReturnStatusHttp204WhenCallEndpointUsersRolesUsingDeleteMethodWithValidPathParamsForIdAppUserAndIdRole() {
        RestAssured.given()
                .pathParams("codeUUIDAppUser", "ab3b7982-5f0c-4935-b5bd-109f6dfaf59a")
                .pathParams("idRole", 1)
                .accept(JSON)
                .when()
                .delete("/{codeUUIDAppUser}/roles/{idRole}")
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    public void itShouldReturnStatusHttp400WhenCallEndpointUsersRolesUsingDeleteMethodWithInalidPathParamsForIdAppUserOrIdRole() {
        RestAssured.given()
                .pathParams("codeUUIDAppUser", "1a9995ed-e3cb-490f-8684-7f24c6c59e7d")
                .pathParams("idRole", 1)
                .accept(JSON)
                .when()
                .post("/{codeUUIDAppUser}/roles/{idRole}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

}
