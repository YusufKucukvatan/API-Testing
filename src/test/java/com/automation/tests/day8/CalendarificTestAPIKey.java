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

public class CalendarificTestAPIKey {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("calendarific.uri");
    }

    /*
    API Key is  secret that API generates and gives to the developer.
    API Key looks like long string suc as: a37139e11f7a02185f40781178973f5908345472
    API Key can go as query parameter or inside a header, it depends on web service how you must pass API Key (as header parameter or query parameter)
    Then you have to pass API Key alongside with every request
    API Key is easy to implement for developer and client
    But non technical people have no idea about this. So, it is mostly used by developers only
    */

    /*
    Given accept type is json
    When user sends GET to "/countries"
    Then user verifies that status code is 401
    And use verifies that status line contains "Unauthorized" message
    And user verifies that meta.error_detail contains "Missing or invalid api credentials." message
     */
    @Test
    @DisplayName("Verify user cannot access web service without valid API key")
    public void test1(){
        given()
                .accept(ContentType.JSON)
        .when()
                .get("/countries").prettyPeek()
        .then()
                .assertThat()
                    .statusCode(401)
                    .statusLine(containsString("Unauthorized"))
                    .body("meta.error_detail", containsString("Missing or invalid api credentials."));
    }
    @Test
    @DisplayName("Verify user can access web service with valid API key")
    public void test2(){
        given()
                .accept(ContentType.JSON)
                .queryParam("api_key", ConfigurationReader.getProperty("calendarific.api_key"))
        .when()
                .get("/countries").prettyPeek()
        .then()
                .assertThat()
                    .statusCode(200)
                    .statusLine(containsString("OK"))
                    .body("response.countries", not(empty()));
    }

    /*
    Given accept type is json
    And query parameter pai_key with valid api key
    And query parameter country is US
    And query parameter type is national
    And query parameter year is 2019
    When user sens GET to "/holidays"
    Then user verifies that number of national holidays in US is equals to 11
     */
    @Test
    @DisplayName("Verify that there are 11 national holidays in the US")
    public void test3(){
        Response response =
                given()
                    .accept(ContentType.JSON)
                    .queryParam("api_key", ConfigurationReader.getProperty("calendarific.api_key"))
                    .queryParam("country", "US")
                    .queryParam("type", "national")
                    .queryParam("year", "2019")
                .when()
                    .get("/holidays").prettyPeek();

        response.then().assertThat()
                .statusCode(200)
                .body("response.holidays", hasSize(11));
    }
}
