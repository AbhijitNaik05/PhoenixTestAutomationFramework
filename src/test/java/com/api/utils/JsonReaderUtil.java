package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
	public static <T> Iterator<T> loadJson(String filename, Class<T[]> clazz) {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		ObjectMapper objectMapper = new ObjectMapper();
		T[] classsArray;
		List<T> list = null;
		try {
			classsArray = objectMapper.readValue(inputStream, clazz);
			list=Arrays.asList(classsArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return list.iterator();

	}
}
