package com.automation.tests.day3;

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    //
    @Test
    public void test1(){
        // .accept("application/json) is shortcut for header("Accept", "application/json")
        // we are asking for json as a response
        given()
                .accept("application/json")
                .get("/employees")
        .then()
                .assertThat().statusCode(200)
        .and()
                .assertThat().contentType("application/json")
                .log().ifError()
                .log().all(true);
    }
    @Test
    public void test2(){
        given()
                .accept("application/json")
                .pathParam("id", 100)
        .when()
                .get("/employees/{id}")
        .then()
                .assertThat().statusCode(200)
        .and().
                assertThat().body("employee_id", is(100))
        .and().
                assertThat().body("department_id", is(90))
        .and()
                .assertThat().body("last_name", is("King"))
        .log().all(true);

    }

    // Same as test2 in short format
    @Test
    public void test3(){
        given()
                .accept("application/json")
                .pathParam("id", 100)
                .when()
                .get("/employees/{id}")
                .then()
                .assertThat().statusCode(200)
                .and().
                assertThat().body("employee_id", is(100),
                            "department_id", is(not(0)),
                                      "last_name", is("King"))
                .log().all(true);

    }

    @Test
    public void test4(){
        given()
                .accept("application/json")
                .pathParam("region_id", 1)
                .get("/regions/{region_id}")
        .then()
                .assertThat().statusCode(200)
                .assertThat().body("region_name", is("Europe"))
                .time(lessThan(2000L), TimeUnit.MILLISECONDS)
                .log().all(true);
    }
    @Test
    public void test5(){
        JsonPath json = given()
                .accept("application/json")
        .when()
                .get("/employees")
        .thenReturn().jsonPath();
        // items[employee1, employee2, ....] ==> employee1 is items[0] in this case
        // to call this element's any value ==> item[0].keyName
        String nameOfFirstEmployee = json.getString("items[0].first_name");
        System.out.println("First employee name is: "+nameOfFirstEmployee);
    }

}
