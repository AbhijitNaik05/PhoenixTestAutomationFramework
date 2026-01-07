package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.services.JobService;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;

public class CreateJobAPITestwithFakedata {
	private CreateJobPayload createJobPayload;
	private JobService jobService;

	@BeforeMethod(description = "creating create job payload for api and instatiating the jobservice")
	public void setup() {
		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
		jobService = new JobService();
	}

	@Test(description = "Verify if the create job API is creating job for inwarrenty flow", groups = { "api", "smoke",
			"regression" })
	public void createJobAPITest() {

		int customerId = jobService.createJob(Role.FD, createJobPayload)
				.then().spec(responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/CreateJobAPIResponse.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_")).extract().body().jsonPath().getInt("data.tr_customer_id");
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerData = CustomerDao.getCustomerInformation(customerId);
		Assert.assertEquals(actualCustomerData.getFirst_name(), expectedCustomerData.first_name());
		Assert.assertEquals(actualCustomerData.getLast_name(), expectedCustomerData.last_name());
		Assert.assertEquals(actualCustomerData.getMobile_number(), expectedCustomerData.mobile_number());
		Assert.assertEquals(actualCustomerData.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
		Assert.assertEquals(actualCustomerData.getEmail_id(), expectedCustomerData.email_id());
		Assert.assertEquals(actualCustomerData.getEmail_id_alt(), expectedCustomerData.email_id_alt());
		
		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao
				.getCustomerAddressData(actualCustomerData.getTr_customer_address_id());
		Assert.assertEquals(createJobPayload.customer_address().flat_number(), customerAddressDBModel.getFlat_number());
		Assert.assertEquals(createJobPayload.customer_address().apartment_name(), customerAddressDBModel.getApartment_name());
		Assert.assertEquals(createJobPayload.customer_address().street_name(), customerAddressDBModel.getStreet_name());
		Assert.assertEquals(createJobPayload.customer_address().landmark(), customerAddressDBModel.getLandmark());
		Assert.assertEquals(createJobPayload.customer_address().area(), customerAddressDBModel.getArea());
		Assert.assertEquals(createJobPayload.customer_address().pincode(), customerAddressDBModel.getPincode());
		Assert.assertEquals(createJobPayload.customer_address().country(), customerAddressDBModel.getCountry());
		Assert.assertEquals(createJobPayload.customer_address().state(), customerAddressDBModel.getState());
		Assert.assertEquals(createJobPayload.customer_address().pincode(), customerAddressDBModel.getPincode());
		
		JobHeadModel jobHeaddata=JobHeadDao.getJobHeadFromDB(customerId);
		Assert.assertEquals(createJobPayload.mst_oem_id(), jobHeaddata.getMst_oem_id());
		Assert.assertEquals(createJobPayload.mst_platform_id(), jobHeaddata.getMst_platform_id());
		Assert.assertEquals(createJobPayload.mst_service_location_id(), jobHeaddata.getMst_service_location_id());
		Assert.assertEquals(createJobPayload.mst_warrenty_status_id(), jobHeaddata.getMst_warrenty_status_id());

	}
}
