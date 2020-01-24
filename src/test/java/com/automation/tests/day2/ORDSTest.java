package com.automation.tests.day2;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

//Those two static import helps us not to use class name to invoke that static methods...
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class ORDSTest {
    // address of ORDS web service, that is runnig on AWS EC2.
    // data is coming from SQL Oracle data base to this web service
    // during back-end testing with SQL developer and JDBC API
    // we are accessing data base directly
    // now we are gonna access web service
    private String baseURI= "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";

    //we start from given()
    //then we can specify type of request like: get(), put(), delete(), post()
    //and as parameter, we enter resource location (URI)
    //then we are getting response back. that response we can put into Response object
    //from response object, we can retrieve: body, header, status code
    //it works without RestAssured.given() because of static import
    //verify that status code is 200
    @Test
    public  void test1() {
        Response response =  given().get(baseURI+"/employees");
        assertEquals(200, response.getStatusCode());
        //System.out.println(response.getBody().asString());
        System.out.println(response.prettyPrint());
    }


    //TASK: get employee with id 100 and verify that response returns status code 200
    //also, print body
    @Test
    public void test2(){
        // Header stands for metadata
        // usually it is used to include cookies
        // in this example, we are specifying what kind of response type we need.
        // because web service can return json or xml
        // when we put header info "Accept", "application/json" we are saying that we need only json as response
        Response response = given().
                header("Accept", "application/json").
                get(baseURI+"/employees/100");
        int expectedStatusCode = 200;
        int actualStatusCode=response.getStatusCode();
        assertEquals(expectedStatusCode, actualStatusCode);
        System.out.println(response.prettyPrint());
        System.out.println("What kind of content server sends to you, in this response: "+response.getHeader("Content-Type"));

    }
    //Task: perform GET request to /regions, print body and all headers.
    @Test
    public  void test3(){
        Response response=given().get(baseURI+"/regions");
        assertEquals(200, response.getStatusCode());
        //to get specific header
        Header header = response.getHeaders().get("Content-Type");
        for (Header h : response.getHeaders()){
            System.out.println(h);
        }
        System.out.println(response.prettyPrint());

    }
}
