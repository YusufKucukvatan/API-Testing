package com.automation.tests.day5;
import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.automation.utilities.ConfigurationReader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;
public class SchoolTests {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("school.uri");
    }

    @Test
    @DisplayName("Delete student")
    public void test1(){
       Response response1 =
                given()
                        .accept(ContentType.JSON)
                        .pathParam("student_id", "all")
                        .get("student/{student_id}");
        response1.body().prettyPrint();
//        Response response2 =
//                given()
//                .accept(ContentType.JSON)
//                .pathParam("student_id", 58)
//                .delete("students/delete/{student_id}");
//
//        Response response3 =
//                given()
//                        .accept(ContentType.JSON)
//                        .pathParam("student_id", 58)
//                        .get("students/delete/{student_id}");
//        System.out.println(response3.body().prettyPrint());
    }



}
