package com.automation.selfStudy;

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
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class GitHub2 {

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("github.uri");
    }


    /*
    1. Send a get request to /orgs/:org. Request includes : • Path param org with value cucumber
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify value of the login field is cucumber
    4. Verify value of the name field is cucumber
    5. Verify value of the id field is 320565
     */
    @Test
    @Description("Verify organization information")
    public void test1() {


                given()
                    .pathParam("org","cucumber")
                .when()
                    .get("/orgs/{org}")
                .then()
                    .assertThat()
                        .statusCode(200)
                        .contentType("application/json; charset=utf-8")
                        .body("login", is("cucumber"))
                        .body("name", is("Cucumber"))
                        .body("id", is(320565));

    }

    /*
    Ascending order by full_name sort
    1. Send a get request to /orgs/:org/repos. Request includes : • Path param org with value cucumber
    • Query param sort with value full_name
    2. Verify that all repositories are listed in alphabetical order based on the value of the field name
     */
    @Test
    @Description("Ascending order by full_name sort")
    public void test2() {

        Response response =
                given()
                    .pathParam("org", "cucumber")
                    .queryParam("sort","full_name")
                .when()
                    .get("/orgs/{org}/repos");

        JsonPath json = response.jsonPath();

        List<String> actualNames = json.getList("name");
        System.out.println(actualNames);

        List<String> expectedNames = json.getList("name");

        Collections.sort(expectedNames);
        System.out.println(expectedNames);

        assertEquals(expectedNames,actualNames);


    }

    /*
    1. Send a get request to /orgs/:org. Request includes : • Path param org with value cucumber
    2. Grab the value of the field id
    3. Send a get request to /orgs/:org/repos. Request includes :
    • Path param org with value cucumber
    4. Verify that value of the id inside the owner object in every response is equal to value from step 2
    //320565
     */

    @Test
    @Description("Repository owner information")
    public void test3() {

        Response response1 = given()
                .pathParam("org", "cucumber")
                .when()
                .get("/orgs/{org}");

        JsonPath json1 =response1.jsonPath();
        int id = json1.getInt("id");
        System.out.println("id = " + id);

        Response response2 =
                given()
                        .pathParam("org", "cucumber")
                .when()
                        .get("/orgs/{org}/repos");

        JsonPath json2 = response2.jsonPath();

        List<Integer> ids = json2.getList("owner.id");
        System.out.println("ids = " + ids);

        for (Integer each : ids) {
            assertEquals(id, each);
        }




    }
}
