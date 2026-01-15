package com.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobBeanMapper {
	private static final Logger LOGGER = LogManager.getLogger(CreateJobBeanMapper.class);

	private CreateJobBeanMapper() {

	}

	public static CreateJobPayload mapper(CreateJobBean bean) {
		LOGGER.info("Converting the create job bean {} to createJobpayload",bean);
		int Mstservicelocationid = Integer.parseInt(bean.getMst_service_location_id());
		int mstplatformid = Integer.parseInt(bean.getMst_platform_id());
		int mstwarrentystatusid = Integer.parseInt(bean.getMst_warrenty_status_id());
		int Mstoemid = Integer.parseInt(bean.getMst_oem_id());

		Customer customer = new Customer(bean.getCustomer__first_name(), bean.getCustomer__last_name(),
				bean.getCustomer__mobile_number(), bean.getCustomer__mobile_number_alt(), bean.getCustomer__email_id(),
				bean.getCustomer__email_id_alt());

		CustomerAddress customerAddress = new CustomerAddress(bean.getCustomer_address__flat_number(),
				bean.getCustomer_address__apartment_name(), bean.getCustomer_address__street_name(),
				bean.getCustomer_address__landmark(), bean.getCustomer_address__area(),
				bean.getCustomer_address__pincode(), bean.getCustomer_address__country(),
				bean.getCustomer_address__state());

		int productId = Integer.parseInt(bean.getCustomer_product__product_id());
		int modelid = Integer.parseInt(bean.getCustomer_product__mst_model_id());

		CustomerProduct customerProduct = new CustomerProduct(bean.getCustomer_product__dop(),
				bean.getCustomer_product__serial_number(), bean.getCustomer_product__imei1(),
				bean.getCustomer_product__imei2(), bean.getCustomer_product__popurl(), productId, modelid);

		List<Problems> problemList = new ArrayList<Problems>();
		int problemId = Integer.parseInt(bean.getProblems__id());

		Problems problems = new Problems(problemId, bean.getProblems__remark());
		problemList.add(problems);
		CreateJobPayload payload = new CreateJobPayload(Mstservicelocationid, mstplatformid, mstwarrentystatusid,
				Mstoemid, customer, customerAddress, customerProduct, problemList);
		LOGGER.info("Converted the create job bean to createJobpayload {}",payload);
		return payload;

	}
}
