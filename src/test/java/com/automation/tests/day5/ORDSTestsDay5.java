package com.automation.tests.day5;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class ORDSTestsDay5 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    /*
    TASK: Verify that average salary is more than $5.000
     */
    @Test
    public void test1(){
        Response response=
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/employees");
        JsonPath json=response.jsonPath();
        List<Integer> salaries = json.getList("items.salary");
       int sum=0;
        for (Integer salary : salaries){
            sum+=salary;
        }
        int avg = sum / salaries.size();

        assertTrue(avg>5000);
    }

}
