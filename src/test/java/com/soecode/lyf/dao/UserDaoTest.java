package com.soecode.lyf.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.soecode.lyf.BaseTest;
import com.soecode.ton.dao.UserDao;
import com.soecode.ton.entity.User;

public class UserDaoTest extends BaseTest {

	@Autowired
	private UserDao userDao;

	@Test
	public void testQueryByName() throws Exception {
		String userName = "ton";
		User user = userDao.queryByName(userName);
		System.out.println(user);
	}
}
