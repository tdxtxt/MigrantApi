package com.soecode.lyf.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.soecode.lyf.BaseTest;
import com.soecode.ton.entity.User;
import com.soecode.ton.service.UserService;

public class UserServiceImplTest extends BaseTest {

	@Autowired
	private UserService userService;

	@Test
	public void testAppoint() throws Exception {
		User user = userService.getByName("ton");
		System.out.println(user);
	}

}
