package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

import io.qameta.allure.Step;

public class ExcelReaderUtil {
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);
	@Step("Loading the test data from excel file")
	public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetName, Class<T> clazz) {
		LOGGER.info("Reading the test data from XLSX file {} and sheet name {}",xlsxFile,sheetName);
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xlsxFile);
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			LOGGER.error("Cannot read the excel {} ",xlsxFile,e);
			e.printStackTrace();
		}
		XSSFSheet mysheet = myworkbook.getSheet(sheetName);
		LOGGER.info("Converting the XFFFsheet {} to POJO type",mysheet);
		List<T> list = Poiji.fromExcel(mysheet, clazz);
		return list.iterator();

	}
}
