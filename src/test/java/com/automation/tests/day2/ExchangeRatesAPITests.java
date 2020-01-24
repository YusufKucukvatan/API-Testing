package com.automation.tests.day2;

import io.restassured.http.Header;
        import io.restassured.response.Response;
        import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRatesAPITests {
    private String baseURI = "https://api.openrates.io/";

    @Test
    public void test1() {
        Response response = given().
                get(baseURI + "latest");
        //verify status code
        assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());
    }

    @Test
    public void test2() {
        Response response = given().get(baseURI + "latest");
        //verify that content type is json
        assertEquals(200, response.getStatusCode());
        //verify that data is coming as json
        assertEquals("application/json", response.getHeader("Content-Type"));
        //or like this
        assertEquals("application/json", response.getContentType());
    }
    //GET https://api.exchangeratesapi.io/latest?base=USD HTTP/1.1
    //base it's a query parameter that will ask web service to change currency from eu to something else

    @Test
    public void test3() {
        //#TASK: get currency exchange rate for dollar. By default it's euro.
        Response response = given().
                            baseUri(baseURI).
                            basePath("latest").
                            queryParam("base", "USD").
                            get();
        assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());
    }

    //TASK: Verify that response body contains today's date for latest currency (2020-01-23 | yyyy-mm-dd)
    @Test
    public void test4(){
        String todaysDate= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Response response=given().
                baseUri(baseURI).
                basePath("latest").
                queryParam("base", "GBP").
                get();
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains(todaysDate));
        System.out.println("Today's date is: "+todaysDate);
    }
    //TASK: Get the currency for year 2000.
    //GET https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01&symbols=ILS,JPY HTTP/1.1
    @Test
    public void test5(){
        Response response=given().
                baseUri(baseURI+"history").
                queryParam("start_at", "2000-01-01").
                queryParam("end_at", "2000-12-31").
                queryParam("base", "USD").
                queryParam("symbols", "EUR","GBP","JPY").
                get();
        System.out.println(response.prettyPrint());

    }

    //Given request parameter "base" is USD
    //When user sends request to https://api.openrates.io/"
    //Then response code should be 200
    //And response body should contain " "base":"USD" "
    @Test
    public void test6(){
        String expectedResult="\"base\":\"USD\"";
        Response response=given().
                baseUri(baseURI).
                basePath("latest").
                queryParam("base", "USD").
                get();
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains(expectedResult));
        System.out.println(response.prettyPrint());
    }
}