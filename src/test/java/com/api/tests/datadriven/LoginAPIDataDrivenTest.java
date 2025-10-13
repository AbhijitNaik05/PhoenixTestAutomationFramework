package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_ok;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.dataproviders.api.bean.UserBean;

public class LoginAPIDataDrivenTest {
	
	@Test(description = "Verify if login api is working for fd user", 
			groups = { "api", "regression", "datadriven" },
			dataProviderClass = com.dataproviders.DataProviderUtils.class,dataProvider = "LoginAPIDataProvider"
	)
	public void loginAPITest(UserBean userBean) {

		given().spec(requestSpec(userBean)).when().post("login").then().spec(responseSpec_ok())
				.and().body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response_schema/LoginResponseSchema.json"));
	}
}
