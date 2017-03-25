package com.translations.globallink.connect.mindtouch.notification.rest.dto;

public class Log {

	private Csv csv;

	public Log(Csv csv) {
		this.csv = csv;
	}

	public Csv getCsv() {
		return csv;
	}

	public void setCsv(Csv csv) {
		this.csv = csv;
	}

	public Log() {
		
	}

	@Override
	public String toString() {
		return "Log [csv=" + csv + "]";
	}
	
	
}
