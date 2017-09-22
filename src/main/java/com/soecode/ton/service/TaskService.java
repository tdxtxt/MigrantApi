package com.soecode.ton.service;

import java.util.List;

import com.soecode.ton.entity.Task;
import com.soecode.ton.entity.tab.TabTask;

public interface TaskService {
	public List<Task> getTasks(int pageNum);
	public String insertTask(String areaId,String jobTypeId,String recruitNum,String price,String name,String userId,String desc,String detailAddress,
			double pointLat,double pointLon,String imgs) throws Exception;
	public boolean modifyById(TabTask task) throws Exception;
	public List<Task> getTaskesByUser(String userId,int pageNum) throws Exception;
	public boolean deleteTaskById(String taskId);
	public Task getTaskById(String taskId);
	public List<Task> getTasksByArea(String areaId,String jobTypeId,int pageNum);
}
