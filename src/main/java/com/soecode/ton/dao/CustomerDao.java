package com.soecode.ton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soecode.ton.entity.tab.Customer;

public interface CustomerDao {
	public List<Customer> queryByType(@Param("whereCase")String whereCase);
}
