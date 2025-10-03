package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform_Id;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warrenty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DateTimeUtil.*;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {

		Customer customer = new Customer("Anika", "Naik", "9999999999", "", "anika@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("605", "Kohinoor", "MG Road", "Phoenix mall", "Pune",
				"410033", "India", "maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "88217383225329",
				"88217383225329", "88217383225329", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getModel());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Phone is slow");
		List<Problems> problem = new ArrayList<Problems>();
		problem.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_CENTER_A.getCode(),
				Platform_Id.FRONTDESK.getCode(), Warrenty_Status.IN_WARRENTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problem);
		given().spec(SpecUtil.requestSpecwithAuth(Role.FD, createJobPayload)).and().when().post("job/create").then()
				.spec(SpecUtil.responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/CreateJobAPIResponse.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}
}
