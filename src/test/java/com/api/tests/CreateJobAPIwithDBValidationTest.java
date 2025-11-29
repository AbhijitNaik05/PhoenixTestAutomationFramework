package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;

import io.restassured.response.Response;

public class CreateJobAPIwithDBValidationTest {
	private CreateJobPayload createJobPayload;
	Customer customer;
	CustomerAddress customerAddress;
	CustomerProduct customerProduct;

	@BeforeMethod(description = "creating create job payload for api")
	public void setup() {
		customer = new Customer("Anika", "Naik", "9999999999", "", "anika@gmail.com", "");
		customerAddress = new CustomerAddress("605", "Kohinoor", "MG Road", "Phoenix mall", "Pune", "410033", "India",
				"maharashtra");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "22555355525553",
				"22555355525553", "22555355525553", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getModel());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Phone is slow");
		List<Problems> problem = new ArrayList<Problems>();
		problem.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_CENTER_A.getCode(),
				Platform_Id.FRONTDESK.getCode(), Warrenty_Status.IN_WARRENTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problem);
	}

	@Test(description = "Verify if the create job API is creating job for inwarrenty flow", groups = { "api", "smoke",
			"regression" })
	public void createJobAPITest() {

		Response response = given().spec(requestSpecwithAuth(Role.FD, createJobPayload)).and().when().post("job/create")
				.then().spec(responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/CreateJobAPIResponse.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_")).extract().response();
		int customerId = response.then().extract().jsonPath().getInt("data.tr_customer_id");
		System.out.println("----------------------------");
		System.out.println(customerId);
		CustomerDBModel customerData = CustomerDao.getCustomerInformation(customerId);
		System.out.println(customerData);
		Assert.assertEquals(customer.first_name(), customerData.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerData.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerData.getMobile_number());
		Assert.assertEquals(customer.email_id(), customerData.getEmail_id());

		System.out.println(customerData.getTr_customer_address_id());
		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao
				.getCustomerAddressData(customerData.getTr_customer_address_id());
		Assert.assertEquals(customerAddress.flat_number(), customerAddressDBModel.getFlat_number());
		Assert.assertEquals(customerAddress.apartment_name(), customerAddressDBModel.getApartment_name());
		Assert.assertEquals(customerAddress.street_name(), customerAddressDBModel.getStreet_name());
		Assert.assertEquals(customerAddress.landmark(), customerAddressDBModel.getLandmark());
		Assert.assertEquals(customerAddress.area(), customerAddressDBModel.getArea());
		Assert.assertEquals(customerAddress.pincode(), customerAddressDBModel.getPincode());
		Assert.assertEquals(customerAddress.country(), customerAddressDBModel.getCountry());
		Assert.assertEquals(customerAddress.state(), customerAddressDBModel.getState());
		Assert.assertEquals(customerAddress.pincode(), customerAddressDBModel.getPincode());

		int customerProductId = response.then().extract().jsonPath().getInt("data.tr_customer_product_id");
		CustomerProductDBModel customerProductDBData = CustomerProductDao.getProductInfoFromDB(customerProductId);
		
		Assert.assertEquals(customerProduct.dop(), customerProductDBData.getDop());
		Assert.assertEquals(customerProduct.popurl(), customerProductDBData.getPopurl());
		Assert.assertEquals(customerProduct.imei1(), customerProductDBData.getImei1());
		Assert.assertEquals(customerProduct.imei2(), customerProductDBData.getImei2());
		Assert.assertEquals(customerProduct.serial_number(), customerProductDBData.getSerial_number());
		Assert.assertEquals(customerProduct.mst_model_id(), customerProductDBData.getMst_model_id());
		
		JobHeadModel jobHeaddata=JobHeadDao.getJobHeadFromDB(customerId);
		Assert.assertEquals(createJobPayload.mst_oem_id(), jobHeaddata.getMst_oem_id());
		Assert.assertEquals(createJobPayload.mst_platform_id(), jobHeaddata.getMst_platform_id());
		Assert.assertEquals(createJobPayload.mst_service_location_id(), jobHeaddata.getMst_service_location_id());
		Assert.assertEquals(createJobPayload.mst_warrenty_status_id(), jobHeaddata.getMst_warrenty_status_id());
		
	}
}
