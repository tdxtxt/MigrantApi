package com.soecode.ton.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.dao.JobTypeDao;
import com.soecode.ton.entity.JobType;
import com.soecode.ton.service.JobTypeService;
@Service
public class JobTypeSericeImpl implements JobTypeService {
	@Autowired
	JobTypeDao jobTypeDao;
	@Override
	public List<JobType> getAllJobType() {
		return jobTypeDao.queryAll();
	}
}
