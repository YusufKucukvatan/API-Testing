package com.automation.tests.day4;

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

public class ORDSTestsDay4 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Verify that response time is less than 3 seconds")
    public void test1() {
        given()
                .accept("application/json")
                .when()
                .get("/employees")
                .then()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(3000L))
                .log().all(true);

    }

    @Test
    @DisplayName("Verify that country_name from payload is \\\"United States of America\\")
    public void test2() {
        given()
                .accept(ContentType.JSON)
                .queryParam("q", "{\"country_id\":\"US\"}")
                .when()
                .get("/countries")
                .then().assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("items[0].country_name", is("United States of America"));

    }

    @Test
    @DisplayName("Get all links and print them")
    public void test3() {
        Response response =
                given()
                        .accept(ContentType.JSON)
                        .queryParam("q", "{\"country_id\":\"US\"}")
                        .when()
                        .get("/countries");
        JsonPath json = response.jsonPath();
        List<?> links = json.getList("links.href");
        for (Object link : links) {
            System.out.println(link);
        }
    }

    @Test
    @DisplayName("Verify that payload contains only 25 countries")
    public void test4() {
        List<?> countries =
                given()
                        .accept(ContentType.JSON)
                        .get("/countries").prettyPeek()
                        .thenReturn().jsonPath().getList("items");
        assertEquals(25, countries.size());
    }

    @Test
    @DisplayName("Verify that payload contains following countries")
    public void test5() {

        List<?> expected = List.of("Argentina", "Brazil", "Canada", "Mexico", "United States of America");

        Response response =
                given()
                        .accept(ContentType.JSON)
                        .queryParam("q", "{\"region_id\":\"2\"}")
                        .get("/countries");
        JsonPath json = response.jsonPath();
        List<?> actual = json.getList("items.country_name");
        assertEquals(expected, actual);

        String country_name = json.getString("items[0].country_name");
        System.out.println("country_name = " + country_name);
        List<?> links = json.getList("items[0].links");
        System.out.println("links = " + links);
        List<Map<String, ?>> countries = json.getList("items");
        for (Map<String, ?> link : countries) {
            System.out.println(link);
        }

    }

    @Test
    @DisplayName("Verify that phone number if the employee with id=100 is 515.123.4568")
    public void test6() {
        Response response =
                given()
                        .accept(ContentType.JSON)
                        .pathParam("id", 101)
                        .get("/employees/{id}")
                ;
        JsonPath json = response.jsonPath();
        assertEquals(200,response.getStatusCode());
        assertEquals("515.123.4568", json.get("phone_number"));

    }
    /**
     * given path parameter is "/employees"
     * when user makes get request
     * then assert that status code is 200
     * Then user verifies that every employee has positive salary
     *
     */
    @Test
    @DisplayName("Verify that every employee has positive salary")
    public void test7(){
        given().
                accept(ContentType.JSON).
                when().
                get("/employees").
                then().
                assertThat().
                statusCode(200).
                body("items.salary", everyItem(greaterThan(0)));

        //whenever you specify path as items.salary, you will get collection of salaries
        //then to check every single value
        //we can use everyItem(is()), everyItem(greaterThan())
        /**
         * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
         * examined {@link Iterable} yields items that are all matched by the specified
         * <code>itemMatcher</code>.
         * For example:
         * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>*/
    }

    @Test
    @DisplayName("Verify that body returns following salary information after sorting from higher to lower ()")
    public void test8(){
        List<Integer> expectedSalaries=List.of(24000,17000,17000,12008,11000,9000,
                                               9000,8200,8200,8000,7900,7800,7700,
                                               6900,6500,6000,5800,4800,4800,4200,
                                               3100,2900,2800,2600,2500);
        List<Integer> actualSalaries = given().
                accept("application/json")
                .when()
                .get("/employees")
                .thenReturn().jsonPath().getList("items.salary");
        Collections.sort(actualSalaries,Collections.reverseOrder());
        assertEquals(expectedSalaries,actualSalaries);
    }

    @Test
    @DisplayName("Verify that body has following key/value information")
    public void test9_1(){
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 2900)
        .when()
                .get("/locations/{id}")
        .then()
                .assertThat()
                    .body("",hasEntry("street_address", "20 Rue des Corps-Saints"))
                    .body("",hasEntry("city", "Geneva"))
                    .body("",hasEntry("postal_code", "1730"))
                    .body("",hasEntry("country_id", "CH"))
                    .body("",hasEntry("state_province", "Geneve"))
                .log().all(true);
    }

    @Test
    @DisplayName("Same as Test9_1")
    public void test9_2(){
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 2900)
                .when()
                .get("/locations/{id}")
                .then()
                .assertThat()
                .body("street_address", is("20 Rue des Corps-Saints"))
                .body("city", is("Geneva"))
                .body("postal_code", is("1730"))
                .body("country_id", is("CH"))
                .body("state_province", is("Geneve"))
                .log().all(true);
    }

    @Test
    @DisplayName("Practice for JsonPath")
    public void testPracticeForJsonPath() {

        Response response =
                given()
                        .accept(ContentType.JSON)
                        .get("/countries");

        JsonPath json = response.jsonPath();

        List<Map<String, ?>> countries = json.getList("items"); //==> Collects all the countries table into single List of Map
        for (Map<String, ?> link : countries) {
            System.out.println(link);               //==> Prints all maps
        }

        Map<String, ?> singleCountry = json.getMap("items[0]");  //==> Collects the first country information as Map
        System.out.println("singleCountry = " + singleCountry);//==> Prints first countries information

        String country_name = json.getString("items[0].country_name");//==> Finds the first country's name as String
        System.out.println("country_name = " + country_name);//==> Prints first countries name

        List<?> countryNames = json.getList("items.country_name"); //==>Collects all country name from the table
        System.out.println("countryNames = " + countryNames);
    }

    @Test
    @DisplayName("Practice for JsonPath")
    public void testPractice1() {
        given()
                .accept(ContentType.JSON)
                .get("/employees")
                .then()
                .assertThat().body("items.employee_id",everyItem(greaterThan(99)));
    }
    @Test
    @DisplayName("Practice for JsonPath")
    public void testPractice2() {
        given()
                .accept(ContentType.JSON)
                .get("/departments")
                .then()
                .assertThat().body("items.department_name",everyItem(notNullValue()));
    }
}
