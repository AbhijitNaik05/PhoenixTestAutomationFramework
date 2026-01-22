package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Count")
public class CountAPITest {
	private static DashboardService dashboardService;

	@BeforeMethod(description = "Intialize the Dashboard service")
	public void setup() {
		dashboardService = new DashboardService();
	}
    @Story("Job count detail is shown correctly")
    @Description("Verify if the count API is giving correct response")
    @Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if the count API is giving correct response", groups = { "api", "smoke", "regression" })
	public void countAPITest() {
		dashboardService.count(FD).then().spec(responseSpec_ok()).body("message", equalTo("Success"))
				.body(matchesJsonSchemaInClasspath("response_schema/CountAPIFDResponseSchema.json"))
				.body("data", notNullValue()).body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0))).body("data.label", not(blankOrNullString()))
				.body("data.key",
						containsInAnyOrder("pending_fst_assignment", "created_today", "pending_for_delivery"));
	}

	@Test(description = "Verify if the count API is giving correct status code for invalid token", groups = { "api",
			"negative", "smoke", "regression" })
	public void countAPITest_missingAuthToken() {
		dashboardService.count().then().spec(responseSpec_TEXT(401));
	}
}
