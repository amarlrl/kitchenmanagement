package com.smartkitchen.kitchenmanagement.responsemodel;

public class Response {

	String message;
	public Response() {
		
	}
	
	public Response(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
