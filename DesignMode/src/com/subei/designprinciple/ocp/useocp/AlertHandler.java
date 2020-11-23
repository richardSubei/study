package com.subei.designprinciple.ocp.useocp;

public abstract class AlertHandler {
	
	protected AlertRule alertRule;
	protected Notification notification;
	
	public AlertHandler(AlertRule alertRule, Notification notification) {
		this.alertRule = alertRule;
		this.notification = notification;
	}

	public abstract void check(ApiStatInfo apiStatInfo);
	
}
