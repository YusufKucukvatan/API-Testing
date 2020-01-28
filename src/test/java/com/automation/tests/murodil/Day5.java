package com.automation.tests.murodil;

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
import java.util.function.Supplier;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class Day5 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    /*
    Warm up task:
    ORDS:
    - Given accept type is Json
    - Path param value- US
    - When users sends request to /countries
    - Then status code is 200
    - And country_id is US
    - And Country_name is United States of America
    - And Region_id is 2Ò
     */
    @Test
    @DisplayName("The same as test1_2")
    public void test1_1() {
        given()
                .accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .when()
                .get("/countries/{country_id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("country_id", is("US"))
                .body("country_name", is("United States of America"))
                .body("region_id", is(2))
                .and()
                .log().body();
    }

    @Test
    @DisplayName("The same as test1_1")
    public void test1_2() {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .when()
                .get("/countries/{country_id}");

        int statusCode = response.statusCode();
        String country_id = response.path("country_id");
        String country_name = response.path("country_name");
        int region_id = response.path("region_id");

        assertEquals(200, statusCode);
        assertEquals("US", country_id);
        assertEquals("United States of America", country_name);
        assertEquals(2, region_id);
    }

    /*
    - Given accept type is Json
    - Query param value - q={"department_id":80}
    - When users sends request to /employees
    - Then status code is 200
    - And all job_ids start with 'SA'
    - And all department_ids are 80
    - Count is 25
     */
    @Test
    @DisplayName("The same as test1_1")
    public void test2() {
        Response response =
        given()
                .accept(ContentType.JSON)
                .queryParam("q","{\"department_id\":80}")
        .when()
                .get("/employees");
        assertEquals(200, response.getStatusCode());

        List<String> job_id=response.path("items.job_id");
        List<Integer> department_id=response.path("items.department_id");
        for (String each:job_id) {
            assertTrue(each.startsWith("SA"));
        }
        for (Integer each:department_id) {
            assertEquals(80, each);
        }
        int count = response.path("count");
        assertEquals(25, count);

    }
}
