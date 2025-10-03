package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserDetailsAPITest {
	@Test (description ="Verify if the Userdetails API response is shown correctly",groups= {"api","smoke","regression"})
	public void userdetailsAPITest()  {
		given()
		.spec(requestSpecwithAuth(FD))
		.when()
		.get("userdetails")
		.then()
		.spec(responseSpec_ok())
		.body(matchesJsonSchemaInClasspath("response_schema/UserDetailsResponseSchema.json"));
	}
}
