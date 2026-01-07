package com.api.services;

import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {
	private static final String CREATE_JOB_ENDPOINT = "job/create";
	private static final String JOB_SEARCH_ENDPOINT = "job/search";

	public Response createJob(Role role, CreateJobPayload createJobPayload) {
		Response response=given().spec(requestSpecwithAuth(role, createJobPayload)).and().when().post(CREATE_JOB_ENDPOINT);
		return response;
	}
	
	public Response search(Role role, Object payload) {
		Response response=given().spec(requestSpecwithAuth(role)).body(payload).and().when().post(JOB_SEARCH_ENDPOINT);
		return response;
	}
}
