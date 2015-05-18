package com.bsi.summer.core.bean;

/**
 * 提示状态&内容
 * @author Administrator
 * @date   2012-3-23
 * @category
 */
public class Message {
	private String status="success";
	private String message;
	
	public Message(){}
	
	public Message(String status){
		super();
		this.status = status;
	}
	
	public Message(String message, String status) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
