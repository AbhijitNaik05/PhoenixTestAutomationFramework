package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {

	@Test
	public void countAPITest() {
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.header("Authorization",getToken(FD))
		.log().uri()
		.log().headers()
		.log().method()
		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(1000L))
		.body("message",equalTo("Success"))
		.body(matchesJsonSchemaInClasspath("response_schema/CountAPIFDResponseSchema.json"))
		.body("data",notNullValue())
		.body("data.size()", equalTo(3))
		.body("data.count", everyItem(greaterThanOrEqualTo(0)))
		.body("data.label", not(blankOrNullString()))
		.body("data.key", containsInAnyOrder("pending_fst_assignment","created_today","pending_for_delivery"));
	}
	
	@Test
	public void countAPITest_missingAuthToken() {
		given()
		.baseUri(getProperty("BASE_URI"))
		.log().uri()
		.log().headers()
		.log().method()
		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}
}
