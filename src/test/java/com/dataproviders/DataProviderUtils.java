package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtility;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil2;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider (name = "LoginAPIDataProvider",parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		return CSVReaderUtility.loadCSV("testData/LoginCreds.csv",UserBean.class);
	}
	@DataProvider(name = "CreateJobAPIDataProvider",parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
		Iterator<CreateJobBean> createJobBeaniterator =CSVReaderUtility.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempbean;
		CreateJobPayload tempcreateJobPayload;
		while(createJobBeaniterator.hasNext()) {
		tempbean = createJobBeaniterator.next();
		tempcreateJobPayload =CreateJobBeanMapper.mapper(tempbean);
		payloadList.add(tempcreateJobPayload);
		}
		return payloadList.iterator();
	}
	
	@DataProvider(name = "CreateJobAPIExcelDataProvider",parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIExcelDataProvider() {
		Iterator<CreateJobBean> createJobBeaniterator =ExcelReaderUtil2.loadTestData("testData/PhoenixTestData.xlsx","CreateJobTestData",CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempbean;
		CreateJobPayload tempcreateJobPayload;
		while(createJobBeaniterator.hasNext()) {
		tempbean = createJobBeaniterator.next();
		tempcreateJobPayload =CreateJobBeanMapper.mapper(tempbean);
		payloadList.add(tempcreateJobPayload);
		}
		return payloadList.iterator();
	}
	
	@DataProvider(name = "CreateJobAPIFakerDataProvider",parallel = true)
	public static Iterator<CreateJobPayload> createFakeJobAPIDataProvider() {
		String fakerCount=System.getProperty("fakerCount", "5");
		int fakerCountInt=Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload>payloaditertaor=FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloaditertaor;
	}
	
	@DataProvider (name = "LoginAPIJsonDataProvider",parallel = true)
	public static Iterator<UserCredentials> loginAPIJsonDataProvider() {
		return JsonReaderUtil.loadJson("testData/LoginAPI.json",UserCredentials[].class);
	}
	
	@DataProvider (name = "CreateJobAPIJsonDataProvider",parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider() {
		return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json",CreateJobPayload[].class);
	}
	
	@DataProvider (name = "LoginAPIExcelDataProvider",parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {
		return ExcelReaderUtil2.loadTestData("testData/PhoenixTestData.xlsx","LoginTestData",UserBean.class);
	}
	
}
