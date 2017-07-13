package com.soecode.ton.dao;

public interface TaskImgDao {
	public void insert(String taskId,String url);
	public int deleteByTaskId(String taskId);
	public void deleteById(String id);
}
