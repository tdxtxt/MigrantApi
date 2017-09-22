package com.soecode.ton.service;

import java.util.List;

import com.soecode.ton.entity.JobType;

public interface JobTypeService {
	List<JobType> getAllJobType();
	String getJobNameById(String id);
}
