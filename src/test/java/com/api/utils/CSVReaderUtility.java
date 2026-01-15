package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtility {
	private static final Logger LOGGER = LogManager.getLogger(CSVReaderUtility.class);
	private CSVReaderUtility() {
		
	}
	public static <T> Iterator<T> loadCSV(String pathOfCSVFile,Class<T>bean) {
		LOGGER.info("Loading the csv file from path {} "+pathOfCSVFile);
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(pathOfCSVFile);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		CSVReader csvReader = new CSVReader(inputStreamReader);
		LOGGER.info("Converting the Csv to the bean class {} "+bean);
		CsvToBean<T> csvBean = new CsvToBeanBuilder(csvReader)
				.withType(bean)
				.withIgnoreEmptyLine(true)
				.build();
		List<T> list = csvBean.parse();
	return	list.iterator();

	}

}
