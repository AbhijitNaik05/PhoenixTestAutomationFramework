package com.api.services;

import static com.api.utils.SpecUtil.requestSpecwithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {
	private static final String CREATE_JOB_ENDPOINT = "job/create";
	private static final String JOB_SEARCH_ENDPOINT = "job/search";
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);
	public Response createJob(Role role, CreateJobPayload createJobPayload) {
		LOGGER.info("Making request for {} with role {} and payload {}",CREATE_JOB_ENDPOINT,role,createJobPayload);
		Response response=given().spec(requestSpecwithAuth(role, createJobPayload)).and().when().post(CREATE_JOB_ENDPOINT);
		return response;
	}
	
	public Response search(Role role, Object payload) {
		LOGGER.info("Making request to {} with role {} and payload {}",JOB_SEARCH_ENDPOINT,role,payload);
		Response response=given().spec(requestSpecwithAuth(role)).body(payload).and().when().post(JOB_SEARCH_ENDPOINT);
		return response;
	}
}
