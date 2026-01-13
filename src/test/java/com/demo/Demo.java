package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {

	private static Logger logger = LogManager.getLogger(Demo.class);

	public static void main(String[] args) {
		logger.info(" Inside the main method");
		int a = 10;
		logger.info(" value of a {} ", a);
		int b = 0;
		if (b == 0) {
			logger.warn(" value of b{}", b);
		} else {
			logger.info("value of b{}", b);
		}
		int result = 0;
		try {
			result =a/b;
			logger.info( "value of result{}", result);
		}
		catch(ArithmeticException e) {
			logger.error("Opertaion cannot happer"+e.getMessage());
		}

		logger.info(" value of result {} ", result);
		System.out.println("result is " + result);
	}

}
