package com.soecode.ton.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.comm.Constant;
import com.soecode.ton.dao.UserDao;
import com.soecode.ton.dto.ReUser;
import com.soecode.ton.entity.User;
import com.soecode.ton.exception.CustomException;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	// 注入Service依赖
	@Autowired
	private UserDao userDao;
	
	@Override
	public ReUser getByName(String userName) {
		return userDao.queryByName(userName);
	}

	@Override
	public ReUser getById(String userId) {
		return userDao.queryById(userId);
	}
	@Override
	public ReUser getByMobile(String mobile) {
		return userDao.queryByMobile(mobile);
	}
	@Override
	public ReUser getByOtherBundId(String otherBundId) {
		return userDao.queryByOtherBundId(otherBundId);
	}
	@Override
	public boolean modifyById(User user) {
		User localUser = userDao.queryUserById(user.getUserId());
		if(localUser == null) return false;
		localUser.setUserId(user.getUserId());
		if(!TextUtils.isEmpty(user.getUserName())){
			localUser.setUserName(user.getUserName());
		}
		if(!TextUtils.isEmpty(user.getMobile())){
			localUser.setMobile(user.getMobile());
		}
		if(!TextUtils.isEmpty(user.getAreaId())){
			localUser.setAreaId(user.getAreaId());
		}
		if(!TextUtils.isEmpty(user.getSenior())){
			localUser.setSenior(user.getSenior());
		}
		if(!TextUtils.isEmpty(user.getJobTypeId())){
			localUser.setJobTypeId(user.getJobTypeId());
		}
		if(!TextUtils.isEmpty(user.getWorkyear())){
			localUser.setWorkyear(user.getWorkyear());
		}
		if(user.getUserState() == 0){
			localUser.setUserState(0);
		}else if(user.getUserState() == 1){
			localUser.setUserState(1);
		}
		userDao.modifyById(localUser.getUserId(), localUser.getUserName(), 
				localUser.getMobile(), localUser.getAreaId(), localUser.getJobTypeId(), localUser.getSenior(),localUser.getUserState(),localUser.getWorkyear());
		return true;
	}
	@Override
	public boolean modifyPwdById(String userId, String oldpwd, String newpwd) throws Exception {
		if(!TextUtils.isEmpty(oldpwd)){
			User localUser = userDao.queryUserById(userId);
			if(!oldpwd.equals(localUser.getPassword())){
				throw new CustomException("旧密码不正确");
			}
		}
		userDao.modifyPwdById(userId, newpwd);
		return true;
	}
	/**
	 * 数据库中userId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值mobile,password,type必须上传
	 */
	@Override
	public ReUser addUser(String otherBundId,String userName, String mobile, String password, String type, String areaId, String jobTypeId,
			String senior,String workyear) throws Exception{
		int count = 0;
		try {
			count = userDao.countMobile(mobile);
		} catch (Exception e) {
			throw new Exception("查询出错");
		}
		if(count > 0) return null;
		String userId = UUID.randomUUID().toString();
		int typeValue = Integer.valueOf(type);
		try {
			userDao.insertUser(userId,TextUtils.isEmpty(otherBundId) ? "" : otherBundId, TextUtils.isEmpty(userName) ? mobile : userName, mobile, password, typeValue, areaId,
					jobTypeId, senior,workyear);
		} catch (Exception e) {
			throw new Exception("插入出错");
		}
		ReUser user = null;
		try {
			user = userDao.queryById(userId);
		} catch (Exception e) {
			throw new Exception("查询出错");
		}
		return user;
	}
	@Override
	public boolean login(String mobile, String password,String type) throws Exception{
		int count = userDao.countMobile(mobile);
		if(count == 0) throw new CustomException("该账户未注册");
		if(!TextUtils.isEmpty(type) && ("1".equals(type) || "0".equals(type))){
			int number = userDao.countMobileAndType(mobile, type);//0:农民工，1：包工头
			if(number == 0){
				throw new CustomException("0".equals(type) ? "该号码已注册为包工头" : "该号码已注册为农民工");
			}
		}
		String pwd = userDao.queryPwdByMobile(mobile);
		if(password.equals(pwd)){
			return true;
		}
		return false;
	}
	/**
	 * 三方登录
	 */
	@Override
	public boolean otherLogin(String otherBundId) throws Exception {
		int count = userDao.countOtherBundId(otherBundId);
		if(count > 0) return true;
		return false;
	}

	@Override
	public List<ReUser> findPeoples(String jobTypeId,String userState,int pageNum)throws Exception{
		if(pageNum < 1) pageNum = 1;
		int start = (pageNum - 1) * Constant.PAGE_SIZE;
		if(!TextUtils.isEmpty(jobTypeId)){
			if(!TextUtils.isEmpty(userState)){
				return userDao.getJobAndUsePeoples(userState, jobTypeId, start, Constant.PAGE_SIZE);
			}else{
				return userDao.getJobPeoples(jobTypeId, start, Constant.PAGE_SIZE);
			}
		}else{
			if (!TextUtils.isEmpty(userState)) {
				return userDao.getUsePeoples(userState, start, Constant.PAGE_SIZE);
			} else {
				return userDao.getAllPeoples(start, Constant.PAGE_SIZE);
			}
		}
	}
}
