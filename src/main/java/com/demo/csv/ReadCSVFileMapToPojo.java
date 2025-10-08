package com.demo.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFileMapToPojo {

	public static void main(String[] args) throws IOException, CsvException {

		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/LoginCreds.csv");
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		CSVReader csvReader = new CSVReader(inputStreamReader);

		CsvToBean<UserPOJO> csvBean = new CsvToBeanBuilder(csvReader).withType(UserPOJO.class).withIgnoreEmptyLine(true)
				.build();
		List<UserPOJO> userlist = csvBean.parse();
		System.out.println(userlist);
		System.out.println(userlist.get(1).getUsername());

	}

}
