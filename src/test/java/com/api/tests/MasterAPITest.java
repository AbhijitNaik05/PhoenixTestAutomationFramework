package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	@Test
	public void masterAPITest() {
     given()
     .baseUri(getProperty("BASE_URI"))
     .header("Authorization",getToken(FD))
     .and()
     .contentType("")
     .when()
     .log().uri()
     .log().method()
     .log().headers()
     .post("master")
     .then()
     .log().all()
     .statusCode(200)
     .time(lessThan(1000L))
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
	     .baseUri(getProperty("BASE_URI"))
	     .header("Authorization","")
	     .and()
	     .contentType("")
	     .when()
	     .log().uri()
	     .log().method()
	     .log().headers()
	     .post("master")
	     .then()
	     .log().all()
	     .statusCode(401);
	}
}
