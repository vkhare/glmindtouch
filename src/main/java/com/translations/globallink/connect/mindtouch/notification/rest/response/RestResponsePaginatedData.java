package com.translations.globallink.connect.mindtouch.notification.rest.response;

public class RestResponsePaginatedData extends RestResponseData {

	private long total;
	private long count;

	public RestResponsePaginatedData(long total, long count, Object[] data) {
		super(data);
		this.total = total;
		this.count = count;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
