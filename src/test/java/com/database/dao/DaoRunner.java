package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBModel=CustomerDao.getCustomerInformation();
		System.out.println(customerDBModel);
		System.out.println(customerDBModel.getFirst_name());
		System.out.println(customerDBModel.getLast_name());
		System.out.println(customerDBModel.getMobile_number());
		System.out.println(customerDBModel.getEmail_id());
		Customer customer = new Customer("Hunter", "Koelpin", "417-516-8366", null, "Crystal.Schuppe@gmail.com", null);
		Assert.assertEquals(customerDBModel.getFirst_name(), customer.first_name());

	}

}
