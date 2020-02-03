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

public class POJOTesting {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1(){
        Response response =
                given()
                    .accept(ContentType.JSON)
                    .get("/jobs");

        JsonPath jsonPath=response.jsonPath();

        //This is deserialization (from JSON to POJO)
        Job job = jsonPath.getObject("items[0]", Job.class);

        System.out.println(job);

        System.out.println(job.getJob_title());
        System.out.println(job.getJobId());
        System.out.println(job.getMax_salary());
        System.out.println(job.getMin_salary());
    }

    @Test
    @DisplayName("Convert from POJO fto JSON")
    public void test2(){
        Job sdet_pojo = new Job("SDET", "SDET", 90_000, 125_000);
        Gson gson = new Gson();
        String sdet_gson = gson.toJson(sdet_pojo); //==> convert from POJO to JSON: Serialization

        System.out.println(sdet_pojo);
        System.out.println(sdet_gson);
    }

    @Test
    @DisplayName("Converting all jobs from JSON to POJO")
    public void test3(){
        Response response =
                given()
                .accept(ContentType.JSON)
                .get("/jobs");
        JsonPath json = response.jsonPath();

        List<Job> jobs = json.getList("items", Job.class);

        for(Job job : jobs) {
            System.out.println(job);
        }
    }
    @Test
    @DisplayName("Converting from JSON to POJO for locations ")
    public void test4() {

        Response response1 =
                given()
                        .accept(ContentType.JSON)
                        .get("/locations");
        JsonPath json1 = response1.jsonPath();
        List<Location> location1 = json1.getList("items", Location.class);
        for (Location location : location1) {
        System.out.println(location1);
        }



        Response response2 =
                given()
                        .accept(ContentType.JSON)
                        .pathParam("location_id", 2500)
                        .get("/locations/{location_id}");
        JsonPath json2 = response2.jsonPath();
        Location location2 = json2.getObject("", Location.class);
        System.out.println(location2);

    }
}
