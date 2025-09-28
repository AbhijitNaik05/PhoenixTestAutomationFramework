package com.api.tests;

import static com.api.constant.Role.*;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

public class CountAPITest {

	@Test
	public void countAPITest() {
		given()
		.spec(SpecUtil.requestSpecwithAuth(FD))
		.when()
		.get("dashboard/count")
		.then()
		.spec(SpecUtil.responseSpec_ok())
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
		.spec(SpecUtil.requestSpec())
		.when()
		.get("dashboard/count")
		.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
	}
}
