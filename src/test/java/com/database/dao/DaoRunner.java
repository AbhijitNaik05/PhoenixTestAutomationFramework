package com.database.dao;

import java.util.ArrayList;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

public class DaoRunner {

	public static void main(String[] args) {
		List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (CreateJobBean createJobbean : beanList) {
			CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(createJobbean);
			payloadList.add(createJobPayload);
		}
		
		for (CreateJobPayload createJobPayload : payloadList) {
			System.out.println(createJobPayload);
		}

	}
}
