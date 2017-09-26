package com.soecode.ton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PushDeviceDao {
	public void insert(@Param("userId")String userId,@Param("mobile")String mobile,@Param("pushToken")String pushToken);
	public void update(@Param("userId")String userId,@Param("mobile")String mobile,@Param("pushToken")String pushToken);
	public void deleteByUserId(@Param("userId")String userId);
	public void deleteByToken(@Param("pushToken")String pushToken);
	public int countToken(@Param("pushToken")String pushToken);
	public int countUserId(@Param("userId")String userId);
	public List<String> queryTokensByMobile(@Param("mobile")String mobile);
	public List<String> queryAllToken();
}
