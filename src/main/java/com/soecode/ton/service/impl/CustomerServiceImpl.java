package com.soecode.ton.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.dao.CustomerDao;
import com.soecode.ton.entity.tab.Customer;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao customerDao;
	
	@Override
	public List<String> getCustomer(String... types) throws Exception {
		StringBuffer sqlCase = new StringBuffer();
		sqlCase.append("WHERE type = " + TextUtils.join(" OR type = ", types));
		List<Customer> data = customerDao.queryByType(sqlCase.toString());
		List<String> result = new ArrayList<String>();
		if(data != null && data.size() > 0){
			for (Customer item : data) {
				result.add(item.getMoblie());
			}
		}else{
			result.add("13220286010");
		}
		return result;
	}

}
