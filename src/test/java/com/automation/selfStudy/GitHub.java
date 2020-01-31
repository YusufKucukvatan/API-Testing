package com.automation.selfStudy;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.github.javafaker.Faker;
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

public class GitHub {
    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("github.uri");
    }

    @Test
    @Description("Verify organization information")
    public void test1(){

        Response response =
                given()
                    .pathParam("org", "cucumber")
                .when()
                    .get("/orgs/{org}");

        JsonPath json = response.jsonPath();

        int statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);
        assertEquals(200, statusCode);

        String login = json.getString("login");
        System.out.println("login = " + login);
        assertEquals("cucumber", login);

        String name = json.getString("name");
        System.out.println("name = " + name);
        assertEquals("Cucumber", name);

        int id = json.getInt("id");
        System.out.println("id = " + id);
        assertEquals(320565, id);

    }

    @Test
    @Description("Verify error message")
    public void test2() {

        Response response =
                given()
                    .accept("application/xml")
                    .pathParam("org", "cucumber")
                .when()
                    .get("/orgs/{org}");

        JsonPath json = response.jsonPath();

        int statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);
        assertEquals(415, statusCode);

        String content = response.getContentType();
        System.out.println("content = " + content);
        assertEquals("application/json; charset=utf-8", content);

        String statusCodeMessage = response.getStatusLine();
        System.out.println("statusCodeMessage = " + statusCodeMessage);
        assertTrue(statusCodeMessage.contains("Unsupported Media Type"));
    }

    @Test
    @Description("Number of repositories")
    public void test3() {

        Response response1 =
                given()
                        .pathParam("org", "cucumber")
                        .when()
                        .get("/orgs/{org}");

        JsonPath json1 = response1.jsonPath();
        int publicRepos = json1.getInt("public_repos");
        System.out.println("publicRepos = " + publicRepos);

        Response response2 =
                given()
                        .pathParam("org", "cucumber")
                .when()
                        .get("/orgs/{org}/repos");

        JsonPath json2 = response2.jsonPath();
        List<Map<String,?>> objects = json2.getList("");
        int numberOfObjects = objects.size();
        System.out.println(numberOfObjects);

        assertEquals(publicRepos,numberOfObjects,"The number of the public repositories and object in response body is not matched...");

    }

    }
