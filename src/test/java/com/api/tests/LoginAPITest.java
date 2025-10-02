package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;


@Test 
public class LoginAPITest {
	public void loginAPITest() throws IOException {
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
    given()
    	.spec(SpecUtil.requestSpec(userCredentials))
    .when()
     	.post("login")
    .then()
    .spec(SpecUtil.responseSpec_ok())
        .and()
        .body("message", equalTo("Success"))
        .and()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response_schema/LoginResponseSchema.json"));
	}
}
