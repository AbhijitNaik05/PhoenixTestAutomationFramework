package com.api.tests;

import static org.hamcrest.Matchers.equalToIgnoringCase;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.Search;
import com.api.services.JobService;
import com.api.utils.SpecUtil;

public class SearchAPITest {
	private JobService jobService;
	private Search searchPayload;
	private static final String JOBNUMBER = "JOB_148785";

	@BeforeMethod(description = "Intiataiting the job service and creating search payload")
	public void setup() {
		jobService = new JobService();
		searchPayload = new Search(JOBNUMBER);
	}

	@Test(description = "Verify if search api working properly", groups = { "api", "smoke", "e2e" })
	public void searchAPITest() {
		jobService.search(Role.FD, searchPayload).then().spec(SpecUtil.responseSpec_ok()).body("message",
				equalToIgnoringCase("success"));
	}
}
