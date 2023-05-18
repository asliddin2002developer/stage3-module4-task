package com.mjc.school.controller;


import com.mjc.school.service.dto.NewsDTORequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.restassured.RestAssured.*;


public class RestfulTest {

    @BeforeAll
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = 8080;
        }
        else{
            RestAssured.port = Integer.parseInt(port);
        }


        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/api/v1";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }

    @Test
    void whenFetchPost_givenBody_thenCreate(){
        given()
                .contentType(ContentType.JSON)
                .body(new NewsDTORequest("rest-assured Test", "lorem ipsum is dummy text.", 2L, Set.of(1L)))
                .when()
                .post("/news")
                .then()
                .statusCode(201);
    }

    @Test
    void whenFetchAll_givenAllNews_thenSuccess(){
        get("/all-news")
                .then()
                .statusCode(200);
    }

    @Test
    void whenFetchOne_givenSingleNews_thenSuccess(){
        get("news/1")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }


    @Test
    void whenFetchDelete_givenId_thenNoContent(){
        given()
            .pathParam("id", 1)
        .when()
            .delete("news/{id}")
        .then()
            .statusCode(204);
    }
}