package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

import io.qameta.allure.Step;

public class CustomerProductDao {
	private static final Logger LOGGER = LogManager.getLogger(CustomerProductDao.class);
	private static final String PRODUCT_QUERY = """
			SELECT * FROM tr_customer_product where id =?
					""";

	private CustomerProductDao() {

	}
	@Step("Retriving the product details from database")
	public static CustomerProductDBModel getProductInfoFromDB(int tr_customer_product_id) {
		CustomerProductDBModel customerProductDBModel = null;
		try {
			LOGGER.info("Getting the connecion from database manager");
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(PRODUCT_QUERY);
			ps.setInt(1, tr_customer_product_id);
			LOGGER.info("Executing the sql query");
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("id"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("mst_model_id"),
						resultSet.getString("dop"), resultSet.getString("popurl"), resultSet.getString("imei2"),
						resultSet.getString("imei1"), resultSet.getString("serial_number"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set into CustomerProductDBModel bean",e);
		}
		return customerProductDBModel;
	}
}
