package com.automation.tests.day6;
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

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class SpartanTests {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("spartan.uri");
    }

    /*
    given accept content type as json
    when user sends get request to "/spartans"
    then user verifies that status code is 200
    and user verifies that content type is json
     */
    @Test
    @Description("Verify that '/spartans' end-point returns 200 and content type is JSON")
    public void test1(){
        given()
                .accept(ContentType.JSON)
        .when()
                .get("/spartans").prettyPeek()
        .then()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON);
    }

    /*
    given accept content type as xml
    when user sends get request to "/spartans"
    then user verifies that status code is 200
    and user verifies that content type is xml
     */
    @Test
    @Description("Verify that '/spartans' end-point returns 200 and content type is XML")
    public void test2(){
        given()
                .accept(ContentType.XML)
                .when()
                .get("/spartans").prettyPeek()
                .then()
                .assertThat().statusCode(200)
                .contentType(ContentType.XML);
    }

    @Test
    @Description("Collect all spartans into a collection and print phone numbers '/spartans' end-point")
    public void test3(){
        Response response =
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/spartans");

        JsonPath json = response.jsonPath();

        List<Map<String, ?>> spartans = json.getList("");
        for(Map<String, ?> spartan : spartans){
            System.out.println(spartan.get("phone"));
        }

        List<?> phones = json.getList("phone");
        System.out.println(phones);
    }

    @Test
    @Description("Collect all spartans into a collection and print phone numbers from '/spartans' end-point by using POJO class")
    public void test4(){
        Response response =
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/spartans");
        List<Spartan> spartans = response.jsonPath().getList("", Spartan.class);
        for(Spartan spartan : spartans) {
            System.out.println(spartan);
        }
    }

    /*
    given accept type is JSON
    when user sends request to '/spartans'
    then user should be able to create new spartan
    | gender | name |    phone   |
    |  male  | Jack | 5711234567 |
    then user verifies status code is 201
     */
    @Test
    @Description("create new spartan")
    public void test5(){
//        Spartan spartan1 = new Spartan()
//                .withGender("Male")
//                .withName("Immo")
//                .withPhone(5711234567L);

        Spartan spartan = new Spartan();
        spartan.setSpartan_gender("Male");
        spartan.setSpartan_name("Jenny");
        spartan.setSpartan_phone(5711234567L);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(spartan)
                .post("/spartans");
        assertEquals(201, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        assertEquals(response.jsonPath().getString("success"), "A Spartan is Born!");
        response.prettyPrint();

        Spartan spartanFromResponse = response.jsonPath().getObject("data", Spartan.class);
        System.out.println(spartanFromResponse.getSpartan_id());
        System.out.println(spartanFromResponse.getSpartan_gender());
        System.out.println(spartanFromResponse.getSpartan_name());
        System.out.println(spartanFromResponse.getSpartan_phone());

        when()
                .delete("/spartans/{id}", spartanFromResponse.getSpartan_id())
                .prettyPeek()
        .then().assertThat().statusCode(204);
    }

    @Test
    @DisplayName("Delete user")
    public void test6(){
        int userId = 125;
        Response response =  when().delete("/spartans/{id}", userId);
        response.prettyPeek();
    }

    @Test
    @DisplayName("Delete half of the records")
    public void test7(){
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/spartans");
        List<Integer> userIDs = response.jsonPath().getList("id");
        Collections.sort(userIDs, Collections.reverseOrder());
        System.out.println(userIDs);

        for (int i=0; i<userIDs.size()/2; i++){
            when().delete("spartans/{id}", userIDs.get(i));
        }
        Response response1 = given()
                .accept(ContentType.JSON)
                .when()
                .get("/spartans");

        List<Integer> newUserIDs = response1.jsonPath().getList("id");
        Collections.sort(newUserIDs, Collections.reverseOrder());
        System.out.println(newUserIDs);
    }

//    @Test
//    @DisplayName("Adding new records through faker")
//    public void test8(){
//        Faker faker = new Faker();
//        for(int i=0; i<10; i++){
//        Spartan spartan = new Spartan();
//        spartan.setSpartan_gender("Male");
//        spartan.setSpartan_name(faker.name().firstName());
//        spartan.setSpartan_phone(Long.parseLong(faker.phoneNumber().subscriberNumber(12).replaceAll("\\D","")));
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(spartan)
//                    .post("/spartans").prettyPrint();
//        }
//    }

    @Test
    @DisplayName("Update spartan")
    public void test9() {
        Spartan spartan = new Spartan().
                withGender("Male").
                withName("Guru of Java").
                withPhone(9999999999L);

        Response response = given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(spartan).
                pathParam("id", 380).
                put("/spartans/{id}").prettyPeek();
        //put update existing record
        // also when you make PUT request, you need to specify entire body
        //post - create new one
        //we never POST/PUT id, it must be auto generated
        //if it's not like this - it's a bug

        // contentType(ContentType.JSON) in the given()
        // you tell to the web service, what data you are sending
    }

    @Test
    @DisplayName("Update only name with PATCH")
    public void test10(){
        Map<String, Long> update = new HashMap<>();
        update.put("phone", 10000000000L);

        Response response = given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(update).
                pathParam("id", 381).
                patch("/spartans/{id}");

        response.prettyPrint();
        //POST - add new spartan
        //PUT - update existing one, but you have to specify all properties
        //PATCH - update existing one, but ypu may specify one or more properties to update


    }
}
