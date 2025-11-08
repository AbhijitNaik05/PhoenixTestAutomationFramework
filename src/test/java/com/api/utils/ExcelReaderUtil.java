package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myworkbook = new XSSFWorkbook(inputStream);
		XSSFSheet mysheet = myworkbook.getSheet("LoginTestData");
		XSSFRow myrow ;
		XSSFCell mycell ;
		int lastRowIndex =mysheet.getLastRowNum();
		System.out.println(lastRowIndex);
		XSSFRow rowHeader =mysheet.getRow(0);
		int lastCellIndex=rowHeader.getLastCellNum()-1;
		System.out.println(lastCellIndex);
		for(int rowIndex=0;rowIndex<=lastRowIndex;rowIndex++) {
			for(int colIndex=0;colIndex<=lastCellIndex;colIndex++) {
				myrow=mysheet.getRow(rowIndex);
				mycell=myrow.getCell(colIndex);
				System.out.print(mycell+" ");
			}
			System.out.println("");
		}

	}

}
