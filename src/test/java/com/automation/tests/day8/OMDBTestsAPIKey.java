package com.automation.tests.day8;
import com.automation.pojos.Spartan;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class OMDBTestsAPIKey {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("omdb.uri");
    }
    @Test
    @DisplayName("Verify user cannot access web service without valid API key")
    public void test1() {
        given()
                .accept(ContentType.JSON)
                .queryParam("t","Home Alone")
        .when()
                .get().prettyPeek()
        .then()
                .assertThat()
                    .statusCode(401)//==> you are not allowed to access this web service
                    .body("Error", is("No API key provided."));
    }

    @Test
    @DisplayName("Verify that Macaulay Culkin appears in 'Actors' list for Home Alone movies")
    public void test2(){
        given()
                .accept(ContentType.JSON)
                .queryParam("t","Home Alone")
                .queryParam("apikey", ConfigurationReader.getProperty("omdb.apikey"))
        .when()
                .get().prettyPeek()
        .then()
                .assertThat()
                    .statusCode(200)
                    .body("Actors", containsString("Macaulay Culkin"));
    }
}
