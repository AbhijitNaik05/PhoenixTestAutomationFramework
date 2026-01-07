package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;

import io.restassured.response.Response;

public class MasterService {
	private static final String MASTER_ENDPOINT = "master";

	public Response master(Role role) {
		Response response = given().spec(requestSpecwithAuth(role)).when().post(MASTER_ENDPOINT);
		return response;
	}

	public Response master() {
		Response response = given().spec(requestSpec()).when().post(MASTER_ENDPOINT);
		return response;
	}
}
