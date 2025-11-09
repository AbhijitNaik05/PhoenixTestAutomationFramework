package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil {

	public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetName, Class<T> clazz) {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xlsxFile);
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet mysheet = myworkbook.getSheet(sheetName);
		List<T> list = Poiji.fromExcel(mysheet, clazz);
		return list.iterator();

	}
}
