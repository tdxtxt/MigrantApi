package com.soecode.ton.dto;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result<T> {

	private boolean success;// 是否成功标志

	private T data;// 成功时返回的数据

	private String msg;// 错误信息
	
	private int status;//状态值【0:成功;非0:不成功;1:三方登录失败，先绑定账号】

	public Result() {
	}

	// 成功时的构造器
	public Result(T data) {
		this.success = true;
		this.data = data;
		this.status = 0;
	}

	// 成功时的构造器
	public Result(T data, String msg) {
		this.success = true;
		this.data = data;
		this.msg = msg;
		this.status = 0;
	}
	public Result(T data,String msg,int status) {
		this.success = true;
		this.data = data;
		this.msg = msg;
		this.status = status;
	}
	public Result(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
		this.status = success ? 0 : -1;
	}
	// 错误时的构造器
	public Result(String msg) {
		this.success = false;
		this.msg = msg;
		this.status = -1;
	}
	public Result(String msg,int status) {
		this.success = false;
		this.msg = msg;
		this.status = status;
	}

	public Result(boolean success, T data, String msg) {
		this.success = success;
		this.data = data;
		this.msg = msg;
		this.status = success ? 0 : -1;
	}
	
	public Result(boolean success, T data, String msg,int status) {
		this.success = success;
		this.data = data;
		this.msg = msg;
		this.status = status;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "JsonResult [success=" + success + ", data=" + data + ", msg=" + msg + "]";
	}

}
