package com.automation.selfStudy;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.PortUnreachableException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class School2 {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("school.uri");
    }

    @Test
    @DisplayName("Just playing with tables")
    public void test1(){

//                given()
//                        .accept(ContentType.JSON)
//                .when()
//                    .get("/student/all")
//                .then()
//                    .assertThat()
//                        .statusCode(200)
//                        .contentType("application/json;charset=UTF-8");

                Response response =
                        given()
                            .accept(ContentType.JSON)
                        .when()
                             .get("/student/all");

                assertEquals(200, response.getStatusCode());
        System.out.println(response.getStatusCode());

        JsonPath json = response.jsonPath();

        List<Map<String, ?>> students = json.getList("students");

        for (Map<String, ?> student : students) {
            //System.out.println(student);
        }

        Map<String, ?> firstStudent = json.getMap("students[0]");
        System.out.println("firstStudent = " + firstStudent);

        String firstName = json.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);

        String city = json.getString("students[0].company.address.city");
        System.out.println("city = " + city);

    }

}
