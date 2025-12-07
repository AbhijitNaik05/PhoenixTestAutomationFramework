package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	private static final String PRODUCT_QUERY = """
			SELECT * FROM tr_customer_product where id =?
					""";

	private CustomerProductDao() {

	}

	public static CustomerProductDBModel getProductInfoFromDB(int tr_customer_product_id) {
		CustomerProductDBModel customerProductDBModel = null;
		try {
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(PRODUCT_QUERY);
			ps.setInt(1, tr_customer_product_id);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("id"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("mst_model_id"),
						resultSet.getString("dop"), resultSet.getString("popurl"), resultSet.getString("imei2"),
						resultSet.getString("imei1"), resultSet.getString("serial_number"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return customerProductDBModel;
	}
}
