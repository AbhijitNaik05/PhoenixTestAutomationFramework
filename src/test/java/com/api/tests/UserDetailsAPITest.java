package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.UserService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("User Details")

public class UserDetailsAPITest {
	private static UserService userService;

	@BeforeMethod(description = "Intailize the user service")
	public void setup() {
		userService = new UserService();
	}

	@Story("User details should be shown")
	@Description("Verify if the Userdetails API response is shown correctly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if the Userdetails API response is shown correctly", groups = { "api", "smoke",
			"regression" })
	public void userdetailsAPITest() {

		userService.userDetails(Role.FD).then().spec(responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/UserDetailsResponseSchema.json"));
	}
}
