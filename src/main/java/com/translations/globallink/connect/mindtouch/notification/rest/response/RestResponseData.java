package com.translations.globallink.connect.mindtouch.notification.rest.response;

public class RestResponseData extends RestResponse {

	private Object[] data;

	public RestResponseData(Object[] data) {
		super("200", "OK");
		this.data = data;
	}
	
	public RestResponseData(String status, String message, Object[] data) {
		super(status, message);
		this.data = data;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

}
