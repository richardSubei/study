package com.subei.designprinciple.ocp.useocp;

public abstract class AlertHandler {

	AlertRule alertRule;
	Notification notification;
	
	public abstract void check(ApiStatInfo apiStatInfo);
}
