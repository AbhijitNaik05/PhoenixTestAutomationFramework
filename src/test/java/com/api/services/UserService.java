package com.api.services;

import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {
	private static final String USERDETAILS_ENDPOINT = "userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	@Step("Making user details API request with role")
	public Response userDetails(Role role) {
		LOGGER.info("Making request to {} for role {} ",USERDETAILS_ENDPOINT,role);
		Response response = given().spec(requestSpecwithAuth(role)).when().get(USERDETAILS_ENDPOINT);
		return response;
	}
}
