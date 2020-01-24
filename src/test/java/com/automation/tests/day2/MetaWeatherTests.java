package com.automation.tests.day2;

import org.junit.jupiter.api.Test;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.TestTemplate;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//Those two static import helps us not to use class name to invoke that static methods...
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MetaWeatherTests {
    /*
        /api/location/search/?query=san
        /api/location/search/?query=london
        /api/location/search/?lattlong=36.96,-122.02
        /api/location/search/?lattlong=50.068,-5.316
        /api/location/{woeid}==> for Washington: 2514815 ==> see below

        {
        "title": "Washington DC",
        "location_type": "City",
        "woeid": 2514815,
        "latt_long": "38.899101,-77.028999"
        }
     */

    // /users/100/ ==> 100 is a path parameter
    // /users/255/ ==> 255 is a path parameter
    // /users/255?name=James ==> name is a query parameter key=value , key it's a query parameter

    private String baseURI="https://www.metaweather.com/api/";

    @Test
    public void test1(){
        Response response=given()
                .baseUri(baseURI+"location/search/")
                .queryParam("query", "washington")
                .get();
        assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());
    }


    /*
    /api/location/{woeid}==> for Washington: 2514815 ==> see below

        {
        "title": "Washington DC",
        "location_type": "City",
        "woeid": 2514815,
        "latt_long": "38.899101,-77.028999"
        }
    */
    @Test
    public void test2(){
        Response response=given()
                .pathParam("woeid", "2514815")
                .get(baseURI+"location/{woeid}");
        System.out.println(response.prettyPrint());
    }

}
