package com.soecode.ton.entity;

import java.util.List;

public class Task {
	private String id;
	private City city;
	private JobType jobType;
	private String recruitNum;
	private String price;
	private String name;
	private String desc;
	private String detailAddress;
	private String releaseTime;
	private User user;
	private List<String> imgs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public JobType getJobType() {
		return jobType;
	}
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	public String getRecruitNum() {
		return recruitNum;
	}
	public void setRecruitNum(String recruitNum) {
		this.recruitNum = recruitNum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getImgs() {
		return imgs;
	}
	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
}
