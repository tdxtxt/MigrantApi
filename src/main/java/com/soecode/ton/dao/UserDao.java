package com.soecode.ton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soecode.ton.dto.ReUser;
import com.soecode.ton.entity.User;

public interface UserDao {
	/**
	 * 插入数据
	 * @param userId
	 * @param userName
	 * @param mobile
	 * @param password
	 * @param type
	 * @param areaId
	 * @param jobTypeId
	 * @param senior
	 * @return
	 */
	void insertUser(@Param("userId") String userId,@Param("otherbundId") String otherbundId , @Param("userName") String userName, @Param("mobile") String mobile,
			@Param("password") String password, @Param("type") int type, @Param("areaId") String areaId,
			@Param("jobTypeId") String jobTypeId, @Param("senior") String senior,@Param("workyear") String workyear);

	/**
	 * 通过用户名称获取用户资料
	 * 
	 * @param name
	 * @return
	 */
	ReUser queryByName(String userName);

	/**
	 * 通过用户ID获取用户资料
	 * 
	 * @param id
	 * @return
	 */
	ReUser queryById(String userId);
	/**
	 * 通过用户ID获取用户资料
	 * @param id
	 * @return
	 */
	User queryUserById(String userId);
	/**
	 * 通过bindid获取用户资料
	 * 
	 * @param name
	 * @return
	 */
	ReUser queryByOtherBundId(String otherBundId);
	/**
	 * 通过用户mobile获取用户资料
	 * @param mobile
	 * @return
	 */  
	ReUser queryByMobile(String mobile);
	/**
	 * 更新数据
	 * 
	 * @param userId
	 * @param userName
	 * @param mobile
	 * @param areaId
	 * @param jobTypeId
	 * @param senior
	 */
	void modifyById(@Param("userId") String userId, @Param("userName") String userName, @Param("mobile") String mobile,
			@Param("areaId") String areaId, @Param("jobTypeId") String jobTypeId, @Param("senior") String senior,@Param("userState") int userState,@Param("workyear") String workyear);
	void modifyPwdById(@Param("userId")String userId,@Param("password")String password);
	/**
	 * 查询mobile在数据库的条数
	 * 
	 * @param mobile
	 * @return
	 */
	int countMobile(@Param("mobile")String mobile);
	/**
	 * 查询mobile在数据库的条数
	 * 
	 * @param mobile
	 * @return
	 */
	int countMobileAndType(@Param("mobile")String mobile,@Param("type")String type);
	/**
	 * 查询userId在数据库的条数
	 * 
	 * @param mobile
	 * @return
	 */
	int countOtherBundId(String otherBundId);
	/**
	 * 根据mobile查询密码
	 * @param mobile
	 * @return
	 */
	String queryPwdByMobile(String mobile);
	/**
	 * 获取空闲工人信息
	 * @return
	 */
	List<ReUser> getAllPeoples(@Param("start")int start, @Param("pageSize")int pageSize);
	/**
	 * 获取空闲工人信息
	 * @return
	 */
	List<ReUser> getUsePeoples(@Param("userState")String userState,@Param("start")int start, @Param("pageSize")int pageSize);
	/**
	 * 获取空闲工人信息
	 * @return
	 */
	List<ReUser> getJobPeoples(@Param("jobTypeId")String jobTypeId,@Param("start")int start, @Param("pageSize")int pageSize);
	/**
	 * 获取空闲工人信息
	 * @return
	 */
	List<ReUser> getJobAndUsePeoples(@Param("userState")String userState,@Param("jobTypeId")String jobTypeId,@Param("start")int start, @Param("pageSize")int pageSize);
}
