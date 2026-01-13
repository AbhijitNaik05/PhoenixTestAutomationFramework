package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CSVReaderUtility;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.database.dao.CreateJobPayloadDataDao;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	private static final Logger LOGGER = LogManager.getLogger(DataProviderUtils.class);

	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		LOGGER.info("Loading the data from csv testData/LoginCreds.csv ");
		return CSVReaderUtility.loadCSV("testData/LoginCreds.csv", UserBean.class);
	}

	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
		LOGGER.info("Loading the data from csv testData/CreateJobData.csv ");
		Iterator<CreateJobBean> createJobBeaniterator = CSVReaderUtility.loadCSV("testData/CreateJobData.csv",
				CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempbean;
		CreateJobPayload tempcreateJobPayload;
		while (createJobBeaniterator.hasNext()) {
			tempbean = createJobBeaniterator.next();
			tempcreateJobPayload = CreateJobBeanMapper.mapper(tempbean);
			payloadList.add(tempcreateJobPayload);
		}
		return payloadList.iterator();
	}

	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIExcelDataProvider() {
		LOGGER.info("Loading the data from excel testData/PhoenixTestData.xlsx ");
		Iterator<CreateJobBean> createJobBeaniterator = ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempbean;
		CreateJobPayload tempcreateJobPayload;
		while (createJobBeaniterator.hasNext()) {
			tempbean = createJobBeaniterator.next();
			tempcreateJobPayload = CreateJobBeanMapper.mapper(tempbean);
			payloadList.add(tempcreateJobPayload);
		}
		return payloadList.iterator();
	}

	@DataProvider(name = "CreateJobAPIDBDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDBDataProvider() {
		LOGGER.info("Loading the data from database ");
		List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (CreateJobBean createJobbean : beanList) {
			CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(createJobbean);
			payloadList.add(createJobPayload);
		}

		return payloadList.iterator();

	}

	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createFakeJobAPIDataProvider() {
		
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount);
		LOGGER.info("Generating the fake data for create job payload with fakeercount{} ",fakerCount);
		Iterator<CreateJobPayload> payloaditertaor = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloaditertaor;
	}

	@DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIJsonDataProvider() {
		LOGGER.info("Loading the data from json testData/LoginAPI.json ");
		return JsonReaderUtil.loadJson("testData/LoginAPI.json",UserBean[].class);
	}

	@DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider() {
		LOGGER.info("Loading the data from json testData/CreateJobAPIData.json ");
		return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json", CreateJobPayload[].class);
	}

	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {
		LOGGER.info("Loading the data from excel testData/PhoenixTestData.xlsx ");
		return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "LoginTestData", UserBean.class);
	}

}
