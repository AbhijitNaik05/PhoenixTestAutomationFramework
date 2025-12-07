package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.JobHeadModel;

public class JobHeadDao {
	private static final String JOB_HEAD_QUERY = """
			SELECT * FROM tr_job_head where tr_customer_id=?
				""";

	private JobHeadDao() {

	}

	public static JobHeadModel getJobHeadFromDB(int tr_customer_id) {
		JobHeadModel jobHeadModel = null;
		try {
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(JOB_HEAD_QUERY);
			ps.setInt(1, tr_customer_id);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				jobHeadModel = new JobHeadModel(resultSet.getInt("id"), resultSet.getString("job_number"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("tr_customer_product_id"),
						resultSet.getInt("mst_service_location_id"), resultSet.getInt("mst_platform_id"),
						resultSet.getInt("mst_warrenty_status_id"), resultSet.getInt("mst_oem_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobHeadModel;
	}
}
