package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerProductDBModel;

public class DaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerProductDBModel customerProductDB = CustomerProductDao.getProductInfoFromDB(114746);
		System.out.println(customerProductDB);

	}

}
