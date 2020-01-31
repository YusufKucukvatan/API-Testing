package com.automation.utilities;

import com.automation.pojos.Spartan;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;

public class APIUtilities {
    private static  String URI = ConfigurationReader.getProperty("spartan.uri");
    /**
     * This method can POST new spartan
     *
     * @param spartan POJO
     * @return response object
     */
    public static  Response postSpartan(Spartan spartan) {
        Response response = given().
                contentType(ContentType.JSON).
                basePath(URI).
                body(spartan).
                when().
                post("/spartans");
        return response;
    }

    /**
     * This method can POST new spartan
     *
     * @param spartan as map
     * @return response object
     */
    public static Response postSpartan(Map<String, ?> spartan) {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri");
        Response response = given().
                contentType(ContentType.JSON).
                body(spartan).
                when().
                post("/spartans");
        return response;
    }


    /**
     * This method can POST new spartan
     *
     * @param filePath to the Spartan external JSON file
     * @return response object
     */
    public static Response postSpartan(String filePath) {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri");
        File file = new File(filePath);
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri");
        Response response = given().
                contentType(ContentType.JSON).
                body(file).
                when().
                post("/spartans");
        return response;
    }

    /**
     * Method to delete spartan
     * @param id of spartan that you would like to delete
     * @return response object that you can assert later
     */
    public static Response deleteSpartanById(int id) {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.uri");
        return RestAssured.when().delete("/spartans/{id}", id);
    }

    /**
     * Delete all spartans
     * @return response
     */
    public static void deleteAllSpartans(){
        Response response = given().
                basePath(baseURI).
                accept(ContentType.JSON).
                when().
                get("/spartans");
        //I collected all user id's
        List<Integer> userIDs = response.jsonPath().getList("id");
        for(int i=0; i< userIDs.size();i++){
            //will delete spartan based on id that you specify
            when().delete("/spartans/{id}", userIDs.get(i)).then().assertThat().statusCode(204);
            System.out.println("Deleted spartan with id: "+userIDs.get(i));
        }
    }
}