package com.soecode.ton.dto;

import com.soecode.ton.entity.City;
import com.soecode.ton.entity.JobType;

public class ReUser {
	private String userId;//用户id
	private String userName;//用户名称
	private int type;//用户类型
	private String mobile;//用户手机
	private JobType jobType;//工作类型
	private String senior;//工龄
	private City city;//地区id
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public JobType getJobType() {
		return jobType;
	}
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	public String getSenior() {
		return senior;
	}
	public void setSenior(String senior) {
		this.senior = senior;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
}
