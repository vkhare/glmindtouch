package com.translations.globallink.connect.mindtouch.notification.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExportNotificationRequest {

	@JsonProperty("@id")
	private String id;
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@status")
	private String status;
	private String site;
	private String uri;
	private Log log;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public ExportNotificationRequest(String id, String type, String status, String site, String uri, Log log) {
		this.id = id;
		this.type = type;
		this.status = status;
		this.site = site;
		this.uri = uri;
		this.log = log;
	}

	public ExportNotificationRequest() {
	}

	@Override
	public String toString() {
		return "ExportNotificationRequest [id=" + id + ", type=" + type + ", status=" + status + ", site=" + site + ", uri=" + uri + ", log=" + log + "]";
	}

}
