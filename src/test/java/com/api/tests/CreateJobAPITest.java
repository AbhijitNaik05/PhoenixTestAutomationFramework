package com.api.tests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {

		Customer customer = new Customer("Anika", "Naik", "9999999999", "", "anika@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("605", "Kohinoor", "MG Road", "Phoenix mall", "Pune",
				"410033", "India", "maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-06-18T18:30:00.000Z", "10217383225329",
				"10217383225329", "10217383225329", "2025-06-18T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Camera is not working");
		Problems [] problemArray = new Problems[1];
		problemArray[0]=problems;
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemArray);
		given()
		.spec(SpecUtil.requestSpecwithAuth(Role.FD, createJobPayload))
		.and()
		.when()
		.post("job/create").then()
		.spec(SpecUtil.responseSpec_ok());


	}
}
