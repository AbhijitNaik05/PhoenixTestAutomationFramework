package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

import io.qameta.allure.Step;

public class MapJobProblemDao {
	private static final Logger LOGGER = LogManager.getLogger(MapJobProblemDao.class);
	private static final String PROBLEM_QUERY = """
			SELECT * FROM map_job_problem where tr_job_head_id =?
			""";

	private MapJobProblemDao() {

	}
	@Step("Retriving the problem details from database")
	public static MapJobProblemModel getProbelmDetails(int tr_job_head_id) {
		MapJobProblemModel mapJobProbModel = null;
		try {
			LOGGER.info("Getting the connecion from database manager");
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(PROBLEM_QUERY);
			ps.setInt(1, tr_job_head_id);
			LOGGER.info("Executing the sql query");
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				mapJobProbModel = new MapJobProblemModel(resultSet.getInt("id"), resultSet.getInt("tr_job_head_id"),
						resultSet.getInt("mst_problem_id"), resultSet.getString("remark"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set into JobHeadModel bean",e);
			e.printStackTrace();
		}
		return mapJobProbModel;
	}
}
