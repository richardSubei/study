package com.subei.designprinciple.ocp.useocp;

public class ApiStatInfo {

	private int requestCount;
	
	private int errorCount;

	public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	
}
