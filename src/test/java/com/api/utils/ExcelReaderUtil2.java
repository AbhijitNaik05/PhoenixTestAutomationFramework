package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	public static Iterator<UserCredentials> loadTestData()  {
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet mysheet = myworkbook.getSheet("LoginTestData");
		XSSFRow headerRows = mysheet.getRow(0);
		int userNameIndex = -1;
		int passwordIndex = -1;
		for (Cell cell : headerRows) {
			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				userNameIndex = cell.getColumnIndex();
			}
			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}
		int lastRowIndex = mysheet.getLastRowNum();
		XSSFRow rowdata;
		UserCredentials userCredentials;
		ArrayList<UserCredentials> userList = new ArrayList<UserCredentials>();
		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
			rowdata = mysheet.getRow(rowIndex);
			userCredentials = new UserCredentials(rowdata.getCell(userNameIndex).toString(),
					rowdata.getCell(passwordIndex).toString());
			userList.add(userCredentials);
			
		}
		return userList.iterator();
		
	}
}
