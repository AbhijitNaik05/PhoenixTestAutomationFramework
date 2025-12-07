package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

public class MapJobProblemDao {
	private static final String PROBLEM_QUERY = """
			SELECT * FROM map_job_problem where tr_job_head_id =?
			""";

	private MapJobProblemDao() {

	}

	public static MapJobProblemModel getProbelmDetails(int tr_job_head_id) {
		MapJobProblemModel mapJobProbModel = null;
		try {
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(PROBLEM_QUERY);
			ps.setInt(1, tr_job_head_id);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				mapJobProbModel = new MapJobProblemModel(resultSet.getInt("id"), resultSet.getInt("tr_job_head_id"),
						resultSet.getInt("mst_problem_id"), resultSet.getString("remark"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapJobProbModel;
	}
}
