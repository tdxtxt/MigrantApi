package com.soecode.ton.entity;

public class User {
	private String userId;//用户id
	private String otherBundId;//三方登录绑定id
	private String userName;//用户名称
	private String password;//用户密码
	private int userState;//状态(0:正在找工作，1:已工作) 
	private String createTime;//账号创建时间
	private int type;//用户类型
	private String mobile;//用户手机
	private String jobTypeId;//工作类型id
	private String senior;//熟练度
	private String areaId;//地区id
	private String workyear;//工龄
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOtherBundId() {
		return otherBundId;
	}
	public void setOtherBundId(String otherBundId) {
		this.otherBundId = otherBundId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getJobTypeId() {
		return jobTypeId;
	}
	public void setJobTypeId(String jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public String getSenior() {
		return senior;
	}
	public void setSenior(String senior) {
		this.senior = senior;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getWorkyear() {
		return workyear;
	}
	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}
}
