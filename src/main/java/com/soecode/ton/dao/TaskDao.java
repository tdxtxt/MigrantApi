package com.soecode.ton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soecode.ton.entity.Task;
import com.soecode.ton.entity.tab.TabTask;

public interface TaskDao {
	TabTask getTabById(@Param("taskId")String taskId);
	List<Task> query(int start,int pageSize);
	void insertTask(@Param("taskId")String taskId,@Param("areaId")String areaId,@Param("jobTypeId")String jobTypeId,@Param("recruitNum")String recruitNum,
			@Param("price")String price,@Param("name")String name,@Param("userId")String userId,@Param("desc")String desc,@Param("detailAddress")String detailAddress
			,@Param("pointLat") double pointLat,@Param("pointLon") double pointLon);
	void updateTask(@Param("taskId")String taskId,@Param("areaId")String areaId,@Param("jobTypeId")String jobTypeId,@Param("recruitNum")String recruitNum,
			@Param("price")String price,@Param("name")String name,@Param("userId")String userId,@Param("desc")String desc,@Param("detailAddress")String detailAddress
			,@Param("pointLat") double pointLat,@Param("pointLon") double pointLon);
	List<Task> getTaskesByUser(@Param("userId")String userId,@Param("start")int start,@Param("pageSize")int pageSize);
	int deleteTaskById(String taskId);
	Task getTaskById(@Param("id")String taskId);
	List<Task> getTasksByArea(@Param("areaId")String areaId,@Param("start")int start,@Param("pageSize")int pageSize);
	List<Task> getTasksByJobType(@Param("jobTypeId")String jobTypeId,@Param("start")int start,@Param("pageSize")int pageSize);
	List<Task> getTasksByAreaAndJobType(@Param("areaId")String areaId,@Param("jobTypeId")String jobTypeId,@Param("start")int start,@Param("pageSize")int pageSize);
}
