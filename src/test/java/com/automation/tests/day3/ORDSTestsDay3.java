package com.automation.tests.day3;

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
        .thenReturn()
                .jsonPath();
        // items[employee1, employee2, ....] ==> employee1 is items[0] in this case
        // to call this element's any value ==> item[0].keyName
        String nameOfFirstEmployee = json.getString("items[0].first_name");
        String nameOfLastEmployee  = json.getString("items[-1].first_name");
        System.out.println("First employee name is: "+nameOfFirstEmployee);
        System.out.println("Last employee name is: "+nameOfLastEmployee);
        // In JSON, employee looks like object that consist of params and their values
        // we can parse that json object and store in map
        Map<String, ?> firstEmloyee = json.get("items[0]");
        //System.out.println(firstEmloyee);
        // since firstEmployee it's a map, we can iterate through it by using Entry. entry represents one <key, value> pair
        for (Map.Entry<String, ?> entry : firstEmloyee.entrySet()) {
            System.out.println("key: "+entry.getKey()+", value: "+entry.getValue());

        }
    }


    //write a code to get info from countries as List<Map<k,v>>
    // prettyPrint()==> prints json/xml/html in nice format and returns string, thus we cannot retrieve jsonpath without extraction...
    // prettyPeek() ==> first prints then returns Response object and from that object we can get json path.
    @Test
    public  void  test6(){

        JsonPath json = given()
                .accept("application/json")
        .when()
                .get("countries").prettyPeek().jsonPath();
        List<Map<String, ?>> allCountries = json.get("items");
        //System.out.println(allCountries);
        for (Map<String, ?> map: allCountries) {
            System.out.println(map);
        }

    }

    // get collection of employee's salaries
    // then sort it
    // and print
    @Test
    public void test7(){
        List<Integer> salaries = given().
                accept("application/json")
                .when().
                get("/employees")
                .thenReturn().jsonPath().get("items.salary");
        Collections.sort(salaries);//sort from a to z, 0-9
        Collections.reverse(salaries);
        System.out.println(salaries);
    }

    //get collection of phone numbers, from employees
    //and replace all dots "." in every phone number with dash "-"


    @Test
    public void test8(){
        given().contentType(ContentType.JSON)
                //accept("application/json")
                .pathParam("location_id", 1700)
                .when().get("/locations/{location_id}")
                .then().assertThat().body("location_id", is(1700))
                .assertThat().body("postal_code", is("98199"))
                .assertThat().body("city", is("Seattle"))
                .assertThat().body("state_province", is("Washington"))
                .log().body();
        ;



    }

}
