package com.soecode.ton.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.comm.Constant;
import com.soecode.ton.dao.TaskDao;
import com.soecode.ton.dao.TaskImgDao;
import com.soecode.ton.entity.Task;
import com.soecode.ton.entity.tab.TabTask;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskImgDao taskImgDao;
	@Override
	public List<Task> getTasks(int pageNum) {
		if(pageNum < 1) pageNum = 1;
		int start = (pageNum - 1) * Constant.PAGE_SIZE;
		List<Task> tasks = taskDao.query(start,Constant.PAGE_SIZE);
		return tasks;
	}

	@Override
	public String insertTask(String areaId, String jobTypeId, String recruitNum, String price, String name,
			String userId,String desc,String detailAddress,double pointLat,double pointLon,String imgs) throws Exception{
		String taskId = UUID.randomUUID().toString();
		taskDao.insertTask(taskId, areaId, jobTypeId, recruitNum, price, name, userId,desc,detailAddress,pointLat,pointLon);
		if(!TextUtils.isEmpty(imgs)){
			String[] strs = imgs.split(",");
			for (String img : strs) {
				taskImgDao.insert(UUID.randomUUID().toString(),taskId, img);
			}
		}
		return taskId;
	}
	@Override
	public boolean modifyById(TabTask task) throws Exception {
		TabTask localTask = taskDao.getTabById(task.id);
		if(localTask == null) return false;
		if(!TextUtils.isEmpty(task.name)) localTask.name = task.name;
		if(!TextUtils.isEmpty(task.areaId)) localTask.areaId = task.areaId;
		if(!TextUtils.isEmpty(task.jobTypeId)) localTask.jobTypeId = task.jobTypeId;
		if(!TextUtils.isEmpty(task.recruitNum)) localTask.recruitNum = task.recruitNum;
		if(!TextUtils.isEmpty(task.price)) localTask.price = task.price;
		if(!TextUtils.isEmpty(task.userId)) localTask.userId = task.userId;
		if(!TextUtils.isEmpty(task.desc)) localTask.desc = task.desc;
		if(!TextUtils.isEmpty(task.detailAddress)) localTask.detailAddress = task.detailAddress;
		if(task.pointLat != 0) localTask.pointLat = task.pointLat;
		if(task.pointLon != 0) localTask.pointLon = task.pointLon;
		
		taskDao.updateTask(localTask.id, localTask.areaId, localTask.jobTypeId, localTask.recruitNum, 
				localTask.price, localTask.name, localTask.userId, localTask.desc, localTask.detailAddress, localTask.pointLat, localTask.pointLon);
		return true;
	}
	@Override
	public List<Task> getTaskesByUser(String userId,int pageNum) throws Exception {
		if(pageNum < 1) pageNum = 1;
		int start = (pageNum - 1) * Constant.PAGE_SIZE;
		return taskDao.getTaskesByUser(userId,start,Constant.PAGE_SIZE);
	}

	@Override
	public boolean deleteTaskById(String taskId) {
		try {
			taskDao.deleteTaskById(taskId);
			taskImgDao.deleteByTaskId(taskId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Task getTaskById(String taskId) {
		return taskDao.getTaskById(taskId);
	}

	@Override
	public List<Task> getTasksByArea(String areaId, String jobTypeId, int pageNum) {
		if(pageNum < 1) pageNum = 1;
		int start = (pageNum - 1) * Constant.PAGE_SIZE;
		
		if(!TextUtils.isEmpty(areaId) && !TextUtils.isEmpty(jobTypeId)){
			return taskDao.getTasksByAreaAndJobType(areaId, jobTypeId, start, Constant.PAGE_SIZE);
		}
		if(!TextUtils.isEmpty(areaId)){
			return taskDao.getTasksByArea(areaId, start, Constant.PAGE_SIZE);
		}
		if(!TextUtils.isEmpty(jobTypeId)){
			return taskDao.getTasksByJobType(jobTypeId, start, Constant.PAGE_SIZE);
		}
		return null;
	}
}
