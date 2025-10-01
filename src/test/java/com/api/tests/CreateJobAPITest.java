package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {

		Customer customer = new Customer("Anika", "Naik", "9999999999", "", "anika@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("605", "Kohinoor", "MG Road", "Phoenix mall", "Pune",
				"410033", "India", "maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-06-18T18:30:00.000Z", "60217383225329",
				"60217383225329", "60217383225329", "2025-06-18T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Camera is not working");
		List<Problems> problem = new ArrayList<Problems>();
		problem.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problem);
		given().spec(SpecUtil.requestSpecwithAuth(Role.FD, createJobPayload)).and().when().post("job/create").then()
				.spec(SpecUtil.responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/CreateJobAPIResponse.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}
}
