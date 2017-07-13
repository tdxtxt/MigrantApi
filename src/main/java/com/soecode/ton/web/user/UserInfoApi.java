package com.soecode.ton.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soecode.ton.dto.Result;
import com.soecode.ton.entity.User;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.UserService;
import com.soecode.ton.web.BaseController;

@Controller
@RequestMapping("/u") // url:/u/资源 
public class UserInfoApi extends BaseController{
	@Autowired
	private UserService userService;
	@ResponseBody
	@RequestMapping(value = "/userInformation", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<User> getUserByName(String userName,String userId) {
		if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userId)){
			return new Result<User>("传参为空");
		}
		if(!TextUtils.isEmpty(userId)){
			User user = userService.getById(userId);
			if(null == user){
				return new Result<User>("不存在该用户");
			}else{
				return new Result<User>(user);
			}
		}
		
		if(!TextUtils.isEmpty(userName)){
			User user = userService.getByName(userName);
			if(null == user){
				return new Result<User>("不存在该用户");
			}else{
				return new Result<User>(user);
			}
		}
		
		return new Result<User>("不存在该用户");
	}
	@ResponseBody
	@RequestMapping(value = "/modify", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<User> modify(String userId,String userName,String mobile,String areaId,String jobTypeId,String senior){
		User user = new User();
		user.setAreaId(areaId);
		user.setJobTypeId(jobTypeId);
		user.setMobile(mobile);
		user.setSenior(senior);
		user.setUserId(userId);
		user.setUserName(userName);
		
		boolean success = userService.modifyById(user);
		if(success){
			User newUser = userService.getById(userId);
			return new Result<User>(newUser,"修改成功");
		}else{
			return new Result<User>("userId有误");
		}
		
	}
	/**
	 * 数据库中userId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值mobile,password,type必须上传
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<User> register(String userName,String password,String type,String mobile,String areaId,String jobTypeId,String senior){
		if(TextUtils.isEmpty(mobile)) return new Result<>("请输入手机号");
		if(TextUtils.isEmpty(password)) return new Result<>("请输入登录密码");
		if(!(!TextUtils.isEmpty(type) && ("0".equals(type) || "1".equals(type)))){
			return new Result<>("注册用户类型格式错误,必须为0或1");
		}
		User user = userService.addUser("",userName, mobile, password, type, areaId, jobTypeId, senior);
		if(user == null) return new Result<>("该手机号已注册");
		return new Result<User>(user,"注册成功");
	}
	/**
	 * 数据库中otherBundId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值otherBundId,mobile,type必须上传
	 */
	@ResponseBody
	@RequestMapping(value = "/bundOtherLogin", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<User> bundOtherLogin(String otherBundId,String type,String mobile,String areaId,String jobTypeId,String senior){
		if(TextUtils.isEmpty(otherBundId)) return new Result<>("请上传三方登录id");
		if(TextUtils.isEmpty(mobile)) return new Result<>("请输入手机号");
		if(!(!TextUtils.isEmpty(type) && ("0".equals(type) || "1".equals(type)))){
			return new Result<>("注册用户类型格式错误,必须为0或1");
		}
		User user = userService.addUser(otherBundId, mobile, mobile, "123456", type, areaId, jobTypeId, senior);
		if(user == null) return new Result<>("该手机号已绑定");
		return new Result<User>(user,"绑定成功");
	}
	@ResponseBody
	@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<User> login(String mobile,String password){
		if(TextUtils.isEmpty(mobile)) return new Result<>("请输入手机号");
		if(TextUtils.isEmpty(password)) return new Result<>("请输入登录密码");
		boolean success = false;
		try {
			success = userService.login(mobile, password);
		} catch (Exception e) {
			return new Result<User>("服务器内部错误:" + e.getMessage());
		}
		if(success){
			User user = userService.getByMobile(mobile);
			return new Result<User>(user,"登录成功");
		}
		return new Result<User>("登录密码不正确");
	}
	/**
	 * 数据库中userId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值userId,mobile,type必须上传
	 */
	@ResponseBody
	@RequestMapping(value = "/otherLogin", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<User> otherLogin(String otherBundId){
		if(TextUtils.isEmpty(otherBundId)) return new Result<>("请上传三方登录id");
		boolean result = false;
		try {
			result = userService.otherLogin(otherBundId);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<User>("服务器内部错误:" + e.getMessage());
		}
		if(result){
			User user = userService.getByOtherBundId(otherBundId);
			return new Result<User>(user,"登录成功");
		}
		return new Result<User>("请绑定账号",1);
	}
	/**
	 * 获取空闲工人信息列表
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUsePeoples", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<List<User>> getUsePeoples(int pageNum){
		List<User> peoples = null;
		try {
			peoples = userService.getUsePeoples(pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>("服务器错误");
		}
		return new Result<>(peoples);
	}
}
