package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.MasterService;

public class MasterAPITest {
	private static MasterService masterService;

	@BeforeMethod(description = "Intiatianted Master service")
	public void setup() {
		masterService = new MasterService();
	}

	@Test(description = "Verify if the master API is giving correct response", groups = { "api", "smoke",
			"regression" })
	public void masterAPITest() {
		masterService.master(FD).then().spec(responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/MasterAPIResponseSchema_FD.json"))
				.body("message", equalToIgnoringCase("Success")).body("data", notNullValue())
				.body("data", hasKey("mst_oem")).body("data", hasKey("mst_model")).body("$", hasKey("message"))
				.body("$", hasKey("data")).body("data.mst_oem.size()", equalTo(2))
				.body("data.mst_model.size()", greaterThan(0)).body("data.mst_oem.id", everyItem(notNullValue()));
	}

	@Test(description = "Verify if the master API is giving correct status code for invalid token", groups = { "api",
			"negative", "smoke", "regression" })
	public void invalidTokenMasterAPITest() {
		masterService.master().then().spec(responseSpec_TEXT(401));
	}
}
