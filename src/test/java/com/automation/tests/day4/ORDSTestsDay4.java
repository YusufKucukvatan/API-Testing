package com.automation.tests.day4;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class ORDSTestsDay4 {
    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Verify that response time is less than 3 seconds")
    public void test1(){
        given()
                .accept("application/json")
        .when()
                .get("/employees")
        .then()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(3000L))
        .log().all(true);

    }

    @Test
    public void test2(){
        given()
                .accept(ContentType.JSON)
                .queryParam("q","{\"country_id\":\"US\"}")
        .when()
                .get("/countries")
         .then().assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("items[0].country_name", is("United States of America"));

    }

    @Test
    @DisplayName("Get all links and print them")
    public void test3(){
        Response response=
                given()
                        .accept(ContentType.JSON)
                        .queryParam("q", "{\"country_id\":\"US\"}")
                .when()
                        .get("/countries");
        JsonPath json=response.jsonPath();
        List<?> links=json.getList("links.href");
        //System.out.println("links = " + links);
        for (Object link:links ) {
            System.out.println(link);
        }
                

    }

}
