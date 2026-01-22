package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.qameta.allure.Step;

public class DatabaseManager {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecrets("DB_URL");
	private static final String DB_USER_NAME = loadSecrets("DB_USER_NAME");
	private static final String DB_PASSWORD = loadSecrets("DB_PASSWORD");
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
	
	@Step("Loading data base screte")
	public static String loadSecrets(String key) {
		String value = null;
		if (isVaultUp) {
			value = VaultDBConfig.getSecrete(key);

			if (value == null) {
				LOGGER.error("Vault is down or some issue with vault");
				isVaultUp = false;
			} else {
				LOGGER.info("Reading the value for Key{} from Vault",key);
				return value;
			}
		}
		LOGGER.info("Reading data from env");
		value = EnvUtil.getValue(key);
		return value;
	}

	private DatabaseManager() {

	}
	@Step("Intializing the database connection pool")
	public static void intializePool() throws SQLException {
		if (hikariDataSource == null) {
			LOGGER.warn("Database connection is not avilable....Creating Hikari datasource");
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
					LOGGER.info("Hikari datasource created");
				}

			}
		}
	}
	@Step("Getting the database connection")
	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			LOGGER.info("Intializing the database connection using hikari cp");
			intializePool();
		} else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIkari Datasource is closed");
			throw new SQLException("HIkari Datasource is closed");
		}

		conn = hikariDataSource.getConnection();

		return conn;
	}
}
