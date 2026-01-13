package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.restassured.response.Response;

public class MasterService {
	private static final String MASTER_ENDPOINT = "master";
	private static final Logger LOGGER = LogManager.getLogger(MasterService.class);
	public Response master(Role role) {
		LOGGER.info("Making request to {} for role {} ",MASTER_ENDPOINT,role);
		Response response = given().spec(requestSpecwithAuth(role)).when().post(MASTER_ENDPOINT);
		return response;
	}

	public Response master() {
		LOGGER.info("Making request to {} ",MASTER_ENDPOINT);
		Response response = given().spec(requestSpec()).when().post(MASTER_ENDPOINT);
		return response;
	}
}
