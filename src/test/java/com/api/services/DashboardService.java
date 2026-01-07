package com.api.services;

import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class DashboardService {
	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";

	public Response count(Role role) {
		Response response = given().spec(requestSpecwithAuth(role)).when().get(COUNT_ENDPOINT);
		return response;
	}
	public Response count() {
		Response response = given().spec(SpecUtil.requestSpec()).when().get(COUNT_ENDPOINT);
		return response;
	}
	
	public Response details(Role role,Object payload) {
		Response response = given().spec(requestSpecwithAuth(role)).body(payload).when().post(DETAILS_ENDPOINT);
		return response;
	}
}
