package com.subei.designprinciple.ocp.nouseocp;

public class AlertRule {

	private int maxTps;
	private int maxErrorCount;
	
	public int getMaxTps() {
		return maxTps;
	}
	public void setMaxTps(int maxTps) {
		this.maxTps = maxTps;
	}
	public int getMaxErrorCount() {
		return maxErrorCount;
	}
	public void setMaxErrorCount(int maxErrorCount) {
		this.maxErrorCount = maxErrorCount;
	}
	
}
