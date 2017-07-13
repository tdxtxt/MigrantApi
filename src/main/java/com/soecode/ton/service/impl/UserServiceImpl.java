package com.soecode.ton.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.comm.Constant;
import com.soecode.ton.dao.UserDao;
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
	public User getByName(String userName) {
		return userDao.queryByName(userName);
	}

	@Override
	public User getById(String userId) {
		return userDao.queryById(userId);
	}
	@Override
	public User getByMobile(String mobile) {
		return userDao.queryByMobile(mobile);
	}
	@Override
	public User getByOtherBundId(String otherBundId) {
		return userDao.queryByOtherBundId(otherBundId);
	}
	@Override
	public boolean modifyById(User user) {
		User localUser = userDao.queryById(user.getUserId());
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
		userDao.modifyById(localUser.getUserId(), localUser.getUserName(), 
				localUser.getMobile(), localUser.getAreaId(), localUser.getJobTypeId(), localUser.getSenior());
		return true;
	}
	/**
	 * 数据库中userId,mobile,userName,password,type,createTime绝逼不能为空
	 * 用户上传值mobile,password,type必须上传
	 */
	@Override
	public User addUser(String otherBundId,String userName, String mobile, String password, String type, String areaId, String jobTypeId,
			String senior){
		int count = userDao.countMobile(mobile);
		if(count > 0) return null;
		String userId = UUID.randomUUID().toString();
		int typeValue = Integer.valueOf(type);
		userDao.insertUser(userId,TextUtils.isEmpty(otherBundId) ? "" : otherBundId, TextUtils.isEmpty(userName) ? mobile : userName, mobile, password, typeValue, areaId,
				jobTypeId, senior);
		return userDao.queryById(userId);
	}
	@Override
	public boolean login(String mobile, String password) throws Exception{
		int count = userDao.countMobile(mobile);
		if(count == 0) throw new CustomException("该账户未注册");
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
	public List<User> getUsePeoples(int pageNum)throws Exception{
		if(pageNum < 1) pageNum = 1;
		int start = (pageNum - 1) * Constant.PAGE_SIZE;
		return userDao.getUsePeoples(start,Constant.PAGE_SIZE);
	}
}
