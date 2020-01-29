package com.automation.selfStudy;
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
public class School {
    @BeforeAll
    public static void setup() {
        baseURI = "http://api.cybertektraining.com/";
    }
    @Test
    public void test1() {
        given()
                .accept(ContentType.JSON)
                .pathParam("student_id_XXX", 2414)
                .when()
                .get("/student/{student_id_XXX}").prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .headers("Vary", is("Accept-Encoding"))
                .contentType("application/json;charset=UTF-8")
                .header("Connection",is("Keep-Alive"))
                .body("students[0].firstName", is("James"))
                .body("students[0].lastName", is("Bond"))
                .body("students[0].batch", is(12))
                .body("students[0].company.address.zipCode", is(22102));

    }
    @Test
    public void test2(){
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("param","all")
        .when()
                .get("/student/{param}");

        JsonPath json = response.jsonPath();

        List<Map<String, ?>> listOfMap = json.getList("students");
        for (Map<String, ?> each:listOfMap) {
        System.out.println(each);
        }

        Map<String,?> student1 = json.getMap("students[0]");
        System.out.println(student1);
        response.then().assertThat().body("students[0]",is(student1));

        String student1FirstName = json.getString("students[0].firstName");
        System.out.println(student1FirstName);
        response.then().assertThat().body("students[0].firstName",is(student1FirstName));


        Map<String,?> student1Company = json.getMap("students[0].company");
        System.out.println(student1Company);
        response.then().assertThat().body("students[0].company",is(student1Company));


        String student1CompanyName = json.getString("students[0].company.companyName");
        System.out.println(student1CompanyName);
        response.then().assertThat().body("students[0].company.companyName",is(student1CompanyName));


        Map<String,?> student1CompanyAddress = json.getMap("students[0].company.address");
        System.out.println(student1CompanyAddress);
        response.then().assertThat().body("students[0].company.address",is(student1CompanyAddress));


        String student1CompanyAddressZIP = json.getString("students[0].company.address.zipCode");
        System.out.println(student1CompanyAddressZIP);
        assertEquals(22102,student1CompanyAddressZIP);
        response.then().assertThat().body("students[0].company.address.zipCode",is(22102));
    }

    @Test
    public void test3(){
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("student_id_yyy", 2414)
                .when()
                .get("/student/{student_id_yyy}").prettyPeek();

        String studentName = response.body().path("students[0].firstName");
        System.out.println(studentName);

        int studentBatch = response.body().path("students[0].batch");
        System.out.println(studentBatch);

    }

}
