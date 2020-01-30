package com.automation.tests.day6;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class SpartanTests {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("spartan.uri");
    }

    /*
    given accept content type as json
    when user sends get request to "/spartans"
    then user verifies that status code is 200
    and user verifies that content type is json
     */
    @Test
    @Description("Verify that '/spartans' end-point returns 200 and content type is JSON")
    public void test1(){
        given()
                .accept(ContentType.JSON)
        .when()
                .get("/spartans").prettyPeek()
        .then()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON);
    }

    /*
    given accept content type as xml
    when user sends get request to "/spartans"
    then user verifies that status code is 200
    and user verifies that content type is xml
     */
    @Test
    @Description("Verify that '/spartans' end-point returns 200 and content type is XML")
    public void test2(){
        given()
                .accept(ContentType.XML)
                .when()
                .get("/spartans").prettyPeek()
                .then()
                .assertThat().statusCode(200)
                .contentType(ContentType.XML);
    }

    @Test
    @Description("Collect all spartans into a collection and print phone numbers '/spartans' end-point")
    public void test3(){
        Response response =
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/spartans");

        JsonPath json = response.jsonPath();

        List<Map<String, ?>> spartans = json.getList("");
        for(Map<String, ?> spartan : spartans){
            System.out.println(spartan.get("phone"));
        }

        List<?> phones = json.getList("phone");
        System.out.println(phones);
    }

    @Test
    @Description("Collect all spartans into a collection and print phone numbers from '/spartans' end-point by using POJO class")
    public void test4(){
        Response response =
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/spartans");
        List<Spartan> spartans = response.jsonPath().getList("", Spartan.class);
        for(Spartan spartan : spartans) {
            System.out.println(spartan);
        }
    }

    /*
    given accept type is JSON
    when user sends request to '/spartans'
    then user should be able to create new spartan
    | gender | name |    phone   |
    |  male  | Jack | 5711234567 |
    then user verifies status code is 201
     */
    @Test
    @Description("create new spartan")
    public void test5(){
//        Spartan spartan1 = new Spartan()
//                .withGender("Male")
//                .withName("Immo")
//                .withPhone(5711234567L);

        Spartan spartan = new Spartan();
        spartan.setSpartan_gender("Male");
        spartan.setSpartan_name("Immo");
        spartan.setSpartan_phone(5711234567L);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(spartan)
                .post("/spartans");
        assertEquals(201, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        assertEquals(response.jsonPath().getString("success"), "A Spartan is Born!");
        response.prettyPrint();

        Spartan spartanFromResponse = response.jsonPath().getObject("data", Spartan.class);
        System.out.println(spartanFromResponse.getSpartan_id());
        System.out.println(spartanFromResponse.getSpartan_gender());
        System.out.println(spartanFromResponse.getSpartan_name());
        System.out.println(spartanFromResponse.getSpartan_phone());

    }
}