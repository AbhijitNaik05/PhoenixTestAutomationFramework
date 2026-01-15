package com.api.services;

import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class DashboardService {
	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);
	public Response count(Role role) {
		LOGGER.info("Making request to the {} for the role {} ",COUNT_ENDPOINT,role);
		Response response = given().spec(requestSpecwithAuth(role)).when().get(COUNT_ENDPOINT);
		return response;
	}
	public Response count() {
		LOGGER.info("Making request to the {} with no auth ",COUNT_ENDPOINT);
		Response response = given().spec(SpecUtil.requestSpec()).when().get(COUNT_ENDPOINT);
		return response;
	}
	
	public Response details(Role role,Object payload) {
		LOGGER.info("Making request to the {} for the role {}and the payload {} ",DETAILS_ENDPOINT,role,payload);
		Response response = given().spec(requestSpecwithAuth(role)).body(payload).when().post(DETAILS_ENDPOINT);
		return response;
	}
}
