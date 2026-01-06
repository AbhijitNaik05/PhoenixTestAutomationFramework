package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.UserService;

public class UserDetailsAPITest {
	private static UserService userService;

	@BeforeMethod(description = "Intailize the user service")
	public void setup() {
		userService = new UserService();
	}

	@Test(description = "Verify if the Userdetails API response is shown correctly", groups = { "api", "smoke",
			"regression" })
	public void userdetailsAPITest() {

		userService.userDetails(Role.FD).then().spec(responseSpec_ok())
				.body(matchesJsonSchemaInClasspath("response_schema/UserDetailsResponseSchema.json"));
	}
}
