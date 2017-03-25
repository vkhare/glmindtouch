package com.translations.globallink.connect.mindtouch.notification;

public interface GloballinkConstants {

	String SUB_SEARCH_BY_STATUS = "status";
	String SUB_STATUS_CANCELLED = "CANCELLED";
	String SUB_STATUS_CREATING = "CREATING";
	String SUB_STATUS_DELIVERED = "DELIVERED";
	String SUB_STATUS_IN_PROCESS = "IN_PROCESS";
	String SUB_STATUS_PROCESSED = "PROCESSED";
	String SUB_STATUS_PROCESSING = "PROCESSING";
	String SUB_STATUS_READY = "READY";
	String SUB_STATUS_STOPPED = "STOPPED";
	String SUB_STATUS_WAITING = "WAITING";

	public static final String PD_ENCODING = "UTF-8";
	public static final String PD_MIME_TYPE = "text/xml";
	public static final long PD_TIMEOUT = 300000;
	public static final boolean PD_ENABLE_MTOM = true;
	
	public static final long PD_CACHE_EXPIRY_TIME = 120; 
}
