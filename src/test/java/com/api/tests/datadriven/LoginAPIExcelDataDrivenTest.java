package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;
@Listeners(com.listeners.APITestListener.class)
public class LoginAPIExcelDataDrivenTest {

	private AuthService authService;

	@BeforeMethod(description = "setting up the Auth service")
	public void setup() {
		authService = new AuthService();
	}

	@Test(description = "Verify if login api is working for fd user", groups = { "api", "regression",
			"datadriven" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "LoginAPIExcelDataProvider")
	public void loginAPITest(UserBean userBean) {

		authService.login(userBean).then().spec(responseSpec_ok()).and().body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response_schema/LoginResponseSchema.json"));
	}
}
