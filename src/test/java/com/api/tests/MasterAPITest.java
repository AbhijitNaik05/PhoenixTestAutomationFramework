package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	@Test
	public void masterAPITest() {
     given()
     .spec(SpecUtil.requestSpecwithAuth(FD))
     .when()
     .post("master")
     .then()
     .spec(SpecUtil.responseSpec_ok())
     .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response_schema/MasterAPIResponseSchema_FD.json"))
     .body("message",equalToIgnoringCase("Success"))
     .body("data",notNullValue())
     .body("data", hasKey("mst_oem"))
     .body("data", hasKey("mst_model"))
     .body("$", hasKey("message"))
     .body("$", hasKey("data"))
     .body("data.mst_oem.size()", equalTo(2))
     .body("data.mst_model.size()", greaterThan(0))
     .body("data.mst_oem.id", everyItem(notNullValue()));
	}
	
	@Test
	public void invalidTokenMasterAPITest() {
		given()
	     .spec(SpecUtil.requestSpec())
	     .when()
	     .post("master")
	     .then()
	     .spec(SpecUtil.responseSpec_TEXT(401));
	}
}
