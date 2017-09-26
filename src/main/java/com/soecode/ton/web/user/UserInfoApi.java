package com.soecode.ton.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soecode.ton.dto.ReUser;
import com.soecode.ton.dto.Result;
import com.soecode.ton.entity.User;
import com.soecode.ton.exception.CustomException;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.PushDeviceService;
import com.soecode.ton.service.UserService;
import com.soecode.ton.web.BaseController;

@Controller
@RequestMapping("/u") // url:/u/资源 
public class UserInfoApi extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private PushDeviceService pushService;
	@ResponseBody
	@RequestMapping(value = "/userInformation", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<ReUser> getUserByName(String userName,String userId) {
		if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userId)){
			return new Result<ReUser>("传参为空");
		}
		if(!TextUtils.isEmpty(userId)){
			ReUser user = userService.getById(userId);
			if(null == user){
				return new Result<ReUser>("不存在该用户");
			}else{
				return new Result<ReUser>(user);
			}
		}
		
		if(!TextUtils.isEmpty(userName)){
			ReUser user = userService.getByName(userName);
			if(null == user){
				return new Result<ReUser>("不存在该用户");
			}else{
				return new Result<ReUser>(user);
			}
		}
		
		return new Result<ReUser>("不存在该用户");
	}
	@ResponseBody
	@RequestMapping(value = "/modify", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<ReUser> modify(String userId,String userName,String mobile,String areaId,String jobTypeId,String senior,String userState,String workyear){
		User user = new User();
		user.setAreaId(areaId);
		user.setJobTypeId(jobTypeId);
		user.setMobile(mobile);
		user.setSenior(senior);
		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserState(TextUtils.getInteger(userState));
		user.setWorkyear(workyear);
		
		boolean success = userService.modifyById(user);
		if(success){
			ReUser newUser = userService.getById(userId);
			return new Result<ReUser>(newUser,"修改成功");
		}else{
			return new Result<ReUser>("userId有误");
		}
	}
	/**
	 * 修改密码
	 * @param userId
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyPwd", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<String> modifyPwd(String userId,String newpwd,String oldpwd){
		if(TextUtils.isEmpty(userId)) return new Result<>(false,"修改账号userid为空");
		if(TextUtils.isEmpty(newpwd)) return new Result<>(false,"新密码为空");
		boolean isOk = false;
		try {
			isOk = userService.modifyPwdById(userId, oldpwd, newpwd);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(false,e instanceof CustomException ? e.getMessage() : "修改失败:" + e.getMessage());
		}
		return new Result<>(isOk,isOk ? "修改成功" : "修改失败");
	}
	/**
	 * 数据库中userId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值mobile,password,type必须上传
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<ReUser> register(String userName,String password,String type,String mobile,String areaId,String jobTypeId,String senior,String workyear){
		if(TextUtils.isEmpty(mobile)) return new Result<>("请输入手机号");
		if(TextUtils.isEmpty(password)) return new Result<>("请输入登录密码");
		if(!(!TextUtils.isEmpty(type) && ("0".equals(type) || "1".equals(type)))){
			return new Result<>("注册用户类型格式错误,必须为0或1");
		}
		ReUser user = null;
		try {
			user = userService.addUser("",userName, mobile, password, type, areaId, jobTypeId, senior,workyear);
		} catch (Exception e) {
			return new Result<ReUser>(e.getMessage());
		}
		if(user == null) return new Result<>("该手机号已注册");
		return new Result<ReUser>(user,"注册成功");
	}
	/**
	 * 数据库中otherBundId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值otherBundId,mobile,type必须上传
	 */
	@ResponseBody
	@RequestMapping(value = "/bundOtherLogin", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<ReUser> bundOtherLogin(String otherBundId,String type,String mobile,String areaId,String jobTypeId,String senior,String workyear){
		if(TextUtils.isEmpty(otherBundId)) return new Result<>("请上传三方登录id");
		if(TextUtils.isEmpty(mobile)) return new Result<>("请输入手机号");
		if(!(!TextUtils.isEmpty(type) && ("0".equals(type) || "1".equals(type)))){
			return new Result<>("注册用户类型格式错误,必须为0或1");
		}
		ReUser user = null;
		try {
			user = userService.addUser(otherBundId, mobile, mobile, "123456", type, areaId, jobTypeId, senior, workyear);
		} catch (Exception e) {
			return new Result<ReUser>(e.getMessage());
		}
		if(user == null) return new Result<>("该手机号已绑定");
		return new Result<ReUser>(user,"绑定成功");
	}
	@ResponseBody
	@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<ReUser> login(String mobile,String password,String type,String pushToken){
		if(TextUtils.isEmpty(mobile)) return new Result<>("请输入手机号");
		if(TextUtils.isEmpty(password)) return new Result<>("请输入登录密码");
		boolean success = false;
		try {
			success = userService.login(mobile, password,type);
		} catch (Exception e) {
			return new Result<ReUser>(e instanceof CustomException ? e.getMessage() : "登陆失败:" + e.getMessage());
		}
		if(success){
			ReUser user = userService.getByMobile(mobile);
			if(!TextUtils.isEmpty(pushToken) && "0".equals(type)) pushService.saveToken(user.getUserId(), mobile, pushToken);
			return new Result<ReUser>(user,"登录成功");
		}
		return new Result<ReUser>("登录密码不正确");
	}
	@ResponseBody
	@RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<String> logout(String pushToken){
		if(TextUtils.isEmpty(pushToken)) return new Result<>("参数不能为空");
		try {
			pushService.deleteToken(pushToken);
		} catch (Exception e) {
			return new Result<>(true,"服务器内部错误");
		}
		return new Result<>(true,"退出成功");
	}
	/**
	 * 数据库中userId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值userId,mobile,type必须上传
	 */
	@ResponseBody
	@RequestMapping(value = "/otherLogin", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<ReUser> otherLogin(String otherBundId){
		if(TextUtils.isEmpty(otherBundId)) return new Result<>("请上传三方登录id");
		boolean result = false;
		try {
			result = userService.otherLogin(otherBundId);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<ReUser>("服务器内部错误:" + e.getMessage());
		}
		if(result){
			ReUser user = userService.getByOtherBundId(otherBundId);
			return new Result<ReUser>(user,"登录成功");
		}
		return new Result<ReUser>("请绑定账号",1);
	}
	/**
	 * 获取空闲工人信息列表
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPeoples", method = {RequestMethod.POST, RequestMethod.GET})
	private Result<List<ReUser>> getUsePeoples(int pageNum,String jobTypeId,String userState){
		List<ReUser> peoples = null;
		try {
			peoples = userService.findPeoples(jobTypeId,userState,pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>("服务器错误");
		}
		return new Result<>(peoples);
	}
}
