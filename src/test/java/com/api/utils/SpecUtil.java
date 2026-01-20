package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	@Step("Setting up of BASEURI, contentType application Json  and attaching the sensitive data filter")
	public static RequestSpecification requestSpec() {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter()).build();

		return requestSpecification;
	}
	@Step("Setting up of BASEURI, contentType application Json  and attaching the sensitive data filter for payload")
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter()).build();

		return requestSpecification;
	}
	@Step("Setting up of BASEURI, contentType application Json  and attaching the sensitive data filter for role")
	public static RequestSpecification requestSpecwithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter()).build();
		return requestSpecification;
	}
	@Step("Setting up of BASEURI, contentType application Json  and attaching the sensitive data filter for role and payload")
	public static RequestSpecification requestSpecwithAuth(Role role, Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).setBody(payload).addFilter(new SensitiveDataFilter()).build();
		return requestSpecification;
	}
	@Step("Expecting the response to have content type as application/Json status code as 200 and response time is less than 1000 mili second ")
	public static ResponseSpecification responseSpec_ok() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;
	}

	@Step("Expecting the response to have content type as application/Json, response time is less than 1000 mili second and status code")
	public static ResponseSpecification responseSpec_JSON(int StatusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(StatusCode).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;
	}
	@Step("Expecting the response to have content type as text, response time is less than 1000 mili second and status code")
	public static ResponseSpecification responseSpec_TEXT(int StatusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(StatusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;
	}
}
