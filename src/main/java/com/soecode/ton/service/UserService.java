package com.soecode.ton.service;

import java.util.List;

import com.soecode.ton.entity.User;

public interface UserService {
	/**
	 * 新增用户
	 * @param user
	 */
	User addUser(String otherBundId,String userName,String mobile,String password,String type,String areaId,String jobTypeId,String senior);
	/**
	 * 查询用户
	 * 
	 * @param userName
	 * @return
	 */
	User getByName(String userName);
	/**
	 * 查询用户
	 * @param mobile
	 * @return
	 */
	User getByMobile(String mobile);
	/**
	 * 查询用户
	 * @param userName
	 * @return
	 */
	User getById(String userId);
	/**
	 * 查询用户
	 * @param otherBundId
	 * @return
	 */
	User getByOtherBundId(String otherBundId);
	/**
	 * 修改用户
	 * @param user
	 */
	boolean modifyById(User user);
	boolean login(String mobile,String password) throws Exception;
	boolean otherLogin(String otherBundId) throws Exception;
	List<User> getUsePeoples(int pageNum)throws Exception;
}
