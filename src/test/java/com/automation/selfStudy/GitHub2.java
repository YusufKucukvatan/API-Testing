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

}
