package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {
	private UserBean userCredentials;
	private AuthService authService;

	@BeforeMethod(description = "Create payload for login API")
	public void setup() {
		userCredentials = new UserBean("iamfd", "password");
		authService = new AuthService();
	}
    @Story("Valid user should be able to login into system")
    @Description("Verify if the fd user is able to login into system via api")
    @Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verify if login api is working for fd user", groups = { "api", "regression", "smoke" })
	public void loginAPITest() throws IOException {
		authService.login(userCredentials).then().spec(responseSpec_ok()).and().body("message", equalTo("Success"))
				.and().body(matchesJsonSchemaInClasspath("response_schema/LoginResponseSchema.json"));
	}
}
