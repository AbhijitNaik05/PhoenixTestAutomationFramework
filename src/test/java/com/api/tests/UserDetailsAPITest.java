package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {
	@Test
	public void userdetailsAPITest()  {
		given()
		.spec(SpecUtil.requestSpecwithAuth(FD))
		.when()
		.get("userdetails")
		.then()
		.spec(SpecUtil.responseSpec_ok())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response_schema/UserDetailsResponseSchema.json"));
	}
}
