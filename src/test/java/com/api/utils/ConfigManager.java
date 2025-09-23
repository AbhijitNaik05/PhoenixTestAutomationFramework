package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private ConfigManager() {

	}

	private static Properties properties = new Properties();
	private static String path = "config/config.properties";
	private static String env;
	static {
		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		System.out.println("Running on " + env);
		switch (env) {
		case "dev" -> path = "config/config.dev.properties";
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		default -> path = "config/config.properties";
		}
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (input == null) {
			throw new RuntimeException("Cannot found the file path" + path);
		}
		try {

			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static String getProperty(String key) throws IOException {

		return properties.getProperty(key);
	}

}
