package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;


@Test 
public class LoginAPITest {
	public void loginAPITest() throws IOException {
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
    given()
    	.baseUri(getProperty("BASE_URI"))
    .and()
        .contentType(ContentType.JSON)
    .and()
        .accept(ContentType.JSON)
        .and()
        .body(userCredentials)
        .log().uri()
        .log().headers()
        .log().body()
        .log().method()
    .when()
     	.post("login")
    .then()
    .log().all()
    	.statusCode(200)
    	.time(lessThan(2000L))
        .and()
        .body("message", equalTo("Success"))
        .and()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response_schema/LoginResponseSchema.json"));
	}
}
