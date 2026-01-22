package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.SpecUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
	private DashboardService dashboardService;
	private Detail detail;

	@BeforeMethod(description = "Intiataiting the dashboard service and creating detail payload")
	public void setup() {
		dashboardService = new DashboardService();
		detail = new Detail("created_today");
	}
	@Story("Job details is shown correctly for fd")
	@Description("Verify if details api working properly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if details api working properly", groups = { "api", "smoke", "e2e" })
	public void detailAPITest() {
		dashboardService.details(FD, detail).then().spec(SpecUtil.responseSpec_ok()).body("message",
				equalToIgnoringCase("success"));
	}
}
