package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private static final int MAX_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MIN_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE-COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SEC = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SEC"));
	private static final int SET_IDLE_TIMEOUT_IN_SEC = Integer
			.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SEC"));
	private static final int SET_MAX_LIFE_TIME_IN_MIN = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MIN"));
	private static final String POOL_NAME = ConfigManager.getProperty("POOL_NAME");
	private static HikariConfig hikariConfig;
	private static volatile HikariDataSource hikariDataSource = null;
	private volatile static Connection conn;

	private DatabaseManager() {

	}

	public static void intializePool() throws SQLException {
		if (hikariDataSource == null) {
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USER_NAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAX_POOL_SIZE);
					hikariConfig.setMinimumIdle(MIN_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SEC * 1000);
					hikariConfig.setIdleTimeout(SET_IDLE_TIMEOUT_IN_SEC * 1000);
					hikariConfig.setMaxLifetime(SET_MAX_LIFE_TIME_IN_MIN * 60 * 1000);// 30 min
					hikariConfig.setPoolName(POOL_NAME);

					hikariDataSource = new HikariDataSource(hikariConfig);
				}

			}
		}
	}

	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			intializePool();
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIkari Datasource is closed");
		}

		conn = hikariDataSource.getConnection();

		return conn;
	}
}
