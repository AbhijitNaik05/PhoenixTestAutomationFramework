package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
	private static final Logger LOGGER = LogManager.getLogger(JsonReaderUtil.class);
	public static <T> Iterator<T> loadJson(String filename, Class<T[]> clazz) {
		LOGGER.info("Reading the Json from the file{}",filename);
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		ObjectMapper objectMapper = new ObjectMapper();
		T[] classsArray;
		List<T> list = null;
		try {
			LOGGER.info("Converting the Json data to bean class{}",clazz);
			classsArray = objectMapper.readValue(inputStream, clazz);
			list=Arrays.asList(classsArray);
		} catch (IOException e) {
			LOGGER.error("Cannot read the json from file{}",filename);
			e.printStackTrace();
		}
		 return list.iterator();

	}
}
