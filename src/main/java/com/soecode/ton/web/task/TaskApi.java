package com.soecode.ton.web.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soecode.ton.dto.Result;
import com.soecode.ton.entity.Task;
import com.soecode.ton.entity.tab.TabTask;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.JobTypeService;
import com.soecode.ton.service.PushDeviceService;
import com.soecode.ton.service.TaskService;

import cn.jpush.api.JPushHelper;

@Controller
@RequestMapping("/task") // url:/task/资源 
public class TaskApi {
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private PushDeviceService pushService;
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
			String userId,String desc,String detailAddress,Double pointLat,Double pointLon,String imgs){
		if(TextUtils.isEmpty(userId)) return new Result<>("发布者id不能为空 ");
		if(TextUtils.isEmpty(areaId)) return new Result<>("工程地址不能为空");
		if(TextUtils.isEmpty(jobTypeId)) return new Result<>("工种类型不能为空");
		String taskId;
		try {
			taskId = taskService.insertTask(areaId, jobTypeId, recruitNum, price, name, userId,desc,detailAddress,
					pointLat == null ? 0 : pointLat,pointLon == null ? 0 : pointLon,imgs);
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			return new Result<>(false,"发布失败:" + errors);
		}
		try {
			String jobTypeName = jobTypeService.getJobNameById(jobTypeId);
			String taskdesc = TextUtils.isEmpty(name) ? "工程名称" + "(招聘" + jobTypeName + ")" : "工程名称:" + name + "\n" + "(招聘" + jobTypeName + ")";
			List<String> tagValues = pushService.getAllToken();
			JPushHelper.getInstance().sendTaskToClientUser(tagValues,taskdesc);
		} catch (Exception e) {
			return new Result<>(true,taskId,"发布成功");
		}
		return new Result<>(true,taskId,"发布成功");
	}
	//编辑已发布的职务
	@ResponseBody
	@RequestMapping(value = "/editTask",method = {RequestMethod.POST,RequestMethod.GET})
	public Result<String> editTask(String taskId,String areaId,String jobTypeId,String recruitNum,String price,String name,
			String userId,String desc,String detailAddress,Double pointLat,Double pointLon,String imgs){
		if(TextUtils.isEmpty(taskId)) return new Result<>("修改职务id不能为空");
		if(TextUtils.isEmpty(areaId)) return new Result<>("工程地址不能为空");
		if(TextUtils.isEmpty(jobTypeId)) return new Result<>("工种类型不能为空");
		TabTask tabTask = new TabTask();
		tabTask.id = taskId;
		tabTask.areaId = areaId;
		tabTask.jobTypeId = jobTypeId;
		tabTask.recruitNum = recruitNum;
		tabTask.price = price;
		tabTask.name = name;
		tabTask.userId = userId;
		tabTask.desc = desc;
		tabTask.detailAddress = detailAddress;
		tabTask.pointLat = pointLat == null ? 0 : pointLat;
		tabTask.pointLon = pointLon == null ? 0 : pointLon;
		boolean isOk = false;
		try {
			isOk = taskService.modifyById(tabTask);
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			return new Result<>(false,"修改过失败:" + errors);
		}
		if(isOk){
			return new Result<String>(true,taskId,"修改成功");
		}
		return new Result<>(false,"不存在taskId");
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