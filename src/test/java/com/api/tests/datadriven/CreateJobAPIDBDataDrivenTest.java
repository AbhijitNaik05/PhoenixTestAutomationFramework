package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;
@Listeners(com.listeners.APITestListener.class)
public class CreateJobAPIDBDataDrivenTest {
	private JobService jobService;

	@BeforeMethod(description = "Instatiating the jobservice")
	public void setup() {
		jobService = new JobService();
	}

	@Test(description = "Verify if the create job API is creating job for inwarrenty flow", groups = { "api",
			"datadriven", "regression",
			"csv" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "CreateJobAPIDBDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
		jobService.createJob(Role.FD, createJobPayload).then()
				.spec(responseSpec_ok()).body(matchesJsonSchemaInClasspath("response_schema/CreateJobAPIResponse.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}
}
