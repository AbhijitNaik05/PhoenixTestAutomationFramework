package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {
	private static final String CUSTOMER_DETAIL_QUERY = """
			SELECT * FROM tr_customer where id =112693;
			""";

	public static CustomerDBModel getCustomerInformation() throws SQLException {
		Connection conn = DatabaseManager.getConnection();
		Statement statement = conn.createStatement();
		ResultSet resulSet = statement.executeQuery(CUSTOMER_DETAIL_QUERY);
		CustomerDBModel customerDBModel = null;
		while (resulSet.next()) {
			System.out.println(resulSet.getString("first_name"));
			System.out.println(resulSet.getString("email_id"));

			customerDBModel = new CustomerDBModel(resulSet.getString("first_name"), resulSet.getString("last_name"),
					resulSet.getString("mobile_number"), resulSet.getString("mobile_number_alt"),
					resulSet.getString("email_id"), resulSet.getString("email_id_alt"));
		}
		return customerDBModel ;
	}
}
