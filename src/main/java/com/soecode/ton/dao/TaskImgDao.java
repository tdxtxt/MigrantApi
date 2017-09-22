package com.soecode.ton.dao;

import org.apache.ibatis.annotations.Param;

public interface TaskImgDao {
	public void insert(@Param("id")String id,@Param("taskId")String taskId,@Param("url")String url);
	public int deleteByTaskId(@Param("taskId")String taskId);
	public void deleteById(@Param("id")String id);
}
