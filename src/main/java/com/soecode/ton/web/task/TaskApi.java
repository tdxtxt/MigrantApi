package com.soecode.ton.web.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soecode.ton.dto.Result;
import com.soecode.ton.entity.Task;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.TaskService;

@Controller
@RequestMapping("/task") // url:/task/资源 
public class TaskApi {
	
	@Autowired
	private TaskService taskService;
	//分页获取职务
	@ResponseBody
	@RequestMapping(value = "/getTaskes", method = {RequestMethod.POST, RequestMethod.GET})
	public Result<List<Task>> getTaskes(int pageNum){
		List<Task> tasks = taskService.getTasks(pageNum);
		Result<List<Task>> result = new Result<List<Task>>(tasks);
		return result;
	}
	//发布职务,imgs表示上传的工程图片地址,多张图片需要用逗号隔开
	@ResponseBody
	@RequestMapping(value = "/releaseTask", method = {RequestMethod.POST, RequestMethod.GET})
	public Result<String> releaseTask(String areaId, String jobTypeId, String recruitNum, String price, String name,
			String userId,String desc,String detailAddress,String imgs){
		if(TextUtils.isEmpty(userId)) return new Result<>("发布者id不能为空 ");
		if(TextUtils.isEmpty(areaId)) return new Result<>("工程地址不能为空");
		if(TextUtils.isEmpty(jobTypeId)) return new Result<>("工种类型不能为空");
		String taskId;
		try {
			taskId = taskService.insertTask(areaId, jobTypeId, recruitNum, price, name, userId,desc,detailAddress,imgs);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(false,"发布失败");
		}
		return new Result<>(true,taskId,"发布成功");
	}
	/**
	 * 获取发布者账号下所有职务
	 * @param userId
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTaskesByUser", method = {RequestMethod.POST, RequestMethod.GET})
	public Result<List<Task>> getTaskesByUser(String userId, int pageNum) {
		if (TextUtils.isEmpty(userId))
			return new Result<>("发布者id不能为空 ");
		List<Task> tasks = new ArrayList<>();
		try {
			tasks = taskService.getTaskesByUser(userId, pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>("获取失败");
		}
		return new Result<>(tasks);
	}
	@ResponseBody
	@RequestMapping(value = "/deleteTaskById", method = {RequestMethod.POST, RequestMethod.GET})
	public Result<String> deleteTaskById(String taskId) {
		if (TextUtils.isEmpty(taskId)){
			return new Result<>(false,"taskId不能为空 ");
		}
		taskService.deleteTaskById(taskId);
		return new Result<>(true,"删除成功");
	}
	@ResponseBody
	@RequestMapping(value = "/getTaskById", method = {RequestMethod.POST, RequestMethod.GET})
	public Result<Task> getTaskById(String taskId){
		if (TextUtils.isEmpty(taskId)){
			return new Result<>(false,"taskId不能为空 ");
		}
		Task task = taskService.getTaskById(taskId);
		if(task == null){
			return new Result<>(false,"未有该记录");
		}
		return new Result<Task>(task);
	}
	/**
	 * 筛选
	 * @param area
	 * @param jobType
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTasksByAreaOrJobtype", method = {RequestMethod.POST, RequestMethod.GET})
	public Result<List<Task>> getTasksByAreaOrJobtype(String areaId,String jobTypeId,int pageNum){
		if(TextUtils.isEmpty(areaId) && TextUtils.isEmpty(jobTypeId)){
			return getTaskes(pageNum);
		}
		List<Task> data = taskService.getTasksByArea(areaId, jobTypeId, pageNum);
		return new Result<>(data);
	}
}