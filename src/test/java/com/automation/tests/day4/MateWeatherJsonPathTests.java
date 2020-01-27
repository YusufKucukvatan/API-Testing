package com.automation.tests.day4;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;

public class MateWeatherJsonPathTests {
    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("meta.weather.uri");
    }

    /**
     * TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is 'New'
     * Then user verifies that payload contains 5 objects
     */
    @Test
    @DisplayName("Verify that are 5 city that are matching 'New'")
    public void test1(){
               given()
                    .accept(ContentType.JSON)
                    .queryParam("query", "New")
                    .get("/search")
               .then()
                    .assertThat()
                        .statusCode(200)
               .body("",hasSize(5))
               .log().body();


    }

    /**
     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is New
     * Then user verifies that 1st object has following info:
     *  |title   |location_type|woeid  |latt_long          |
     *  |New York|City         |2459115|40.71455,-74.007118|
     */
    @Test
    @DisplayName("Verify that are 1st city has the information above")
    public void test2(){
        given()
                .accept(ContentType.JSON)
                .queryParam("query", "New")
                .get("/search")
        .then()
            .assertThat()
                .statusCode(200)
                .body("title[0]",is("New York"))
                .body("location_type[0]",is("City"))
                .body("woeid[0]",is(2459115))
                .body("latt_long[0]",is("40.71455,-74.007118"));
    }

    /**
     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is 'Las'
     * Then user verifies that payload  contains following titles:
     *  |Glasgow  |
     *  |Dallas   |
     *  |Las Vegas|
     *
     */
    @Test
    @DisplayName("Verify body contains the cities above when query parameter is 'Las'")
    public void test3(){
        given()
                .accept(ContentType.JSON)
                .queryParam("query", "Las")
                .get("/search")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", contains("Glasgow","Dallas","Las Vegas"));
        // hasItems ==> exact match
        // contains ==> partial match
    }
}
