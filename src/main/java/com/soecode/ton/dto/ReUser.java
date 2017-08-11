package com.soecode.ton.dto;

public class ReUser {
	private String userId;//用户id
	private String userName;//用户名称
	private int type;//用户类型
	private String mobile;//用户手机
	private String jobTypeName;//工作类型
	private String senior;//熟练度
	private String cityName;//地区name
	private String workyear;//工龄
	private String createTime;//账号创建时间
	private int userState;//状态(0:正在找工作，1:已工作) 
	private String otherBundId;//三方登录绑定id
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
	public String getSenior() {
		return senior;
	}
	public void setSenior(String senior) {
		this.senior = senior;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getJobTypeName() {
		return jobTypeName;
	}
	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}
	public String getWorkyear() {
		return workyear;
	}
	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	public String getOtherBundId() {
		return otherBundId;
	}
	public void setOtherBundId(String otherBundId) {
		this.otherBundId = otherBundId;
	}
}
