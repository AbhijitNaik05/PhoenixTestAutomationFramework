package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	public static RequestSpecification requestSpec() {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return requestSpecification;
	}

	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter())
				.log(LogDetail.URI).log(LogDetail.METHOD).log(LogDetail.HEADERS).build();

		return requestSpecification;
	}

	public static RequestSpecification requestSpecwithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).log(LogDetail.URI).log(LogDetail.METHOD).log(LogDetail.HEADERS)
				.log(LogDetail.BODY).build();
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecwithAuth(Role role,Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).setBody(payload)
				.log(LogDetail.URI).log(LogDetail.METHOD).log(LogDetail.HEADERS)
				.log(LogDetail.BODY).build();
		return requestSpecification;
	}

	public static ResponseSpecification responseSpec_ok() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int StatusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(StatusCode).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int StatusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
				.expectStatusCode(StatusCode).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;
	}
}
