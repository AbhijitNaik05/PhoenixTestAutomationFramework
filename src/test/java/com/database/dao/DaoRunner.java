package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;

public class DaoRunner {

	public static void main(String[] args) throws SQLException {
		JobHeadModel jobHeaddata=JobHeadDao.getJobHeadFromDB(114913);
		System.out.println(jobHeaddata);

	}

}
