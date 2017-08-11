package com.soecode.ton.service;

import java.util.List;

import com.soecode.ton.dto.ReUser;
import com.soecode.ton.entity.User;

public interface UserService {
	/**
	 * 新增用户
	 * @param user
	 */
	ReUser addUser(String otherBundId,String userName,String mobile,String password,String type,String areaId,String jobTypeId,String senior,String workyear) throws Exception;
	/**
	 * 查询用户
	 * 
	 * @param userName
	 * @return
	 */
	ReUser getByName(String userName);
	/**
	 * 查询用户
	 * @param mobile
	 * @return
	 */
	ReUser getByMobile(String mobile);
	/**
	 * 查询用户
	 * @param userName
	 * @return
	 */
	ReUser getById(String userId);
	/**
	 * 查询用户
	 * @param otherBundId
	 * @return
	 */
	ReUser getByOtherBundId(String otherBundId);
	/**
	 * 修改用户
	 * @param user
	 */
	boolean modifyById(User user);
	boolean login(String mobile,String password) throws Exception;
	boolean otherLogin(String otherBundId) throws Exception;
	List<ReUser> getUsePeoples(int pageNum)throws Exception;
}
