package com.translations.globallink.connect.mindtouch.notification.rest.dto;

public class Csv {
	private String uri;

	public Csv(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Csv() {

	}

	@Override
	public String toString() {
		return "Csv [uri=" + uri + "]";
	}

}
