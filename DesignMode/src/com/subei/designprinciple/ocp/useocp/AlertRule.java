package com.subei.designprinciple.ocp.useocp;

public class AlertRule {

	private int maxErrorCount;
	
	private int maxTps;

	public int getMaxErrorCount() {
		return maxErrorCount;
	}

	public void setMaxErrorCount(int maxErrorCount) {
		this.maxErrorCount = maxErrorCount;
	}

	public int getMaxTps() {
		return maxTps;
	}

	public void setMaxTps(int maxTps) {
		this.maxTps = maxTps;
	}
}
