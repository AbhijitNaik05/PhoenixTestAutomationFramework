package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;

public class CreateJobAPIJsonDataDrivenTest {

	@Test(description = "Verify if the create job API is creating job for inwarrenty flow", groups = { "api",
			"datadriven", "regression",
			"csv" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "CreateJobAPIJsonDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
		given().spec(requestSpecwithAuth(Role.FD, createJobPayload)).and().when().post("job/create").then()
				.spec(responseSpec_ok()).body(matchesJsonSchemaInClasspath("response_schema/CreateJobAPIResponse.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}
}
